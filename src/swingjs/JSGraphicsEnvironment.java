package swingjs;

import swingjs.api.DOMNode;
import swingjs.api.HTML5Canvas;
import jsjava.util.Locale;

import jsjava.awt.Font;
import jsjava.awt.Graphics;
import jsjava.awt.Graphics2D;
import jsjava.awt.GraphicsDevice;
import jsjava.awt.GraphicsEnvironment;
import jsjava.awt.image.BufferedImage;


public class JSGraphicsEnvironment extends GraphicsEnvironment {

	private static GraphicsDevice device;

	/*
	 * NOTE: This class is called from jsjava.awt.GraphicsEnvironment
	 * within in j2sNative block.
	 * 
	 */
	public JSGraphicsEnvironment(){
		System.out.println("JSGraphicsEnvironment initialized");		
	}

	@Override
	public Graphics2D createGraphics(BufferedImage img) {
		return (Graphics2D) img.getImageGraphic().create();
	}
	
	@Override
	public Font[] getAllFonts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getAvailableFontFamilyNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getAvailableFontFamilyNames(Locale l) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GraphicsDevice getDefaultScreenDevice() {
		if (device == null)
			device = (GraphicsDevice) JSToolkit.getInstance("swingjs.JSScreenDevice"); 
		return device;
	}

}
