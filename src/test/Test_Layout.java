package test;

//web_Ready
//web_AppletName= MyTest2
//web_Description= A test
//web_JavaVersion= http://www
//web_AppletImage= dddd
//web_Category= test
//web_Date= $Date$
//web_Features= graphics, AWT-to-Swing


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JTextField;

//import java.text.NumberFormat;





import a2s.Applet;

//import a2s.Canvas;
import a2s.Checkbox;
import a2s.Frame;
import a2s.Label;
import a2s.Scrollbar;
import a2s.Button;
import a2s.Choice;
//import a2s.Panel;

import a2s.TextArea;

import javax.swing.JApplet;
//import javax.swing.JButton;
import javax.swing.JPanel;
//import javax.swing.JScrollBar;
import javax.swing.Timer;

import java.lang.Math;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javajs.J2SRequireImport;
@J2SRequireImport({ java.text.DecimalFormat.class})
public class Test_Layout extends JApplet {
  static DecimalFormatSymbols dfs; // = new DecimalFormatSymbols();
  static DecimalFormat onePlaceDF; // = new DecimalFormat("#.#");
  
  public int test_native(String s) {
    /**
     * @j2sNative
     * 
     *            return s.length; 
     */
    {
      return 7;
    }
  }


  @Override  public void init() {
  	setSize(1000, 500);
    if (dfs == null) {
      dfs = new DecimalFormatSymbols();
      onePlaceDF = new DecimalFormat("#.#");

    }
    JPanel p = new JPanel();
    p.setLayout(new GridLayout(2,2,10,10));
    Con con = new Con();
    p.add(new Label("quadrant 1", Label.CENTER));
    p.add(con);
    p.add(new Label("quadrant 3", Label.CENTER));
    p.add(new Label("quadrant 4", Label.CENTER));
    getContentPane().add(p);
    
   
  }
  
  class Con extends JPanel {
    //FoilOrig outerparent;
    Label l1, l2, blank, pitchMomentLabel, liftOverDrag, reynoldsLabel,
        FSlabel;
    Choice outch, dragOutputCh, untch;
    Button bt3, ibt1, ibt2, ibt3, ibt4, ibt5;
    Button obt1, obt2, obt3, obt4, obt5;
    JTextField outlft, outDrag, outMoment, outLD, outReynolds;

    Con() {
      //outerparent = target;
      setLayout(new GridLayout(7, 4, 10, 10));

      l1 = new Label("Output", Label.RIGHT);
      l1.setForeground(Color.red);
      l2 = new Label("Input", Label.CENTER);
      l2.setForeground(Color.blue);

      FSlabel = new Label("FoilSim III", Label.CENTER);
      FSlabel.setForeground(Color.red);

      bt3 = new Button("Reset");
      bt3.setBackground(Color.red);
      bt3.setForeground(Color.white);

      ibt1 = new Button("Flight");
      ibt1.setBackground(Color.white);
      ibt1.setForeground(Color.blue);

      ibt2 = new Button("Shape");
      ibt2.setBackground(Color.yellow);
      ibt2.setForeground(Color.blue);

      ibt3 = new Button("Size");
      ibt3.setBackground(Color.white);
      ibt3.setForeground(Color.blue);

      ibt4 = new Button("Select Plot");
      ibt4.setBackground(Color.white);
      ibt4.setForeground(Color.blue);

      ibt5 = new Button("Analysis");
      ibt5.setBackground(Color.white);
      ibt5.setForeground(Color.blue);

      obt1 = new Button("Plot");
      obt1.setBackground(Color.yellow);
      obt1.setForeground(Color.red);

      obt2 = new Button("Probe");
      obt2.setBackground(Color.white);
      obt2.setForeground(Color.red);

      obt3 = new Button("Gages");
      obt3.setBackground(Color.white);
      obt3.setForeground(Color.red);

      obt4 = new Button("Geometry");
      obt4.setBackground(Color.white);
      obt4.setForeground(Color.red);

      obt5 = new Button("Data");
      obt5.setBackground(Color.white);
      obt5.setForeground(Color.red);

      untch = new Choice();
      untch.setBackground(Color.white);
      untch.setForeground(Color.black);
      untch.addItem("Imperial");
      untch.addItem("Metric");
      untch.select(0);

      outch = new Choice();
      outch.setBackground(Color.white);
      outch.setForeground(Color.black);
      outch.addItem("Lift ");
      outch.addItem("  Cl ");
      outch.select(0);

      dragOutputCh = new Choice();
      dragOutputCh.setBackground(Color.white);
      dragOutputCh.setForeground(Color.black);
      dragOutputCh.addItem("Drag");
      dragOutputCh.addItem(" Cd ");
      dragOutputCh.select(0);

      outlft = new JTextField("12.5", 5);
      outlft.setBackground(Color.black);
      outlft.setForeground(Color.yellow);

      outDrag = new JTextField("12.5", 5);
      outDrag.setBackground(Color.black);
      outDrag.setForeground(Color.yellow);

      pitchMomentLabel = new Label("Cm", Label.RIGHT);

      liftOverDrag = new Label("L/D ratio", Label.RIGHT);
      liftOverDrag.setForeground(Color.black);

      outLD = new JTextField("12.5", 5);
      outLD.setBackground(Color.black);
      outLD.setForeground(Color.yellow);
      // this fixes the typing problem!!
      outLD.setPreferredSize(outLD.getPreferredSize());

      outReynolds = new JTextField("12.5", 5);
      outReynolds.setBackground(Color.black);
      outReynolds.setForeground(Color.yellow);

      reynoldsLabel = new Label("Reynolds #", Label.RIGHT);
      reynoldsLabel.setForeground(Color.black);

      add(FSlabel);
      add(new Label("Units: ", Label.RIGHT));
      add(untch);
      add(bt3);

      add(l2);
      add(new Label("Student ", Label.RIGHT));
      add(new Label(" Version 1.5b", Label.LEFT));
      add(l1);

      add(ibt1);
      add(ibt2);
      add(obt2);
      add(obt3);

      add(ibt3);
      add(ibt5);
      add(obt4);
      add(obt5);

      add(new Label(" ", Label.CENTER));
      add(ibt4);
      add(obt1);
      add(new Label(" ", Label.CENTER));

      add(outch);
      add(outlft);
      add(reynoldsLabel);
      add(outReynolds);

      add(dragOutputCh);
      add(outDrag);
      add(liftOverDrag);
      add(outLD);
    }

  }
}
