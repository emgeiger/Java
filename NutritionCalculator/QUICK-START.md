# 🚀 Quick Start & Build Guide - NutritionCalculator

This guide helps you quickly build, run, and debug the NutritionCalculator project.

## 📋 Prerequisites

Before you begin, ensure you have:

- ☑️ **Java 11 or higher** installed ([Download OpenJDK](https://adoptium.net/))
- ☑️ **Android Studio** (for Android development)
- ☑️ **Git** installed
- ☑️ **VS Code** with Java extensions (recommended for desktop development)

## 🏗️ Quick Build Commands

### Desktop Application

```bash
# Standard build
.\gradlew.bat build

# Run desktop application
.\gradlew.bat run

# Run with debugging
.\gradlew.bat run --debug-jvm

# Clean and rebuild
.\gradlew.bat clean build
```

### Android Application

```bash
# Build Android APK
.\gradlew.bat :app:assembleDebug

# Install on connected device
.\gradlew.bat :app:installDebug

# Run Android tests
.\gradlew.bat :app:testDebugUnitTest
```

### For Corporate Networks

If you're behind a corporate firewall, use these alternatives:

```bash
# Use corporate-enabled wrapper
.\gradlew-corporate.bat build

# Or run setup script first
.\setup-corporate-network.ps1
```

## 🐛 Debugging & Troubleshooting

### Common Build Issues

#### 1. Gradle Wrapper Download Fails

**Problem**: `Could not download gradle-8.10.2-all.zip`

**Solutions**:
```bash
# Option 1: Use corporate script
.\setup-corporate-network.ps1

# Option 2: Manual download
# Download from: https://services.gradle.org/distributions/gradle-8.10.2-all.zip
# Extract to project directory

# Option 3: Use corporate wrapper
.\gradlew-corporate.bat build
```

#### 2. SSL Certificate Errors

**Problem**: `PKIX path building failed`

**Solutions**:
```bash
# Temporary fix (development only)
set GRADLE_OPTS=-Dtrust_all_cert=true
.\gradlew.bat build

# Or use corporate wrapper
.\gradlew-corporate.bat build
```

#### 3. Proxy Authentication Required

**Problem**: `407 Proxy Authentication Required`

**Solution**: Edit `gradle.properties`:
```properties
systemProp.http.proxyHost=your-proxy.com
systemProp.http.proxyPort=8080
systemProp.http.proxyUser=DOMAIN\\username
systemProp.http.proxyPassword=your-password
```

#### 4. Out of Memory Errors

**Problem**: `OutOfMemoryError` during build

**Solution**: Increase memory in `gradle.properties`:
```properties
org.gradle.jvmargs=-Xmx4096m -Dfile.encoding=UTF-8
```

### Android-Specific Issues

#### 1. Kotlin Compilation Errors

**Problem**: Kotlin version conflicts

**Solution**:
```bash
# Clean and rebuild
.\gradlew.bat :app:clean :app:build

# Check Kotlin version in build.gradle
```

#### 2. Compose UI Issues

**Problem**: Jetpack Compose compilation errors

**Solution**:
```bash
# Update Compose BOM version in app/build.gradle
implementation platform('androidx.compose:compose-bom:2024.08.00')

# Clean and rebuild
.\gradlew.bat :app:clean :app:assembleDebug
```

#### 3. Android SDK Not Found

**Problem**: `ANDROID_HOME` not set

**Solution**:
```bash
# Set environment variable
set ANDROID_HOME=C:\Users\%USERNAME%\AppData\Local\Android\Sdk
set PATH=%PATH%;%ANDROID_HOME%\tools;%ANDROID_HOME%\platform-tools
```

## 🎯 Development Workflow

### Desktop Development

1. **Setup Environment**:
   ```bash
   # Verify Java installation
   java -version
   
   # Test build
   .\gradlew.bat build
   ```

2. **Development Cycle**:
   ```bash
   # Make code changes
   # Run tests
   .\gradlew.bat test
   
   # Run application
   .\gradlew.bat run
   ```

3. **Debugging**:
   ```bash
   # Run with debug output
   .\gradlew.bat run --debug
   
   # Profile build performance
   .\gradlew.bat build --profile
   ```

### Android Development

1. **Setup**:
   ```bash
   # Sync project
   .\gradlew.bat :app:build
   
   # Verify Android SDK
   .\gradlew.bat :app:dependencies
   ```

2. **Development Cycle**:
   ```bash
   # Build debug APK
   .\gradlew.bat :app:assembleDebug
   
   # Install on device
   .\gradlew.bat :app:installDebug
   
   # Run unit tests
   .\gradlew.bat :app:testDebugUnitTest
   ```

3. **Compose UI Development**:
   ```bash
   # Enable Compose metrics
   .\gradlew.bat :app:assembleDebug -P enableComposeCompilerReports=true
   
   # Check Compose compiler metrics in build/compose_metrics/
   ```

## 🔧 Performance Optimization

### Build Performance

```bash
# Enable parallel builds
echo "org.gradle.parallel=true" >> gradle.properties

# Enable build cache
echo "org.gradle.caching=true" >> gradle.properties

# Use Gradle daemon
echo "org.gradle.daemon=true" >> gradle.properties
```

### Android Build Optimization

```bash
# Enable R8 code shrinking
# In app/build.gradle:
# minifyEnabled true
# proguardFiles getDefaultProguardFile('proguard-android-optimize.txt')

# Build specific variant
.\gradlew.bat :app:assembleDebug
# Instead of
.\gradlew.bat build
```

## 📱 Testing Strategy

### Unit Tests

```bash
# Run all tests
.\gradlew.bat test

# Run specific test class
.\gradlew.bat test --tests "*NutritionCalculatorTest"

# Run with coverage
.\gradlew.bat test jacocoTestReport
```

### Android Tests

```bash
# Unit tests
.\gradlew.bat :app:testDebugUnitTest

# Instrumentation tests (requires device/emulator)
.\gradlew.bat :app:connectedDebugAndroidTest

# Compose UI tests
.\gradlew.bat :app:testDebugUnitTest --tests "*ComposeTest"
```

## 🚀 Deployment

### Desktop JAR

```bash
# Build distributable JAR
.\gradlew.bat jar

# JAR location: build/libs/NutritionCalculator.jar
```

### Android APK

```bash
# Debug APK
.\gradlew.bat :app:assembleDebug
# Location: app/build/outputs/apk/debug/

# Release APK (requires signing)
.\gradlew.bat :app:assembleRelease
# Location: app/build/outputs/apk/release/
```

## 📞 Getting Help

### Error Resolution Priority

1. **Check this guide** for common solutions
2. **Review logs**: `build/reports/` directory
3. **Check corporate network guide**: `CORPORATE-NETWORK-GUIDE.md`
4. **Search issues**: [Gradle Forums](https://discuss.gradle.org/)
5. **Contact team**: Internal development support

### Useful Resources

- 📖 [Gradle User Manual](https://docs.gradle.org/current/userguide/userguide.html)
- 🤖 [Android Developer Guide](https://developer.android.com/)
- 🎨 [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- 💬 [Stack Overflow](https://stackoverflow.com/questions/tagged/gradle)

## ✅ Success Checklist

- [ ] Project builds without errors
- [ ] Desktop application runs successfully
- [ ] Android APK builds and installs
- [ ] Unit tests pass
- [ ] Corporate network issues resolved
- [ ] Development environment configured

Happy coding! 🎉
