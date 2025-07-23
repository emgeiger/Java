# Quick Android SDK Environment Setup
# Run this in PowerShell as Administrator after installing Android Studio

# Set the Android SDK path (update this path if yours is different)
$ANDROID_SDK_PATH = "C:\Users\$env:USERNAME\AppData\Local\Android\Sdk"

Write-Host "Setting up Android SDK environment variables..." -ForegroundColor Yellow
Write-Host "SDK Path: $ANDROID_SDK_PATH" -ForegroundColor Cyan

# Verify SDK exists
if (Test-Path $ANDROID_SDK_PATH) {
    Write-Host "✓ Android SDK found at: $ANDROID_SDK_PATH" -ForegroundColor Green
    
    # Set environment variables
    [Environment]::SetEnvironmentVariable("ANDROID_HOME", $ANDROID_SDK_PATH, "User")
    [Environment]::SetEnvironmentVariable("ANDROID_SDK_ROOT", $ANDROID_SDK_PATH, "User")
    
    # Update PATH
    $currentPath = [Environment]::GetEnvironmentVariable("PATH", "User")
    $androidTools = "$ANDROID_SDK_PATH\platform-tools;$ANDROID_SDK_PATH\tools;$ANDROID_SDK_PATH\build-tools"
    
    if ($currentPath -notlike "*platform-tools*") {
        $newPath = "$currentPath;$androidTools"
        [Environment]::SetEnvironmentVariable("PATH", $newPath, "User")
        Write-Host "✓ Updated PATH with Android SDK tools" -ForegroundColor Green
    } else {
        Write-Host "✓ Android tools already in PATH" -ForegroundColor Green
    }
    
    # Set for current session
    $env:ANDROID_HOME = $ANDROID_SDK_PATH
    $env:ANDROID_SDK_ROOT = $ANDROID_SDK_PATH
    
    Write-Host ""
    Write-Host "✅ Android SDK environment setup complete!" -ForegroundColor Green
    Write-Host ""
    Write-Host "Environment Variables Set:" -ForegroundColor Cyan
    Write-Host "  ANDROID_HOME = $ANDROID_SDK_PATH"
    Write-Host "  ANDROID_SDK_ROOT = $ANDROID_SDK_PATH"
    Write-Host ""
    Write-Host "Next Steps:" -ForegroundColor Yellow
    Write-Host "1. Restart your terminal"
    Write-Host "2. Test: echo `$env:ANDROID_HOME"
    Write-Host "3. Build project: .\gradlew-corporate.bat build"
    
} else {
    Write-Host "❌ Android SDK not found at: $ANDROID_SDK_PATH" -ForegroundColor Red
    Write-Host ""
    Write-Host "Please install Android Studio first:" -ForegroundColor Yellow
    Write-Host "1. Download from: https://developer.android.com/studio"
    Write-Host "2. Install Android Studio"
    Write-Host "3. Run this script again"
    Write-Host ""
    Write-Host "Alternative SDK locations to check:" -ForegroundColor Cyan
    $alternativePaths = @(
        "C:\Android\Sdk",
        "C:\Program Files\Android\Sdk",
        "C:\Program Files (x86)\Android\Sdk"
    )
    
    foreach ($path in $alternativePaths) {
        if (Test-Path $path) {
            Write-Host "✓ Found Android SDK at: $path" -ForegroundColor Green
            Write-Host "Update the `$ANDROID_SDK_PATH variable to use this path" -ForegroundColor Yellow
        } else {
            Write-Host "✗ Not found: $path" -ForegroundColor DarkGray
        }
    }
}
