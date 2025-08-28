# Eclipse Slider - Swiss Ephemeris Integration

A Java Swing application that displays current lunar phase information using Swiss Ephemeris for accurate astronomical calculations.

## Features

- **Swiss Ephemeris Integration**: Accurate lunar phase calculations using professional astronomical algorithms
- **Current Lunar Data**: Moon phase, illumination percentage, and phase names shown at startup
- **Visual Moon Display**: Accurate moon phase visualization with realistic shadows
- **Single Load Operation**: Shows current lunar phase when opened (no continuous updates)
- **Offline Operation**: No internet connection or API keys required
- **Modern Java**: Built with Java 11+ and Gradle

## Prerequisites

- Java 11 or higher
- No external dependencies (Swiss Ephemeris algorithms built-in)

## Quick Start

### 1. No Configuration Required!

The Swiss Ephemeris integration works out-of-the-box with no API keys or internet connection required.

### 2. Build and Run

**Option 1: Using Gradle (Recommended)**

```powershell
# Compile the project
.\gradlew build

# Run the application
.\gradlew run
```

**Option 2: Direct Compilation (if Java is in PATH)**

```powershell
# Compile to bin directory
javac -d bin -cp src\main\java src\main\java\*.java

# Run the application
java -cp bin EclipseSlider
```

**Option 3: Using Batch Files (Easy Windows Setup)**

```powershell
# Run the full application
.\scripts\run.bat

# Test just the Swiss Ephemeris integration
.\scripts\test-api.bat
```

**Option 4: Using Pre-compiled Classes**
```powershell
# Navigate to bin directory and run
cd bin
java EclipseSlider
```

**Option 5: Simple VS Code Development**

```powershell
# Simple compilation for basic development
javac -d bin src/*.java

# Run the basic version
java -cp bin EclipseSlider
```

### 3. Verify Compilation

Check that class files are present:
```powershell
Get-ChildItem build\classes\java\main\*.class
```

Expected output should include:
- `EclipseSlider.class`
- `EclipseSlider$MoonPhasePanel.class`
- `MoonPhases.class`
- `SimpleApiTest.class`

✅ **Verification Status**: All required class files are present after running `.\gradlew build`!

## Project Structure

```
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── EclipseSlider.java     # Main GUI application with lunar integration
│   │   │   ├── MoonPhases.java        # Swiss Ephemeris lunar calculations
│   │   │   └── SimpleApiTest.java     # Swiss Ephemeris testing utility
│   │   └── resources/
│   │       ├── config.properties      # Application configuration
│   │       └── config.properties.template # Configuration template
│   └── test/
│       ├── java/
│       │   ├── MoonPhasesTest.java    # Unit tests for MoonPhases
│       │   └── SwissEphemerisMoonPhasesTest.java # Integration tests
│       └── resources/
│           └── config.properties      # Test configuration
├── build-tools/                       # Gradle distribution files
├── docs/                              # Project documentation
├── scripts/                           # Build and run scripts
├── artifacts/                         # Compiled JAR files
├── build.gradle                       # Gradle build configuration
└── README.md                          # This file
```

## Swiss Ephemeris Features

The application provides accurate astronomical calculations:

- **Current Lunar Phase**: Real-time moon phase as percentage (0-100)
- **Lunar Illumination**: Percentage of moon surface illuminated
- **Phase Names**: New Moon, Waxing Crescent, First Quarter, etc.
- **Historical Data**: Calculate phases for any date
- **High Precision**: Professional-grade astronomical algorithms

## Usage Examples

### Real-time Lunar Data

The application automatically:

1. Calculates current lunar phase
2. Sets the eclipse slider to match the moon phase
3. Displays phase name and illumination percentage
4. Updates the visual eclipse simulation

### Testing Swiss Ephemeris
```powershell
# Test current calculations
.\scripts\test-api.bat

# Test specific date calculations using Gradle
.\gradlew run -PmainClass=SimpleApiTest
```

## Troubleshooting

### Common Issues

1. **"Swiss Ephemeris initialization error"**
   - Ensure Java 11+ is installed
   - Check that the Swiss Ephemeris library is in the classpath
   - Verify Gradle dependencies are downloaded

2. **"Lunar calculation error"**
   - This usually indicates a date/time issue
   - Check system clock is set correctly
   - Verify date format in historical calculations

3. **"Class not found: swisseph"**
   - Run `.\gradlew build` to download dependencies
   - Ensure Swiss Ephemeris JAR is in the classpath
   - Check the date format (YYYY-MM-DD)
   - Ensure the date is not in the future

### Debug Mode

Run with debug output:
```bash
java -cp bin EclipseSlider
```

## Development

### Building from Source

```bash
# Clean build
.\gradlew clean build

# Run tests
.\gradlew test

# Generate JAR
.\gradlew jar
```

### IDE Setup

The project is configured for:
- **VS Code**: Launch configurations included
- **Eclipse**: Standard Java project structure
- **IntelliJ**: Gradle import supported

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is provided as-is for educational purposes.

## API Credits

This application uses Swiss Ephemeris for accurate astronomical calculations. Learn more at [Swiss Ephemeris](https://www.astro.com/swisseph/).

---

**Note**: This application is for educational purposes and demonstrates integration with NASA's public APIs. The eclipse simulation is a simplified representation for learning purposes.

## ✅ Project Status Verification

### Current Implementation Status

- **✅ Secure API Key Management**: Fully implemented with template system
- **✅ NASA APOD Integration**: Working with error handling and timeouts
- **✅ Eclipse Simulation**: Interactive slider with real-time visual updates  
- **✅ Class Files Compiled**: All required .class files present in `bin/` directory
- **✅ Configuration Setup**: API key configured and ready to use

### Verified Components
```
bin/
├── EclipseSlider.class           ✅ Main application class
├── EclipseSlider$1.class         ✅ Anonymous listener class
├── EclipseSlider$CirclePanel.class ✅ Custom panel for eclipse rendering
├── EclipseSlider$Listener.class  ✅ Slider event handler
├── MoonPhases.class              ✅ NASA API integration
├── config.properties             ✅ API key configuration
└── config.properties.template    ✅ Configuration template
```

## 🚀 What's Next After Setup?

### Immediate Next Steps (Development & Testing)

1. **Test the Application**

   ```powershell
   cd bin
   java EclipseSlider
   ```
   - Verify the GUI opens correctly
   - Test the slider interaction
   - Check console for NASA API data retrieval

2. **Validate API Integration**

   ```powershell
   java MoonPhases  # Test standalone API calls
   ```
   - Should display JSON response from NASA APOD API
   - Verify no HTTP errors or connectivity issues

3. **Test Different Scenarios**

   - Try running without internet connection
   - Test with invalid API key (temporarily modify config)
   - Test with different date parameters

### Enhancement Opportunities

4. **Display NASA Data in GUI**

   - Parse JSON response to extract image URLs and descriptions
   - Add image display panel to show NASA's astronomy picture
   - Display astronomy facts alongside the eclipse simulation

5. **Add Advanced Features**

   - Save favorite astronomy images locally
   - Add date picker to view historical NASA APOD data
   - Implement caching to reduce API calls
   - Add more celestial animations (moon phases, planetary alignments)

6. **Improve User Experience**

   - Add tooltips explaining eclipse phases
   - Include educational content about eclipses
   - Add sound effects or animations
   - Create full-screen mode for presentations

### Production Deployment

7. **Package for Distribution**

   ```powershell
   .\gradlew jar  # Create executable JAR
   ```

8. **Security Hardening**

   - Add `.gitignore` entry for `config.properties`
   - Consider environment variable support for API keys
   - Add input validation for user-provided dates

9. **Testing & Quality Assurance**

   - Create unit tests for MoonPhases API calls
   - Add integration tests for GUI components
   - Performance testing with multiple API calls

### Advanced Development Paths

10. **Cross-Platform Compatibility**

    - Test on different operating systems
    - Create platform-specific build scripts
    - Add native installers for Windows/Mac/Linux

11. **Modern UI Enhancements**

    - Migrate to JavaFX for modern UI components
    - Add responsive design for different screen sizes
    - Implement dark/light theme switching

12. **Data Integration Expansion**

    - Connect to additional astronomy APIs
    - Add real-time space weather data
    - Include ISS tracking information
    - Implement celestial event notifications

## 📋 Pre-Flight Checklist

Before proceeding with development:

- [ ] Java runtime environment verified
- [ ] Class files compiled successfully
- [ ] NASA API key configured and tested
- [ ] Application runs without errors
- [ ] GUI displays correctly
- [ ] API integration working
- [ ] Configuration files secured

## 🔧 Development Environment Setup

For continued development, ensure you have:

- **Java Development Kit (JDK 11+)**
- **IDE** (Eclipse, IntelliJ IDEA, or VS Code with Java extensions)
- **Version Control** (Git repository initialized)
- **Build Tool** (Gradle already configured)
- **Testing Framework** (JUnit for unit tests)

## 📚 Learning Resources

To enhance this project further:
- [NASA API Documentation](https://api.nasa.gov/)
- [Java Swing Tutorial](https://docs.oracle.com/javase/tutorial/uiswing/)
- [Astronomy Data Sources](https://github.com/astronexus/HYG-Database)
- [Eclipse Calculation Algorithms](https://eclipse.gsfc.nasa.gov/SEcat5/SE5000-4999.html)

## VS Code Development

### Folder Structure

For VS Code development, the workspace contains:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

### Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
