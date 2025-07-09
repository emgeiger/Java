import javax.sound.sampled.*;
import java.io.File;
import java.util.Scanner;

/**
 * Audio Diagnostic Tool for Enhanced Rotational Rhythm
 * 
 * This utility helps diagnose audio loading issues by testing
 * audio files and reporting detailed information about compatibility.
 */
public class AudioDiagnosticTool {
    
    public static void main(String[] args) {
        System.out.println("=== Enhanced Rotational Rhythm - Audio Diagnostic Tool ===");
        System.out.println("This tool helps diagnose audio loading issues.\n");
        
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the path to your audio folder: ");
            String folderPath = scanner.nextLine().trim();
            
            File audioFolder = new File(folderPath);
            
            if (!audioFolder.exists()) {
                System.err.println("ERROR: Folder does not exist: " + folderPath);
                return;
            }
            
            if (!audioFolder.isDirectory()) {
                System.err.println("ERROR: Path is not a directory: " + folderPath);
                return;
            }
            
            System.out.println("\n=== Analyzing Audio Folder ===");
            System.out.println("Folder: " + audioFolder.getAbsolutePath());
            
            // List all files
            File[] allFiles = audioFolder.listFiles();
            if (allFiles == null) {
                System.err.println("ERROR: Cannot read folder contents");
                return;
            }
            
            System.out.println("Total files in folder: " + allFiles.length);
            
            // Filter audio files
            File[] audioFiles = audioFolder.listFiles((dir, name) -> {
                String lower = name.toLowerCase();
                return lower.endsWith(".wav") || lower.endsWith(".au") || 
                       lower.endsWith(".aiff") || lower.endsWith(".aif");
            });
            
            if (audioFiles == null || audioFiles.length == 0) {
                System.out.println("No compatible audio files found.");
                System.out.println("Supported formats: WAV, AU, AIFF, AIF");
                
                System.out.println("\nAll files in folder:");
                for (File file : allFiles) {
                    System.out.println("  " + file.getName());
                }
                return;
            }
            
            System.out.println("Compatible audio files found: " + audioFiles.length);
            System.out.println("\n=== Testing Audio Files ===");
            
            int successCount = 0;
            int failureCount = 0;
            
            for (File file : audioFiles) {
                System.out.println("\nTesting: " + file.getName());
                
                try {
                    // Test file readability
                    if (!file.canRead()) {
                        System.out.println("  [FAIL] Cannot read file (permission issue?)");
                        failureCount++;
                        continue;
                    }
                    
                    // Test file size
                    long sizeKB = file.length() / 1024;
                    System.out.println("  [SIZE] " + sizeKB + " KB");
                    
                    if (sizeKB > 10000) { // > 10MB
                        System.out.println("  [WARNING] Large file size may cause issues");
                    }
                    
                    // Test audio format
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
                    AudioFormat format = audioStream.getFormat();
                    
                    System.out.println("  [FORMAT] " + format.getEncoding());
                    System.out.println("  [SAMPLE RATE] " + format.getSampleRate() + " Hz");
                    System.out.println("  [CHANNELS] " + format.getChannels() + " (" + 
                                     (format.getChannels() == 1 ? "Mono" : "Stereo") + ")");
                    System.out.println("  [SAMPLE SIZE] " + format.getSampleSizeInBits() + " bits");
                    
                    // Test clip creation
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    
                    System.out.println("  [SUCCESS] File loaded successfully");
                    successCount++;
                    
                    // Clean up
                    clip.close();
                    audioStream.close();
                    
                } catch (UnsupportedAudioFileException e) {
                    System.out.println("  [FAIL] Unsupported audio format");
                    System.out.println("       Details: " + e.getMessage());
                    failureCount++;
                } catch (Exception e) {
                    System.out.println("  [FAIL] " + e.getClass().getSimpleName());
                    System.out.println("       Details: " + e.getMessage());
                    failureCount++;
                }
            }
            
            System.out.println("\n=== Diagnostic Summary ===");
            System.out.println("[SUCCESS] Successfully loaded: " + successCount + " files");
            System.out.println("[FAILED] Failed to load: " + failureCount + " files");
            
            if (failureCount > 0) {
                System.out.println("\n[TROUBLESHOOTING TIPS]");
                System.out.println("1. Convert failed files to standard WAV format (44.1kHz, 16-bit)");
                System.out.println("2. Check file permissions and ensure files aren't corrupted");
                System.out.println("3. Try smaller file sizes (< 5MB recommended)");
                System.out.println("4. Avoid special characters in filenames");
            }
            
            if (successCount == 0) {
                System.out.println("\n[ERROR] No files could be loaded. Possible causes:");
                System.out.println("- Audio files are in unsupported formats");
                System.out.println("- Files are corrupted or incomplete");
                System.out.println("- Java audio system configuration issues");
                System.out.println("- File permission restrictions");
            }
            
            System.out.println("\n=== System Audio Information ===");
            try {
                Mixer.Info[] mixers = AudioSystem.getMixerInfo();
                System.out.println("Available audio mixers: " + mixers.length);
                for (Mixer.Info mixerInfo : mixers) {
                    System.out.println("  - " + mixerInfo.getName());
                }
            } catch (Exception e) {
                System.out.println("Could not retrieve audio system information: " + e.getMessage());
            }
            
            System.out.println("\nDiagnostic complete!");
        }
    }
}
