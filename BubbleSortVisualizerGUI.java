import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BubbleSortVisualizerGUI extends JFrame {

    private static int[] array;
    private static int currentElementIndex;
    private static int nextElementIndex;

    private JPanel drawingPanel;
    private JButton sortButton;

    public BubbleSortVisualizerGUI() {
        super("Bubble Sort Visualizer");

        // Create the drawing panel
        drawingPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Draw the array elements
                for (int i = 0; i < array.length; i++) {
                    int x = i * 10;
                    int y = getHeight() - array[i];

                    g.setColor(Color.BLACK);
                    g.fillRect(x, y, 10, 10);
                }

                // Highlight the current and next elements
                g.setColor(Color.RED);
                g.fillRect(currentElementIndex * 10, getHeight() - array[currentElementIndex], 10, 10);
                g.setColor(Color.GREEN);
                g.fillRect(nextElementIndex * 10, getHeight() - array[nextElementIndex], 10, 10);
            }
        };

        // Create the sort button
        sortButton = new JButton("Sort");
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Start the bubble sort
                new Thread(() -> {
                    while (currentElementIndex < array.length - 1) {
                        // Compare the current element to the next element
                        if (array[currentElementIndex] > array[nextElementIndex]) {
                            // Swap the elements
                            int temp = array[currentElementIndex];
                            array[currentElementIndex] = array[nextElementIndex];
                            array[nextElementIndex] = temp;
                        }

                        // Update the drawing panel
                        drawingPanel.repaint();

                        // Increment the indices
                        currentElementIndex++;
                        nextElementIndex++;

                        // Wait for a bit
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        // Add the components to the frame
        getContentPane().add(drawingPanel, BorderLayout.CENTER);
        getContentPane().add(sortButton, BorderLayout.SOUTH);

        // Set the frame size and visibility
        setSize(new Dimension(500, 300));
        setVisible(true);
    }

    public static void main(String[] args) {
        new BubbleSortVisualizerGUI();
    }
}
