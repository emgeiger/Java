import java.awt.Graphics;

import java.awt.BasicStroke;
import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Circle extends JPanel
{
//    private static final int RADIUS = 150;
    private static final int TOP_LEFT_X = 200;
    private static final int TOP_LEFT_Y = 200;
    private static final double DEGREES_PER_RADIAN = 360.0 / (2 * Math.PI);

    private List<Line2D> lines = new ArrayList<>();
    private List<Point2D> points = new ArrayList<>();
    private Point2D selectedPoint = null;

//    private static final int RADIUS = 100;
    private static final int CENTER_X = 200;
    private static final int CENTER_Y = 200;
    private static final int LINE_LENGTH = 50;
    private static final int CIRCLE_RADIUS = 10;

    private int x;
    private int y;
    private int radius;
    private double angle;

    public Circle(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.angle = 0;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void update() {
        // Update the angle of the circle
        angle += 0.1;
    }

    public void draw(Graphics g) {
        // Draw the circle
        g.drawOval(x - radius, y - radius, radius * 2, radius * 2);
    }
}