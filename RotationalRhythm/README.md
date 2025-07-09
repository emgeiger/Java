# Enhanced Rotational Rhythm

A visual rhythm sequencer with a clock-like interface that allows you to create and play drum patterns using multiple instrument tracks.

## Features

### 🎵 Audio Support
- Load audio files from any folder (supports WAV, AU, AIFF formats)
- Multiple instrument tracks with different sounds
- Real-time rhythm playback with adjustable BPM (60-200)

### 🎨 Visual Interface
- Clock-like circular layout with beat indicators
- Multiple rings for different instruments
- Color-coded instrument tracks
- Visual beat progression indicator
- Click to toggle beats on/off

### 🥁 Rhythm Sequencer
- 16-beat patterns (4/4 time signature)
- Add/remove instrument rings dynamically
- Clear individual tracks or remove them entirely
- Real-time tempo adjustment

## Quick Start

### 1. Build the Application
```bash
build.bat
```

### 2. Generate Sample Audio Files (Optional)
```bash
run-audio-generator.bat
```
This creates sample drum sounds (kick, snare, hi-hat, cymbal) in the `audio-samples` folder.

### 3. Run the Enhanced Application
```bash
run-enhanced.bat
```

## How to Use

1. **Load Audio Files**: Click "Load Sounds Folder" and select a folder containing audio files
2. **Add Instrument Rings**: Click "Add Ring" to create new instrument tracks
3. **Program Beats**: Click on the beat positions around each ring to toggle them on/off
4. **Adjust Tempo**: Use the BPM slider to change the playback speed
5. **Play/Stop**: Click the Play button to start/stop the rhythm

## Controls

- **BPM Slider**: Adjust tempo from 60 to 200 beats per minute
- **Play/Stop Button**: Start and stop rhythm playback
- **Add Ring Button**: Add a new instrument track
- **Load Sounds Folder**: Select a folder containing audio files
- **Remove Ring**: Delete the selected instrument track
- **Clear Ring**: Clear all beats from the selected track

## Audio Requirements

- Supported formats: WAV, AU, AIFF, AIF
- Recommended: Short percussion samples (0.1-1.0 seconds)
- Files should be in mono or stereo, 44.1kHz sample rate preferred

## File Structure

- `src/EnhancedRotationalRhythm.java` - Main enhanced application
- `src/AudioSampleGenerator.java` - Utility to generate sample audio files
- `src/RotationalRhythm.java` - Original application (legacy)
- `build.bat` - Build script
- `run-enhanced.bat` - Run the enhanced application
- `run-audio-generator.bat` - Generate sample audio files

## Original VS Code Java Project Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## Contributing

This is an enhanced version of the original RotationalRhythm project with added audio capabilities and improved UI. Feel free to contribute improvements or additional features!
