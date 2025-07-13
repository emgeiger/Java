# Swiss Ephemeris Lunar Monitor - Cross-Platform Project

This project provides Swiss Ephemeris-powered lunar phase monitoring applications for both desktop and Android platforms.

## Project Overview

The Swiss Ephemeris Lunar Monitor demonstrates professional astronomical calculations using the Swiss Ephemeris library, providing accurate lunar phase data across multiple platforms:

- **Desktop Version**: Java Swing application with traditional GUI
- **Android Version**: Modern Kotlin/Compose mobile application

## Platform Comparison

| Feature | Desktop (Java/Swing) | Android (Kotlin/Compose) |
|---------|---------------------|---------------------------|
| **UI Framework** | Java Swing | Jetpack Compose |
| **Language** | Java 11+ | Kotlin 1.9+ |
| **Swiss Ephemeris** | ✅ Full Integration | ✅ Full Integration |
| **Moon Visualization** | Custom Graphics2D | Custom Canvas API |
| **Real-time Updates** | ✅ On Launch | ✅ On Launch |
| **Starfield Background** | ✅ Animated | ✅ Procedural |
| **Corporate Network** | ✅ Gradle Setup | ✅ Android Setup |

## Project Structure

```
Eclipse/
├── src/                          # Desktop Java source
│   ├── EclipseSlider.java       # Main Swing application
│   ├── MoonPhases.java          # Swiss Ephemeris integration
│   └── test/                    # Desktop tests
├── android/                      # Android module
│   ├── src/main/java/           # Android Kotlin source
│   │   └── com/emgeiger/swissephemeris/lunar/
│   │       ├── MainActivity.kt   # Compose main activity
│   │       ├── LunarViewModel.kt # MVVM pattern
│   │       └── MoonPhasesAndroid.kt # Android Swiss Ephemeris
│   ├── build.gradle             # Android build configuration
│   └── README.md                # Android-specific documentation
├── build.gradle                 # Desktop build configuration
├── build.gradle.root            # Root project configuration
├── settings.gradle              # Multi-module settings
├── setup-android-corporate.ps1  # Android corporate network setup
└── docs/                        # Project documentation
```

## Swiss Ephemeris Integration

Both platforms use the Swiss Ephemeris library (version 2.10.03-2) for:

### Accurate Calculations
- Lunar phase percentages (0-100%)
- Moon illumination levels
- Phase name determination
- Lunar position calculations

### Professional Features
- Julian day calculations
- Ecliptic longitude computations
- Moon age calculations
- Real-time astronomical data

## Building the Project

### Prerequisites
- **Java 11+**: Required for both platforms
- **Gradle 8.0+**: Build system
- **Android Studio**: For Android development
- **Corporate Network**: See setup scripts for enterprise environments

### Desktop Application
```bash
# Build desktop version
./gradlew build

# Run desktop application
./gradlew run

# Run with corporate network setup
./setup-corporate-network.ps1
```

### Android Application
```bash
# Build Android module
./gradlew :android:build

# Install on device
./gradlew :android:installDebug

# Corporate network setup
./setup-android-corporate.ps1
```

### Build Both Platforms
```bash
# Build all modules
./gradlew buildAll

# Desktop + Android
./gradlew build :android:build
```

## Corporate Network Support

Following the **emgeiger/Gradle** template pattern, this project includes comprehensive corporate network support:

### Setup Scripts
- `setup-corporate-network.ps1` - Desktop Gradle configuration
- `setup-android-corporate.ps1` - Android-specific setup
- Manual SSL certificate handling
- Proxy configuration templates

### Configuration Files
- `gradle.properties` - Corporate proxy settings
- `android/gradle.properties` - Android-specific network config
- SSL bypass options for development
- Corporate certificate integration

## Swiss Ephemeris Features

### Desktop Implementation (`MoonPhases.java`)
```java
// Get current lunar phase percentage
double phase = MoonPhases.getCurrentLunarPhase();

// Get illumination percentage
double illumination = MoonPhases.getLunarIllumination();

// Get phase name
String phaseName = MoonPhases.getPhaseName(phase);
```

### Android Implementation (`MoonPhasesAndroid.kt`)
```kotlin
// Initialize Swiss Ephemeris
val moonPhases = MoonPhasesAndroid()

// Get detailed lunar information
val lunarInfo = moonPhases.getDetailedLunarInfo()

// Calculate moon age in days
val moonAge = moonPhases.getMoonAge()
```

## User Interface Design

### Desktop (Swing)
- **Header Panel**: Swiss Ephemeris branding with raised border
- **Moon Visualization**: 300x300 pixel Graphics2D canvas
- **Data Panel**: GridBagLayout with formatted lunar information
- **Starfield**: Procedural star generation
- **Color Scheme**: Space-themed dark background

### Android (Compose)
- **Material 3**: Modern Android design system
- **Responsive Layout**: Adaptive to different screen sizes
- **Canvas Drawing**: Hardware-accelerated moon rendering
- **Dark Theme**: Space-themed UI consistent with desktop
- **StateFlow**: Reactive data updates

## Technical Architecture

### Desktop Architecture
- **Main Class**: `EclipseSlider extends JFrame`
- **Data Model**: Static methods in `MoonPhases`
- **UI Pattern**: Traditional Swing component hierarchy
- **Threading**: SwingUtilities for UI updates

### Android Architecture
- **MVVM Pattern**: ViewModel + StateFlow
- **Compose UI**: Declarative interface components
- **Coroutines**: Async lunar data calculations
- **Material 3**: Modern design components

## Development Workflow

### 1. Desktop Development
```bash
# Start with desktop version
./gradlew run

# Make changes to EclipseSlider.java
# Test Swiss Ephemeris integration
# Verify UI components
```

### 2. Android Development
```bash
# Switch to Android development
cd android/

# Open in Android Studio
# Test on emulator/device
# Debug Compose UI
```

### 3. Cross-Platform Testing
```bash
# Test both platforms
./gradlew buildAll

# Verify Swiss Ephemeris consistency
# Compare visual outputs
# Validate calculation accuracy
```

## Future Enhancements

### Planned Features
- [ ] **Real-time Updates**: Periodic lunar data refresh
- [ ] **Historical Data**: Past and future lunar phases
- [ ] **Calendar Integration**: Lunar calendar display
- [ ] **Notifications**: Lunar event alerts (Android)
- [ ] **Augmented Reality**: AR moon tracking (Android)
- [ ] **Web Version**: Progressive Web App
- [ ] **API Service**: REST API for lunar data

### Technical Improvements
- [ ] **Unit Tests**: Comprehensive test coverage
- [ ] **CI/CD Pipeline**: Automated builds and deployment
- [ ] **Performance**: Optimized calculations
- [ ] **Accessibility**: Screen reader support
- [ ] **Internationalization**: Multi-language support
- [ ] **Offline Mode**: Cached lunar data

## Swiss Ephemeris Credits

This project uses the **Swiss Ephemeris** library developed by **Astrodienst AG**. Swiss Ephemeris is the industry standard for high-precision astronomical calculations and is used by professional astronomers and astrologers worldwide.

- **Website**: https://www.astro.com/swisseph/
- **Documentation**: Swiss Ephemeris Programming Interface
- **License**: Swiss Ephemeris Public License
- **Version**: 2.10.03-2 (Java implementation)

## Project Templates

This project demonstrates the integration of the **emgeiger/Gradle** template for:
- Corporate network configuration
- Multi-platform Gradle builds
- Professional development setup
- SSL certificate handling
- Enterprise environment support

## Contributing

1. **Fork the repository**
2. **Create feature branch** (`git checkout -b feature/lunar-enhancement`)
3. **Make changes** to desktop or Android versions
4. **Test on both platforms** where applicable
5. **Submit pull request** with detailed description

## License

This project inherits licensing from the parent Eclipse repository. Swiss Ephemeris library usage follows its respective license terms.

---

**Swiss Ephemeris Lunar Monitor** - Professional astronomical calculations for desktop and mobile platforms.
