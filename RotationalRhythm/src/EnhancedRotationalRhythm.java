import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Enhanced Rotational Rhythm - A visual rhythm sequencer with clock-like interface
 * Features:
 * - Load audio files from a folder
 * - Multiple instrument tracks (rings)
 * - Visual beat indicators on a clock face
 * - Real-time rhythm playback
 */
public class EnhancedRotationalRhythm extends JFrame {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 700;
    private static final int CLOCK_CENTER_X = 400;
    private static final int CLOCK_CENTER_Y = 300;
    private static final int BASE_RADIUS = 80;
    private static final int RING_SPACING = 30;
    private static final int BEATS_PER_MEASURE = 16;
    private static final int CLOCK_HAND_LENGTH = 60;
    private static final int CENTER_CIRCLE_RADIUS = 15;
    
    // Audio and timing
    private Timer rhythmTimer;
    private int currentBeat = 0;
    private int bpm = 120;
    private boolean isPlaying = false;
    private double clockHandAngle = 0.0; // Angle for the tempo clock hand
    
    // UI Components
    private RhythmPanel rhythmPanel;
    private JLabel bpmLabel;
    private JSlider bpmSlider;
    private JButton playButton;
    private JButton addRingButton;
    private JButton loadSoundsButton;
    private JList<String> instrumentList;
    private DefaultListModel<String> instrumentListModel;
    
    // Audio management
    private Map<String, Clip> audioClips = new HashMap<>();
    private File audioFolder;
    
    // Rhythm data
    private List<RhythmRing> rhythmRings = new ArrayList<>();
    
    public EnhancedRotationalRhythm() {
        super("Enhanced Rotational Rhythm");
        initializeUI();
        setupEventHandlers();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        
        // Create main rhythm panel
        rhythmPanel = new RhythmPanel();
        add(rhythmPanel, BorderLayout.CENTER);
        
        // Create control panel
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.SOUTH);
        
        // Create side panel for instruments
        JPanel sidePanel = createSidePanel();
        add(sidePanel, BorderLayout.EAST);
        
        // Add initial drum ring
        addNewRing("Default", Color.RED);
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        // BPM controls
        panel.add(new JLabel("BPM:"));
        bpmLabel = new JLabel(String.valueOf(bpm));
        bpmSlider = new JSlider(60, 200, bpm);
        bpmSlider.addChangeListener(e -> {
            bpm = bpmSlider.getValue();
            bpmLabel.setText(String.valueOf(bpm));
            updateTimerDelay();
        });
        panel.add(bpmSlider);
        panel.add(bpmLabel);
        
        // Play/Stop button
        playButton = new JButton("Play");
        panel.add(playButton);
        
        // Add ring button
        addRingButton = new JButton("Add Ring");
        panel.add(addRingButton);
        
        // Load sounds button
        loadSoundsButton = new JButton("Load Sounds Folder");
        panel.add(loadSoundsButton);
        
        return panel;
    }
    
    private JPanel createSidePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(200, 0));
        panel.setBorder(BorderFactory.createTitledBorder("Instruments"));
        
        // Instrument list
        instrumentListModel = new DefaultListModel<>();
        instrumentList = new JList<>(instrumentListModel);
        instrumentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(instrumentList);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Ring controls
        JPanel ringControls = new JPanel(new GridLayout(2, 1));
        JButton removeRingButton = new JButton("Remove Ring");
        JButton clearRingButton = new JButton("Clear Ring");
        
        removeRingButton.addActionListener(e -> removeSelectedRing());
        clearRingButton.addActionListener(e -> clearSelectedRing());
        
        ringControls.add(removeRingButton);
        ringControls.add(clearRingButton);
        panel.add(ringControls, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void setupEventHandlers() {
        playButton.addActionListener(e -> togglePlayback());
        addRingButton.addActionListener(e -> showAddRingDialog());
        loadSoundsButton.addActionListener(e -> loadSoundsFolder());
        
        // Setup rhythm timer
        rhythmTimer = new Timer(calculateBeatDelay(), e -> nextBeat());
    }
    
    private void togglePlayback() {
        if (isPlaying) {
            stopPlayback();
        } else {
            startPlayback();
        }
    }
    
    private void startPlayback() {
        isPlaying = true;
        playButton.setText("Stop");
        rhythmTimer.start();
    }
    
    private void stopPlayback() {
        isPlaying = false;
        playButton.setText("Play");
        rhythmTimer.stop();
        currentBeat = 0;
        clockHandAngle = 0.0; // Reset clock hand to 12 o'clock position
        rhythmPanel.repaint();
    }
    
    private void nextBeat() {
        currentBeat = (currentBeat + 1) % BEATS_PER_MEASURE;
        
        // Update clock hand angle - full rotation every measure (16 beats)
        clockHandAngle = (currentBeat * 2 * Math.PI) / BEATS_PER_MEASURE;
        
        // Play sounds for active beats
        for (RhythmRing ring : rhythmRings) {
            if (ring.isActive(currentBeat)) {
                playSound(ring.getSoundName());
            }
        }
        
        rhythmPanel.repaint();
    }
    
    private void playSound(String soundName) {
        Clip clip = audioClips.get(soundName);
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }
    
    private void updateTimerDelay() {
        if (rhythmTimer != null) {
            rhythmTimer.setDelay(calculateBeatDelay());
        }
    }
    
    private int calculateBeatDelay() {
        // Calculate delay in milliseconds for each beat
        return (60 * 1000) / (bpm * 4); // 16th notes
    }
    
    private void showAddRingDialog() {
        String[] availableSounds = audioClips.keySet().toArray(new String[0]);
        if (availableSounds.length == 0) {
            JOptionPane.showMessageDialog(this, "Please load a sounds folder first!");
            return;
        }
        
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField nameField = new JTextField("Ring " + (rhythmRings.size() + 1));
        JComboBox<String> soundCombo = new JComboBox<>(availableSounds);
        JButton colorButton = new JButton("Choose Color");
        Color selectedColor = Color.BLUE;
        
        colorButton.addActionListener(e -> {
            Color color = JColorChooser.showDialog(this, "Choose Ring Color", selectedColor);
            if (color != null) {
                colorButton.setBackground(color);
            }
        });
        
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Sound:"));
        panel.add(soundCombo);
        panel.add(new JLabel("Color:"));
        panel.add(colorButton);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Ring", 
                                                  JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String sound = (String) soundCombo.getSelectedItem();
            Color color = colorButton.getBackground();
            
            if (!name.isEmpty() && sound != null) {
                addNewRing(name, color, sound);
            }
        }
    }
    
    private void addNewRing(String name, Color color) {
        addNewRing(name, color, "Default");
    }
    
    private void addNewRing(String name, Color color, String soundName) {
        RhythmRing ring = new RhythmRing(name, color, soundName);
        rhythmRings.add(ring);
        instrumentListModel.addElement(name);
        rhythmPanel.repaint();
    }
    
    private void removeSelectedRing() {
        int selectedIndex = instrumentList.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < rhythmRings.size()) {
            rhythmRings.remove(selectedIndex);
            instrumentListModel.remove(selectedIndex);
            rhythmPanel.repaint();
        }
    }
    
    private void clearSelectedRing() {
        int selectedIndex = instrumentList.getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < rhythmRings.size()) {
            rhythmRings.get(selectedIndex).clearBeats();
            rhythmPanel.repaint();
        }
    }
    
    private void loadSoundsFolder() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Select Audio Folder");
        
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            audioFolder = chooser.getSelectedFile();
            loadAudioFiles();
        }
    }
    
    private void loadAudioFiles() {
        audioClips.clear();
        
        if (audioFolder != null && audioFolder.exists()) {
            File[] files = audioFolder.listFiles((dir, name) -> {
                String lower = name.toLowerCase();
                return lower.endsWith(".wav") || lower.endsWith(".au") || 
                       lower.endsWith(".aiff") || lower.endsWith(".aif");
            });
            
            if (files != null) {
                for (File file : files) {
                    try {
                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioStream);
                        
                        String name = file.getName().replaceFirst("[.][^.]+$", "");
                        audioClips.put(name, clip);
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                        System.err.println("Error loading " + file.getName() + ": " + e.getMessage());
                    }
                }
                
                JOptionPane.showMessageDialog(this, 
                    "Loaded " + audioClips.size() + " audio files");
            }
        }
    }
    
    // Inner class for rhythm panel
    private class RhythmPanel extends JPanel {
        
        public RhythmPanel() {
            setBackground(Color.BLACK);
            
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    handleMouseClick(e.getX(), e.getY());
                }
            });
        }
        
        private void handleMouseClick(int x, int y) {
            // Calculate which ring and beat was clicked
            for (int ringIndex = 0; ringIndex < rhythmRings.size(); ringIndex++) {
                int ringRadius = BASE_RADIUS + ringIndex * RING_SPACING;
                
                for (int beat = 0; beat < BEATS_PER_MEASURE; beat++) {
                    double angle = (beat * 2 * Math.PI) / BEATS_PER_MEASURE - Math.PI / 2;
                    int beatX = (int) (CLOCK_CENTER_X + ringRadius * Math.cos(angle));
                    int beatY = (int) (CLOCK_CENTER_Y + ringRadius * Math.sin(angle));
                    
                    if (Math.sqrt(Math.pow(x - beatX, 2) + Math.pow(y - beatY, 2)) <= 8) {
                        rhythmRings.get(ringIndex).toggleBeat(beat);
                        repaint();
                        return;
                    }
                }
            }
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Draw clock face background
            drawClockFace(g2d);
            
            // Draw rhythm rings
            for (int i = 0; i < rhythmRings.size(); i++) {
                drawRhythmRing(g2d, rhythmRings.get(i), i);
            }
            
            // Draw current beat indicator
            drawCurrentBeatIndicator(g2d);
            
            // Draw tempo clock hand in center
            drawTempoClockHand(g2d);
        }
        
        private void drawClockFace(Graphics2D g2d) {
            // Draw outer circle
            g2d.setColor(Color.DARK_GRAY);
            g2d.setStroke(new BasicStroke(2));
            int outerRadius = BASE_RADIUS + rhythmRings.size() * RING_SPACING + 20;
            g2d.drawOval(CLOCK_CENTER_X - outerRadius, CLOCK_CENTER_Y - outerRadius, 
                        outerRadius * 2, outerRadius * 2);
            
            // Draw beat markers
            g2d.setColor(Color.GRAY);
            for (int i = 0; i < BEATS_PER_MEASURE; i++) {
                double angle = (i * 2 * Math.PI) / BEATS_PER_MEASURE - Math.PI / 2;
                int innerRadius = BASE_RADIUS - 10;
                int outerMarkerRadius = BASE_RADIUS + rhythmRings.size() * RING_SPACING + 10;
                
                int x1 = (int) (CLOCK_CENTER_X + innerRadius * Math.cos(angle));
                int y1 = (int) (CLOCK_CENTER_Y + innerRadius * Math.sin(angle));
                int x2 = (int) (CLOCK_CENTER_X + outerMarkerRadius * Math.cos(angle));
                int y2 = (int) (CLOCK_CENTER_Y + outerMarkerRadius * Math.sin(angle));
                
                g2d.setStroke(i % 4 == 0 ? new BasicStroke(3) : new BasicStroke(1));
                g2d.drawLine(x1, y1, x2, y2);
                
                // Draw beat numbers
                if (i % 4 == 0) {
                    g2d.setColor(Color.WHITE);
                    String beatNum = String.valueOf(i / 4 + 1);
                    FontMetrics fm = g2d.getFontMetrics();
                    int textX = (int) (CLOCK_CENTER_X + (outerMarkerRadius + 15) * Math.cos(angle) - fm.stringWidth(beatNum) / 2);
                    int textY = (int) (CLOCK_CENTER_Y + (outerMarkerRadius + 15) * Math.sin(angle) + fm.getHeight() / 2);
                    g2d.drawString(beatNum, textX, textY);
                    g2d.setColor(Color.GRAY);
                }
            }
        }
        
        private void drawRhythmRing(Graphics2D g2d, RhythmRing ring, int ringIndex) {
            int radius = BASE_RADIUS + ringIndex * RING_SPACING;
            
            // Draw ring circle
            g2d.setColor(ring.getColor().darker());
            g2d.setStroke(new BasicStroke(1));
            g2d.drawOval(CLOCK_CENTER_X - radius, CLOCK_CENTER_Y - radius, radius * 2, radius * 2);
            
            // Draw beat indicators
            for (int beat = 0; beat < BEATS_PER_MEASURE; beat++) {
                double angle = (beat * 2 * Math.PI) / BEATS_PER_MEASURE - Math.PI / 2;
                int x = (int) (CLOCK_CENTER_X + radius * Math.cos(angle));
                int y = (int) (CLOCK_CENTER_Y + radius * Math.sin(angle));
                
                if (ring.isActive(beat)) {
                    g2d.setColor(ring.getColor());
                    g2d.fillOval(x - 6, y - 6, 12, 12);
                } else {
                    g2d.setColor(ring.getColor().darker().darker());
                    g2d.fillOval(x - 4, y - 4, 8, 8);
                }
                
                g2d.setColor(Color.WHITE);
                g2d.drawOval(x - 6, y - 6, 12, 12);
            }
        }
        
        private void drawCurrentBeatIndicator(Graphics2D g2d) {
            if (!isPlaying) return;
            
            double angle = (currentBeat * 2 * Math.PI) / BEATS_PER_MEASURE - Math.PI / 2;
            int innerRadius = BASE_RADIUS - 20;
            int outerRadius = BASE_RADIUS + rhythmRings.size() * RING_SPACING + 15;
            
            g2d.setColor(Color.YELLOW);
            g2d.setStroke(new BasicStroke(3));
            
            int x1 = (int) (CLOCK_CENTER_X + innerRadius * Math.cos(angle));
            int y1 = (int) (CLOCK_CENTER_Y + innerRadius * Math.sin(angle));
            int x2 = (int) (CLOCK_CENTER_X + outerRadius * Math.cos(angle));
            int y2 = (int) (CLOCK_CENTER_Y + outerRadius * Math.sin(angle));
            
            g2d.drawLine(x1, y1, x2, y2);
        }
        
        private void drawTempoClockHand(Graphics2D g2d) {
            // Draw center circle
            g2d.setColor(Color.DARK_GRAY);
            g2d.fillOval(CLOCK_CENTER_X - CENTER_CIRCLE_RADIUS, 
                        CLOCK_CENTER_Y - CENTER_CIRCLE_RADIUS, 
                        CENTER_CIRCLE_RADIUS * 2, CENTER_CIRCLE_RADIUS * 2);
            
            // Draw center circle border
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(CLOCK_CENTER_X - CENTER_CIRCLE_RADIUS, 
                        CLOCK_CENTER_Y - CENTER_CIRCLE_RADIUS, 
                        CENTER_CIRCLE_RADIUS * 2, CENTER_CIRCLE_RADIUS * 2);
            
            // Draw BPM text in center
            g2d.setColor(Color.WHITE);
            Font bpmFont = new Font(Font.SANS_SERIF, Font.BOLD, 10);
            g2d.setFont(bpmFont);
            String bpmText = String.valueOf(bpm);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = CLOCK_CENTER_X - fm.stringWidth(bpmText) / 2;
            int textY = CLOCK_CENTER_Y + fm.getHeight() / 4;
            g2d.drawString(bpmText, textX, textY);
            
            // Draw clock hand only when playing
            if (isPlaying) {
                // Calculate hand position (starts at top and rotates clockwise)
                double handAngle = clockHandAngle - Math.PI / 2; // Start at 12 o'clock position
                int handEndX = (int) (CLOCK_CENTER_X + CLOCK_HAND_LENGTH * Math.cos(handAngle));
                int handEndY = (int) (CLOCK_CENTER_Y + CLOCK_HAND_LENGTH * Math.sin(handAngle));
                
                // Draw the main hand
                g2d.setColor(Color.CYAN);
                g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.drawLine(CLOCK_CENTER_X, CLOCK_CENTER_Y, handEndX, handEndY);
                
                // Draw hand tip
                g2d.setColor(Color.WHITE);
                g2d.fillOval(handEndX - 3, handEndY - 3, 6, 6);
                
                // Draw center dot
                g2d.setColor(Color.CYAN);
                g2d.fillOval(CLOCK_CENTER_X - 3, CLOCK_CENTER_Y - 3, 6, 6);
            }
            
            // Draw tempo scale markers around the center
            drawTempoScaleMarkers(g2d);
        }
        
        private void drawTempoScaleMarkers(Graphics2D g2d) {
            g2d.setColor(Color.GRAY);
            g2d.setStroke(new BasicStroke(1));
            
            // Draw 4 main tempo markers (quarter note positions)
            for (int i = 0; i < 4; i++) {
                double angle = (i * 2 * Math.PI / 4) - Math.PI / 2; // Start at top
                int innerRadius = CENTER_CIRCLE_RADIUS + 5;
                int outerRadius = CENTER_CIRCLE_RADIUS + 12;
                
                int x1 = (int) (CLOCK_CENTER_X + innerRadius * Math.cos(angle));
                int y1 = (int) (CLOCK_CENTER_Y + innerRadius * Math.sin(angle));
                int x2 = (int) (CLOCK_CENTER_X + outerRadius * Math.cos(angle));
                int y2 = (int) (CLOCK_CENTER_Y + outerRadius * Math.sin(angle));
                
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(x1, y1, x2, y2);
            }
            
            // Draw 16 smaller markers for 16th note positions
            for (int i = 0; i < 16; i++) {
                if (i % 4 != 0) { // Skip the main markers
                    double angle = (i * 2 * Math.PI / 16) - Math.PI / 2;
                    int innerRadius = CENTER_CIRCLE_RADIUS + 7;
                    int outerRadius = CENTER_CIRCLE_RADIUS + 10;
                    
                    int x1 = (int) (CLOCK_CENTER_X + innerRadius * Math.cos(angle));
                    int y1 = (int) (CLOCK_CENTER_Y + innerRadius * Math.sin(angle));
                    int x2 = (int) (CLOCK_CENTER_X + outerRadius * Math.cos(angle));
                    int y2 = (int) (CLOCK_CENTER_Y + outerRadius * Math.sin(angle));
                    
                    g2d.setStroke(new BasicStroke(1));
                    g2d.drawLine(x1, y1, x2, y2);
                }
            }
        }
    }
    
    // Inner class for rhythm ring data
    private static class RhythmRing {
        private String name;
        private Color color;
        private String soundName;
        private boolean[] beats;
        
        public RhythmRing(String name, Color color, String soundName) {
            this.name = name;
            this.color = color;
            this.soundName = soundName;
            this.beats = new boolean[BEATS_PER_MEASURE];
        }
        
        public void toggleBeat(int beat) {
            if (beat >= 0 && beat < beats.length) {
                beats[beat] = !beats[beat];
            }
        }
        
        public boolean isActive(int beat) {
            return beat >= 0 && beat < beats.length && beats[beat];
        }
        
        public void clearBeats() {
            Arrays.fill(beats, false);
        }
        
        public String getName() { return name; }
        public Color getColor() { return color; }
        public String getSoundName() { return soundName; }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EnhancedRotationalRhythm();
        });
    }
}
