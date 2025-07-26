# Receipt Scanner Android App

A modern Android application for scanning and managing receipts using camera capture and OCR technology. Built with Kotlin, CameraX, ML Kit, and Room database.

## 🚀 Features

- **📱 Camera Integration**: Real-time camera preview with CameraX
- **🔍 OCR Processing**: Extract text from receipts using ML Kit Text Recognition
- **💾 Local Storage**: Room database for secure local data storage
- **📊 Expense Tracking**: Organize receipts by categories and dates
- **🏢 Corporate Ready**: Pre-configured for corporate network environments
- **🎨 Material Design 3**: Modern UI following Material Design guidelines
- **🔒 Privacy Focused**: All data stored locally on device

## 🛠️ Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM with LiveData
- **Camera**: CameraX
- **OCR**: ML Kit Text Recognition
- **Database**: Room with SQLite
- **UI**: Material Design 3 Components
- **Build System**: Gradle with corporate network support
- **Async**: Kotlin Coroutines

## 📋 Prerequisites

- Android Studio Arctic Fox or later
- Android SDK 24+ (Android 7.0)
- Java 17+
- Camera permission for receipt scanning

## 🚦 Quick Start

### 1. Clone and Setup

```bash
git clone <repository-url>
cd ReceiptScanner
```

### 2. Corporate Network Setup (if applicable)

If you're in a corporate environment with SSL/proxy issues:

```powershell
# Run the setup script
.\setup-corporate-network.ps1
```

### 3. Build and Run

```bash
# Build the project
.\gradlew assembleDebug

# Install on device/emulator
.\gradlew installDebug
```

## 📁 Project Structure

```
app/
├── src/main/java/com/ovintiv/receiptscanner/
│   ├── data/
│   │   └── database/           # Room database entities and DAOs
│   ├── ui/
│   │   └── scan/              # Camera and OCR functionality
│   ├── MainActivity.kt        # Main navigation activity
│   └── ReceiptScannerApplication.kt
├── src/main/res/
│   ├── layout/                # XML layout files
│   ├── values/                # Strings, colors, themes
│   ├── drawable/              # Icons and graphics
│   └── menu/                  # Navigation menus
└── build.gradle               # Module dependencies
```

## 🔧 Configuration

### Corporate Network

The app includes pre-configured settings for corporate environments:

- **gradle.properties**: SSL bypass and proxy settings
- **setup-corporate-network.ps1**: Automated setup script
- See `CORPORATE-NETWORK-GUIDE.md` for detailed troubleshooting

### Database Schema

```kotlin
@Entity(tableName = "receipts")
data class Receipt(
    val merchantName: String,
    val totalAmount: Double,
    val date: String,
    val category: String,
    val imagePath: String,
    val ocrText: String,
    // ... additional fields
)
```

## 📸 How to Use

1. **Scan Receipt**: Open the app and tap the camera button
2. **Position Receipt**: Align the receipt within the camera frame
3. **Capture**: Tap the capture button to take a photo
4. **OCR Processing**: The app automatically extracts text data
5. **Review & Save**: Verify the extracted information and save

## 🔍 OCR Features

The app automatically extracts:
- **Merchant Name**: Business or store name
- **Total Amount**: Purchase total with currency
- **Date**: Transaction date
- **Receipt Text**: Full OCR text for reference

## 🧪 Testing

```bash
# Run unit tests
.\gradlew test

# Run instrumentation tests
.\gradlew connectedAndroidTest

# Generate test coverage report
.\gradlew jacocoTestReport
```

## 🏢 Corporate Environment

### SSL Certificate Issues

If you encounter SSL errors in corporate networks:

1. Run the provided setup script: `.\setup-corporate-network.ps1`
2. Configure proxy settings in `gradle.properties`
3. Import corporate certificates if required

### Proxy Configuration

Edit `gradle.properties` to add your proxy settings:

```properties
systemProp.http.proxyHost=proxy.company.com
systemProp.http.proxyPort=8080
systemProp.https.proxyHost=proxy.company.com
systemProp.https.proxyPort=8080
```

## 🔒 Privacy & Security

- **Local Storage**: All data stored locally on device
- **No Cloud Sync**: No automatic cloud backup
- **Permissions**: Only camera and storage permissions required
- **Data Encryption**: Consider implementing encryption for sensitive data

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/new-feature`
3. Make your changes and test thoroughly
4. Commit your changes: `git commit -m 'Add new feature'`
5. Push to the branch: `git push origin feature/new-feature`
6. Submit a pull request

## 📄 License

This project is part of the Ovintiv Java monorepo. See the main repository license for details.

## 🔗 Related Documentation

- [Android CameraX Documentation](https://developer.android.com/training/camerax)
- [ML Kit Text Recognition](https://developers.google.com/ml-kit/vision/text-recognition)
- [Room Database Guide](https://developer.android.com/training/data-storage/room)
- [Material Design 3](https://m3.material.io/)

## 🆘 Troubleshooting

### Common Issues

1. **Build Fails**: Run `.\setup-corporate-network.ps1` for corporate networks
2. **Camera Not Working**: Check camera permissions in device settings
3. **OCR Accuracy**: Ensure good lighting and clear receipt text
4. **Database Errors**: Clear app data or reinstall the app

### Getting Help

- Check the [Issues](../../issues) section for known problems
- Review `CORPORATE-NETWORK-GUIDE.md` for network-related issues
- Contact the development team for additional support

---

Built with ❤️ using modern Android development practices.
