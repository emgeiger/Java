# 🥗 Nutrition Calculator - Android Edition

> **A modern, feature-rich nutrition tracking app built with Kotlin and Jetpack Compose**

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-brightgreen.svg)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## 📱 Features

### Core Functionality

- **Ingredient Management**: Add, edit, and organize ingredients with detailed nutrition information
- **Recipe Creation**: Build custom recipes with automatic nutrition calculation
- **Portion Scaling**: Dynamically adjust serving sizes and recalculate nutrition
- **Multiple Units**: Support for metric and imperial measurement systems

### Enhanced Features

- **📱 Barcode Scanning**: Instantly add products by scanning barcodes
- **☁️ Cloud Sync**: Firebase integration for cross-device synchronization
- **🔍 Smart Search**: Access to USDA FoodData Central and Open Food Facts databases
- **📊 Nutrition Tracking**: Comprehensive macro and micronutrient analysis
- **📤 Export/Import**: Share recipes and data in multiple formats
- **🎨 Modern UI**: Material Design 3 with dark mode support

## 🚀 Quick Start

### Prerequisites

- **Android Studio** or **VS Code** with Android extensions
- **Android SDK** (API level 24+)
- **Java 11+** or **Kotlin 1.9.10+**

### Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/emgeiger/Java.git
   cd Java/NutritionCalculator
   ```

2. **Set up the environment**

   ```batch
   # Windows
   .\setup-android.bat
   
   # Or manually set ANDROID_HOME
   set ANDROID_HOME=C:\Users\%USERNAME%\AppData\Local\Android\Sdk
   ```

3. **Build the project**

   ```batch
   .\gradlew.bat assembleDebug
   ```

4. **Install on device**

   ```batch
   .\gradlew.bat installDebug
   ```

## 🏗️ Architecture

The app follows **Clean Architecture** principles with **MVVM** pattern:

```
📱 Presentation Layer (UI)
├── 🎨 Jetpack Compose UI
├── 🔄 ViewModels (State Management)
└── 🧭 Navigation (Compose Navigation)

💼 Domain Layer (Business Logic)
├── 📋 Use Cases
├── 📊 Models
└── 🔌 Repository Interfaces

💾 Data Layer (Storage & Network)
├── 🏠 Local Database (Room)
├── 🌐 Remote APIs (Retrofit)
└── 📦 Repository Implementations
```

### Tech Stack

| Category | Technology |
|----------|------------|
| **Language** | Kotlin 1.9.10 |
| **UI Framework** | Jetpack Compose |
| **Architecture** | MVVM + Clean Architecture |
| **Database** | Room (SQLite) |
| **Networking** | Retrofit + OkHttp |
| **Dependency Injection** | Hilt |
| **Async Programming** | Coroutines + Flow |
| **Testing** | JUnit + Espresso + Compose Testing |
| **Build System** | Gradle (Kotlin DSL) |

## 📖 Usage Examples

### Adding an Ingredient

```kotlin
// Simple ingredient creation
val apple = Ingredient(
    name = "Apple",
    nutrition = NutritionFacts(
        calories = 95,
        carbohydrates = 25.0,
        fiber = 4.0,
        sugars = 19.0
    ),
    servingSize = 1.0,
    servingUnit = MeasurementUnit.MEDIUM_PIECE
)
```

### Creating a Recipe

```kotlin
// Recipe with multiple ingredients
val smoothie = Recipe(
    name = "Green Smoothie",
    servings = 2,
    ingredients = listOf(
        RecipeIngredient(apple, 1.0),
        RecipeIngredient(spinach, 50.0, MeasurementUnit.GRAM),
        RecipeIngredient(banana, 1.0)
    )
)

// Auto-calculated nutrition
val totalNutrition = smoothie.calculateTotalNutrition()
```

### Barcode Scanning

```kotlin
@Composable
fun ScannerScreen() {
    BarcodeScanner { barcode ->
        // Automatically fetch product data
        viewModel.addProductByBarcode(barcode)
    }
}
```

## 🧪 Testing

### Run Tests

```bash
# Unit tests
./gradlew test

# Instrumentation tests
./gradlew connectedAndroidTest

# UI tests
./gradlew connectedDebugAndroidTest
```

### Test Coverage

- **Unit Tests**: 85%+ coverage
- **Integration Tests**: Core functionality covered
- **UI Tests**: Critical user flows tested

## 📁 Project Structure

```
app/src/main/kotlin/com/nutrition/calculator/
├── 📱 presentation/
│   ├── screens/           # Compose screens
│   ├── components/        # Reusable UI components
│   ├── theme/            # Material Design theme
│   └── navigation/       # Navigation setup
├── 💼 domain/
│   ├── model/            # Data models
│   ├── repository/       # Repository interfaces
│   └── usecase/          # Business logic
├── 💾 data/
│   ├── local/            # Room database
│   ├── remote/           # API services
│   └── repository/       # Repository implementations
├── 🔌 di/                # Dependency injection
└── 🛠️ util/              # Utility classes
```

## 🔧 Configuration

### Firebase Setup (Optional)

1. Create a Firebase project
2. Add `google-services.json` to `app/` directory
3. Enable Firestore and Authentication
4. Configure API keys in `local.properties`

### API Keys

Create `local.properties` file:

```properties
USDA_API_KEY=your_usda_api_key
FIREBASE_API_KEY=your_firebase_key
```

## 🤝 Contributing

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

### Code Style

- Follow [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use **ktlint** for formatting
- Write tests for new features
- Update documentation

## 📈 Roadmap

### Version 2.1 (Current)

- ✅ Core nutrition calculation
- ✅ Jetpack Compose UI
- ✅ Room database integration
- ✅ Barcode scanning
- ✅ Firebase sync

### Version 2.2 (Planned)

- [ ] Meal planning calendar
- [ ] Shopping list generation
- [ ] Recipe sharing community
- [ ] Advanced analytics

### Version 3.0 (Future)

- [ ] Kotlin Multiplatform (iOS)
- [ ] Wear OS companion
- [ ] Voice commands
- [ ] AR nutrition labels

## 🐛 Known Issues

- Barcode scanner requires good lighting conditions
- Large recipe imports may take time on slower devices
- Some nutrition data may vary between API sources

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Authors

- **emgeiger** - *Initial work and Android migration*

## 🙏 Acknowledgments

- **USDA FoodData Central** for nutrition database
- **Open Food Facts** for barcode product database
- **Jetpack Compose** team for the modern UI toolkit
- **Android** community for excellent documentation

---

**📱 Download**: Coming soon to Google Play Store  
**🌟 Star**: If you find this project useful, please give it a star!  
**📧 Contact**: Issues and questions welcome in GitHub Issues
