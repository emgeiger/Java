@echo off
echo 🥗 Building Nutrition Calculator...

REM Create bin directory if it doesn't exist
if not exist bin mkdir bin

REM Compile model classes first
echo Compiling model classes...
javac -d bin -cp src/main/java src/main/java/com/nutrition/calculator/model/*.java
if %errorlevel% neq 0 (
    echo ❌ Failed to compile model classes
    exit /b 1
)

REM Compile service classes (skip JsonRecipeService due to missing dependencies)
echo Compiling service classes...
javac -d bin -cp "src/main/java;bin" src/main/java/com/nutrition/calculator/service/RecipeService.java
javac -d bin -cp "src/main/java;bin" src/main/java/com/nutrition/calculator/service/RecipeStatistics.java
javac -d bin -cp "src/main/java;bin" src/main/java/com/nutrition/calculator/service/InMemoryRecipeService.java
if %errorlevel% neq 0 (
    echo ❌ Failed to compile service classes
    exit /b 1
)

REM Compile UI classes
echo Compiling UI classes...
javac -d bin -cp "src/main/java;bin" src/main/java/com/nutrition/calculator/ui/*.java
if %errorlevel% neq 0 (
    echo ❌ Failed to compile UI classes
    exit /b 1
)

REM Compile main application
echo Compiling main application...
javac -d bin -cp "src/main/java;bin" src/main/java/com/nutrition/calculator/NutritionApp.java
if %errorlevel% neq 0 (
    echo ❌ Failed to compile main application
    exit /b 1
)

echo ✅ Build completed successfully!
echo.
echo To run the application: 
echo java -cp bin com.nutrition.calculator.NutritionApp
