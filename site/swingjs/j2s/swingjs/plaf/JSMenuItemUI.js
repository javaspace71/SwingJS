Clazz.declarePackage ("swingjs.plaf");
Clazz.load (["swingjs.plaf.JSButtonUI"], "swingjs.plaf.JSMenuItemUI", ["javax.swing.LookAndFeel", "$.UIManager", "javax.swing.plaf.UIResource", "swingjs.api.DOMNode", "swingjs.plaf.JSButtonListener"], function () {
c$ = Clazz.decorateAsClass (function () {
this.$domBtn = null;
this.$shiftOffset = 0;
this.$defaultTextShiftOffset = 0;
Clazz.instantialize (this, arguments);
}, swingjs.plaf, "JSMenuItemUI", swingjs.plaf.JSButtonUI);
Clazz.overrideMethod (c$, "getDOMObject", 
function () {
if (this.domNode == null) this.$domBtn = this.enableNode = this.valueNode = this.domNode = this.createDOMObject ("input", this.id, ["type", "button"]);
this.setCssFont (swingjs.api.DOMNode.setAttr (this.domNode, "value", (this.c).getText ()), this.c.getFont ());
return this.domNode;
});
Clazz.overrideMethod (c$, "installJSUI", 
function () {
this.installDefaults (this.c);
this.installListeners (this.c);
this.installKeyboardActions (this.c);
});
Clazz.overrideMethod (c$, "uninstallJSUI", 
function () {
this.uninstallKeyboardActions (this.c);
this.uninstallListeners (this.c);
});
Clazz.overrideMethod (c$, "installListeners", 
function (b) {
var listener =  new swingjs.plaf.JSButtonListener (b);
if (listener != null) {
b.addMouseListener (listener);
b.addMouseMotionListener (listener);
b.addFocusListener (listener);
b.addPropertyChangeListener (listener);
b.addChangeListener (listener);
}}, "javax.swing.AbstractButton");
Clazz.overrideMethod (c$, "uninstallListeners", 
function (b) {
var listener = this.getButtonListener (b);
if (listener != null) {
b.removeMouseListener (listener);
b.removeMouseMotionListener (listener);
b.removeFocusListener (listener);
b.removeChangeListener (listener);
b.removePropertyChangeListener (listener);
}}, "javax.swing.AbstractButton");
Clazz.overrideMethod (c$, "installKeyboardActions", 
function (b) {
var listener = this.getButtonListener (b);
if (listener != null) {
listener.installKeyboardActions (b);
}}, "javax.swing.AbstractButton");
Clazz.overrideMethod (c$, "uninstallKeyboardActions", 
function (b) {
var listener = this.getButtonListener (b);
if (listener != null) {
listener.uninstallKeyboardActions (b);
}}, "javax.swing.AbstractButton");
Clazz.overrideMethod (c$, "getPropertyPrefix", 
function () {
return "Button.";
});
Clazz.overrideMethod (c$, "installDefaults", 
function (b) {
var pp = this.getPropertyPrefix ();
this.$defaultTextShiftOffset = javax.swing.UIManager.getInt (pp + "textShiftOffset");
if (b.getMargin () == null || (Clazz.instanceOf (b.getMargin (), javax.swing.plaf.UIResource))) {
b.setMargin (javax.swing.UIManager.getInsets (pp + "margin"));
}javax.swing.LookAndFeel.installColorsAndFont (b, pp + "background", pp + "foreground", pp + "font");
javax.swing.LookAndFeel.installProperty (b, "iconTextGap",  new Integer (4));
}, "javax.swing.AbstractButton");
});
