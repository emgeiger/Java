import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.List;

// import org.w3c.dom.events.MouseEvent;

public class CirclePanel extends JPanel {
    private static final int RADIUS = 150;
    private static final int TOP_LEFT_X = 200;
    private static final int TOP_LEFT_Y = 200;
    private static final double DEGREES_PER_RADIAN = 360.0 / (2 * Math.PI);

    private List<Line2D> lines = new ArrayList<>();
    private List<Point2D> points = new ArrayList<>();
    private Point2D selectedPoint = null;

    public CirclePanel(int numLines) {
        // Create the linear lines around the outer circle
        double angle = 0;
        double angleIncrement = 2 * Math.PI / numLines;
        for (int i = 0; i < numLines; i++) {
            double x1 = TOP_LEFT_X + RADIUS * Math.cos(angle);
            double y1 = TOP_LEFT_Y + RADIUS * Math.sin(angle);
            double x2 = TOP_LEFT_X + RADIUS * Math.cos(angle + Math.PI);
            double y2 = TOP_LEFT_Y + RADIUS * Math.sin(angle + Math.PI);
            lines.add(new Line2D.Double(x1, y1, x2, y2));
            angle += angleIncrement;
        }

        // Create the draggable circles
        points.add(new Point2D.Double(TOP_LEFT_X + RADIUS, TOP_LEFT_Y));
        points.add(new Point2D.Double(TOP_LEFT_X, TOP_LEFT_Y + RADIUS));
        points.add(new Point2D.Double(TOP_LEFT_X - RADIUS, TOP_LEFT_Y));

        // Add a mouse listener to handle dragging the circles
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (Point2D point : points) {
                    if (point.distance(e.getPoint()) <= 10) {
                        selectedPoint = point;
                        break;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedPoint = null;
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedPoint != null) {
                    selectedPoint.setLocation(e.getPoint());
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the linear lines around the outer circle
        g2.setColor(Color.BLACK);
        for (Line2D line : lines) {
            g2.draw(line);
        }

        // Draw the outer circle
        g2.setColor(Color.WHITE);
        g2.fillOval(TOP_LEFT_X - RADIUS, TOP_LEFT_Y - RADIUS, 2 * RADIUS, 2 * RADIUS);

        // Draw the draggable circles
        g2.setColor(Color.RED);
        for (Point2D point : points) {
            g2.fillOval((int) point.getX() - 10, (int) point.getY() - 10, 20, 20);
        }

        // Draw the lines connecting the draggable circles to the outer circle
        g2.setColor(Color.BLUE);
        for (Point2D point : points) {
            double angle = Math.atan2(point.getY() - TOP_LEFT_Y, point.getX() - TOP_LEFT_X);
            double x = TOP_LEFT_X + RADIUS * Math.cos(angle);
            double y = TOP_LEFT_Y + RADIUS * Math.sin(angle);
            g2.draw(new Line2D.Double(point, new Point2D.Double(x, y)));
        }

        // Draw the filled arcs for the draggable circles
        g2.setColor(Color.GREEN);
        for (Point2D point : points) {
            double angle = Math.atan2(point.getY() - TOP_LEFT_Y, point.getX() - TOP_LEFT_X);
            double startAngle = angle - Math.PI / 4;
            double endAngle = angle + Math.PI / 4;
            AffineTransform transform = AffineTransform.getTranslateInstance(point.getX(), point.getY());
            transform.rotate(angle + Math.PI / 2);
            Arc2D arc = new Arc2D.Double(-10, -10, 20, 20, startAngle * DEGREES_PER_RADIAN, (endAngle - startAngle) * DEGREES_PER_RADIAN, Arc2D.PIE);
            g2.fill(transform.createTransformedShape(arc));
        }
    }
} // end class CirclePanel