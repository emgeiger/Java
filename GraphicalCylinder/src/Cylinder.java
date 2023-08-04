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
/*
    public void setElevation(double elev)
    {
        cylElev = Math.toRadians(elev);
        if (Math.abs(cylElev) >= Math.PI / 2.0)
        {
          cylElev = Math.signum(cylElev) * Math.PI / 2.0001;
        }
    }

    public void setAzimuth(double azimuth)
    {
        cylAzm = Math.toRadians(azimuth);
        if (Math.abs(cylAzm) >= Math.PI / 2.0)
        {
          cylAzm = Math.signum(cylAzm) * Math.PI / 2.0001;
        }
    }

    public void setHeight(double height)
    {
        cylH = height;
    }

    public void setDiameter(double diameter)
    {
        cylD = diameter;
    }

    public void setMinBrightness(float minBrightness)
    {
        c1 = minBrightness;
    }

    public void setMaxBrightness(float maxBrightness)
    {
        c2 = maxBrightness;
    }
 */
/*
    public void setMinColor(Color minColor)
    {
        r1 = minColor.getRed() / 255.0f;
        g1 = minColor.getGreen() / 255.0f;
        b1 = minColor.getBlue() / 255.0f;
    }

    public void setMaxColor(Color maxColor)
    {
        r2 = maxColor.getRed() / 255.0f;
        g2 = maxColor.getGreen() / 255.0f;
        b2 = maxColor.getBlue() / 255.0f;
    }
*/    
/*
    public double getElevation()
    {
        return Math.toDegrees(cylElev);
    }

    public double getAzimuth()
    {
        return Math.toDegrees(cylAzm);
    }

    public double getHeight()
    {
        return cylH;
    }

    public double getDiameter()
    {
        return cylD;
    }
 */
/*
    public float getMinBrightness()
    {
        return c1;
    }

    public float getMaxBrightness()
    {
        return c2;
    }
*/
/*
    public Color getMinColor()
    {
        return new Color(r1, g1, b1);
    }

    public Color getMaxColor()
    {
        return new Color(r2, g2, b2);
    }
*/
/*
    public void setElevSlider(JSlider elevSlider)
    {
        cylElev = Math.toRadians(elevSlider.getValue());
        if (Math.abs(cylElev) >= Math.PI / 2.0)
        {
          cylElev = Math.signum(cylElev) * Math.PI / 2.0001;
        }
    }

    public void setAzmSlider(JSlider azmSlider)
    {
        cylAzm = Math.toRadians(azmSlider.getValue());
        if (Math.abs(cylAzm) >= Math.PI / 2.0)
        {
          cylAzm = Math.signum(cylAzm) * Math.PI / 2.0001;
        }
    }

    public void setHeightSlider(JSlider heightSlider)
    {
        cylH = heightSlider.getValue();
    }

    public void setDiameterSlider(JSlider diameterSlider)
    {
        cylD = diameterSlider.getValue();
    }

    public void setMinBrightnessSlider(JSlider minBrightnessSlider)
    {
        c1 = minBrightnessSlider.getValue() / 100.0f;
    }

    public void setMaxBrightnessSlider(JSlider maxBrightnessSlider)
    {
        c2 = maxBrightnessSlider.getValue() / 100.0f;
    }

    public void setMinColorSlider(JSlider minColorSlider)
    {
        r1 = minColorSlider.getValue() / 255.0f;
        g1 = minColorSlider.getValue() / 255.0f;
        b1 = minColorSlider.getValue() / 255.0f;
    }

    public void setMaxColorSlider(JSlider maxColorSlider)
    {
        r2 = maxColorSlider.getValue() / 255.0f;
        g2 = maxColorSlider.getValue() / 255.0f;
        b2 = maxColorSlider.getValue() / 255.0f;
    }
*/
/*
    public JSlider getElevSlider()
    {
        JSlider elevSlider = new JSlider(JSlider.HORIZONTAL, -90, 90, 0);
        elevSlider.setMajorTickSpacing(5);
        elevSlider.setPaintTicks(true);
        elevSlider.addChangeListener(new Listener());
        return elevSlider;
    }

    public JSlider getAzmSlider()
    {
        JSlider azmSlider = new JSlider(JSlider.HORIZONTAL, -90, 90, 0);
        azmSlider.setMajorTickSpacing(5);
        azmSlider.setPaintTicks(true);
        azmSlider.addChangeListener(new Listener());
        return azmSlider;
    }

    public JSlider getHeightSlider()
    {
        JSlider heightSlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 400);
        heightSlider.setMajorTickSpacing(50);
        heightSlider.setPaintTicks(true);
        heightSlider.addChangeListener(new Listener());
        return heightSlider;
    }

    public JSlider getDiameterSlider()
    {
        JSlider diameterSlider = new JSlider(JSlider.HORIZONTAL, 0, 1000, 200);
        diameterSlider.setMajorTickSpacing(50);
        diameterSlider.setPaintTicks(true);
        diameterSlider.addChangeListener(new Listener());
        return diameterSlider;
    }

    public JSlider getMinBrightnessSlider()
    {
        JSlider minBrightnessSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 30);
        minBrightnessSlider.setMajorTickSpacing(5);
        minBrightnessSlider.setPaintTicks(true);
        minBrightnessSlider.addChangeListener(new Listener());
        return minBrightnessSlider;
    }

    public JSlider getMaxBrightnessSlider()
    {
        JSlider maxBrightnessSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 70);
        maxBrightnessSlider.setMajorTickSpacing(5);
        maxBrightnessSlider.setPaintTicks(true);
        maxBrightnessSlider.addChangeListener(new Listener());
        return maxBrightnessSlider;
    }

    public JSlider getMinColorSlider()
    {
        JSlider minColorSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 76);
        minColorSlider.setMajorTickSpacing(25);
        minColorSlider.setPaintTicks(true);
        minColorSlider.addChangeListener(new Listener());
        return minColorSlider;
    }

    public JSlider getMaxColorSlider()
    {
        JSlider maxColorSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 178);
        maxColorSlider.setMajorTickSpacing(25);
        maxColorSlider.setPaintTicks(true);
        maxColorSlider.addChangeListener(new Listener());
        return maxColorSlider;
    }
 */
    public void setRed1(int red1)
    {
        r1 = red1 / 255.0f;
    }

    public void setGreen1(int green1)
    {
        g1 = green1 / 255.0f;
    }

    public void setBlue1(int blue1)
    {
        b1 = blue1 / 255.0f;
    }

    public void setRed2(int red2)
    {
        r2 = red2 / 255.0f;
    }

    public void setGreen2(int green2)
    {
        g2 = green2 / 255.0f;
    }

    public void setBlue2(int blue2)
    {
        b2 = blue2 / 255.0f;
    }

    public int getRed1()
    {
        return (int) (r1 * 255.0f);
    }

    public int getGreen1()
    {
        return (int) (g1 * 255.0f);
    }

    public int getBlue1()
    {
        return (int) (b1 * 255.0f);
    }

    public int getRed2()
    {
        return (int) (r2 * 255.0f);
    }

    public int getGreen2()
    {
        return (int) (g2 * 255.0f);
    }

    public int getBlue2()
    {
        return (int) (b2 * 255.0f);
    }
/*
    public void setRed1Slider(JSlider red1Slider)
    {
        r1 = red1Slider.getValue() / 255.0f;
    }

    public void setGreen1Slider(JSlider green1Slider)
    {
        g1 = green1Slider.getValue() / 255.0f;
    }

    public void setBlue1Slider(JSlider blue1Slider)
    {
        b1 = blue1Slider.getValue() / 255.0f;
    }

    public void setRed2Slider(JSlider red2Slider)
    {
        r2 = red2Slider.getValue() / 255.0f;
    }

    public void setGreen2Slider(JSlider green2Slider)
    {
        g2 = green2Slider.getValue() / 255.0f;
    }

    public void setBlue2Slider(JSlider blue2Slider)
    {
        b2 = blue2Slider.getValue() / 255.0f;
    }
*/
/*
    public JSlider getRed1Slider()
    {
        JSlider red1Slider = new JSlider(JSlider.HORIZONTAL, 0, 255, 76);
        red1Slider.setMajorTickSpacing(25);
        red1Slider.setPaintTicks(true);
        red1Slider.addChangeListener(new Listener());
        return red1Slider;
    }

    public JSlider getGreen1Slider()
    {
        JSlider green1Slider = new JSlider(JSlider.HORIZONTAL, 0, 255, 76);
        green1Slider.setMajorTickSpacing(25);
        green1Slider.setPaintTicks(true);
        green1Slider.addChangeListener(new Listener());
        return green1Slider;
    }

    public JSlider getBlue1Slider()
    {
        JSlider blue1Slider = new JSlider(JSlider.HORIZONTAL, 0, 255, 76);
        blue1Slider.setMajorTickSpacing(25);
        blue1Slider.setPaintTicks(true);
        blue1Slider.addChangeListener(new Listener());
        return blue1Slider;
    }

    public JSlider getRed2Slider()
    {
        JSlider red2Slider = new JSlider(JSlider.HORIZONTAL, 0, 255, 178);
        red2Slider.setMajorTickSpacing(25);
        red2Slider.setPaintTicks(true);
        red2Slider.addChangeListener(new Listener());
        return red2Slider;
    }

    public JSlider getGreen2Slider()
    {
        JSlider green2Slider = new JSlider(JSlider.HORIZONTAL, 0, 255, 178);
        green2Slider.setMajorTickSpacing(25);
        green2Slider.setPaintTicks(true);
        green2Slider.addChangeListener(new Listener());
        return green2Slider;
    }

    public JSlider getBlue2Slider()
    {
        JSlider blue2Slider = new JSlider(JSlider.HORIZONTAL, 0, 255, 178);
        blue2Slider.setMajorTickSpacing(25);
        blue2Slider.setPaintTicks(true);
        blue2Slider.addChangeListener(new Listener());
        return blue2Slider;
    }
 */
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