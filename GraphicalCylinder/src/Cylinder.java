import java.awt.*;  // BorderLayout,Color,Graphics2D,Graphics
import java.awt.geom.*;      // for Ellipse2D and GeneralPath
import javax.swing.*; // JFrame,JPanel,SwingConstants

public class Cylinder extends JPanel
{

    private double cylElev;    // cylinder axis elevation radians
    private double cylAzm;     // cylinder axis azimuth radians
    private double cylH = 400; // cylinder height in pixels
    private double cylD = 200; // cylinder diameter in pixels
    private float c1 = 0.3f;   // minimum illumination brightness
    private float c2 = 0.7f;   // maximum illumination brightness
    private float r1;
    private float g1;
    private float b1;
    private float r2;
    private float g2;
    private float b2;  

    public Cylinder(double elev, double azimuth)
    {
        cylElev = Math.toRadians(elev);
        if (Math.abs(cylElev) >= Math.PI / 2.0)
        {
          cylElev = Math.signum(cylElev) * Math.PI / 2.0001;
        }
        cylAzm = Math.toRadians(azimuth);
        if (Math.abs(cylAzm) >= Math.PI / 2.0)
        {
          cylAzm = Math.signum(cylAzm) * Math.PI / 2.0001;
        }
    }
    
    //***********************************************************
    
    public void paint(Graphics g)
    {
        // g.drawOval(x, y, 100, 100);
    }
    
    //***********************************************************
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        final double MIDX = 0.5 * getWidth();
        final double MIDY = 0.5 * getHeight();
        Graphics2D g2d = (Graphics2D) g;
        double imageRotAngle;               // image rotation angle
        GeneralPath shape;                  // curved cylinder side
        float c;                            // current color level
    
        // Apparent tipping of cylinder
        double tipCosine = Math.cos(cylAzm) * Math.cos(cylElev);
        double tipSine = Math.sqrt(1.0 - tipCosine * tipCosine);
        double frontEndAngle =
          Math.acos(tipCosine) * 2.0 / Math.PI;
    
        // Minor diameter of end ovals & apparent cylinder height
        double minorD = cylD * tipCosine;
        double apparentH = cylH * tipSine;
    
        // Shapes of curved sides and oval ends
        Rectangle rectangle = new Rectangle(
          (int) Math.round(MIDX - cylD / 2),
          (int) Math.round(MIDY - apparentH / 2),
          (int) Math.round(cylD),
          (int) Math.round(apparentH));
        Ellipse2D.Double frontEllipse = new Ellipse2D.Double(
          (int) Math.round(MIDX - cylD / 2),
          (int) Math.round(MIDY - apparentH / 2 - minorD / 2),
          (int) Math.round(cylD),
          (int) Math.round(minorD));
        Ellipse2D.Double backEllipse = new Ellipse2D.Double(
          (int) Math.round(MIDX - cylD / 2),
          (int) Math.round(MIDY + apparentH / 2 - minorD / 2),
          (int) Math.round(cylD),
          (int) Math.round(minorD));
    
        // Color for sides of cylinder
        GradientPaint gradientPaint = new GradientPaint(
          (float) (MIDX - cylD / 2), 0.0f, new Color(r1, g1, b1),
          (float) (MIDX), 0.0f, new Color(r2, g2, b2), true);
    
        // Rotate image from vertical around center
        if (cylElev == 0.0)
        {
          imageRotAngle =
            Math.signum(Math.sin(cylAzm)) * Math.PI / 2.0;
        }
        else
        {
          imageRotAngle =
            Math.atan(Math.sin(cylAzm) / Math.tan(cylElev));
          if (Math.tan(cylElev) < 0)
          {
            imageRotAngle += Math.PI;
          }
        }
        g2d.rotate(imageRotAngle, MIDX, MIDY);
    
        // Define and paint curved sides of cylinder
        shape = new GeneralPath(rectangle);
        shape.append(backEllipse, false);
        g2d.setPaint(gradientPaint);
        g2d.fill(shape);
    
        // Paint visible end of cylinder
        c = c2 - (float) ((c2 - c1) * frontEndAngle);
        g2d.setColor(new Color(c, c, c));
        g2d.fill(frontEllipse);        
    }
}