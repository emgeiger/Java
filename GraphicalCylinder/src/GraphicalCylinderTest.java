import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.Point;
import java.awt.Component;

public class GraphicalCylinderTest {
    private GraphicalCylinder graphicalCylinder;
    
    @BeforeEach
    public void setUp() {
        // Run on EDT for Swing components
        try {
            SwingUtilities.invokeAndWait(() -> {
                graphicalCylinder = new GraphicalCylinder();
            });
        } catch (Exception e) {
            fail("Failed to initialize GUI: " + e.getMessage());
        }
    }
    
    @AfterEach
    public void tearDown() {
        if (graphicalCylinder != null) {
            SwingUtilities.invokeLater(() -> {
                graphicalCylinder.dispose();
            });
        }
    }
    
    @Test
    public void testFrameCreation() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                assertNotNull(graphicalCylinder);
                assertEquals("Graphical Cylinder", graphicalCylinder.getTitle());
                assertEquals(JFrame.EXIT_ON_CLOSE, graphicalCylinder.getDefaultCloseOperation());
            });
        } catch (Exception e) {
            fail("Test failed: " + e.getMessage());
        }
    }
    
    @Test
    public void testFrameVisibility() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                assertTrue(graphicalCylinder.isVisible());
            });
        } catch (Exception e) {
            fail("Test failed: " + e.getMessage());
        }
    }
    
    @Test
    public void testComponentsExist() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                assertNotNull(graphicalCylinder.getContentPane());
                assertTrue(graphicalCylinder.getContentPane().getComponentCount() > 0);
            });
        } catch (Exception e) {
            fail("Test failed: " + e.getMessage());
        }
    }
    
    @Test
    public void testSliderInitialization() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                // Find sliders in the component hierarchy
                JSlider[] sliders = findSliders(graphicalCylinder);
                assertTrue(sliders.length >= 2, "Should have at least 2 sliders (elevation and azimuth)");
                
                // Check that sliders have appropriate ranges
                for (JSlider slider : sliders) {
                    assertTrue(slider.getMinimum() <= slider.getMaximum());
                    assertTrue(slider.getValue() >= slider.getMinimum() && 
                              slider.getValue() <= slider.getMaximum());
                }
            });
        } catch (Exception e) {
            fail("Test failed: " + e.getMessage());
        }
    }
    
    @Test
    public void testCylinderComponentExists() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                Cylinder[] cylinders = findCylinders(graphicalCylinder);
                assertTrue(cylinders.length > 0, "Should have at least one Cylinder component");
                assertNotNull(cylinders[0]);
            });
        } catch (Exception e) {
            fail("Test failed: " + e.getMessage());
        }
    }
    
    @Test
    public void testMouseListenersAttached() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                assertTrue(graphicalCylinder.getMouseListeners().length > 0, 
                          "Frame should have mouse listeners attached");
                assertTrue(graphicalCylinder.getMouseMotionListeners().length > 0, 
                          "Frame should have mouse motion listeners attached");
            });
        } catch (Exception e) {
            fail("Test failed: " + e.getMessage());
        }
    }
    
    @Test
    public void testSliderInteraction() {
        try {
            SwingUtilities.invokeAndWait(() -> {
                JSlider[] sliders = findSliders(graphicalCylinder);
                if (sliders.length >= 2) {
                    // Test changing slider values
                    int originalValue = sliders[0].getValue();
                    sliders[0].setValue(45);
                    assertEquals(45, sliders[0].getValue());
                    
                    // Reset
                    sliders[0].setValue(originalValue);
                }
            });
        } catch (Exception e) {
            fail("Test failed: " + e.getMessage());
        }
    }
    
    // Helper methods to find components
    private JSlider[] findSliders(Container container) {
        java.util.List<JSlider> sliders = new java.util.ArrayList<>();
        findSlidersRecursive(container, sliders);
        return sliders.toArray(new JSlider[0]);
    }
    
    private void findSlidersRecursive(Container container, java.util.List<JSlider> sliders) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JSlider) {
                sliders.add((JSlider) comp);
            } else if (comp instanceof Container) {
                findSlidersRecursive((Container) comp, sliders);
            }
        }
    }
    
    private Cylinder[] findCylinders(Container container) {
        java.util.List<Cylinder> cylinders = new java.util.ArrayList<>();
        findCylindersRecursive(container, cylinders);
        return cylinders.toArray(new Cylinder[0]);
    }
    
    private void findCylindersRecursive(Container container, java.util.List<Cylinder> cylinders) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof Cylinder) {
                cylinders.add((Cylinder) comp);
            } else if (comp instanceof Container) {
                findCylindersRecursive((Container) comp, cylinders);
            }
        }
    }
}
