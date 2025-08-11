# Java Monorepo Project Instructions

Multi-project Java repository containing desktop applications, Android apps, and learning projects with focus on GUI development, astronomical calculations, and Bluetooth/serial communication.

## Repository Information

### GitHub Repository

- **Repository**: https://github.com/emgeiger/Java
- **Owner**: emgeiger
- **Primary Branch**: main
- **Remote**: origin (HTTPS)
- **Clone URL**: https://github.com/emgeiger/Java.git

### Branch Structure

- **dev**: Main development branch with latest features
- **main**: Stable release branch
- **feature/***: Feature branches for new functionality

### Repository Setup Commands

```bash
# Clone repository
git clone https://github.com/emgeiger/Java.git

# Add remote (if not already configured)
git remote add origin https://github.com/emgeiger/Java.git

# Switch to primary branch
git checkout Eclipse

# Pull latest changes
git pull origin Eclipse

# Push changes
git push origin Eclipse
```

## Build Configuration ⚠️ CRITICAL

### Version Compatibility Requirements

- **Java Version**: 11+ (tested on Java 21)
- **Build Tool**: Gradle 8.10.2
- **Package Manager**: Gradle Wrapper
- **Target Environment**: Desktop (Swing), Android (Compose)

### Project Migration Status

- ✅ **Modern Build System**: Using Gradle with wrapper
- ✅ **Configuration Cache**: Enabled for faster builds
- ✅ **Clean Project Structure**: Organized and maintainable codebase
- ✅ **Dependency Management**: Swiss Ephemeris, JFreeChart, BlueCove integrated

### Build Commands

```bash
# Clean build
./gradlew clean

# Development build
./gradlew build

# Run main Eclipse application
./gradlew run

# Run Android module
./gradlew :android:build

# Run tests
./gradlew test

# Build all projects
./gradlew buildAll

# Create distributable JAR
./gradlew jar
```

## Project Architecture

### Core Components

- **Eclipse**: Swiss Ephemeris lunar phase calculator with Swing GUI
- **BrainControl**: EEG data visualization and Bluetooth communication
- **GraphicalCylinder**: 3D cylinder visualization and geometry calculations
- **Android Module**: Jetpack Compose mobile version of Eclipse application

### Package/Directory Structure

```
Java/
├── Eclipse/                            # Main astronomical application
│   ├── src/main/java/                  # Desktop Java source
│   │   ├── EclipseSlider.java         # Main Swing application
│   │   ├── MoonPhases.java            # Swiss Ephemeris integration
│   │   └── SimpleApiTest.java         # Testing utility
│   ├── android/                       # Android Compose module
│   │   ├── src/main/java/             # Android Kotlin source
│   │   └── build.gradle               # Android build config
│   ├── build.gradle                   # Desktop build configuration
│   └── README.md                      # Project documentation
├── BrainControl/                      # EEG visualization system
│   ├── BrainPlotter/                  # Main application
│   │   └── src/BrainPlotter/          # Java source
│   └── btapi/                         # Bluetooth API libraries
├── GraphicalCylinder/                 # 3D geometry visualization
│   ├── src/                           # Java source files
│   └── bin/                           # Compiled classes
├── Projects/                          # Learning projects
├── Property/                          # Property management demo
├── RotationalRhythm/                  # Rhythm calculation app
└── SuperMarketProject/                # Shopping cart simulation
```

## Technology Stack

### Core Dependencies

- **Swiss Ephemeris** (2.10.03-2): Professional astronomical calculations
- **JFreeChart** (bundled): Real-time data visualization and charting
- **BlueCove** (2.1.0): Bluetooth communication for EEG devices
- **Java Swing**: Desktop GUI framework

### Android Dependencies

- **Jetpack Compose** (BOM 2024.02.00): Modern Android UI framework
- **Material 3**: Android design system
- **Kotlin** (1.9.22): Primary Android development language
- **Swiss Ephemeris Android**: Mobile astronomical calculations

### Development Dependencies

- **JUnit Jupiter** (5.9.2): Unit testing framework
- **Mockito** (5.1.1): Mocking framework for tests
- **Gradle Wrapper**: Build automation and dependency management

### Testing Framework

- **JUnit 5**: Modern Java testing with parameterized tests
- **Mockito**: Mock objects for unit testing
- **Android Testing**: Compose UI testing and instrumentation

## Key Implementation Patterns

### Swiss Ephemeris Integration

Professional astronomical calculations using Swiss Ephemeris library for accurate lunar phase data, eclipse predictions, and celestial mechanics.

### MVVM Architecture (Android)

```kotlin
// Android ViewModel pattern with StateFlow
class LunarViewModel : ViewModel() {
    private val _lunarData = MutableStateFlow(LunarData())
    val lunarData: StateFlow<LunarData> = _lunarData.asStateFlow()
    
    fun updateLunarPhase() {
        viewModelScope.launch {
            val phase = MoonPhasesAndroid.getCurrentPhase()
            _lunarData.value = phase
        }
    }
}
```

### Bluetooth Communication Pattern

- **Serial Port Management**: javax.comm API for EEG device communication
- **Real-time Data Streaming**: Continuous data acquisition and visualization
- **Device Discovery**: BlueCove library for Bluetooth device management

## Data Management

### Astronomical Data Processing

- **Swiss Ephemeris Integration**: High-precision astronomical calculations
- **Lunar Phase Calculations**: Real-time moon phase percentage and illumination
- **Eclipse Predictions**: Solar and lunar eclipse calculations
- **Celestial Mechanics**: Orbital calculations and astronomical events

### EEG Data Processing

```java
// Real-time EEG data processing
public class EEGDataProcessor {
    private XYSeries dataSeries;
    
    public void processEEGData(double[] samples) {
        for (double sample : samples) {
            dataSeries.add(timestamp++, sample);
            updateChart();
        }
    }
}
```

## UI/UX Design Principles

### Swing Components (Desktop)

- **EclipseSlider**: Custom lunar phase visualization with realistic shadows
- **JFreeChart Integration**: Real-time data plotting and visualization
- **Custom Panels**: Specialized drawing components for astronomical displays

### Jetpack Compose (Android)

- **Material 3 Design**: Modern Android design system
- **Declarative UI**: Compose-based lunar phase display
- **Responsive Layout**: Adaptive UI for different screen sizes

## Development Guidelines

### Error Handling Patterns

- Comprehensive exception handling for astronomical calculations
- Bluetooth connection error recovery and retry mechanisms
- Configuration file validation and fallback values
- Network timeout handling for API communications

### Corporate Network Management

- SSL certificate bypass for development environments
- Proxy configuration templates for enterprise networks
- Offline build capability with local Gradle distributions
- Corporate setup scripts for network-restricted environments

### Testing Strategy

- **Unit Tests**: Swiss Ephemeris calculation verification
- **Integration Tests**: Bluetooth communication testing
- **E2E Tests**: Full application workflow testing
- **Mock Usage**: External dependency mocking for reliable tests

### Code Quality Standards

- Java 11+ modern language features and best practices
- Consistent naming conventions across all projects
- Comprehensive documentation for astronomical algorithms
- Resource management and proper cleanup for GUI components
- Thread safety for real-time data processing

## Common Development Tasks

### Adding New Astronomical Calculations

1. Extend `MoonPhases.java` with new Swiss Ephemeris methods
2. Add corresponding test cases in `MoonPhasesTest.java`
3. Update GUI components to display new data
4. Add Android equivalent in `MoonPhasesAndroid.kt`

### Integrating New Bluetooth Devices

1. Add device profile to `BluetoothDeviceManager.java`
2. Implement device-specific communication protocol
3. Add data parsing and validation logic
4. Create device-specific visualization components

### Creating New GUI Components

1. Design custom Swing component extending appropriate base class
2. Implement proper event handling and listener patterns
3. Add to main application window layout
4. Create corresponding Android Compose component

### Adding Cross-Platform Features

1. Implement desktop version in Java/Swing
2. Create Android equivalent using Kotlin/Compose
3. Ensure consistent behavior across platforms
4. Add platform-specific optimizations

## Testing Commands

### Local Testing

```bash
# Unit tests
./gradlew test

# Integration tests
./gradlew integrationTest

# Android tests
./gradlew :android:test

# Test coverage
./gradlew jacocoTestReport

# Specific test patterns
./gradlew test --tests "*MoonPhases*"
```

### Test Coverage Areas

- ✅ Swiss Ephemeris astronomical calculations
- ✅ Bluetooth communication protocols
- ✅ GUI component functionality
- ✅ Configuration file handling
- ✅ Cross-platform compatibility

## Troubleshooting Guide

### Build Issues

- **Swiss Ephemeris not found**: Ensure Maven Central is accessible or use offline dependencies
- **Bluetooth libraries missing**: Check BlueCove JAR files in lib/ directory
- **Android build fails**: Verify Android SDK and Gradle plugin versions
- **Corporate network issues**: Use setup scripts for proxy and SSL configuration

### Runtime Issues

- **GUI not displaying**: Check Java version compatibility and Swing threading
- **Bluetooth connection failed**: Verify device pairing and COM port availability
- **Astronomical calculations incorrect**: Validate Swiss Ephemeris data files and timezone settings
- **Android app crashes**: Check target SDK compatibility and permissions

### Testing Issues

- **Tests fail in CI**: Use headless mode for GUI tests and mock external dependencies
- **Bluetooth tests unreliable**: Use device simulators and mock communication layers
- **Astronomical test precision**: Account for floating-point precision in calculations

## Code Maintenance Notes

### Performance Considerations

- Swiss Ephemeris calculations are CPU-intensive, cache results when possible
- GUI updates should be performed on Event Dispatch Thread
- Bluetooth communication requires proper buffer management
- Android Compose recomposition optimization for real-time updates

### Security & Privacy

- API keys and configuration stored in secure properties files
- Bluetooth device pairing requires proper authentication
- Corporate network setup includes SSL certificate management
- No sensitive astronomical data transmitted over network

### Accessibility

- High contrast mode support for astronomical displays
- Keyboard navigation for all GUI components
- Screen reader compatibility for text-based data
- Configurable font sizes and display scaling

## Environment-Specific Configuration

### Development Environment

- IntelliJ IDEA or Eclipse IDE with Java 11+ support
- Android Studio for Android module development
- Gradle wrapper for consistent build environment
- Local Swiss Ephemeris data files for offline operation

### Production Environment

- Packaged JAR files for desktop distribution
- Android APK with ProGuard optimization
- Corporate network compatibility scripts
- Configuration templates for end-user setup

### CI/CD Pipeline

- GitHub Actions with Java 11+ and Android SDK
- Automated testing with headless GUI mode
- Multi-platform build verification
- Artifact generation for distribution

---

## Swiss Ephemeris Integration Notes

### Key Implementation Details

- **High Precision**: Swiss Ephemeris provides accurate astronomical calculations
- **Offline Operation**: No internet connection required for basic lunar calculations
- **Cross-Platform**: Same algorithms work on desktop and Android
- **Professional Grade**: Used by observatories and astronomical software

### Common Calculation Patterns

```java
// Lunar phase calculation
public static double getCurrentLunarPhase() {
    double julianDay = SwissEph.swe_julday(year, month, day, hour, SE_GREG_CAL);
    double[] moonPos = new double[6];
    SwissEph.swe_calc_ut(julianDay, SE_MOON, SEFLG_SWIEPH, moonPos);
    return calculatePhaseFromPosition(moonPos);
}
```

### EEG Data Processing Notes

- **Real-time Requirements**: Sub-millisecond data processing for smooth visualization
- **Bluetooth Protocol**: Custom protocol for EEG device communication
- **Data Validation**: Signal quality assessment and noise filtering
- **Visualization**: JFreeChart integration for real-time plotting

### Corporate Network Compatibility

- SSL bypass scripts for development environments
- Proxy configuration templates for enterprise networks
- Offline Gradle distribution for network-restricted environments
- Corporate certificate integration for secure connections
