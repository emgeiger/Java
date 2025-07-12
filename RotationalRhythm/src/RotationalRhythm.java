import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.Timer;
import javax.swing.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
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

public class RotationalRhythm extends JFrame
{

    private static final Map<String, Integer> BPM_BY_GENRE = new HashMap<>();
    private static final Map<String, Integer> BPM_BY_INSTRUMENT = new HashMap<>();

    private static final String[] GENRES = {"Pop", "Rock", "Hip Hop", "Classical", "Jazz"};
    private static final String[] INSTRUMENTS = {"Guitar", "Piano", "Drums", "Violin", "Flute"};

    [// Create a new Clip object to play the sound
    Clip clip;

    int bpm;

    static
    {
            BPM_BY_GENRE.put("Pop", 120);
            BPM_BY_GENRE.put("Rock", 100);
            BPM_BY_GENRE.put("Hip Hop", 90);
            BPM_BY_GENRE.put("Classical", 60);
            BPM_BY_GENRE.put("Jazz", 70);

            BPM_BY_INSTRUMENT.put("Guitar", 120);
            BPM_BY_INSTRUMENT.put("Piano", 100);
            BPM_BY_INSTRUMENT.put("Drums", 0);
            BPM_BY_INSTRUMENT.put("Violin", 60);
            BPM_BY_INSTRUMENT.put("Flute", 70);
    }

    public RotationalRhythm()
    {
        super("Rotational Rhythm");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createContents();
        pack();
        setVisible(true);
    }

    private void createContents()
    {
            // Create a combo box for the genres
            JComboBox<String> genreComboBox = new JComboBox<>(GENRES);

            // Create a combo box for the instruments
            JComboBox<String> instrumentComboBox = new JComboBox<>(INSTRUMENTS);

            // Create a button to start and stop the sound
            JButton soundButton = new JButton("Start Sound");
            soundButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (clip == null) {
                        // Start playing the sound
                        int bpm = getBpm((String) genreComboBox.getSelectedItem(), (String) instrumentComboBox.getSelectedItem());
                        playSound(bpm);
                        soundButton.setText("Stop Sound");
                    } else {
                        // Stop playing the sound
                        clip.stop();
                        clip.close();
                        clip = null;
                        soundButton.setText("Start Sound");
                    }
                }
            });

            // Add the combo boxes to the panel
            add(genreComboBox);
            add(instrumentComboBox);
            add(soundButton);        
    }

/*
    public static void main(String[] args) {
        Frame frame = new Frame("Rotation Rhythm");
        Canvas canvas = new Canvas();

        frame.add(canvas);
        frame.setSize(500, 500);
        frame.setVisible(true);

        // Create a circle in the center of the canvas
        Circle circle = new Circle(250, 250, 100);

        // Add a slider to the canvas
        Slider slider = new Slider(0, 100, 50);
        canvas.add(slider);

        // Add a listener to the slider
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Set the rotation angle of the circle based on the slider value
                circle.setAngle(slider.getValue());
            }
        });

        // Draw the circle
        canvas.paint(circle);

        // Start the animation loop
        while (true) {
            // Update the circle
            circle.update();

            // Redraw the circle
            canvas.repaint();

            // Try to sleep for 10 milliseconds
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
 */

    private static void playRhythm(int bpm) {
        // Implementation of the playRhythm method

        // Calculate the delay between beats in milliseconds
        int delay = 60000 / bpm;

        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            return;
        }

        // Load the beep sound from a file
        try {
            clip.open(AudioSystem.getAudioInputStream(RotationalRhythm.class.getResourceAsStream("beep.wav")));
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
            return;
        }

        // Start playing the sound repeatedly
        clip.loop(Clip.LOOP_CONTINUOUSLY);

        // Wait for the user to press Enter to stop the sound
        System.out.println("Press Enter to stop the sound.");
        System.console().readLine();

        // Stop playing the sound
        clip.stop();
        clip.close();
    }

    public static void main(String[] args) {
        // Get the genre of music chosen by the user
//        System.out.print("Enter the genre of music you want to play: ");
//        String genre = System.console().readLine();

        // Get the instrument chosen by the user
//        System.out.print("Enter the instrument you want to play: ");
//        String instrument = System.console().readLine();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Rotational Rhythm");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new RotationalRhythm());
            frame.pack();
            frame.setVisible(true);
        });
    }

        public static void playSound(String filename) {
            try {
                // Get the audio input stream from the file
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filename).getAbsoluteFile());

                // Get the clip for the audio input stream
                Clip clip = AudioSystem.getClip();

                // Open the clip with the audio input stream
                clip.open(audioInputStream);

                // Start playing the clip
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }
}
/*
    public class RotationRhythm {

        public static void main(String[] args) {
            Frame frame = new Frame("Rotation Rhythm");
            Canvas canvas = new Canvas();

            frame.add(canvas);
            frame.setSize(500, 500);
            frame.setVisible(true);

            // Create a circle in the center of the canvas
            Circle circle = new Circle(250, 250, 100);

            // Add a slider to the canvas
            Slider slider = new Slider(0, 100, 50);
            canvas.add(slider);

            // Add a listener to the slider
            slider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    // Set the rotation angle of the circle based on the slider value
                    circle.setAngle(slider.getValue());
                }
            });

            // Draw the circle
            canvas.paint(circle);

            // Start the animation loop
            while (true) {
                // Update the circle
                circle.update();

                // Redraw the circle
                canvas.repaint();

                // Try to sleep for 10 milliseconds
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
 */