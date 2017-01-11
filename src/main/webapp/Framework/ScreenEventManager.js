/***
ScreenEventManager
协调在当前屏幕内的事件，使mousedown mousemove等事件跨iframe传递。
典型应用如：在有menu控件处于显示状态时，在当前屏幕内任意一个iframe内点击，都应该去调用menu组件的onMouseDown方法，以隐藏menu控件。
**/
