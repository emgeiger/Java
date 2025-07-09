import java.io.*;
import java.nio.file.*;

/**
 * Utility class to generate simple audio samples for testing the RotationalRhythm app
 */
public class AudioSampleGenerator {
    
    public static void main(String[] args) {
        try {
            // Create audio samples directory
            Path audioDir = Paths.get("audio-samples");
            Files.createDirectories(audioDir);
            
            // Generate different drum sounds
            generateKickDrum(audioDir.resolve("kick.wav").toString());
            generateSnare(audioDir.resolve("snare.wav").toString());
            generateHiHat(audioDir.resolve("hihat.wav").toString());
            generateCymbal(audioDir.resolve("cymbal.wav").toString());
            
            System.out.println("Audio samples generated in: " + audioDir.toAbsolutePath());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void generateKickDrum(String filename) throws Exception {
        generateTone(filename, 60, 0.3, 0.8); // Low frequency, short duration
    }
    
    private static void generateSnare(String filename) throws Exception {
        generateNoise(filename, 0.2, 0.6); // White noise burst
    }
    
    private static void generateHiHat(String filename) throws Exception {
        generateTone(filename, 8000, 0.1, 0.3); // High frequency, very short
    }
    
    private static void generateCymbal(String filename) throws Exception {
        generateNoise(filename, 0.5, 0.4); // Longer noise burst
    }
    
    private static void generateTone(String filename, double frequency, double duration, double amplitude) throws Exception {
        int sampleRate = 44100;
        int numSamples = (int) (sampleRate * duration);
        byte[] audioData = new byte[numSamples * 2]; // 16-bit samples
        
        for (int i = 0; i < numSamples; i++) {
            double time = i / (double) sampleRate;
            double envelope = Math.exp(-time * 10); // Exponential decay
            double sample = amplitude * envelope * Math.sin(2 * Math.PI * frequency * time);
            
            // Convert to 16-bit signed integer
            short sampleShort = (short) (sample * Short.MAX_VALUE);
            audioData[i * 2] = (byte) (sampleShort & 0xFF);
            audioData[i * 2 + 1] = (byte) ((sampleShort >> 8) & 0xFF);
        }
        
        writeWavFile(filename, audioData, sampleRate);
    }
    
    private static void generateNoise(String filename, double duration, double amplitude) throws Exception {
        int sampleRate = 44100;
        int numSamples = (int) (sampleRate * duration);
        byte[] audioData = new byte[numSamples * 2];
        
        for (int i = 0; i < numSamples; i++) {
            double time = i / (double) sampleRate;
            double envelope = Math.exp(-time * 8); // Exponential decay
            double sample = amplitude * envelope * (Math.random() * 2 - 1); // White noise
            
            short sampleShort = (short) (sample * Short.MAX_VALUE);
            audioData[i * 2] = (byte) (sampleShort & 0xFF);
            audioData[i * 2 + 1] = (byte) ((sampleShort >> 8) & 0xFF);
        }
        
        writeWavFile(filename, audioData, sampleRate);
    }
    
    private static void writeWavFile(String filename, byte[] audioData, int sampleRate) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filename);
             DataOutputStream dos = new DataOutputStream(fos)) {
            
            // WAV header
            dos.writeBytes("RIFF");
            dos.writeInt(Integer.reverseBytes(audioData.length + 36));
            dos.writeBytes("WAVE");
            dos.writeBytes("fmt ");
            dos.writeInt(Integer.reverseBytes(16)); // Format chunk size
            dos.writeShort(Short.reverseBytes((short) 1)); // PCM format
            dos.writeShort(Short.reverseBytes((short) 1)); // Mono
            dos.writeInt(Integer.reverseBytes(sampleRate));
            dos.writeInt(Integer.reverseBytes(sampleRate * 2)); // Byte rate
            dos.writeShort(Short.reverseBytes((short) 2)); // Block align
            dos.writeShort(Short.reverseBytes((short) 16)); // Bits per sample
            dos.writeBytes("data");
            dos.writeInt(Integer.reverseBytes(audioData.length));
            dos.write(audioData);
        }
    }
}
