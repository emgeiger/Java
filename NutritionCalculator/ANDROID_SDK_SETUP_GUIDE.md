# Manual Android SDK Setup Guide
# For NutritionCalculator Project

## Quick Setup Instructions

### Option 1: Install Android Studio (Recommended)
1. Download Android Studio from: https://developer.android.com/studio
2. Install Android Studio (this automatically includes Android SDK)
3. During installation, note the SDK location (usually: C:\Users\YourName\AppData\Local\Android\Sdk)
4. Run the setup commands below

### Option 2: Command Line Tools Only
1. Download Android SDK Command Line Tools from: https://developer.android.com/studio#command-tools
2. Extract to: C:\Android\Sdk\cmdline-tools\latest\
3. Run the setup commands below

## Environment Variable Setup Commands

### For PowerShell (Run as Administrator):
```powershell
# Set Android SDK path (replace with your actual path)
$ANDROID_SDK_PATH = "C:\Users\$env:USERNAME\AppData\Local\Android\Sdk"

# Set environment variables
[Environment]::SetEnvironmentVariable("ANDROID_HOME", $ANDROID_SDK_PATH, "User")
[Environment]::SetEnvironmentVariable("ANDROID_SDK_ROOT", $ANDROID_SDK_PATH, "User")

# Update PATH
$currentPath = [Environment]::GetEnvironmentVariable("PATH", "User")
$newPath = "$currentPath;$ANDROID_SDK_PATH\platform-tools;$ANDROID_SDK_PATH\tools;$ANDROID_SDK_PATH\tools\bin;$ANDROID_SDK_PATH\build-tools"
[Environment]::SetEnvironmentVariable("PATH", $newPath, "User")

# Set for current session
$env:ANDROID_HOME = $ANDROID_SDK_PATH
$env:ANDROID_SDK_ROOT = $ANDROID_SDK_PATH
$env:PATH += ";$ANDROID_SDK_PATH\platform-tools;$ANDROID_SDK_PATH\tools"

Write-Host "✓ Android SDK environment variables set!" -ForegroundColor Green
```

### For Command Prompt (Run as Administrator):
```cmd
REM Set Android SDK path (replace with your actual path)
set ANDROID_SDK_PATH=C:\Users\%USERNAME%\AppData\Local\Android\Sdk

REM Set environment variables
setx ANDROID_HOME "%ANDROID_SDK_PATH%"
setx ANDROID_SDK_ROOT "%ANDROID_SDK_PATH%"
setx PATH "%PATH%;%ANDROID_SDK_PATH%\platform-tools;%ANDROID_SDK_PATH%\tools;%ANDROID_SDK_PATH%\build-tools"

echo Android SDK environment variables set!
```

## Common Android SDK Locations

Check these paths for existing installations:
- `C:\Users\%USERNAME%\AppData\Local\Android\Sdk` (Android Studio default)
- `C:\Android\Sdk` (custom installation)
- `C:\Program Files\Android\Sdk`
- `C:\Program Files (x86)\Android\Sdk`

## Verification Commands

After setting up, restart your terminal and test:

```powershell
# Check environment variables
echo $env:ANDROID_HOME
echo $env:ANDROID_SDK_ROOT

# Test Android tools
adb version
```

## For NutritionCalculator Project

Once Android SDK is configured:

```bash
# Build desktop version
.\gradlew-corporate.bat build

# Build Android version
.\gradlew-corporate.bat :app:assembleDebug

# Install on device/emulator
.\gradlew-corporate.bat :app:installDebug
```

## Troubleshooting

### If Android SDK is not found:
1. Install Android Studio first
2. Open Android Studio and go to SDK Manager
3. Install latest Android SDK Platform and Build Tools
4. Note the SDK location and use it in the setup commands

### If build fails with "SDK location not found":
1. Create `local.properties` file in project root:
```
sdk.dir=C:\\Users\\YourName\\AppData\\Local\\Android\\Sdk
```

### If PATH issues persist:
1. Restart your terminal/IDE completely
2. Check PATH with: `echo $env:PATH`
3. Manually add SDK tools to PATH if needed

## Required SDK Components

Ensure these are installed via Android Studio SDK Manager:
- Android SDK Platform (API 34 or latest)
- Android SDK Build-Tools (34.0.0 or latest)
- Android SDK Platform-Tools
- Android Emulator (optional, for testing)
