# Corporate Network Troubleshooting Guide for Android Development

This guide helps resolve common issues when developing Android applications in corporate environments with firewalls, proxies, and SSL inspection.

## 🚨 Common Corporate Network Issues

### 1. SSL Certificate Issues

**Error**: `PKIX path building failed: unable to find valid certification path to requested target`

**Cause**: Corporate SSL inspection replaces certificates with internal ones that Java/Gradle doesn't trust.

### 2. Proxy Authentication Issues

**Error**: `407 Proxy Authentication Required` or connection timeouts

**Cause**: Corporate proxy requires authentication that Gradle isn't configured for.

### 3. Blocked Domains

**Error**: Connection refused or DNS resolution failures

**Cause**: Corporate firewall blocks access to external repositories (Google, Maven Central).

## 🔧 Solution Steps (Try in Order)

### Step 1: Quick Setup with Provided Scripts

#### Option A: PowerShell Script (Recommended)

```powershell
# Run in PowerShell as Administrator
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
.\setup-corporate-network.ps1
```

### Step 2: Configure Proxy Settings

#### Find Your Proxy Settings

1. **Windows Settings**: Settings → Network & Internet → Proxy
2. **Control Panel**: Internet Options → Connections → LAN Settings
3. **Ask IT Team**: Get proxy host, port, and authentication details

#### Update gradle.properties

```properties
# Replace with your actual proxy details
systemProp.http.proxyHost=proxy.yourcompany.com
systemProp.http.proxyPort=8080
systemProp.https.proxyHost=proxy.yourcompany.com
systemProp.https.proxyPort=8080

# If authentication required
systemProp.http.proxyUser=DOMAIN\\username
systemProp.http.proxyPassword=your-password
systemProp.https.proxyUser=DOMAIN\\username
systemProp.https.proxyPassword=your-password
```

### Step 3: SSL Certificate Solutions

#### Option A: Import Corporate Certificates (Recommended)

```bash
# Get corporate root certificate from IT team
# Import to Java trust store (run as Administrator)
keytool -import -alias corporate-root -file corporate-cert.crt -keystore "%JAVA_HOME%\lib\security\cacerts" -storepass changeit
```

#### Option B: Temporary SSL Bypass (Development Only)

The provided `gradle.properties` includes SSL bypass flags. **Use only for development!**

#### Option C: Use Corporate Certificate Store

```properties
# Point to Windows certificate store
systemProp.javax.net.ssl.trustStoreType=Windows-ROOT
```

### Step 4: Android SDK Repository Issues

#### Configure Android SDK Proxy

In Android Studio: File → Settings → Appearance & Behavior → System Settings → HTTP Proxy

#### Alternative: Use Local Maven Cache

```gradle
repositories {
    google()
    mavenCentral()
    // Add local repository if available
    maven {
        url "http://your-internal-nexus.company.com/repository/maven-public/"
    }
}
```

## 🔍 Diagnostic Commands

### Test Java and Network

```batch
# Check Java version
java -version

# Test basic connectivity
ping google.com
nslookup dl.google.com

# Test HTTPS connectivity
curl -I https://dl.google.com/android/maven2/
```

### Check Proxy Settings

```batch
# Windows proxy settings
netsh winhttp show proxy

# Environment variables
echo %HTTP_PROXY%
echo %HTTPS_PROXY%
```

### Gradle Diagnostics

```batch
# Verbose Gradle output
gradlew assembleDebug --info --stacktrace

# Debug SSL issues
gradlew assembleDebug -Djavax.net.debug=ssl:handshake:verbose
```

## 🆘 Getting Help from IT Team

When contacting your IT team, ask for:

### Network Information

- [ ] HTTP/HTTPS proxy host and port
- [ ] Proxy authentication method (basic, NTLM, etc.)
- [ ] PAC (Proxy Auto-Configuration) file location
- [ ] Non-proxy hosts list

### Certificate Information

- [ ] Corporate root certificate file
- [ ] Instructions for importing certificates
- [ ] Whether they can whitelist google.com and maven repositories

### Repository Access

- [ ] Internal Maven/Gradle repository URLs
- [ ] VPN requirements for external access
- [ ] Approved external repository domains

## 🏠 Alternative Workarounds

### 1. Home Network Build

- Build project from home network
- Commit built artifacts
- Use in corporate environment

### 2. Mobile Hotspot

- Temporarily use mobile hotspot
- Download dependencies
- Switch back to corporate network

### 3. Offline Mode

- Build once with internet access
- Use `--offline` flag for subsequent builds
- Gradle will use cached dependencies

### 4. Docker Development

- Use Docker with pre-configured Gradle
- Build inside container with network access
- Mount project directory

## 📞 Support Contacts

### Internal Support

- **IT Help Desk**: [Your company's IT contact]
- **Development Tools Team**: [Your DevOps/Tools team]
- **Network Security**: [Your security team]

### External Resources

- **Gradle Forums**: <https://discuss.gradle.org/>
- **Android Developer Documentation**: <https://developer.android.com/>
- **Stack Overflow**: <https://stackoverflow.com/questions/tagged/android-gradle>

## ⚠️ Security Reminder

**Important**: The SSL bypass configurations in this template are for **development only**.

For production environments:

- Always use proper certificate validation
- Never commit passwords to version control
- Use secure credential storage (environment variables, credential managers)
- Remove temporary SSL bypass flags

## 🚀 Success Indicators

You'll know everything is working when:

- [ ] `gradlew assembleDebug` completes without errors
- [ ] `gradlew test` runs successfully  
- [ ] Android dependencies download properly
- [ ] No SSL or proxy error messages appear

Once these work, you're ready to develop the Receipt Scanner app! 🎉
