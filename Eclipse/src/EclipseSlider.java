/*************************************************************
* EclipseSlider.java
* Dean & Dean
*
* This program mimics the movement of an eclipse.
* The slider allows the user to drag a shadow across a circle.
*************************************************************/

import javax.swing.*; // JFrame,JPanel,JSlider,SwingConstants
import javax.swing.event.*; // ChangeListener, ChangeEvent
import java.awt.*;  // BorderLayout,Color,Graphics2D,Graphics
import java.awt.geom.Arc2D;

public class EclipseSlider extends JFrame
{
  private JSlider slider;
  private CirclePanel panel;
  private static final int RADIUS = 100;
  private static final int TOP_LEFT_X = 40;
  private static final int TOP_LEFT_Y = 40;

  //**************************************

  public EclipseSlider()
  {
    setSize(290, 340);
    setTitle("Eclipse Slider");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    createContents();
    setVisible(true);
  } // end EclipseSlider constructor

  //**************************************

  private void createContents()
  {
    panel = new CirclePanel();
    panel.setBackground(new Color(.6f, .6f, .6f));
    slider = new JSlider(SwingConstants.HORIZONTAL, 0, 100, 0);
    slider.setMajorTickSpacing(5);
    slider.setPaintTicks(true);
    slider.addChangeListener(new Listener());

    add(panel, BorderLayout.CENTER);
    add(slider, BorderLayout.SOUTH);
  } // end createContents

  //**************************************

  @SuppressWarnings("serial")
  private class CirclePanel extends JPanel
  {
    @Override
    public void paintComponent(Graphics g)
    {
      final double DEGREES_PER_RADIAN = 360.0 / (2 * Math.PI);
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D) g;
      int shiftAmount; // amount shifted right for shadow arc
      int x; // left x value for shadow arc's bounding rectangle
      double angle; // starting angle for filled arc

      // Draw moon (white circle)
      g2.setColor(Color.WHITE);
      g2.fillOval(TOP_LEFT_X, TOP_LEFT_X, 2 * RADIUS, 2 * RADIUS);

      // Draw right side of shadow
      g2.setColor(new Color(.7f, .7f, .7f));
      shiftAmount = (int) (.01 * slider.getValue() * (2 * RADIUS));
      x = TOP_LEFT_X - 2 * RADIUS + shiftAmount;
      angle = -Math.acos((RADIUS - shiftAmount * .5) / RADIUS) *
        DEGREES_PER_RADIAN;
      g2.fill(new Arc2D.Double(
        x, TOP_LEFT_Y, 2 * RADIUS, 2 * RADIUS,
        angle, -2 * angle, Arc2D.OPEN));

      // Draw left side of shadow
      g2.fill(new Arc2D.Double(
        TOP_LEFT_X, TOP_LEFT_Y, 2 * RADIUS, 2 * RADIUS,
        180 + angle, -2 * angle, Arc2D.OPEN));
    }
  } // end class CirclePanel

  //**************************************

  private class Listener implements ChangeListener
  {
    public void stateChanged(ChangeEvent e)
    {
      panel.repaint();
    }
  } // end class CirclePanel

  //**************************************

  public static void main(String[] args)
  {
    new EclipseSlider();

	byte[] moonPhases = MoonPhases.getMoonPhases();
	System.out.println(moonPhases);

  }
} // end class EclipseSlider