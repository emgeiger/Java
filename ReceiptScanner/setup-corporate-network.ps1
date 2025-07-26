# Corporate Network Gradle Setup Script for Android
# Based on emgeiger/Gradle template
# Run this script to configure environment for SSL issues

Write-Host "🔧 Setting up Android Gradle for Corporate Network..." -ForegroundColor Green

# Set Java system properties for SSL bypassing
$env:JAVA_OPTS = "-Dtrust_all_cert=true -Dcom.sun.net.ssl.checkRevocation=false -Dsun.security.ssl.allowUnsafeRenegotiation=true -Djava.net.useSystemProxies=true"
$env:GRADLE_OPTS = "-Dtrust_all_cert=true -Dcom.sun.net.ssl.checkRevocation=false -Dsun.security.ssl.allowUnsafeRenegotiation=true -Djava.net.useSystemProxies=true"

# Android SDK and build tools environment
$env:ANDROID_HOME = "C:\Users\$env:USERNAME\AppData\Local\Android\Sdk"
$env:PATH = "$env:ANDROID_HOME\tools;$env:ANDROID_HOME\tools\bin;$env:ANDROID_HOME\platform-tools;$env:PATH"

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
Write-Host "`n🔍 Checking Android SDK..." -ForegroundColor Green
if (Test-Path $env:ANDROID_HOME) {
    Write-Host "✅ Android SDK found at: $env:ANDROID_HOME" -ForegroundColor Yellow
} else {
    Write-Host "❌ Android SDK not found. Please install Android Studio first." -ForegroundColor Red
    Write-Host "   Download from: https://developer.android.com/studio" -ForegroundColor Red
}

# Method 1: Try with system proxy
Write-Host "`n🌐 Method 1: Attempting build with system proxy settings..." -ForegroundColor Green
try {
    .\gradlew.bat assembleDebug --stacktrace
    Write-Host "✅ Build successful with system proxy!" -ForegroundColor Green
    exit 0
}
catch {
    Write-Host "❌ Method 1 failed. Trying alternative approaches..." -ForegroundColor Yellow
}

# Method 2: Manual Gradle download
Write-Host "`n📦 Method 2: Manual Gradle download..." -ForegroundColor Green
$gradleUrl = "https://services.gradle.org/distributions/gradle-8.5-all.zip"
$gradleZip = "gradle-8.5-all.zip"
$gradleDir = "gradle-8.5"

if (Test-Path $gradleZip) {
    Write-Host "✅ Gradle zip already exists, skipping download" -ForegroundColor Yellow
}
else {
    Write-Host "⬇️  Downloading Gradle manually..." -ForegroundColor Yellow
    try {
        # Try with system proxy first
        Invoke-WebRequest -Uri $gradleUrl -OutFile $gradleZip -UseBasicParsing
        Write-Host "✅ Download successful!" -ForegroundColor Green
    }
    catch {
        Write-Host "❌ Direct download failed. You may need to:" -ForegroundColor Red
        Write-Host "   1. Download manually from: $gradleUrl" -ForegroundColor Red
        Write-Host "   2. Place it in the current directory as: $gradleZip" -ForegroundColor Red
        Write-Host "   3. Run this script again" -ForegroundColor Red
        
        # Try alternative download with proxy bypass
        Write-Host "`n🔄 Trying download with proxy bypass..." -ForegroundColor Yellow
        try {
            [System.Net.ServicePointManager]::ServerCertificateValidationCallback = { $true }
            [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.SecurityProtocolType]::Tls12
            $webClient = New-Object System.Net.WebClient
            $webClient.DownloadFile($gradleUrl, $gradleZip)
            Write-Host "✅ Alternative download successful!" -ForegroundColor Green
        }
        catch {
            Write-Host "❌ All download methods failed. Manual intervention required." -ForegroundColor Red
            exit 1
        }
    }
}

# Extract Gradle if needed
if (-not (Test-Path $gradleDir)) {
    Write-Host "📂 Extracting Gradle..." -ForegroundColor Yellow
    Expand-Archive -Path $gradleZip -DestinationPath . -Force
    Write-Host "✅ Gradle extracted!" -ForegroundColor Green
}

# Set up Gradle wrapper to use local Gradle
Write-Host "`n⚙️  Configuring Gradle wrapper..." -ForegroundColor Green
$gradleBin = Join-Path $gradleDir "bin\gradle.bat"
if (Test-Path $gradleBin) {
    Write-Host "✅ Local Gradle found at: $gradleBin" -ForegroundColor Yellow
    
    # Try building with local Gradle
    Write-Host "`n🔨 Attempting build with local Gradle..." -ForegroundColor Green
    & $gradleBin assembleDebug --stacktrace
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Build successful with local Gradle!" -ForegroundColor Green
    }
    else {
        Write-Host "❌ Build failed even with local Gradle." -ForegroundColor Red
    }
}
else {
    Write-Host "❌ Local Gradle not found after extraction." -ForegroundColor Red
}

# Provide troubleshooting guidance
Write-Host "`n📋 Additional Troubleshooting Steps:" -ForegroundColor Cyan
Write-Host "   1. Contact your IT team for corporate certificates" -ForegroundColor White
Write-Host "   2. Ask for proxy configuration details" -ForegroundColor White
Write-Host "   3. Request whitelisting of gradle.org and services.gradle.org" -ForegroundColor White
Write-Host "   4. Consider using a corporate Nexus/Artifactory repository" -ForegroundColor White
Write-Host "   5. Try building from a different network (home/mobile hotspot)" -ForegroundColor White

Write-Host "`n🔧 If SSL issues persist, you can manually configure:" -ForegroundColor Cyan
Write-Host "   - Edit gradle.properties to add your proxy settings" -ForegroundColor White
Write-Host "   - Import corporate certificates to Java keystore" -ForegroundColor White
Write-Host "   - Use offline Gradle distribution" -ForegroundColor White
