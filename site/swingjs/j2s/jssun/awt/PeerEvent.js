Clazz.declarePackage ("jssun.awt");
Clazz.load (["java.awt.event.InvocationEvent"], "jssun.awt.PeerEvent", null, function () {
c$ = Clazz.decorateAsClass (function () {
this.flags = 0;
Clazz.instantialize (this, arguments);
}, jssun.awt, "PeerEvent", java.awt.event.InvocationEvent);
Clazz.makeConstructor (c$, 
function (source, runnable, flags) {
this.construct (source, runnable, null, false, flags);
}, "~O,Runnable,~N");
Clazz.makeConstructor (c$, 
function (source, runnable, notifier, catchExceptions, flags) {
Clazz.superConstructor (this, jssun.awt.PeerEvent, [source, runnable, notifier, catchExceptions]);
this.flags = flags;
}, "~O,Runnable,~O,~B,~N");
Clazz.defineMethod (c$, "getFlags", 
function () {
return this.flags;
});
Clazz.defineMethod (c$, "coalesceEvents", 
function (newEvent) {
return null;
}, "jssun.awt.PeerEvent");
Clazz.defineStatics (c$,
"PRIORITY_EVENT", 0x01,
"ULTIMATE_PRIORITY_EVENT", 0x02,
"LOW_PRIORITY_EVENT", 0x04);
});
