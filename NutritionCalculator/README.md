# 🥗 Nutrition Calculator - MVP

A cross-platform nutrition calculator for logging custom recipes and calculating average calories per serving.

## 🎯 Project Vision

**Phase 1**: Java Desktop Application (MVP)  
**Phase 2**: Mobile-ready with Android/Kotlin compatibility  
**Phase 3**: Cross-platform mobile deployment  

## 🚀 Features (MVP)

### Core Functionality
- ✅ **Ingredient Management**: Add ingredients with custom measurements
- ✅ **Recipe Creation**: Combine multiple ingredients into recipes  
- ✅ **Calorie Calculation**: Calculate total and per-serving calories
- ✅ **Measurement Units**: Support for oz, grams, lbs, mL, cups, etc.
- ✅ **Serving Calculator**: Find average calories per Y servings

### Technical Features
- **Clean Architecture**: Designed for mobile portability
- **Data Persistence**: Save recipes and ingredients locally
- **Unit Testing**: Comprehensive test coverage
- **Modular Design**: Easy transition to Android/Kotlin

## 📁 Project Structure

```
NutritionCalculator/
├── src/main/java/com/nutrition/calculator/
│   ├── model/          # Data models (Ingredient, Recipe, etc.)
│   ├── service/        # Business logic layer
│   ├── ui/             # User interface components
│   ├── utils/          # Utility classes
│   └── NutritionApp.java  # Main application entry
├── src/main/resources/ # Configuration and data files
├── src/test/java/      # Unit tests
└── docs/              # Documentation
```

## 🛠️ Getting Started

### Prerequisites
- Java 11 or higher
- Maven (for advanced builds - optional)

### Quick Start (Recommended)

#### Option 1: Simple Standalone Version
```bash
# Navigate to project directory
cd NutritionCalculator

# Run directly (no compilation needed)
java SimpleNutritionCalculator
```

#### Option 2: Full Featured Version
```bash
# Build the project using provided scripts
.\build.bat       # Windows
# or ./build.sh   # Linux/Mac (if available)

# Run the application
.\run.bat         # Windows  
# or ./run.sh     # Linux/Mac (if available)

# Or run directly after building
java -cp bin com.nutrition.calculator.NutritionApp
```

#### Option 3: VS Code Development
- Open the Java monorepo in VS Code
- Navigate to NutritionCalculator folder
- Use `F5` or `Ctrl+F5` to run
- Choose launch configuration:
  - "SimpleNutritionCalculator" (standalone)
  - "NutritionApp (Full)" (full featured)

### Using Maven (Advanced)
```bash
# Build the project
mvn clean compile

# Run tests
mvn test

# Run the application
mvn exec:java -Dexec.mainClass="com.nutrition.calculator.NutritionApp"
```

## 🎯 MVP Requirements

### Must-Have Features
1. **Add Ingredients** - Name, calories per unit, measurement type
2. **Create Recipes** - Combine ingredients with quantities
3. **Calculate Totals** - Total calories for entire recipe
4. **Serving Division** - Average calories per X servings
5. **Data Persistence** - Save/load recipes locally

### Nice-to-Have Features
- Ingredient database with common foods
- Nutritional information beyond calories (protein, carbs, fat)
- Recipe scaling (double/halve recipes)
- Export recipes to text/CSV

## 🔮 Future Phases

### Phase 2: Mobile Preparation
- Convert UI layer to be Android-compatible
- Implement proper Model-View-ViewModel (MVVM) pattern
- Add JSON serialization for cross-platform data

### Phase 3: Android/Kotlin Port
- Port core business logic to Kotlin
- Create Android UI with Material Design
- Add cloud sync capabilities
- Barcode scanning for ingredients

## 🧪 Testing Strategy

- **Unit Tests**: All business logic classes
- **Integration Tests**: Recipe calculation workflows  
- **UI Tests**: User interface interactions

## 📊 Success Metrics (MVP)

- ✅ Can add 10+ ingredients with different units
- ✅ Can create recipes with 5+ ingredients  
- ✅ Accurate calorie calculations within 1% margin
- ✅ Data persists between application sessions
- ✅ Intuitive user interface requiring no tutorial

---

**Ready to revolutionize recipe nutrition tracking! 🚀**
