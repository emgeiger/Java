import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class CylinderTest {
    private Cylinder cylinder;
    
    @BeforeEach
    public void setUp() {
        cylinder = new Cylinder(0, 0);
    }
    
    @Test
    public void testConstructorWithValidAngles() {
        Cylinder testCylinder = new Cylinder(45, 30);
        assertNotNull(testCylinder);
        assertEquals(45.0, testCylinder.getElevation(), 0.1);
        assertEquals(30.0, testCylinder.getAzimuth(), 0.1);
    }
    
    @Test
    public void testConstructorWithExtremeAngles() {
        // Test with angles at the limit
        Cylinder testCylinder = new Cylinder(89, 89);
        assertNotNull(testCylinder);
        
        // Test with angles beyond limit - should be clamped
        Cylinder testCylinder2 = new Cylinder(100, -100);
        assertNotNull(testCylinder2);
    }
    
    @Test
    public void testElevationSetterAndGetter() {
        cylinder.setElevation(45.0);
        assertEquals(45.0, cylinder.getElevation(), 0.1);
        
        cylinder.setElevation(-30.0);
        assertEquals(-30.0, cylinder.getElevation(), 0.1);
        
        // Test boundary values
        cylinder.setElevation(89.0);
        assertTrue(cylinder.getElevation() <= 89.0);
        
        cylinder.setElevation(-89.0);
        assertTrue(cylinder.getElevation() >= -89.0);
    }
    
    @Test
    public void testAzimuthSetterAndGetter() {
        cylinder.setAzimuth(60.0);
        assertEquals(60.0, cylinder.getAzimuth(), 0.1);
        
        cylinder.setAzimuth(-45.0);
        assertEquals(-45.0, cylinder.getAzimuth(), 0.1);
        
        // Test boundary values
        cylinder.setAzimuth(89.0);
        assertTrue(cylinder.getAzimuth() <= 89.0);
        
        cylinder.setAzimuth(-89.0);
        assertTrue(cylinder.getAzimuth() >= -89.0);
    }
    
    @Test
    public void testPositionSettersAndGetters() {
        cylinder.setCylX(100.0);
        cylinder.setCylY(200.0);
        
        assertEquals(100.0, cylinder.getCylX(), 0.1);
        assertEquals(200.0, cylinder.getCylY(), 0.1);
        
        // Test negative values
        cylinder.setCylX(-50.0);
        cylinder.setCylY(-75.0);
        
        assertEquals(-50.0, cylinder.getCylX(), 0.1);
        assertEquals(-75.0, cylinder.getCylY(), 0.1);
    }
    
    @Test
    public void testColorSettersAndGetters() {
        cylinder.setRed1(128);
        cylinder.setGreen1(64);
        cylinder.setBlue1(192);
        
        assertEquals(128, cylinder.getRed1());
        assertEquals(64, cylinder.getGreen1());
        assertEquals(192, cylinder.getBlue1());
        
        cylinder.setRed2(255);
        cylinder.setGreen2(128);
        cylinder.setBlue2(0);
        
        assertEquals(255, cylinder.getRed2());
        assertEquals(128, cylinder.getGreen2());
        assertEquals(0, cylinder.getBlue2());
    }
    
    @Test
    public void testColorBoundaries() {
        // Test minimum values
        cylinder.setRed1(0);
        cylinder.setGreen1(0);
        cylinder.setBlue1(0);
        
        assertEquals(0, cylinder.getRed1());
        assertEquals(0, cylinder.getGreen1());
        assertEquals(0, cylinder.getBlue1());
        
        // Test maximum values
        cylinder.setRed2(255);
        cylinder.setGreen2(255);
        cylinder.setBlue2(255);
        
        assertEquals(255, cylinder.getRed2());
        assertEquals(255, cylinder.getGreen2());
        assertEquals(255, cylinder.getBlue2());
    }
    
    @Test
    public void testPaintComponentDoesNotThrowException() {
        BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        
        cylinder.setSize(400, 400);
        cylinder.setRed1(100);
        cylinder.setGreen1(100);
        cylinder.setBlue1(100);
        cylinder.setRed2(200);
        cylinder.setGreen2(200);
        cylinder.setBlue2(200);
        
        // Should not throw any exceptions
        assertDoesNotThrow(() -> cylinder.paintComponent(g2d));
        
        g2d.dispose();
    }
    
    @Test
    public void testAngleRangeConstraints() {
        // Test that extreme angles are properly constrained
        Cylinder testCylinder = new Cylinder(180, 180);
        
        // Angles should be constrained to reasonable values
        double elev = testCylinder.getElevation();
        double azim = testCylinder.getAzimuth();
        
        assertTrue(elev >= -90 && elev <= 90, "Elevation should be constrained");
        assertTrue(azim >= -90 && azim <= 90, "Azimuth should be constrained");
    }
}
