# Swiss Ephemeris Lunar Monitor - Android Version

This is the Android version of the Swiss Ephemeris Lunar Phase Monitor, built with Jetpack Compose and Kotlin.

## Features

- **Real-time Lunar Data**: Displays current moon phase, illumination percentage, and phase name
- **Accurate Calculations**: Uses Swiss Ephemeris library for precise astronomical calculations
- **Modern UI**: Built with Jetpack Compose and Material 3 design
- **Visual Moon Display**: Interactive moon phase visualization with starfield background
- **Live Updates**: Real-time lunar data updates on app launch

## Technical Stack

- **Kotlin**: Modern Android development language
- **Jetpack Compose**: Declarative UI toolkit
- **Material 3**: Latest Material Design components
- **Swiss Ephemeris**: Professional astronomical calculation library
- **MVVM Architecture**: Clean separation of concerns with ViewModel pattern

## Swiss Ephemeris Integration

The app uses the Swiss Ephemeris library (version 2.10.03-2) for:
- Calculating precise lunar phase percentages
- Determining moon illumination levels
- Computing lunar position and phase names
- Providing astronomical accuracy for moon tracking

## Moon Phase Visualization

The custom `MoonPhaseVisualization` composable provides:
- Accurate moon phase rendering based on Swiss Ephemeris calculations
- Starfield background for astronomical context
- Lunar shadow visualization showing current phase
- Moon surface details including craters

## Build Requirements

- Android Studio Arctic Fox or later
- Minimum SDK: API 24 (Android 7.0)
- Target SDK: API 34 (Android 14)
- Kotlin 1.9.22+
- Gradle 8.10.2+

## Dependencies

### Core Android
- AndroidX Core KTX 1.12.0
- Lifecycle Runtime KTX 2.7.0
- Activity Compose 1.8.2

### Jetpack Compose
- Compose BOM 2024.02.00
- UI, Graphics, Tooling Preview
- Material 3

### Astronomical Calculations
- Swiss Ephemeris 2.10.03-2

### Networking (Future Enhancement)
- OkHttp 4.12.0
- Retrofit 2.9.0
- Gson Converter 2.9.0

### Date/Time
- Kotlinx DateTime 0.5.0

## Project Structure

```
android/
├── src/main/
│   ├── java/com/emgeiger/swissephemeris/lunar/
│   │   ├── MainActivity.kt              # Main activity with Compose UI
│   │   ├── LunarViewModel.kt            # MVVM ViewModel for data management
│   │   ├── MoonPhasesAndroid.kt         # Swiss Ephemeris integration
│   │   └── ui/theme/                    # Compose theme configuration
│   ├── res/                             # Android resources
│   └── AndroidManifest.xml              # App manifest
├── build.gradle                         # Android module build configuration
└── proguard-rules.pro                   # ProGuard rules for release builds
```

## Key Components

### MainActivity.kt
- Jetpack Compose entry point
- Hosts the main UI with header, moon visualization, and data panel
- Uses Material 3 theming with dark space theme

### LunarViewModel.kt
- MVVM pattern implementation
- Manages lunar data state using Kotlin StateFlow
- Handles Swiss Ephemeris calculations in coroutines

### MoonPhasesAndroid.kt
- Swiss Ephemeris wrapper for Android
- Provides accurate lunar phase calculations
- Calculates moon position, illumination, and phase names

### UI Components
- **SwissEphemerisHeader**: App title and branding
- **MoonPhaseVisualization**: Custom Canvas drawing for moon phases
- **LunarDataPanel**: Data display with Material 3 cards

## Building the Android Version

1. Ensure Android Studio is installed with latest SDK
2. Navigate to the Eclipse project root
3. Run the Android module build:

```bash
./gradlew :android:build
```

4. To install on connected device:

```bash
./gradlew :android:installDebug
```

## Running the App

1. Connect an Android device or start an emulator
2. Build and run from Android Studio, or use Gradle:

```bash
./gradlew :android:run
```

The app will launch displaying the current lunar phase with real-time Swiss Ephemeris calculations.

## Features Comparison with Desktop Version

| Feature | Desktop (Swing) | Android (Compose) |
|---------|----------------|-------------------|
| Swiss Ephemeris | ✅ | ✅ |
| Moon Visualization | ✅ | ✅ |
| Real-time Data | ✅ | ✅ |
| Starfield Background | ✅ | ✅ |
| Phase Names | ✅ | ✅ |
| UI Framework | Java Swing | Jetpack Compose |
| Platform | Desktop | Android |

## Future Enhancements

- [ ] Real-time updates with periodic refresh
- [ ] Historical lunar phase data
- [ ] Moon phase calendar
- [ ] Notification system for lunar events
- [ ] Augmented reality moon tracking
- [ ] Multi-language support
- [ ] Accessibility improvements

## Swiss Ephemeris Credits

This application uses the Swiss Ephemeris library, developed by Astrodienst AG, for precise astronomical calculations. Swiss Ephemeris is the industry standard for astronomical software requiring high precision.

## License

This Android module follows the same licensing as the parent Eclipse project. The Swiss Ephemeris library is used under its respective license terms.
