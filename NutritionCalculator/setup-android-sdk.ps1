# Android SDK Setup Script for NutritionCalculator Project
# This script helps set up Android SDK environment variables
# Run this script as Administrator for system-wide installation

param(
    [Parameter(Mandatory=$false)]
    [string]$AndroidSdkPath = "",
    
    [Parameter(Mandatory=$false)]
    [switch]$UserOnly = $false,
    
    [Parameter(Mandatory=$false)]
    [switch]$DownloadSdk = $false
)

Write-Host "=== Android SDK Environment Setup ===" -ForegroundColor Green
Write-Host "For NutritionCalculator Android Module" -ForegroundColor Yellow
Write-Host ""

# Function to set environment variables
function Set-AndroidEnvironmentVariables {
    param($SdkPath, $UserLevel)
    
    $envTarget = if ($UserLevel) { "User" } else { "Machine" }
    
    try {
        Write-Host "Setting Android SDK environment variables..." -ForegroundColor Yellow
        
        # Set ANDROID_HOME (legacy but still used)
        [Environment]::SetEnvironmentVariable("ANDROID_HOME", $SdkPath, $envTarget)
        Write-Host "✓ ANDROID_HOME = $SdkPath" -ForegroundColor Green
        
        # Set ANDROID_SDK_ROOT (modern standard)
        [Environment]::SetEnvironmentVariable("ANDROID_SDK_ROOT", $SdkPath, $envTarget)
        Write-Host "✓ ANDROID_SDK_ROOT = $SdkPath" -ForegroundColor Green
        
        # Update PATH to include SDK tools
        $currentPath = [Environment]::GetEnvironmentVariable("PATH", $envTarget)
        $sdkTools = @(
            "$SdkPath\tools",
            "$SdkPath\tools\bin",
            "$SdkPath\platform-tools",
            "$SdkPath\build-tools",
            "$SdkPath\emulator"
        )
        
        foreach ($toolPath in $sdkTools) {
            if ($currentPath -notlike "*$toolPath*") {
                $currentPath = "$currentPath;$toolPath"
            }
        }
        
        [Environment]::SetEnvironmentVariable("PATH", $currentPath, $envTarget)
        Write-Host "✓ Updated PATH with Android SDK tools" -ForegroundColor Green
        
        # Set current session variables
        $env:ANDROID_HOME = $SdkPath
        $env:ANDROID_SDK_ROOT = $SdkPath
        $env:PATH = "$env:PATH;$SdkPath\tools;$SdkPath\tools\bin;$SdkPath\platform-tools;$SdkPath\build-tools;$SdkPath\emulator"
        
        Write-Host "✓ Environment variables set successfully!" -ForegroundColor Green
        return $true
    }
    catch {
        Write-Host "✗ Error setting environment variables: $($_.Exception.Message)" -ForegroundColor Red
        return $false
    }
}

# Function to verify Android SDK installation
function Test-AndroidSdkInstallation {
    param($SdkPath)
    
    Write-Host "Verifying Android SDK installation..." -ForegroundColor Yellow
    
    $requiredDirs = @(
        "platforms",
        "platform-tools", 
        "build-tools",
        "tools"
    )
    
    $valid = $true
    foreach ($dir in $requiredDirs) {
        $dirPath = Join-Path $SdkPath $dir
        if (Test-Path $dirPath) {
            Write-Host "✓ Found: $dir" -ForegroundColor Green
        } else {
            Write-Host "✗ Missing: $dir" -ForegroundColor Red
            $valid = $false
        }
    }
    
    return $valid
}

# Function to download and install Android SDK
function Install-AndroidSdk {
    param($InstallPath)
    
    Write-Host "Downloading Android SDK Command Line Tools..." -ForegroundColor Yellow
    
    $cmdlineToolsUrl = "https://dl.google.com/android/repository/commandlinetools-win-9477386_latest.zip"
    $downloadPath = Join-Path $env:TEMP "android-cmdline-tools.zip"
    
    try {
        # Create installation directory
        if (!(Test-Path $InstallPath)) {
            New-Item -ItemType Directory -Path $InstallPath -Force | Out-Null
        }
        
        # Download command line tools
        Write-Host "Downloading from: $cmdlineToolsUrl" -ForegroundColor Cyan
        Invoke-WebRequest -Uri $cmdlineToolsUrl -OutFile $downloadPath -UseBasicParsing
        
        # Extract to SDK directory
        $cmdlineToolsDir = Join-Path $InstallPath "cmdline-tools"
        Expand-Archive -Path $downloadPath -DestinationPath $cmdlineToolsDir -Force
        
        # Move contents to proper location
        $extractedDir = Join-Path $cmdlineToolsDir "cmdline-tools"
        $latestDir = Join-Path $cmdlineToolsDir "latest"
        if (Test-Path $extractedDir) {
            Move-Item $extractedDir $latestDir -Force
        }
        
        Write-Host "✓ Android SDK Command Line Tools installed" -ForegroundColor Green
        
        # Install essential SDK components
        $sdkManagerPath = Join-Path $latestDir "bin\sdkmanager.bat"
        if (Test-Path $sdkManagerPath) {
            Write-Host "Installing essential SDK components..." -ForegroundColor Yellow
            
            # Accept licenses
            Write-Host "y" | & $sdkManagerPath --licenses
            
            # Install platform tools and build tools
            & $sdkManagerPath "platform-tools" "build-tools;34.0.0" "platforms;android-34"
            
            Write-Host "✓ Essential SDK components installed" -ForegroundColor Green
        }
        
        # Clean up
        Remove-Item $downloadPath -Force -ErrorAction SilentlyContinue
        
        return $true
    }
    catch {
        Write-Host "✗ Error installing Android SDK: $($_.Exception.Message)" -ForegroundColor Red
        return $false
    }
}

# Main script logic
try {
    # Check if running as Administrator for system-wide installation
    $isAdmin = ([Security.Principal.WindowsPrincipal] [Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole] "Administrator")
    
    if (!$UserOnly -and !$isAdmin) {
        Write-Host "⚠️  Not running as Administrator. Setting user-level environment variables only." -ForegroundColor Yellow
        $UserOnly = $true
    }
    
    # Determine Android SDK path
    if ($AndroidSdkPath -eq "") {
        # Try to find existing installation
        $possiblePaths = @(
            "C:\Android\Sdk",
            "C:\Users\$env:USERNAME\AppData\Local\Android\Sdk",
            "C:\Program Files\Android\Sdk",
            "C:\Program Files (x86)\Android\Sdk"
        )
        
        $foundSdk = $false
        foreach ($path in $possiblePaths) {
            if (Test-Path $path) {
                $AndroidSdkPath = $path
                $foundSdk = $true
                Write-Host "✓ Found existing Android SDK at: $AndroidSdkPath" -ForegroundColor Green
                break
            }
        }
        
        if (!$foundSdk) {
            if ($DownloadSdk) {
                $AndroidSdkPath = "C:\Android\Sdk"
                Write-Host "Installing Android SDK to: $AndroidSdkPath" -ForegroundColor Yellow
                
                if (!(Install-AndroidSdk -InstallPath $AndroidSdkPath)) {
                    throw "Failed to install Android SDK"
                }
            } else {
                Write-Host "⚠️  Android SDK not found in common locations." -ForegroundColor Yellow
                Write-Host ""
                Write-Host "Options:" -ForegroundColor Cyan
                Write-Host "1. Install Android Studio (recommended): https://developer.android.com/studio"
                Write-Host "2. Download SDK manually: https://developer.android.com/studio#command-tools"
                Write-Host "3. Run this script with -DownloadSdk to auto-install"
                Write-Host ""
                
                do {
                    $manualPath = Read-Host "Enter Android SDK path (or press Enter to download automatically)"
                    if ($manualPath -eq "") {
                        $AndroidSdkPath = "C:\Android\Sdk"
                        if (Install-AndroidSdk -InstallPath $AndroidSdkPath) {
                            break
                        }
                    } elseif (Test-Path $manualPath) {
                        $AndroidSdkPath = $manualPath
                        break
                    } else {
                        Write-Host "✗ Path does not exist: $manualPath" -ForegroundColor Red
                    }
                } while ($true)
            }
        }
    }
    
    Write-Host ""
    Write-Host "Using Android SDK path: $AndroidSdkPath" -ForegroundColor Cyan
    
    # Verify SDK installation
    if (Test-AndroidSdkInstallation -SdkPath $AndroidSdkPath) {
        Write-Host "✓ Android SDK installation verified" -ForegroundColor Green
    } else {
        Write-Host "⚠️  Android SDK appears incomplete. You may need to install additional components." -ForegroundColor Yellow
    }
    
    # Set environment variables
    if (Set-AndroidEnvironmentVariables -SdkPath $AndroidSdkPath -UserLevel $UserOnly) {
        Write-Host ""
        Write-Host "=== Setup Complete ===" -ForegroundColor Green
        Write-Host "Android SDK environment variables configured successfully!" -ForegroundColor Green
        Write-Host ""
        Write-Host "Environment Variables Set:" -ForegroundColor Cyan
        Write-Host "  ANDROID_HOME = $AndroidSdkPath"
        Write-Host "  ANDROID_SDK_ROOT = $AndroidSdkPath"
        Write-Host ""
        Write-Host "Next Steps:" -ForegroundColor Yellow
        Write-Host "1. Restart your terminal/IDE to pick up new environment variables"
        Write-Host "2. Run: .\gradlew-corporate.bat build"
        Write-Host "3. For Android build: .\gradlew-corporate.bat :app:assembleDebug"
        Write-Host ""
        Write-Host "Verification Commands:" -ForegroundColor Cyan
        Write-Host "  echo `$env:ANDROID_HOME"
        Write-Host "  adb version"
        Write-Host "  .\gradlew-corporate.bat :app:dependencies"
    }
    
} catch {
    Write-Host ""
    Write-Host "✗ Setup failed: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host ""
    Write-Host "Manual Setup Instructions:" -ForegroundColor Yellow
    Write-Host "1. Download Android Studio: https://developer.android.com/studio"
    Write-Host "2. Install Android Studio and SDK"
    Write-Host "3. Set environment variables manually:"
    Write-Host "   - ANDROID_HOME = C:\Users\YourName\AppData\Local\Android\Sdk"
    Write-Host "   - ANDROID_SDK_ROOT = C:\Users\YourName\AppData\Local\Android\Sdk"
    Write-Host "4. Add to PATH: %ANDROID_HOME%\platform-tools;%ANDROID_HOME%\tools"
    exit 1
}
