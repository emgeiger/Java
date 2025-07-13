#!/usr/bin/env powershell
# Android Build Setup Script for Corporate Networks
# Based on emgeiger/Gradle template corporate network configuration

Write-Host "🔧 Setting up Android Build Environment for Corporate Network..." -ForegroundColor Green

# Set Java system properties for SSL bypassing (Android builds)
$env:JAVA_OPTS = "-Dtrust_all_cert=true -Dcom.sun.net.ssl.checkRevocation=false -Dsun.security.ssl.allowUnsafeRenegotiation=true -Djava.net.useSystemProxies=true"
$env:GRADLE_OPTS = "-Dtrust_all_cert=true -Dcom.sun.net.ssl.checkRevocation=false -Dsun.security.ssl.allowUnsafeRenegotiation=true -Djava.net.useSystemProxies=true"

# Android-specific environment variables
$env:ANDROID_HOME = "C:\Users\$env:USERNAME\AppData\Local\Android\Sdk"
$env:ANDROID_SDK_ROOT = $env:ANDROID_HOME

Write-Host "✅ Environment variables set:" -ForegroundColor Yellow
Write-Host "   JAVA_OPTS: $env:JAVA_OPTS"
Write-Host "   GRADLE_OPTS: $env:GRADLE_OPTS"
Write-Host "   ANDROID_HOME: $env:ANDROID_HOME"

# Check Java version
Write-Host "`n🔍 Checking Java installation..." -ForegroundColor Green
try {
    $javaVersion = java -version 2>&1 | Select-String "version"
    Write-Host "✅ Java found: $javaVersion" -ForegroundColor Yellow
}
catch {
    Write-Host "❌ Java not found in PATH. Please install Java 17+ first." -ForegroundColor Red
    exit 1
}

# Check Android SDK
Write-Host "`n📱 Checking Android SDK..." -ForegroundColor Green
if (Test-Path $env:ANDROID_HOME) {
    Write-Host "✅ Android SDK found at: $env:ANDROID_HOME" -ForegroundColor Yellow
} else {
    Write-Host "❌ Android SDK not found. Please install Android Studio first." -ForegroundColor Red
    Write-Host "   Download from: https://developer.android.com/studio" -ForegroundColor Red
}

# Method 1: Try building Android module with system proxy
Write-Host "`n🌐 Method 1: Attempting Android build with system proxy settings..." -ForegroundColor Green
try {
    .\gradlew.bat :android:build --stacktrace
    Write-Host "✅ Android build successful with system proxy!" -ForegroundColor Green
    exit 0
}
catch {
    Write-Host "❌ Method 1 failed. Trying alternative approaches..." -ForegroundColor Yellow
}

# Method 2: Try desktop build first
Write-Host "`n🖥️  Method 2: Building desktop version first..." -ForegroundColor Green
try {
    .\gradlew.bat build --stacktrace
    Write-Host "✅ Desktop build successful!" -ForegroundColor Green
    
    # Now try Android
    Write-Host "`n📱 Attempting Android build after desktop success..." -ForegroundColor Green
    .\gradlew.bat :android:assembleDebug --stacktrace
    Write-Host "✅ Android build successful!" -ForegroundColor Green
}
catch {
    Write-Host "❌ Method 2 failed. Manual intervention required." -ForegroundColor Yellow
}

# Provide troubleshooting guidance
Write-Host "`n📋 Android-Specific Troubleshooting Steps:" -ForegroundColor Cyan
Write-Host "   1. Ensure Android Studio is installed and updated" -ForegroundColor White
Write-Host "   2. Check Android SDK path: $env:ANDROID_HOME" -ForegroundColor White
Write-Host "   3. Verify Android SDK Tools are installed" -ForegroundColor White
Write-Host "   4. Check Android SDK Manager for required components" -ForegroundColor White
Write-Host "   5. Ensure Android Gradle Plugin is compatible" -ForegroundColor White

Write-Host "`n🔧 If SSL issues persist with Android builds:" -ForegroundColor Cyan
Write-Host "   - Configure proxy in android/gradle.properties" -ForegroundColor White
Write-Host "   - Import corporate certificates to Android SDK" -ForegroundColor White
Write-Host "   - Use offline Gradle distribution for Android" -ForegroundColor White
Write-Host "   - Build from a different network if possible" -ForegroundColor White

Write-Host "`n🚀 Build Commands Summary:" -ForegroundColor Cyan
Write-Host "   Desktop: .\gradlew.bat build" -ForegroundColor White
Write-Host "   Android: .\gradlew.bat :android:build" -ForegroundColor White
Write-Host "   Both: .\gradlew.bat buildAll" -ForegroundColor White
