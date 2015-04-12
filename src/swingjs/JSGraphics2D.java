/* Copyright (c) 2002-2012 The University of the West Indies
 *
 * Contact: robert.lancashire@uwimona.edu.jm
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */

// CHANGES to 'JSVPanel.java'
// University of the West Indies, Mona Campus
//
// 25-06-2007 rjl - bug in ReversePlot for non-continuous spectra fixed
//                - previously, one point less than npoints was displayed
// 25-06-2007 cw  - show/hide/close modified
// 10-02-2009 cw  - adjust for non zero baseline in North South plots
// 24-08-2010 rjl - check coord output is not Internationalised and uses decimal point not comma
// 31-10-2010 rjl - bug fix for drawZoomBox suggested by Tim te Beek
// 01-11-2010 rjl - bug fix for drawZoomBox
// 05-11-2010 rjl - colour the drawZoomBox area suggested by Valery Tkachenko
// 23-07-2011 jak - Added feature to draw the x scale, y scale, x units and y units
//					independently of each other. Added independent controls for the font,
//					title font, title bold, and integral plot color.
// 24-09-2011 jak - Altered drawGraph to fix bug related to reversed highlights. Added code to
//					draw integration ratio annotations
// 03-06-2012 rmh - Full overhaul; code simplification; added support for Jcamp 6 nD spectra

package swingjs;

import jsjava.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import swingjs.api.HTML5Canvas;
import swingjs.api.HTMLCanvasContext2D;

import jsjava.awt.BasicStroke;
import jsjava.awt.Color;
import jsjava.awt.Font;
import jsjava.awt.FontMetrics;
import jsjava.awt.Graphics;
import jsjava.awt.Graphics2D;
import jsjava.awt.GraphicsConfiguration;
import jsjava.awt.Image;
import jsjava.awt.Paint;
import jsjava.awt.Rectangle;
import jsjava.awt.RenderingHints;
import jsjava.awt.RenderingHints.Key;
import jsjava.awt.Shape;
import jsjava.awt.Stroke;
import jsjava.awt.font.FontRenderContext;
import jsjava.awt.geom.AffineTransform;
import jsjava.awt.geom.PathIterator;
import jsjava.awt.image.BufferedImage;
import jsjava.awt.image.BufferedImageOp;
import jsjava.awt.image.ImageObserver;
import jsjava.awt.image.RenderedImage;
import jsjava.awt.image.renderable.RenderableImage;
import jsjava.text.AttributedCharacterIterator;



/**
 * generic 2D drawing methods -- JavaScript version
 * 
 * guessing a lot here -- just getting something out; 
 * converted from JSpecView
 * 
 * @author Bob Hanson hansonr@stolaf.edu
 */

public class JSGraphics2D extends Graphics2D  {

	private int windowWidth;
	private int windowHeight;
	private HTML5Canvas canvas;

  public JSGraphics2D(Object canvas) {
  	this.canvas = (HTML5Canvas) canvas;
  	
  	ctx = this.canvas.getContext("2d");
  	/**
  	 * @j2sNative
  	 * 
  	 * this.gc = SwingJS;
  	 *
  	 */
  	{}
	}

  private HTMLCanvasContext2D ctx;
  private GraphicsConfiguration gc;
  
	@Override
	public GraphicsConfiguration getDeviceConfiguration() {
		return gc;
	}

	@Override
	public void drawLine(int x0, int y0, int x1, int y1) {
		boolean inPath = this.inPath;
		if (!inPath)
			ctx.beginPath();
		ctx.moveTo(x0, y0);
		ctx.lineTo(x1, y1);
		if (!inPath)
			ctx.stroke();
	}

	public void drawCircle(int x, int y, int diameter) {
		drawArc(x, y, diameter, diameter, 0, 360);		
	}

	@Override
	public void fillArc(int x, int y, int width, int height, int startAngle,
			int arcAngle) {
		doArc(x, y, width, height, startAngle, arcAngle, true);
	}

	@Override
	public void drawArc(int x, int y, int width, int height, int startAngle,
			int arcAngle) {
		doArc(x, y, width, height, startAngle, arcAngle, false);
	}


	private void doArc(int x, int y, int width, int height, int startAngle,
			int arcAngle, boolean fill) {
		// width
		boolean isCircle = (arcAngle - startAngle == 360);
		ctx.save();
		ctx.translate(x, y);
		ctx.scale(width / height, height);
		ctx.beginPath();
		if (fill) {
				// do something here to fill this arc
		}
		ctx.arc(0.5, 0.5, 0.5, toRad(startAngle), toRad(arcAngle), false);
		if (isCircle)
			ctx.closePath();
		ctx.stroke();
		ctx.restore();
	}

	private double toRad(int a) {
		return a * Math.PI / 180;
	}

	public void drawPolygon(int[] ayPoints, int[] axPoints, int nPoints) {
		doPoly(ayPoints, axPoints, nPoints, false);
	}

	/**
	 * @param axPoints
	 * @param ayPoints
	 * @param nPoints
	 * @param doFill
	 */
	private void doPoly(int[] axPoints, int[] ayPoints, int nPoints,
			boolean doFill) {
		ctx.beginPath();
		ctx.moveTo(axPoints[0], ayPoints[0]);
		for (int i = 1; i < nPoints; i++)
			ctx.lineTo(axPoints[i], ayPoints[i]);
		if (doFill)
			ctx.fill();
		else
			ctx.stroke();
	}

	public void drawRect(int x, int y, int width,
			int height) {
		  ctx.beginPath();
      ctx.rect(x ,y, width, height);
      ctx.stroke();
	}

	@Override
	public void drawString(String s, int x, int y) {
	  ctx.fillText(s,x,y);
}

	boolean isShifted;// private, but only JavaScript
	private Color bgColor;
	private Font font;
	
	public void background(Color bgcolor) {
		bgColor = bgcolor;
		if (bgcolor == null) {
			/*
			 * 
			 * reduce antialiasing, thank you,
			 * http://www.rgraph.net/docs/howto-get-crisp-lines-with-no-antialias.html
			 */
			if (!isShifted)
				ctx.translate(-0.5, -0.5);
			isShifted = true;
			return;
		}
		ctx.clearRect(0, 0, windowWidth, windowHeight);
		setGraphicsColor(bgcolor);
		fillRect(0, 0, windowWidth, windowHeight);
	}

	public void fillCircle(int x, int y, int diameter) {
		double r = diameter/2f;
		 		ctx.beginPath();
		    ctx.arc(x + r, y + r, r, 0, 2 * Math.PI, false);
		    ctx.fill();
	}

	public void fillPolygon(int[] ayPoints, int[] axPoints, int nPoints) {
		doPoly(ayPoints, axPoints, nPoints, true);
	}

	public void fillRect(int x, int y, int width, int height) {
		 ctx.fillRect(x, y, width, height);
	}

	public void setGraphicsColor(Color c) {
		String s = toCSSString(c);
		/**
		 * @j2sNative
		 *  this.ctx.fillStyle = s;
		 *  this.ctx.strokeStyle = s;
		 */
		{
		ctx._setFillStyle(s);
		ctx._setStrokeStyle(s);
		}
	}

	private String toCSSString(Color c) {
	  String s = "000000" + Integer.toHexString(c.getRGB()&0xFFFFFF);
	  return "#" + s.substring(s.length() - 6);
	}

	public void setFont(Font font) {
		this.font = font;
		String s = JSToolkit.getCanvasFont(font);
		/**
		 * @j2sNative
		 * 
		 * this.ctx.font = s;
		 */
		{
		ctx._setFont(s);
		}
	}

	public void setStrokeBold(boolean tf) {
		setLineWidth(tf ? 2. : 1.);
	}

	private void setLineWidth(double d) {
		/**
		 * @j2sNative
		 * 
		 * this.ctx.lineWidth = d;
		 */
		{
		  ctx._setLineWidth(d);
		}
	}

	public void setWindowParameters(int width, int height) {
		windowWidth = width;
		windowHeight = height;
	}


	public boolean canDoLineTo() {
		return true;
	}

	boolean inPath;
	
	public void doStroke(boolean isBegin) {
		inPath = isBegin;
		  if (isBegin) {
		  	 ctx.beginPath();
		  } else {
		    ctx.stroke();
		  }
	}

	public void lineTo(int x2, int y2) {
		 ctx.lineTo(x2, y2);	
	}

	@Override
	public void clip(Shape s) {
		doShape(s);
		ctx.clip();
	}

	@Override
	public void draw(Shape s) {
		doShape(s);
		ctx.stroke();
	}

	private void doShape(Shape s) {
		ctx.beginPath();
		double[] pts = new double[6];
		PathIterator pi = s.getPathIterator(null);
		while (!pi.isDone()) {
			switch (pi.currentSegment(pts)) {
			case PathIterator.SEG_MOVETO:
				ctx.moveTo(pts[0], pts[1]);
				break;
			case PathIterator.SEG_LINETO:
				ctx.lineTo(pts[0], pts[1]);
				break;
			case PathIterator.SEG_QUADTO:
				ctx.quadraticCurveTo(pts[0], pts[1], pts[2], pts[3]);
				break;
			case PathIterator.SEG_CUBICTO:
				ctx.bezeierCurveTo(pts[0], pts[1], pts[2], pts[3], pts[4], pts[5]);
				break;
			case PathIterator.SEG_CLOSE:
				ctx.closePath();
				break;
			}
			pi.next();			
		}
		// then fill or stroke or clip
	}

	@Override
	public void fill(Shape s) {
		doShape(s);
		ctx.fill();
	}

	@Override
	public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
		JSToolkit.notImplemented();
		return false;
	}

	@Override
	public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
		JSToolkit.notImplemented();
	}

	@Override
	public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
		JSToolkit.notImplemented();
	}

	@Override
	public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
		JSToolkit.notImplemented();
	}

	@Override
	public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
		JSToolkit.notImplemented();
		return false;
	}

	@Override
	public void setPaint(Paint paint) {
		JSToolkit.notImplemented();
	}

	@SuppressWarnings("unused")
	@Override
	public void setStroke(Stroke s) {
		if (!(s instanceof BasicStroke))
			return;
		BasicStroke b = (BasicStroke) s;
		float[] dash = b.getDashArray();
		int[] idash = new int[dash == null ? 0 : dash.length];
		for (int i = idash.length; --i >= 0;)
			idash[i] = (int) dash[i];
		ctx.setLineDash(idash);
		setLineWidth(b.getLineWidth());
		String lineCap, lineJoin;
		float miterLimit = -1;
		switch (b.getEndCap()) {
		case BasicStroke.CAP_BUTT:
			lineCap = "butt";
			break;
		case BasicStroke.CAP_SQUARE:
			lineCap = "square";
			break;
		case BasicStroke.CAP_ROUND:
		default:
			lineCap = "round";
		}
		switch (b.getLineJoin()) {
		case BasicStroke.JOIN_BEVEL:
			lineJoin = "bevel";
			break;
		case BasicStroke.JOIN_MITER:
			lineJoin = "miter";
			miterLimit= b.getMiterLimit();
			break;
		case BasicStroke.JOIN_ROUND:
			lineJoin = "round";
		}
		/**
		 * @j2sNative
		 * 
		 * this.ctx.lineCap = lineCap;
		 * this.ctx.lineJoin = lineJoin;
		 * if (miterLimit >= 0)
		 *   this.ctx.miterLimit = miterLimit;
		 */
		{}
		//SwingJS TODO more here
	}

	@Override
	public void setRenderingHint(Key hintKey, Object hintValue) {
		hints.put(hintKey, hintValue);
	}

	private RenderingHints hints = new RenderingHints(new HashMap());
	private Color color;
	
	@Override
	public Object getRenderingHint(Key hintKey) {
		return hints.get(hintKey);
	}

	@Override
	public void setRenderingHints(Map<?, ?> hints) {
		this.hints = new RenderingHints((Map<Key, ?>)hints);
	}

	@Override
	public void addRenderingHints(Map<?, ?> hints) {
		for (Entry<?, ?> e  : hints.entrySet())
			this.hints.put(e.getKey(), e.getValue());	
	}

	@Override
	public RenderingHints getRenderingHints() {
		return hints;
	}

	@Override
	public void translate(int x, int y) {
		ctx.translate(x, y);
	}

	@Override
	public void scale(double sx, double sy) {
		ctx.scale(sx, sy);
	}

	@Override
	public void setBackground(Color color) {
		background(color);
	}

	@Override
	public Color getBackground() {
		return bgColor;
	}

	@Override
	public Graphics createSwingJS() {
		ctx.save();
		return this;// just testing here. It's supposed to be a clone, but...
	}

	@Override
	public void dispose() {
		// we don't really dispose of this, as create doesn't really create a clone
		ctx.restore();
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color c) {
		color = c;
		setGraphicsColor(c);
	}

	@Override
	public Font getFont() {
		return font;
	}

	@Override
	public FontMetrics getFontMetrics(Font f) {
		return Toolkit.getDefaultToolkit().getFontMetrics(f);
	}


	@Override
	public void clipRect(int x, int y, int width, int height) {
		//SwingJS --  this is not quite right. Should ADD this to the clipping region
    ctx.beginPath();
		ctx.rect(x, y, width, height);
		ctx.clip(); 
	}

	@Override
	public void setClip(int x, int y, int width, int height) {
		ctx.beginPath();
		ctx.rect(x, y, width, height);
		ctx.clip(); 
	}

	@Override
	public void setClip(Shape clip) {
		ctx.beginPath();
		doShape(clip);
		ctx.clip();
	}

	@Override
	public void clearRect(int x, int y, int width, int height) {
		ctx.clearRect(x, y, width, height);
	}

	@Override
	public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
		if (nPoints < 2)
			return;
		ctx.moveTo(xPoints[0], yPoints[0]);
		for (int i = 1; i < nPoints; i++) {
			ctx.lineTo(xPoints[i], yPoints[i]);
		}
	}

	@Override
	public void copyArea(int x, int y, int width, int height, int dx, int dy) {
		JSToolkit.notImplemented();
	}

	@Override
	public void drawRoundRect(int x, int y, int width, int height, int arcWidth,
			int arcHeight) {
		JSToolkit.notImplemented();
		drawRect(x, y, width, height); 		
	}

	@Override
	public void fillRoundRect(int x, int y, int width, int height, int arcWidth,
			int arcHeight) {
		JSToolkit.notImplemented();
		fillRect(x, y, width, height); 		
	}

	@Override
	public void drawOval(int x, int y, int width, int height) {
		JSToolkit.notImplemented();
	}

	@Override
	public void fillOval(int x, int y, int width, int height) {
		JSToolkit.notImplemented();
	}

	@Override
	public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
		JSToolkit.notImplemented();
		return false;
	}

	@Override
	public boolean drawImage(Image img, int x, int y, int width, int height,
			ImageObserver observer) {
		JSToolkit.notImplemented();
		return false;
	}

	@Override
	public boolean drawImage(Image img, int x, int y, Color bgcolor,
			ImageObserver observer) {
		JSToolkit.notImplemented();
		return false;
	}

	@Override
	public boolean drawImage(Image img, int x, int y, int width, int height,
			Color bgcolor, ImageObserver observer) {
		JSToolkit.notImplemented();
		return false;
	}

	@Override
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2,
			int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
		JSToolkit.notImplemented();
		return false;
	}

	@Override
	public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2,
			int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
		JSToolkit.notImplemented();
		return false;
	}

	@Override
	public Shape getClip() {
		// not available in JavaScript?
		JSToolkit.notImplemented();
		return null;
	}

	@Override
	public void drawStringTrans(String str, float x, float y) {
		// apply affine transformation first
		JSToolkit.notImplemented();
	}

	@Override
	public void drawString(AttributedCharacterIterator iterator, int x, int y) {
		JSToolkit.notImplemented();
	}

	@Override
	public void drawStringAttrTrans(AttributedCharacterIterator iterator, float x, float y) {
		JSToolkit.notImplemented();
	}

	@Override
	public void translateTrans(double tx, double ty) {
		JSToolkit.notImplemented();
	}

	@Override
	public void rotate(double theta) {
		JSToolkit.notImplemented();
	}

	@Override
	public void rotate(double theta, double x, double y) {
		JSToolkit.notImplemented();
	}

	@Override
	public void shear(double shx, double shy) {
		JSToolkit.notImplemented();
	}

	@Override
	public void transform(AffineTransform Tx) {
		JSToolkit.notImplemented();
	}

	@Override
	public void setTransform(AffineTransform Tx) {
		JSToolkit.notImplemented();
	}

	@Override
	public AffineTransform getTransform() {
		JSToolkit.notImplemented();
		return null;
	}

	@Override
	public Paint getPaint() {
		JSToolkit.notImplemented();
		return null;
	}


	@Override
	public Stroke getStroke() {
		JSToolkit.notImplemented();
		return null;
	}

	@Override
	public FontRenderContext getFontRenderContext() {
		JSToolkit.notImplemented();
		return null;
	}

	@Override
	public void setPaintMode() {
		JSToolkit.notImplemented();
	}

	@Override
	public void setXORMode(Color c1) {
		JSToolkit.notImplemented();
	}

	@Override
	public Rectangle getClipBounds() {
		JSToolkit.notImplemented();
		return null;
	}

}
