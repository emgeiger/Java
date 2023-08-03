import java.awt.*;

public class Circle
{
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

    public static void main(String[] args) {
        // Create a new frame.
        Frame frame = new Frame("Circle");

        // Set the size of the frame.
        frame.setSize(800, 400);

        // Create a new canvas.
        Canvas canvas = new Canvas();

        // Add the canvas to the frame.
        frame.add(canvas);

        // Set the background color of the canvas.
        canvas.setBackground(Color.WHITE);

        // Draw the circle.
        Graphics g = canvas.getGraphics();
        // g.drawOval(100, 100, 200, 200);

        // Show the frame.
        frame.setVisible(true);
    }
}
