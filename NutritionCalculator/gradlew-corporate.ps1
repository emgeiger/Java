# Corporate Network Gradle Wrapper Script (PowerShell)
# This script sets SSL bypass environment variables before running Gradle

Write-Host "Setting up corporate network environment variables..." -ForegroundColor Green

# Set JAVA_HOME to Microsoft JDK 21
$env:JAVA_HOME = "C:\Program Files\Microsoft\jdk-21.0.7.6-hotspot"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# SSL Bypass for corporate networks (TEMPORARY WORKAROUND)
$env:JAVA_OPTS = "-Djavax.net.ssl.trustStore=NONE -Djavax.net.ssl.trustStoreType=Windows-ROOT -Dcom.sun.net.ssl.checkRevocation=false -Dtrust_all_cert=true -Djavax.net.ssl.trustStorePassword= -Djava.net.useSystemProxies=true -Dcom.sun.net.ssl.enableECC=false -Dsun.security.ssl.allowUnsafeRenegotiation=true -Dsun.security.ssl.allowLegacyHelloMessages=true"

# Additional Gradle options
$env:GRADLE_OPTS = "-Dorg.gradle.daemon=true -Dorg.gradle.parallel=true"

# Network timeouts
$env:JAVA_OPTS += " -Dsun.net.client.defaultConnectTimeout=60000 -Dsun.net.client.defaultReadTimeout=60000"

Write-Host "Using Java: $env:JAVA_HOME" -ForegroundColor Yellow
Write-Host "Environment variables set. Running Gradle..." -ForegroundColor Green
Write-Host ""

# Run Gradle with the provided arguments
& ".\gradlew.bat" $args

Write-Host ""
Write-Host "Gradle execution completed." -ForegroundColor Green
