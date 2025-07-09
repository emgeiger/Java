@echo off
echo 🚀 Starting Nutrition Calculator...
cd /d "%~dp0"
java -cp bin com.nutrition.calculator.NutritionApp
pause
