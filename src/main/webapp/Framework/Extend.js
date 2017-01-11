extend=function() {
	 var io = function(o) {
		 for (var m in o) {
			 this[m] = o[m]
		 }
	 };
	 var oc = Object.prototype.constructor;
	 return function(sb, sp, overrides) {
		 if (isObject(sp)) {
			 overrides = sp;
			 sp = sb;
			 sb = overrides.constructor != oc ? overrides.constructor: function() {
				 sp.apply(this, arguments)
			 }
		 }
		 var F = function() {},
		 sbp,
		 spp = sp.prototype;
		 F.prototype = spp;
		 sbp = sb.prototype = new F();
		 sbp.constructor = sb;
		 sb.superclass = spp;
		 if (spp.constructor == oc) {
			 spp.constructor = sp
		 }
		 sb.override = function(o) {
			 override(sb, o)
		 };
		 sbp.superclass = sbp.supr = (function() {
			 return spp
		 });
		 sbp.override = io;
		 override(sb, overrides);
		 sb.extend = function(o) {
			 return extend(sb, o)
		 };
		 return sb
	 }
 } ();

override= function(origclass, overrides) {
	 if (overrides) {
		 var p = origclass.prototype;
		 apply(p, overrides);
		 if (isIE && overrides.hasOwnProperty('toString')) {
			 p.toString = overrides.toString
		 }
	 }
 };
