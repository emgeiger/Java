# Project Structure - Nutrition Calculator Android

## Overview

This document provides a detailed breakdown of the Nutrition Calculator Android project structure, following Clean Architecture principles and modern Android development practices.

## Root Directory Structure

```
NutritionCalculator/
├── 📁 .github/                    # GitHub workflows and templates
├── 📁 .gradle/                    # Gradle cache and temporary files
├── 📁 .idea/                      # IntelliJ/Android Studio configuration
├── 📁 app/                        # Main application module
├── 📁 docs/                       # Project documentation
├── 📁 gradle/                     # Gradle wrapper files
├── 📄 build.gradle.kts            # Root build configuration (Kotlin DSL)
├── 📄 settings.gradle.kts         # Project settings (Kotlin DSL)
├── 📄 gradle.properties           # Gradle properties and optimization settings
├── 📄 gradlew.bat                 # Gradle wrapper script (Windows)
├── 📄 local.properties            # Local SDK paths and API keys
└── 📄 README.md                   # Project documentation
```

## App Module Structure

### Source Code Organization

```
app/src/
├── 📁 main/
│   ├── 📁 kotlin/com/nutrition/calculator/
│   │   ├── 📁 data/                # Data layer (repositories, APIs, database)
│   │   ├── 📁 domain/              # Domain layer (models, use cases)
│   │   ├── 📁 presentation/        # Presentation layer (UI, ViewModels)
│   │   ├── 📁 di/                  # Dependency injection modules
│   │   ├── 📄 MainActivity.kt      # Main Android activity
│   │   └── 📄 NutritionCalculatorApplication.kt
│   ├── 📁 res/                     # Android resources
│   └── 📄 AndroidManifest.xml      # App manifest
├── 📁 test/                        # Unit tests
└── 📁 androidTest/                 # Instrumentation tests
```

## Detailed Layer Breakdown

### 1. Data Layer (`app/src/main/kotlin/com/nutrition/calculator/data/`)

#### Local Data Sources

```
data/local/
├── 📄 NutritionDatabase.kt         # Room database configuration
├── 📄 DatabaseTypeConverters.kt    # Type converters for complex objects
├── 📁 dao/                         # Data Access Objects
│   ├── 📄 IngredientDao.kt
│   ├── 📄 RecipeDao.kt
│   ├── 📄 NutritionFactsDao.kt
│   └── 📄 MealPlanDao.kt
└── 📁 entities/                    # Room entities
    ├── 📄 IngredientEntity.kt
    ├── 📄 RecipeEntity.kt
    ├── 📄 RecipeIngredientCrossRef.kt
    └── 📄 MealPlanEntity.kt
```

#### Remote Data Sources

```
data/remote/
├── 📁 api/                         # API service interfaces
│   ├── 📄 USDAApiService.kt        # USDA FoodData Central API
│   ├── 📄 OpenFoodFactsService.kt  # Open Food Facts API
│   └── 📄 FirebaseApiService.kt    # Firebase custom functions
├── 📁 dto/                         # Data Transfer Objects
│   ├── 📄 USDASearchResponse.kt
│   ├── 📄 OpenFoodFactsResponse.kt
│   └── 📄 FirebaseRecipeDto.kt
└── 📁 interceptors/                # Network interceptors
    ├── 📄 AuthInterceptor.kt
    └── 📄 LoggingInterceptor.kt
```

#### Repository Implementations

```
data/repository/
├── 📄 IngredientRepositoryImpl.kt   # Ingredient data management
├── 📄 RecipeRepositoryImpl.kt       # Recipe data management
├── 📄 NutritionRepositoryImpl.kt    # Nutrition calculation logic
├── 📄 UserRepositoryImpl.kt         # User preferences and settings
└── 📄 SyncRepositoryImpl.kt         # Cloud synchronization
```

### 2. Domain Layer (`app/src/main/kotlin/com/nutrition/calculator/domain/`)

#### Models (Data Classes)

```
domain/model/
├── 📄 Ingredient.kt                # Core ingredient model
├── 📄 Recipe.kt                    # Recipe with ingredients
├── 📄 RecipeIngredient.kt          # Ingredient within a recipe
├── 📄 NutritionFacts.kt            # Nutrition information
├── 📄 MeasurementUnit.kt           # Units of measurement
├── 📄 MealPlan.kt                  # Meal planning
├── 📄 User.kt                      # User profile
└── 📄 EnhancedModels.kt            # Extended models for advanced features
```

#### Repository Interfaces

```
domain/repository/
├── 📄 IngredientRepository.kt      # Ingredient operations contract
├── 📄 RecipeRepository.kt          # Recipe operations contract
├── 📄 NutritionRepository.kt       # Nutrition calculation contract
├── 📄 UserRepository.kt            # User data contract
└── 📄 SyncRepository.kt            # Synchronization contract
```

#### Use Cases (Business Logic)

```
domain/usecase/
├── 📄 CalculateNutritionUseCase.kt # Nutrition calculations
├── 📄 SearchIngredientsUseCase.kt  # Ingredient search logic
├── 📄 CreateRecipeUseCase.kt       # Recipe creation logic
├── 📄 ScaleRecipeUseCase.kt        # Recipe scaling logic
├── 📄 ScanBarcodeUseCase.kt        # Barcode processing
└── 📄 SyncDataUseCase.kt           # Data synchronization
```

### 3. Presentation Layer (`app/src/main/kotlin/com/nutrition/calculator/presentation/`)

#### Screens (Compose UI)

```
presentation/screens/
├── 📄 HomeScreen.kt                # Main dashboard
├── 📄 IngredientListScreen.kt      # Browse ingredients
├── 📄 IngredientDetailScreen.kt    # Ingredient details/editing
├── 📄 RecipeListScreen.kt          # Browse recipes
├── 📄 RecipeDetailScreen.kt        # Recipe details/editing
├── 📄 CreateRecipeScreen.kt        # Recipe creation wizard
├── 📄 NutritionSummaryScreen.kt    # Nutrition overview
├── 📄 BarcodeScannerScreen.kt      # Camera scanning
├── 📄 SettingsScreen.kt            # App settings
└── 📄 ProfileScreen.kt             # User profile
```

#### Reusable Components

```
presentation/components/
├── 📄 NutritionCard.kt             # Nutrition facts display
├── 📄 IngredientItem.kt            # Ingredient list item
├── 📄 RecipeCard.kt                # Recipe list item
├── 📄 SearchBar.kt                 # Search input component
├── 📄 UnitSelector.kt              # Measurement unit picker
├── 📄 LoadingIndicator.kt          # Loading states
├── 📄 ErrorMessage.kt              # Error display
└── 📄 EmptyState.kt                # Empty list states
```

#### ViewModels (State Management)

```
presentation/viewmodel/
├── 📄 HomeViewModel.kt             # Home screen state
├── 📄 IngredientListViewModel.kt   # Ingredient list state
├── 📄 IngredientDetailViewModel.kt # Ingredient detail state
├── 📄 RecipeListViewModel.kt       # Recipe list state
├── 📄 RecipeDetailViewModel.kt     # Recipe detail state
├── 📄 CreateRecipeViewModel.kt     # Recipe creation state
├── 📄 BarcodeScannerViewModel.kt   # Scanner state
└── 📄 SettingsViewModel.kt         # Settings state
```

#### Navigation

```
presentation/navigation/
├── 📄 NutritionNavigation.kt       # Navigation graph
├── 📄 NavigationRoutes.kt          # Route definitions
└── 📄 NavigationArgs.kt            # Navigation arguments
```

#### Theme & Styling

```
presentation/theme/
├── 📄 Color.kt                     # Material Design colors
├── 📄 Typography.kt                # Text styles
├── 📄 Theme.kt                     # Overall theme configuration
└── 📄 Shapes.kt                    # UI shapes and corners
```

### 4. Dependency Injection (`app/src/main/kotlin/com/nutrition/calculator/di/`)

```
di/
├── 📄 DatabaseModule.kt            # Room database injection
├── 📄 NetworkModule.kt             # Retrofit and network injection
├── 📄 RepositoryModule.kt          # Repository implementations binding
├── 📄 UseCaseModule.kt             # Use case injection
└── 📄 ViewModelModule.kt           # ViewModel injection
```

## Resource Structure

### Android Resources (`app/src/main/res/`)

```
res/
├── 📁 drawable/                    # Vector icons and images
│   ├── 📄 ic_nutrition.xml
│   ├── 📄 ic_recipe.xml
│   ├── 📄 ic_scanner.xml
│   └── 📄 ic_settings.xml
├── 📁 mipmap/                      # App icons (different densities)
│   ├── 📁 mipmap-hdpi/
│   ├── 📁 mipmap-mdpi/
│   ├── 📁 mipmap-xhdpi/
│   ├── 📁 mipmap-xxhdpi/
│   └── 📁 mipmap-xxxhdpi/
├── 📁 values/                      # Default values
│   ├── 📄 colors.xml               # Color definitions
│   ├── 📄 strings.xml              # Text strings
│   ├── 📄 themes.xml               # Material themes
│   └── 📄 dimens.xml               # Dimension values
├── 📁 values-night/                # Dark theme overrides
│   ├── 📄 colors.xml
│   └── 📄 themes.xml
└── 📁 xml/                         # Configuration files
    ├── 📄 backup_rules.xml
    ├── 📄 data_extraction_rules.xml
    └── 📄 network_security_config.xml
```

## Testing Structure

### Unit Tests (`app/src/test/`)

```
test/kotlin/com/nutrition/calculator/
├── 📁 domain/
│   ├── 📁 usecase/                 # Use case tests
│   └── 📁 model/                   # Model tests
├── 📁 data/
│   ├── 📁 repository/              # Repository tests
│   └── 📁 local/                   # Database tests
└── 📁 presentation/
    └── 📁 viewmodel/               # ViewModel tests
```

### Instrumentation Tests (`app/src/androidTest/`)

```
androidTest/kotlin/com/nutrition/calculator/
├── 📁 data/
│   ├── 📁 local/                   # Database integration tests
│   └── 📁 remote/                  # API integration tests
├── 📁 presentation/
│   ├── 📁 screens/                 # UI tests for screens
│   └── 📁 components/              # UI tests for components
└── 📁 di/                          # Test dependency injection
```

## Build Configuration

### Gradle Files

#### Root Build Script (`build.gradle.kts`)

```kotlin
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
    id("com.google.firebase.crashlytics") version "2.9.9" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
}
```

#### App Build Script (`app/build.gradle.kts`)

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    // ... other plugins
}

android {
    namespace = "com.nutrition.calculator"
    compileSdk = 34
    // ... configuration
}
```

## File Naming Conventions

### Kotlin Files

- **Models**: `PascalCase.kt` (e.g., `NutritionFacts.kt`)
- **ViewModels**: `NameViewModel.kt` (e.g., `HomeViewModel.kt`)
- **Repositories**: `NameRepository.kt` or `NameRepositoryImpl.kt`
- **Use Cases**: `VerbNounUseCase.kt` (e.g., `CalculateNutritionUseCase.kt`)
- **Composables**: `PascalCase.kt` (e.g., `NutritionCard.kt`)

### Resource Files

- **Layouts**: `snake_case.xml` (though we use Compose)
- **Drawables**: `ic_name.xml` for icons, `img_name.xml` for images
- **Colors**: `colors.xml` with lowercase_underscore naming
- **Strings**: `strings.xml` with dot.notation.naming

## Dependencies Overview

### Major Dependencies

```kotlin
dependencies {
    // Core Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    
    // Compose
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    
    // Architecture
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.navigation:navigation-compose:2.7.5")
    
    // Dependency Injection
    implementation("com.google.dagger:hilt-android:2.48")
    ksp("com.google.dagger:hilt-compiler:2.48")
    
    // Database
    implementation("androidx.room:room-runtime:2.6.0")
    implementation("androidx.room:room-ktx:2.6.0")
    ksp("androidx.room:room-compiler:2.6.0")
    
    // Network
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    
    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    implementation("com.google.firebase:firebase-firestore-ktx")
    
    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}
```

## Configuration Files

### Key Configuration Files

- **`AndroidManifest.xml`**: App permissions, components, and metadata
- **`gradle.properties`**: Build optimization and feature flags
- **`local.properties`**: Local SDK paths and sensitive API keys
- **`proguard-rules.pro`**: Code obfuscation rules for release builds

## Development Workflow

### Recommended IDE Structure

```
Project View (Android Studio/IntelliJ):
├── 📁 app/
├── 📁 Gradle Scripts/
├── 📁 External Libraries/
└── 📁 Scratches and Consoles/
```

### VS Code Structure

```
Explorer View:
├── 📁 .vscode/                     # VS Code settings
├── 📁 app/
├── 📁 gradle/
├── 📄 build.gradle.kts
└── 📄 settings.gradle.kts
```

## Best Practices Followed

### Code Organization

- **Single Responsibility**: Each class has one clear purpose
- **Dependency Inversion**: Abstractions don't depend on details
- **Clean Architecture**: Clear separation of concerns across layers

### File Structure

- **Feature-based**: Group related functionality together
- **Layer-based**: Separate by architectural layers (data, domain, presentation)
- **Consistent Naming**: Follow Android and Kotlin conventions

### Testing Structure

- **Mirror Production**: Test structure mirrors main source structure
- **Unit Testing**: Focus on business logic and ViewModels
- **Integration Testing**: Test data layer and API interactions
- **UI Testing**: Test critical user flows with Compose Testing

---

*This structure follows Android and Kotlin best practices as of 2025, ensuring maintainability, testability, and scalability.*