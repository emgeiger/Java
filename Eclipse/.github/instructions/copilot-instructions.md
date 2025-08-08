# Java Eclipse Monorepo - Copilot Instructions

A comprehensive Java development monorepo containing multiple astronomical, graphical, and utility applications with modern build systems and cross-platform support.

## Repository Information

### GitHub Repository

- **Repository**: https://github.com/emgeiger/Java
- **Owner**: emgeiger
- **Primary Branch**: feature/braincontrol-copilot-instructions
- **Remote**: origin (HTTPS)
- **Clone URL**: https://github.com/emgeiger/Java.git

### Branch Structure

- **feature/braincontrol-copilot-instructions**: Main development branch with latest features
- **main**: Stable release branch
- **feature/***: Feature branches for new functionality

### Repository Setup Commands

```bash
# Clone repository
git clone https://github.com/emgeiger/Java.git

# Add remote (if not already configured)
git remote add origin https://github.com/emgeiger/Java.git

# Switch to primary branch
git checkout feature/braincontrol-copilot-instructions

# Pull latest changes
git pull origin feature/braincontrol-copilot-instructions

# Push changes
git push origin feature/braincontrol-copilot-copilot-instructions
```

## Build Configuration ⚠️ CRITICAL

### Version Compatibility Requirements

- **Java Version**: OpenJDK 21.0.7 LTS (minimum Java 11)
- **Build Tool**: Gradle 8.5
- **Package Manager**: Gradle with Maven Central repositories
- **Target Environment**: Desktop (Swing), Android, Cross-platform

### Project Migration Status

- ✅ **Modern Build System**: Using Gradle 8.5 with offline capabilities
- ✅ **Configuration Cache**: Enabled for faster builds
- ✅ **Clean Project Structure**: Organized monorepo with multiple applications
- ✅ **Dependency Management**: Swiss Ephemeris, JFreeChart, and testing frameworks

### Build Commands

```bash
# Clean build
.\gradlew clean build

# Development build
.\gradlew build

# Production build
.\gradlew jar

# Run tests
.\gradlew test

# Run specific application
.\gradlew run

# Android build (from android subdirectory)
.\gradlew assembleDebug
```

## Project Architecture

### Core Components

- **Eclipse Slider**: Swiss Ephemeris lunar phase calculator and visualizer
- **BrainControl/BrainPlotter**: EEG/brain wave data visualization and analysis
- **Android Module**: Cross-platform lunar phase monitor with Jetpack Compose
- **Utility Applications**: Templates, Inventory management, and various GUI tools

### Package/Directory Structure

```
Java/Eclipse/
├── src/                            # Main Java source files
│   ├── EclipseSlider.java          # Primary astronomical application
│   ├── MoonPhases.java             # Swiss Ephemeris integration
│   └── SimpleApiTest.java          # API testing utilities
├── android/                        # Android application module
│   ├── src/main/kotlin/            # Kotlin source files
│   └── build.gradle                # Android-specific build configuration
├── BrainControl/                   # Neural interface applications
│   └── BrainPlotter/               # EEG visualization tools
├── bin/                            # Compiled class files
├── build/                          # Gradle build artifacts
├── gradle/                         # Gradle wrapper configuration
└── scripts/                        # Build and utility scripts
```

## Technology Stack

### Core Dependencies

- **Swiss Ephemeris** (2.10.03-2): Professional astronomical calculation library
- **Java Swing**: Desktop GUI framework for cross-platform applications
- **JFreeChart**: Data visualization and charting library
- **Jetpack Compose**: Modern Android UI toolkit (Android module)

### Development Dependencies

- **JUnit Jupiter**: Unit testing framework with parameterized tests
- **Mockito**: Mocking framework for isolated unit tests
- **Gradle Test Logger**: Enhanced test output formatting

### Testing Framework

- **JUnit 5**: Modern testing framework with extensive annotation support
- **Mockito**: Mock object framework for comprehensive unit testing
- **Gradle Test Suite**: Integrated testing with build system

## Key Implementation Patterns

### Swiss Ephemeris Integration

The project uses Swiss Ephemeris for accurate astronomical calculations, providing professional-grade lunar phase data and eclipse predictions.

### MVC Architecture Pattern

```java
// Model-View-Controller separation
public class EclipseSlider extends JFrame {
    private MoonPhases moonPhases;        // Model
    private MoonPhasePanel displayPanel;  // View
    private SliderListener controller;    // Controller
}
```

### Secure Configuration Management

- **Template System**: Configuration templates for sensitive data
- **Environment Variables**: Support for runtime configuration
- **Git Ignore Protection**: Automatic exclusion of sensitive files

## Data Management

### Astronomical Data Layer

- **Swiss Ephemeris**: High-precision astronomical calculations
- **Real-time Updates**: Live lunar phase and eclipse data
- **Historical Data**: Support for past and future astronomical events

### Configuration Management Implementation

```java
// Secure API key and configuration handling
public class ConfigurationManager {
    private static final String CONFIG_FILE = "config.properties";
    private Properties loadConfiguration() {
        // Secure loading with fallback to templates
    }
}
```

## UI/UX Design Principles

### Java Swing Components

- **Custom Panels**: Specialized rendering for astronomical visualizations
- **Event Listeners**: Responsive user interface with real-time updates
- **Graphics2D**: High-quality rendering with antialiasing and smooth animations

### Design System Features

- Professional astronomical visualization standards
- Responsive layout design for multiple screen sizes
- Accessibility compliance with keyboard navigation
- Cross-platform look and feel consistency

## Development Guidelines

### Error Handling Patterns

- Comprehensive exception handling for astronomical calculations
- Graceful degradation when external services are unavailable
- Detailed error logging with contextual information
- User-friendly error messages for end-user scenarios

### Security Management

- Secure API key storage with template-based configuration
- Input validation for all user-provided data
- Protection against path traversal and injection attacks
- Encrypted storage for sensitive astronomical data

### Testing Strategy

- **Unit Tests**: Comprehensive coverage of astronomical calculations
- **Integration Tests**: Swiss Ephemeris library integration verification
- **UI Tests**: Swing component behavior and rendering tests
- **Mock Usage**: Isolated testing of external dependencies

### Code Quality Standards

- Java coding conventions with comprehensive JavaDoc documentation
- Consistent indentation and formatting using IDE standards
- Comprehensive error handling with specific exception types
- Performance optimization for real-time astronomical calculations
- Memory management for long-running GUI applications

## Common Development Tasks

### Adding New Astronomical Features

1. Implement calculations using Swiss Ephemeris APIs
2. Create corresponding visualization components
3. Add comprehensive unit tests for accuracy
4. Update documentation with feature descriptions

### Extending GUI Components

1. Create custom JPanel subclasses for specialized displays
2. Implement proper event handling and user interaction
3. Add Graphics2D rendering for smooth visual updates
4. Ensure cross-platform compatibility and accessibility

### Android Module Development

1. Use Jetpack Compose for modern UI development
2. Implement MVVM architecture with ViewModels
3. Integrate Swiss Ephemeris through JNI or equivalent
4. Follow Material 3 design guidelines

## Testing Commands

### Local Testing

```bash
# Unit tests
.\gradlew test

# Integration tests
.\gradlew integrationTest

# Android tests
.\gradlew connectedAndroidTest

# Test coverage
.\gradlew jacocoTestReport

# Specific test patterns
.\gradlew test --tests "*MoonPhases*"
```

### Test Coverage Areas

- ✅ Swiss Ephemeris astronomical calculations
- ✅ GUI component rendering and interaction
- ✅ Configuration management and security
- ✅ Cross-platform compatibility
- ✅ Performance and memory usage

## Troubleshooting Guide

### Build Issues

- **Gradle Daemon Issues**: Run `.\gradlew --stop` then retry build
- **Java Version Conflicts**: Ensure JAVA_HOME points to Java 11+
- **Swiss Ephemeris Loading**: Check native library paths and permissions

### Runtime Issues

- **GUI Rendering Problems**: Verify Graphics2D antialiasing settings
- **Astronomical Data Accuracy**: Validate Swiss Ephemeris calculations
- **Configuration Loading**: Check file permissions and template setup

### Testing Issues

- **Test Isolation**: Use Mockito for external dependency mocking
- **Astronomical Precision**: Allow for floating-point calculation tolerance
- **GUI Testing**: Use appropriate threading for Swing components

## Code Maintenance Notes

### Performance Considerations

- Optimize astronomical calculations for real-time updates
- Use efficient Graphics2D rendering techniques
- Implement proper memory management for GUI components
- Cache frequently accessed Swiss Ephemeris data

### Security & Privacy

- Protect sensitive configuration data from version control
- Validate all user inputs for astronomical calculations
- Implement secure storage for API keys and credentials
- Follow OWASP guidelines for Java application security

### Accessibility

- Provide keyboard navigation for all GUI components
- Implement proper screen reader support
- Use high contrast colors for astronomical visualizations
- Support multiple operating system accessibility standards

## Environment-Specific Configuration

### Development Environment

- Local Gradle distribution for offline development
- Hot reload support for rapid iteration
- Comprehensive logging for debugging astronomical calculations

### Production Environment

- Optimized JAR packaging for distribution
- Secure configuration management
- Performance monitoring and error reporting

### CI/CD Pipeline

- Automated testing for all astronomical calculations
- Cross-platform build verification
- Security scanning for dependencies and code

## Project-Specific Features

### Swiss Ephemeris Integration

The project leverages Swiss Ephemeris for professional-grade astronomical calculations:

- **Lunar Phase Calculation**: Accurate moon phase percentages and illumination
- **Eclipse Prediction**: Solar and lunar eclipse timing and visibility
- **Planetary Positions**: Precise celestial body positioning
- **Historical Data**: Support for past and future astronomical events

### Multi-Platform Architecture

- **Desktop Application**: Java Swing for cross-platform compatibility
- **Android Module**: Kotlin with Jetpack Compose for mobile
- **Build System**: Gradle with multi-module support
- **Testing**: Comprehensive test suite for all platforms

### Scientific Accuracy

- **Astronomical Precision**: Swiss Ephemeris provides sub-arcsecond accuracy
- **Real-time Updates**: Live astronomical data calculation
- **Historical Range**: Supports calculations from 13201 BCE to 17191 CE
- **Professional Standards**: Meets observatory and research requirements

---

## Specialized Development Notes

### Swiss Ephemeris Development

When working with Swiss Ephemeris calculations:

```java
// Example of proper Swiss Ephemeris usage
public double calculateMoonPhase(double julianDay) {
    // Always handle potential calculation errors
    try {
        SweDate date = new SweDate(julianDay);
        double moonLon = sw.swe_calc_ut(julianDay, SweConst.SE_MOON, 
                                       SweConst.SEFLG_SWIEPH);
        return calculatePhaseFromLongitude(moonLon);
    } catch (Exception e) {
        logger.error("Swiss Ephemeris calculation failed", e);
        return -1.0; // Error indicator
    }
}
```

### GUI Performance Optimization

For smooth astronomical visualizations:

```java
// Efficient Graphics2D rendering
@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    
    // Enable antialiasing for smooth rendering
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                         RenderingHints.VALUE_ANTIALIAS_ON);
    
    // Use double buffering for smooth updates
    // Implement efficient redraw regions
}
```

### Configuration Security

Always protect sensitive astronomical data and API keys:

```java
// Secure configuration loading
private Properties loadAstronomicalConfig() {
    Properties props = new Properties();
    try (InputStream is = getClass().getResourceAsStream("/config.properties")) {
        if (is != null) {
            props.load(is);
        } else {
            // Fallback to template or environment variables
            loadFromTemplate();
        }
    } catch (IOException e) {
        logger.warn("Configuration loading failed, using defaults");
    }
    return props;
}
```

This monorepo represents a sophisticated astronomical software suite with professional-grade calculations, modern build systems, and comprehensive cross-platform support. All development should maintain the high standards of scientific accuracy and software engineering best practices established in the existing codebase.
