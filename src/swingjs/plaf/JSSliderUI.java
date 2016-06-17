package swingjs.plaf;


import javax.swing.SwingConstants;

import jsjava.awt.Dimension;
import jsjava.beans.PropertyChangeEvent;
import jsjava.beans.PropertyChangeListener;
import jsjavax.swing.JSlider;
import jsjavax.swing.event.ChangeEvent;
import jsjavax.swing.event.ChangeListener;
import swingjs.J2SRequireImport;
import swingjs.JSToolkit;
import swingjs.api.DOMNode;

@J2SRequireImport(swingjs.jquery.JQueryUI.class)
public class JSSliderUI extends JSLightweightUI implements PropertyChangeListener, ChangeListener {

	private JSlider jSlider;
	private int min, max, val;
	private String orientation;
	
	protected DOMNode jqSlider;
	private int z0 = Integer.MIN_VALUE;

	public JSSliderUI() {
		needPreferred = true;
		setDoc();
	}

	static {		
		JSToolkit.getJavaResource("swingjs/jquery/jquery-ui-slider.css", true);
		JSToolkit.getJavaResource("swingjs/jquery/jquery-ui-slider.js", true);
	}
	@Override
	public DOMNode createDOMNode() {
		JSlider js  = jSlider = (JSlider) c;
		boolean isNew = (domNode == null);
		if (isNew) {
			domNode = wrap("div", id + "_wrap", jqSlider = DOMNode.createElement("div", id));
			$(domNode).addClass("swingjs");
			orientation = (js.getOrientation() == SwingConstants.VERTICAL ? "vertical" : "horizontal");
			min = js.getMinimum();
			max = js.getMaximum();
			val = js.getValue();
					

			/**	
			 * @j2sNative
			 *
    var me = this;
    me.$(me.jqSlider).slider({
      orientation: me.orientation,
      range: false,
      min: me.min,
      max: me.max,
      value: me.val,
      change: function( event, handle ) {
      	me.jqueryCallback(event, handle);
      },
      slide: function( event, handle ) {
      	me.jqueryCallback(event, handle);
      }
    });
    
			 */
			{}
			
		}
		setZ(isNew);
    return domNode;
	}

	/**
	 * 
	 * @param isNew
	 */
	private void setZ(boolean isNew) {
		int z = getZIndex(null);
		if (z == z0)
			return;
		z0 = z;
		System.out.println("JSSliderUI setting z to " + z);
		DOMNode sliderTrack = null;
		DOMNode sliderHandle = null;
		/**
		 * @j2sNative
		 * 
		 * sliderTrack = this.domNode.firstChild;
		 * sliderHandle = sliderTrack.firstChild;
		 * sliderTrack.style["z-index"] = z++;
		 * sliderHandle.style["z-index"] = z++;
		 */
		{}
		// mark the handle and track with the "swingjs-ui" class
		// so as to ignore all mouse/touch events from Jmol._jsSetMouse();
		if (isNew) {
			$(sliderHandle).addClass("swingjs-ui");
			$(sliderTrack).addClass("swingjs-ui");
		}
	}

	/**
	 * called from JavaScript via the hook added in getDOMObject  
	 * 
	 * @param event
	 * @param ui
	 */
	public void jqueryCallback(Object event, DOMNode ui) {
		// from JavaScript
		int value = 0;
		
		/**
		 * @j2sNative
		 * 
		 * value = ui.value;
		 * 
		 */
		{}
		
		jSlider.setValue(val = value);
	}
	
	@Override
	protected Dimension setHTMLSize(DOMNode obj, boolean addCSS) {
		return (orientation == "horizontal" ? new Dimension(100, 20) : new Dimension(20, 100));
	}

	@Override
	protected void installJSUI() {
	  jSlider.addChangeListener(this);
	  jSlider.addPropertyChangeListener(this);
	}

	@Override
	protected void uninstallJSUI() {
	  jSlider.removeChangeListener(this);
	  jSlider.removePropertyChangeListener(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		String prop = e.getPropertyName();
		System.out.println(id + " propertyChange " + prop);
		if (prop == "ancestor")
			setZ(false);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// from Java
		int v;
		if ((v = jSlider.getMinimum()) != min)
			setSliderAttr("min", min = v);
		if ((v = jSlider.getMaximum()) != max)
			setSliderAttr("max", max = v);		
		if ((v = jSlider.getValue()) != val)
			setSliderAttr("value", val = v);		
		setZ(false);
	}

	private void setSliderAttr(String key, int val) {
		System.out.println(id + " setting " + key + " = " + val);
		/**
		 * @j2sNative
		 * 
		 *  var a = {};
		 *  a[key]= val;
		 *  this.$(this.jqSlider).slider(a);
		 */
		{}
	}

}
