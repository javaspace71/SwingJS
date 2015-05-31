/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package jsjava.awt.peer;

import jsjava.awt.AWTEvent;
//import jsjava.awt.AWTException;
//import jsjava.awt.BufferCapabilities;
import jsjava.awt.Color;
import jsjava.awt.Component;
import jsjava.awt.Dimension;
import jsjava.awt.Font;
import jsjava.awt.FontMetrics;
import jsjava.awt.Graphics;
import jsjava.awt.GraphicsConfiguration;
import jsjava.awt.Image;
import jsjava.awt.Point;
import jsjava.awt.Rectangle;
import jsjava.awt.Toolkit;
import jsjava.awt.event.PaintEvent;
import jsjava.awt.image.ColorModel;
import jsjava.awt.image.ImageObserver;
import jsjava.awt.image.ImageProducer;
import jsjava.awt.image.VolatileImage;

//import jsjavax.swing.plaf.synth.Region;

import jssun.awt.CausedFocusEvent;

/**
 * The peer interfaces are intended only for use in porting the AWT. They are
 * not intended for use by application developers, and developers should not
 * implement peers nor invoke any of the peer methods directly on the peer
 * instances.
 */
public interface ComponentPeer {
	public static final int 
	SET_LOCATION = 1, 
	SET_SIZE = 2, 
	SET_BOUNDS = 3,
	SET_CLIENT_SIZE = 4, 
	RESET_OPERATION = 5, 
	NO_EMBEDDED_CHECK = (1 << 14),
  DEFAULT_OPERATION = SET_BOUNDS;

	boolean isObscured();

	boolean canDetermineObscurity();

	void setVisible(boolean b);

	void setEnabled(boolean b);

	void paint(Graphics g);

	void repaint(long tm, int x, int y, int width, int height);

	void print(Graphics g);

	void setBounds(int x, int y, int width, int height, int op);

	void handleEvent(AWTEvent e);

	void coalescePaintEvent(PaintEvent e);

	Point getLocationOnScreen();

	Dimension getPreferredSize();

	Dimension getMinimumSize();

	ColorModel getColorModel();

	Toolkit getToolkit();

	Graphics getGraphics();

	FontMetrics getFontMetrics(Font font);

	void dispose();

	void setForeground(Color c);

	void setBackground(Color c);

	void setFont(Font f);

	void updateCursorImmediately();

	boolean requestFocus(Component lightweightChild, boolean temporary,
			boolean focusedWindowChangeAllowed, long time,
			CausedFocusEvent.Cause cause);

	boolean isFocusable();

	Image createImage(ImageProducer producer);

	Image createImage(int width, int height);

	VolatileImage createVolatileImage(int width, int height);

	boolean prepareImage(Image img, int w, int h, ImageObserver o);

	int checkImage(Image img, int w, int h, ImageObserver o);

	GraphicsConfiguration getGraphicsConfiguration();

	boolean handlesWheelScrolling();

	// void createBuffers(int numBuffers, BufferCapabilities caps) throws
	// AWTException;
	Image getBackBuffer();

	// void flip(int x1, int y1, int x2, int y2, BufferCapabilities.FlipContents
	// flipAction);
	void destroyBuffers();

	/**
	 * Reparents this peer to the new parent referenced by
	 * <code>newContainer</code> peer Implementation depends on toolkit and
	 * container.
	 * 
	 * @param newContainer
	 *          peer of the new parent container
	 * @since 1.5
	 */
	void reparent(ContainerPeer newContainer);

	/**
	 * Returns whether this peer supports reparenting to another parent withour
	 * destroying the peer
	 * 
	 * @return true if appropriate reparent is supported, false otherwise
	 * @since 1.5
	 */
	boolean isReparentSupported();

	/**
	 * Used by lightweight implementations to tell a ComponentPeer to layout its
	 * sub-elements. For instance, a lightweight Checkbox needs to layout the box,
	 * as well as the text label.
	 */
	void layout();

	Rectangle getBounds();

	// /**
	// * Applies the shape to the native component window.
	// * @since 1.7
	// */
	// void applyShape(Region shape);

	// /**
	// * DEPRECATED: Replaced by getPreferredSize().
	// */
	// Dimension preferredSize();
	//
	// /**
	// * DEPRECATED: Replaced by getMinimumSize().
	// */
	// Dimension minimumSize();
	//
	// /**
	// * DEPRECATED: Replaced by setVisible(boolean).
	// */
	// void show();
	//
	// /**
	// * DEPRECATED: Replaced by setVisible(boolean).
	// */
	// void hide();
	//
	// /**
	// * DEPRECATED: Replaced by setEnabled(boolean).
	// */
	// void enable();
	//
	// /**
	// * DEPRECATED: Replaced by setEnabled(boolean).
	// */
	// void disable();
	//
	// /**
	// * DEPRECATED: Replaced by setBounds(int, int, int, int).
	// */
	// void reshape(int x, int y, int width, int height);
}
