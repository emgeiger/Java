import javax.swing.*;
import java.awt.event.*;
import java.util.Queue;
import java.util.LinkedList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Form extends JFrame {
    private Queue<Integer> points = new LinkedList<>();
    private String saveFile;
    private static final Object locker = new Object();
    private short writeThreshold = 0;
    private static final float FOCUS_THRESHOLD = 1.15f;
    private static final float FOCUS_SCOPE = .2f;

    public Form() {
        initializeComponents();
        saveFile = new java.text.SimpleDateFormat("dd__HH-mm-ss").format(new java.util.Date()) + ".csv";
        try {
            new File(saveFile).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        startArd("COM4");
        startBT();
    }

    private void initializeComponents() {
        // Initialize your GUI components here
    }

    private void startArd(String portName) {
        // Initialize and open your serial port here
    }

    private void startBT() {
        // Initialize and start your Bluetooth manager here
    }

    private void checkFocusThreshold(List<Integer> vals) {
        List<Integer> fullRange = new LinkedList<>(vals);
        double focusRange = fullRange.subList(0, (int)(vals.size() * FOCUS_SCOPE)).stream().mapToInt(val -> val).average().orElse(0);

        if (focusRange > fullRange.stream().mapToInt(val -> val).average().orElse(0) * FOCUS_THRESHOLD) {
            System.out.println("BING BANG BOOM REEEEEE");
            // Additional logic here
        } else {
            // Additional logic here
        }
    }

    private void appendToFile(String path, String txt) {
        synchronized (locker) {
            try (FileWriter writer = new FileWriter(path, true)) {
                writer.write(txt);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Additional methods here
}
