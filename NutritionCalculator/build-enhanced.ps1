# Enhanced PowerShell Build Script for NutritionCalculator
# Provides comprehensive build, test, and deployment options

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   NutritionCalculator Build Script" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Function to check prerequisites
function Test-Prerequisites {
    Write-Host "🔍 Checking prerequisites..." -ForegroundColor Yellow
    
    # Check if in correct directory
    if (-not (Test-Path "gradlew.bat")) {
        Write-Host "❌ Error: gradlew.bat not found. Are you in the project root?" -ForegroundColor Red
        exit 1
    }
    
    # Check Java installation
    try {
        $javaVersion = java -version 2>&1 | Select-String "version"
        Write-Host "✅ Java found: $javaVersion" -ForegroundColor Green
    }
    catch {
        Write-Host "❌ Java not found. Please install Java 11+ and ensure it's in PATH" -ForegroundColor Red
        exit 1
    }
    
    Write-Host ""
}

# Function to display menu
function Show-Menu {
    Write-Host "📋 Build Options:" -ForegroundColor Cyan
    Write-Host "   1. Build Desktop Application" -ForegroundColor White
    Write-Host "   2. Build Android APK (Debug)" -ForegroundColor White
    Write-Host "   3. Build Android APK (Release)" -ForegroundColor White
    Write-Host "   4. Build Both Desktop and Android" -ForegroundColor White
    Write-Host "   5. Clean and Build All" -ForegroundColor White
    Write-Host "   6. Run Tests" -ForegroundColor White
    Write-Host "   7. Corporate Network Build" -ForegroundColor White
    Write-Host "   8. Quick Development Build" -ForegroundColor White
    Write-Host "   9. Performance Analysis" -ForegroundColor White
    Write-Host "   0. Exit" -ForegroundColor White
    Write-Host ""
}

# Function to build desktop
function Build-Desktop {
    Write-Host "🏗️ Building Desktop Application..." -ForegroundColor Green
    & .\gradlew.bat build
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Desktop build successful!" -ForegroundColor Green
        Write-Host "📁 JAR location: build\libs\" -ForegroundColor Yellow
    }
    else {
        Write-Host "❌ Desktop build failed" -ForegroundColor Red
        Show-TroubleshootingTips
        return $false
    }
    return $true
}

# Function to build Android debug
function Build-AndroidDebug {
    Write-Host "🏗️ Building Android APK (Debug)..." -ForegroundColor Green
    & .\gradlew.bat :app:assembleDebug
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Android debug build successful!" -ForegroundColor Green
        Write-Host "📁 APK location: app\build\outputs\apk\debug\" -ForegroundColor Yellow
    }
    else {
        Write-Host "❌ Android debug build failed" -ForegroundColor Red
        Show-TroubleshootingTips
        return $false
    }
    return $true
}

# Function to build Android release
function Build-AndroidRelease {
    Write-Host "🏗️ Building Android APK (Release)..." -ForegroundColor Green
    Write-Host "⚠️ Note: Requires signing configuration" -ForegroundColor Yellow
    & .\gradlew.bat :app:assembleRelease
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Android release build successful!" -ForegroundColor Green
        Write-Host "📁 APK location: app\build\outputs\apk\release\" -ForegroundColor Yellow
    }
    else {
        Write-Host "❌ Android release build failed" -ForegroundColor Red
        Show-TroubleshootingTips
        return $false
    }
    return $true
}

# Function to run tests
function Run-Tests {
    Write-Host "🧪 Running tests..." -ForegroundColor Green
    & .\gradlew.bat test :app:testDebugUnitTest
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ All tests passed!" -ForegroundColor Green
        Write-Host "📁 Test reports: build\reports\tests\" -ForegroundColor Yellow
    }
    else {
        Write-Host "❌ Tests failed" -ForegroundColor Red
        Write-Host "📁 Test reports: build\reports\tests\" -ForegroundColor Yellow
        return $false
    }
    return $true
}

# Function for corporate network build
function Build-Corporate {
    Write-Host "🏢 Running corporate network build..." -ForegroundColor Green
    
    if (Test-Path "gradlew-corporate.bat") {
        Write-Host "Using corporate Gradle wrapper..." -ForegroundColor Yellow
        & .\gradlew-corporate.bat build
    }
    elseif (Test-Path "setup-corporate-network.ps1") {
        Write-Host "Running corporate network setup..." -ForegroundColor Yellow
        & .\setup-corporate-network.ps1
    }
    else {
        Write-Host "Using standard build with SSL bypass..." -ForegroundColor Yellow
        $env:GRADLE_OPTS = "-Dtrust_all_cert=true -Djava.net.useSystemProxies=true"
        & .\gradlew.bat build
    }
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Corporate build successful!" -ForegroundColor Green
    }
    else {
        Write-Host "❌ Corporate build failed" -ForegroundColor Red
        Write-Host "💡 Try running setup-corporate-network.ps1 first" -ForegroundColor Yellow
        return $false
    }
    return $true
}

# Function for quick development build
function Build-QuickDev {
    Write-Host "⚡ Quick Development Build..." -ForegroundColor Green
    Write-Host "Building with optimizations for fast iteration..." -ForegroundColor Yellow
    
    # Build only what's needed for development
    & .\gradlew.bat :app:assembleDebug --parallel --build-cache
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Quick development build successful!" -ForegroundColor Green
        Write-Host "📱 Installing on connected device..." -ForegroundColor Yellow
        & .\gradlew.bat :app:installDebug
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host "✅ App installed successfully!" -ForegroundColor Green
        }
    }
    else {
        Write-Host "❌ Quick development build failed" -ForegroundColor Red
        return $false
    }
    return $true
}

# Function for performance analysis
function Analyze-Performance {
    Write-Host "📊 Running Performance Analysis..." -ForegroundColor Green
    Write-Host "Building with profiling enabled..." -ForegroundColor Yellow
    
    & .\gradlew.bat build --profile --parallel
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Performance analysis complete!" -ForegroundColor Green
        Write-Host "📁 Performance report: build\reports\profile\" -ForegroundColor Yellow
        
        # Check for common performance issues
        Write-Host "🔍 Checking for performance recommendations..." -ForegroundColor Yellow
        
        if (Test-Path "build\reports\profile\") {
            Write-Host "💡 Performance tips:" -ForegroundColor Cyan
            Write-Host "   - Review build profile in: build\reports\profile\" -ForegroundColor White
            Write-Host "   - Consider enabling: org.gradle.parallel=true" -ForegroundColor White
            Write-Host "   - Consider enabling: org.gradle.caching=true" -ForegroundColor White
        }
    }
    else {
        Write-Host "❌ Performance analysis failed" -ForegroundColor Red
        return $false
    }
    return $true
}

# Function to show troubleshooting tips
function Show-TroubleshootingTips {
    Write-Host ""
    Write-Host "🔧 Troubleshooting suggestions:" -ForegroundColor Cyan
    Write-Host "   1. Check QUICK-START.md for common solutions" -ForegroundColor White
    Write-Host "   2. Review CORPORATE-NETWORK-GUIDE.md for network issues" -ForegroundColor White
    Write-Host "   3. Try: .\gradlew.bat build --info --stacktrace" -ForegroundColor White
    Write-Host "   4. Check Java version: java -version" -ForegroundColor White
    Write-Host "   5. For corporate networks, try: .\gradlew-corporate.bat build" -ForegroundColor White
    Write-Host ""
}

# Main execution
Test-Prerequisites

do {
    Show-Menu
    $choice = Read-Host "Enter your choice (0-9)"
    Write-Host ""
    
    switch ($choice) {
        "1" { Build-Desktop }
        "2" { Build-AndroidDebug }
        "3" { Build-AndroidRelease }
        "4" { 
            Write-Host "🏗️ Building Both Desktop and Android..." -ForegroundColor Green
            $desktopSuccess = Build-Desktop
            if ($desktopSuccess) {
                Build-AndroidDebug
            }
        }
        "5" {
            Write-Host "🧹 Cleaning and building all..." -ForegroundColor Green
            & .\gradlew.bat clean build :app:assembleDebug
            if ($LASTEXITCODE -eq 0) {
                Write-Host "✅ Clean build successful!" -ForegroundColor Green
            }
            else {
                Write-Host "❌ Clean build failed" -ForegroundColor Red
                Show-TroubleshootingTips
            }
        }
        "6" { Run-Tests }
        "7" { Build-Corporate }
        "8" { Build-QuickDev }
        "9" { Analyze-Performance }
        "0" { 
            Write-Host "👋 Goodbye!" -ForegroundColor Green
            break
        }
        default { 
            Write-Host "❌ Invalid choice. Please try again." -ForegroundColor Red
        }
    }
    
    if ($choice -ne "0") {
        Write-Host ""
        Write-Host "Press any key to continue..." -ForegroundColor Gray
        $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
        Clear-Host
    }
} while ($choice -ne "0")

Write-Host "🎉 Build script completed!" -ForegroundColor Green
