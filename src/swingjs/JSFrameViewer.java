package swingjs;

import jsjava.awt.Container;
import jsjava.awt.Graphics;
import jsjava.awt.Insets;
import jsjavax.swing.JApplet;
import jsjavax.swing.JRootPane;
import swingjs.api.DOMNode;
import swingjs.api.HTML5Applet;
import swingjs.api.HTML5Canvas;
import swingjs.api.JSInterface;
import swingjs.plaf.JSComponentUI;

/**
 * JSJavaViewer 
 * 
 * SwingJS class to support an independent Window, either from using Main() 
 * or one created from a JApplet. Each viewer has an independent mouse event processor. 
 *
 * This "Panel" is never viewed.
 * 
 * @author Bob Hanson
 * 
 */
public class JSFrameViewer implements JSInterface {

	protected JSGraphics2D jsgraphics;

	public String fullName = "Main";

	public Container top; // null for a standard applet

	public JSAppletViewer appletViewer;
	public boolean isApplet, isFrame;	
	public HTML5Applet html5Applet;

	protected Insets insets;

	public Insets getInsets() {
		return insets;
	}
	
	public JSFrameViewer setForWindow(Container window) {
		isFrame = true;
		appletViewer = window.appletViewer;
		this.top = window;
		applet = window;
		window.frameViewer = this;
		this.fullName = appletViewer.fullName;
		canvas = null;
		jsgraphics = null;
		insets = new Insets(30, 0, 0, 0);
		getGraphics(0, 0);
		return this;
	}
	
	
	public Container getTop() {
		return top;
	}
	
	public Object display;
	
	public Container applet;  // really just for JSmolCore 
	public JApplet japplet;
	              // SwingJS core library uses.

	protected JSMouse mouse;

	public HTML5Canvas canvas;

  // ///////// javajs.api.JSInterface ///////////
	//
	// methods called by page JavaScript
	//
	//

	@Override
	public int cacheFileByName(String fileName, boolean isAdd) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void cachePut(String key, Object data) {
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public String getFullName() {
		return fullName;
	}

	@Override
	public void openFileAsyncSpecial(String fileName, int flags) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean processMouseEvent(int id, int x, int y, int modifiers,
			long time, Object jqevent) {
		getMouse().processEvent(id, x, y, modifiers, time, jqevent);
		return false;
	}

	private JSMouse getMouse() {	
		return (mouse == null ? mouse = new JSMouse(this) : mouse);
	}

	@Override
	public void processTwoPointGesture(float[][][] touches) {
		getMouse().processTwoPointGesture(touches);
	}

	/** 
	 * Page can define a canvas to use or to clear it with null
	 */
	@Override
	public void setDisplay(HTML5Canvas canvas) {
		this.canvas = canvas;
		jsgraphics = null;
	}

	@Override
	public void setScreenDimension(int width, int height) {
		setGraphics(jsgraphics = null, width, height);
		//resize(width, height);
		if (top != null)
			top.resize(width, height);
	}

	/**
	 * SwingJS will deliver a null graphics here.
	 * 
	 * @param g
	 * @return
	 */
	protected Graphics setGraphics(Graphics g, int width, int height) {
		return (g == null ? getGraphics(width, height) : g);
	}
		

	@Override
	public boolean setStatusDragDropped(int mode, int x, int y, String fileName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void startHoverWatcher(boolean enable) {
		// TODO Auto-generated method stub

	}
	
	
	public String frameID;

	private String canvasId;

	private static int canvasCount;

	public Graphics getGraphics(int wNew, int hNew) {
		if (wNew == 0 && top != null) {
			wNew = Math.max(0, top.getWidth() - insets.left - insets.right);
			hNew = Math.max(0, top.getHeight() - insets.top - insets.bottom);
		}
		int wOld = 0, hOld = 0;
		/**
		 * @j2sNative
		 * 
		 *            wOld = (this.canvas == null ? 0 : this.canvas.width); hOld =
		 *            (this.canvas == null ? 0 : this.canvas.height)
		 * 
		 */
		{
		}
		if (wNew >= 0
				&& hNew >= 0
				&& (wOld != wNew || hOld != hNew || canvas == null || jsgraphics == null)) {
			jsgraphics = new JSGraphics2D(canvas = newCanvas(wNew, hNew));
			jsgraphics.setWindowParameters(wNew, hNew);
		}
		return jsgraphics;
	}


	public HTML5Canvas newCanvas(int width, int height) {
		if (isApplet) {
			// applets create their own canvas
			canvas = html5Applet._getHtml5Canvas();
			return canvas;
		}
		DOMNode parent = (top.getComponentCount() > 0 ? ((JSComponentUI)((JRootPane) top.getComponent(0)).getUI()).domNode : null);
		if (parent != null)
			DOMNode.remove(canvas);
		display = canvasId = appletViewer.appletName + "_canvas" + ++canvasCount;
		System.out.println("JSFrameViewer creating new canvas " + canvasId + ": "
				+ width + "  " + height);
		canvas = (HTML5Canvas) DOMNode.createElement("canvas", canvasId);
		DOMNode.setPositionAbsolute(canvas, 0, 0);
		DOMNode.setStyles(canvas, "width", width + "px", "height", height + "px");
		if (parent != null) {
			parent.appendChild(canvas);
		}
		// this next call to j2sApplet binds mouse actions to this canvas. When the
		// content pane is created, this canvas will be placed appropriately and used
		// to transfer mouse and button actions to the right FrameViewer
		/**
		 * @j2sNative
		 * 
		 *            this.canvas.width = width; this.canvas.height = height;
		 */
		{}	
		return canvas;
	}

}