# Receipt Scanner Android App

Receipt Scanner is an Android app for capturing receipts, extracting text with OCR, and storing results locally for tracking and review.

## Features

- Camera capture with CameraX preview
- OCR extraction with ML Kit Text Recognition
- Local persistence with Room
- Receipt organization by category and date
- Material Design 3 UI

## Tech Stack

- Kotlin
- MVVM with LiveData and ViewModel
- CameraX
- ML Kit Text Recognition
- Room (SQLite)
- Kotlin Coroutines

## Prerequisites

- Android Studio (Arctic Fox or later)
- Android SDK 24+
- Java 17+

## Quick Start

1. Clone the repository and open the ReceiptScanner project.
2. Build the debug app:

```powershell
.\gradlew assembleDebug
```

3. Install to a connected device or emulator:

```powershell
.\gradlew installDebug
```

## Project Structure

```text
app/
  src/main/java/com/Geiger/receiptscanner/
    data/
    ui/
    MainActivity.kt
    ReceiptScannerApplication.kt
  src/main/res/
    layout/
    values/
    drawable/
    menu/
  build.gradle
```

## Testing

Run unit tests:

```powershell
.\gradlew testDebugUnitTest
```

Run instrumentation tests:

```powershell
.\gradlew connectedAndroidTest
```

## Privacy and Security

- Data is stored on-device in the local database.
- The app requests only required runtime permissions.

## Troubleshooting

- Build issues in synced folders: stop running Gradle daemons and run a clean build.
- Camera issues: verify camera permissions in Android settings.
- OCR quality issues: improve lighting and framing before capture.

## Related Documentation

- Android CameraX: https://developer.android.com/training/camerax
- ML Kit Text Recognition: https://developers.google.com/ml-kit/vision/text-recognition
- Room Database: https://developer.android.com/training/data-storage/room
- Material Design 3: https://m3.material.io/
