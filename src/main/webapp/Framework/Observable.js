Observable = function() {
 this.initObservable=function(){
	 var me = this, e = me.events;
	 me.events = e || {}
 }
 this.filterOptRe= /^(?:scope|delay|buffer|single)$/;
 this.fireEvent= function() {
	 var a = Array.prototype.slice.call(arguments),
	 ename = a[0].toLowerCase(),
	 me = this,
	 ret = true,
	 ce = me.events[ename],
	 q,
	 c;
	 if (me.eventsSuspended === true) {
		 if (q = me.eventQueue) {
			 q.push(a)
		 }
	 } else if (isObject(ce) && ce.bubble) {
		 if (ce.fire.apply(ce, a.slice(1)) === false) {
			 return false
		 }
		 c = me.getBubbleTarget && me.getBubbleTarget();
		 if (c && c.enableBubble) {
			 if (!c.events[ename] || !isObject(c.events[ename]) || !c.events[ename].bubble) {
				 c.enableBubble(ename)
			 }
			 return c.fireEvent.apply(c, a)
		 }
	 } else {
		 if (isObject(ce)) {
			 a.shift();
			 ret = ce.fire.apply(ce, a)
		 }
	 }
	 return ret
 };
 this.addListener= function(eventName, fn, scope, o) {
	 var me = this, e, oe, isF, ce;
	 if (isObject(eventName)) {
		 o = eventName;
		 for (e in o) {
			 oe = o[e];
			 if (!me.filterOptRe.test(e)) {
				 me.addListener(e, oe.fn || oe, oe.scope || o.scope, oe.fn ? oe: o)
			 }
		 }
	 } else {
		 eventName = eventName.toLowerCase();
		 ce = me.events[eventName] || true;
		 if (typeof(ce) == 'boolean') {
			 me.events[eventName] = ce = new Util.Event(me, eventName)
		 }
		 ce.addListener(fn, scope, isObject(o) ? o: {})
	 }
 };
 this.removeListener= function(eventName, fn, scope) {
	 var ce = this.events[eventName.toLowerCase()];
	 if (isObject(ce)) {
		 ce.removeListener(fn, scope)
	 }
 };
 this.purgeListeners= function() {
	 var events = this.events,
	 evt, key;
	 for (key in events) {
		 evt = events[key];
		 if (isObject(evt)) {
			 evt.clearListeners()
		 }
	 }
 };
 this.addEvents= function(o) {
	 var me = this;
	 me.events = me.events || {};
	 if (typeof(o)=='string') {
		 var a = arguments,
		 i = a.length;
		 while (i--) {
			 me.events[a[i]] = me.events[a[i]] || true
		 }
	 } else {
		 extraIf(me.events, o)
	 }
	 if (me.listeners) {
		 me.on(me.listeners);
		 delete me.listeners
	 }
 };
 this.hasListener= function(eventName) {
	 var e = this.events[eventName];
	 return isObject(e) && e.listeners.length > 0
 };
 this.suspendEvents= function(queueSuspended) {
	 this.eventsSuspended = true;
	 if (queueSuspended && !this.eventQueue) {
		 this.eventQueue = []
	 }
 };
 this.resumeEvents= function() {
	 var me = this,
	 queued = me.eventQueue || [];
	 me.eventsSuspended = false;
	 delete me.eventQueue;
	 queued.each(
	 function(e) {
		 me.fireEvent.apply(me, e)
	 })
 };
 this.on=this.addListener;
 this.un=this.removeListener;
 Observable.releaseCapture = function(o) {
	 o.fireEvent = this.fireEvent
  };
 this.initObservable();
};

function createTargeted(h, o, scope) {
 return function() {
     if (o.target == arguments[0]) {
         h.apply(scope, Array.prototype.slice.call(arguments))
     }
 }
};

function createBuffered(h, o, l, scope) {
 l.task = new Util.DelayedTask();
 return function() {
     l.task.delay(o.buffer, h, scope, Array.prototype.slice.call(arguments))
 }
};

function createSingle(h, e, fn, scope) {
 return function() {
     e.removeListener(fn, scope);
     return h.apply(scope, arguments)
 }
};

function createDelayed(h, o, l, scope) {
 return function() {
     var task = new Util.DelayedTask();
     if (!l.tasks) {
         l.tasks = []
     }
     l.tasks.push(task);
     task.delay(o.delay || 10, h, scope, Array.prototype.slice.call(arguments))
 }
};