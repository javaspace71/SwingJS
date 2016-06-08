// package.js from Jmol project -- template only here for Swingjs

// NOTE: Any changes here must also be reflected in build xml
// NOTE: this code does not allow for multiple J2S applications on a page

if (!window["java.registered"])
 window["java.registered"] = false;

(function (ClazzLoader) {

	if (window["java.packaged"]) return;
	window["java.packaged"] = true;

	//if (!Jmol._isAsync) {
		for (var i = 0; i < Jmol._coreFiles.length; i++)
		  ClazzLoader.loadZJar(Jmol._coreFiles[i], ClazzLoader.runtimeKeyClass);
	//}
		
ClazzLoader.jarClasspath (ClazzLoader.getJ2SLibBase() + "jsjava/awt/geom/Point2D.js", [
  "jsjava.awt.geom.Point2D", 
  "jsjava.awt.geom.Point2D.Double", 
  "jsjava.awt.geom.Point2D.Float"  
	]);

ClazzLoader.jarClasspath (ClazzLoader.getJ2SLibBase() + "jssun/awt/SunHints.js", [
  "jssun.awt.SunHints", 
  "jssun.awt.SunHints.Value", 
  "jssun.awt.SunHints.Key", 
  "jssun.awt.SunHints.LCDContrastKey",
  "jssun.awt.SunHints.SunKey" 
	]);

ClazzLoader.jarClasspath (ClazzLoader.getJ2SLibBase() + "jsjavax/swing/text/AbstractDocument.js", [
  "jsjavax.swing.text.AbstractDocument", 
  "jsjavax.swing.text.AbstractDocument.UndoRedoDocumentEvent" 
	]);

ClazzLoader.jarClasspath (ClazzLoader.getJ2SLibBase() + "jsjavax/swing/UIDefaults.js", [
  "jsjavax.swing.UIDefaults",
  "jsjavax.swing.UIDefaults.ActiveValue",
  "jsjavax.swing.UIDefaults.LazyValue"
	]);

ClazzLoader.jarClasspath (ClazzLoader.getJ2SLibBase() + "jsjavax/swing/Popup.js", [
  "jsjavax.swing.Popup", 
  "jsjavax.swing.Popup.DefaultFrame",
  "jsjavax.swing.Popup.HeavyWeightWindow" 
	]);

ClazzLoader.jarClasspath (ClazzLoader.getJ2SLibBase() + "jsjavax/swing/text/LayeredHighlighter.js", [
  "jsjavax.swing.text.LayeredHighlighter", 
  "jsjavax.swing.text.LayeredHighlighter.LayerPainter" 
	]);

ClazzLoader.jarClasspath (ClazzLoader.getJ2SLibBase() + "jsjavax/swing/JComponent.js", [
  "jsjavax.swing.JComponent", 
  "jsjavax.swing.JComponent.KeyboardState", 
  "jsjavax.swing.JComponent.ActionStandin", 
  "jsjavax.swing.JComponent.IntVector" 
	]);

ClazzLoader.jarClasspath (ClazzLoader.getJ2SLibBase() + "jssun/util/resources/LocaleData.js", [
  "jssun.util.resources.LocaleData", 
  "jssun.util.resources.LocaleDataResourceBundleControl"
	]);

ClazzLoader.jarClasspath (ClazzLoader.getJ2SLibBase() + "jsjava/text/DateFormat.js", [
  "jsjava.text.DateFormat", 
  "jsjava.text.DateFormat.Field"
	]);

  if (Jmol._debugCode)
    return;

	var	base = ClazzLoader.getJ2SLibBase() + "core/";

// note - we don't need to list ALL the classes -- only the ones that are entry points.
// several more classe are in each of these files -- see build_03_tojs.xml

/*
	ClazzLoader.jarClasspath (base + "coretext.z.js",	[    
	"JM.Object2d",
	"$.Text",
	"J.shape.Object2dShape",
	"$.TextShape",
	"$.Labels",
	"$.Measures",
	"$.Echo",
	"$.Hover",
	"J.render.TextRenderer",
	"$.LabelsRenderer",
	"$.MeasuresRenderer",
	"$.EchoRenderer",
	"$.HoverRenderer"	
	]);
   ...
*/

}) (Clazz._Loader);
window["java.registered"] = true;
