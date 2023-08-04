import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GraphicalCylinder extends JFrame
{
    private JSlider slider;
    private JSlider elevSlider;
    private JSlider azimuthSlider;
    private JPanel panel;
    private JPanel sliderPanel;
    private Cylinder cylinder;
    private Point mouseDownPoint;

    public GraphicalCylinder()
    {
        super("Graphical Cylinder");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createContents();
        
       // panel.setBackground(Color.white);
        add(panel);

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                mouseDownPoint = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point currentPoint = e.getPoint();
                int deltaX = currentPoint.x - mouseDownPoint.x;
                int deltaY = currentPoint.y - mouseDownPoint.y;

                cylinder.setX(cylinder.getX() + deltaX);
                cylinder.setY(cylinder.getY() + deltaY);
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void createContents()
    {
        panel = new JPanel();
        sliderPanel = new JPanel();

        // slider = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
        slider.setMajorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.addChangeListener(new Listener());

        elevSlider = new JSlider(JSlider.HORIZONTAL, -90, 90, 0);
        elevSlider.setMajorTickSpacing(5);
        elevSlider.setPaintTicks(true);
        elevSlider.addChangeListener(new Listener());

        azimuthSlider = new JSlider(JSlider.HORIZONTAL, -90, 90, 0);
        azimuthSlider.setMajorTickSpacing(5);
        azimuthSlider.setPaintTicks(true);
        azimuthSlider.addChangeListener(new Listener());

        cylinder = new Cylinder(elevSlider.getValue(), azimuthSlider.getValue());
        
        // sliderPanel.add(slider);
        sliderPanel.add(elevSlider);
        sliderPanel.add(azimuthSlider);
        
        panel.add(cylinder, BordderLayout.CENTER);
        panel.add(sliderPanel, BorderLayout.SOUTH);
    }

    private class Listener implements ChangeListener
    {
        public void stateChanged(ChangeEvent e)
        {
            panel.repaint();
        }
    }

    public static void main(String[] args) {
        new GraphicalCylinder();
    }
}
