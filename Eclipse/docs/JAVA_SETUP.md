# Java Development Kit (JDK) Setup and Management

This document provides a comprehensive guide for managing JDK installations and updates for the Eclipse project.

## Current Configuration

### Detected JDK Installation

- **Location**: `C:\jdk-21.0.7+6`
- **Version**: JDK 21.0.7+6
- **Type**: Eclipse Temurin/Adoptium (inferred from path structure)

### Project Configuration Files

- `gradle.properties` - Gradle-specific Java configuration
- `scripts/set-java-env.bat` - Session-specific environment setup
- `gradle-enhanced.bat` - Enhanced Gradle wrapper with Java config
- `scripts/gradlew-ssl.bat` - SSL-configured Gradle wrapper
- `scripts/setup-java.bat` - Comprehensive Java setup utility

## Quick Setup

### Option 1: Automatic Setup (Recommended)

```powershell
# Run the comprehensive setup script
.\scripts\setup-java.bat
```

### Option 2: Manual Session Setup

```powershell
# Set for current PowerShell session
$env:JAVA_HOME = "C:\jdk-21.0.7+6"
$env:PATH = "C:\jdk-21.0.7+6\bin;" + $env:PATH

# Or use batch script
.\scripts\set-java-env.bat
```

### Option 3: Use Enhanced Gradle Wrapper

```powershell
# Uses project-specific Java configuration automatically
.\gradle-enhanced.bat build
.\gradle-enhanced.bat run
```

## JDK Update Workflow

### 1. Check for New JDK Versions

```powershell
# Visit official sources:
# - Eclipse Adoptium: https://adoptium.net/
# - Oracle JDK: https://www.oracle.com/java/technologies/downloads/
# - Microsoft OpenJDK: https://docs.microsoft.com/en-us/java/openjdk/download
```

### 2. Install New JDK

- Download and install to `C:\jdk-<version>`
- Example: `C:\jdk-21.0.8+10` for a newer patch version

### 3. Update Project Configuration

#### Method A: Run Setup Script

```powershell
.\scripts\setup-java.bat
# Select the new JDK from the detected list
```

#### Method B: Manual Update

Update these files with the new JDK path:

**gradle.properties:**

```properties
org.gradle.java.home=C:/jdk-21.0.8+10
systemProp.java.home=C:/jdk-21.0.8+10
systemProp.javax.net.ssl.trustStore=C:/jdk-21.0.8+10/lib/security/cacerts
```

**scripts/set-java-env.bat:**

```batch
set "JAVA_HOME=C:\jdk-21.0.8+10"
set "PATH=C:\jdk-21.0.8+10\bin;%PATH%"
```

**gradle-enhanced.bat:**

```batch
set "PROJECT_JAVA_HOME=C:\jdk-21.0.8+10"
```

**scripts/gradlew-ssl.bat:**

```batch
set "JAVA_HOME=C:\jdk-21.0.8+10"
```

### 4. Verify Update

```powershell
# Test Java version
.\scripts\set-java-env.bat
java -version

# Test Gradle
.\gradlew.bat --version

# Test project build
.\gradlew.bat clean build
```

### 5. Cleanup Old JDK (Optional)

```powershell
# After confirming everything works
Remove-Item "C:\jdk-21.0.7+6" -Recurse -Confirm
```

## Environment Variable Management

### System-Wide Setup (Administrator Required)

```powershell
# PowerShell as Administrator
[Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\jdk-21.0.7+6", "Machine")

# Update PATH (careful not to duplicate entries)
$path = [Environment]::GetEnvironmentVariable("PATH", "Machine")
if ($path -notlike "*C:\jdk-21.0.7+6\bin*") {
    [Environment]::SetEnvironmentVariable("PATH", "C:\jdk-21.0.7+6\bin;" + $path, "Machine")
}
```

### User-Specific Setup

```powershell
[Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\jdk-21.0.7+6", "User")
```

### Project-Only Setup (Current Approach)

The project is configured to work without system environment variables by:
1. Setting `org.gradle.java.home` in `gradle.properties`
2. Using project-specific scripts that set JAVA_HOME locally
3. Enhanced wrappers that configure Java automatically

## Troubleshooting

### Issue: "JAVA_HOME is not set"

**Solution:**
```powershell
# Run project environment setup
.\scripts\set-java-env.bat

# Or set manually for session
$env:JAVA_HOME = "C:\jdk-21.0.7+6"
```

### Issue: "Java version mismatch"

**Solution:**
```powershell
# Verify which Java is being used
where.exe java
java -version

# Reset environment
.\scripts\set-java-env.bat
```

### Issue: Gradle SSL errors

**Solution:**
```powershell
# Use SSL-configured wrapper
.\scripts\gradlew-ssl.bat build

# Or use enhanced wrapper
.\gradle-enhanced.bat build
```

### Issue: Multiple JDK versions detected

**Solution:**
```powershell
# Run setup script to choose correct version
.\scripts\setup-java.bat
```

## Best Practices

### 1. JDK Installation

- Install JDKs to simple paths: `C:\jdk-<version>`
- Avoid spaces and special characters in paths
- Keep multiple versions for compatibility testing

### 2. Project Configuration

- Use `gradle.properties` for Gradle-specific Java config
- Keep project scripts in sync with JDK updates
- Test after any JDK changes

### 3. Team Collaboration

- Document the required JDK version in README
- Use relative paths where possible
- Provide setup scripts for team members

### 4. Version Management

- Update patch versions promptly for security
- Test major version updates in development first
- Keep one version back as fallback

## Integration with VS Code

The project's VS Code tasks automatically use the Java configuration from:
1. `gradle.properties` settings
2. Project-specific scripts
3. Enhanced Gradle wrappers

No additional VS Code configuration needed if project setup is correct.

## Scripts Reference

| Script | Purpose | Usage |
|--------|---------|-------|
| `setup-java.bat` | Full JDK detection and setup | `.\scripts\setup-java.bat` |
| `set-java-env.bat` | Quick environment setup | `.\scripts\set-java-env.bat` |
| `gradle-enhanced.bat` | Gradle with Java config | `.\gradle-enhanced.bat <task>` |
| `gradlew-ssl.bat` | Gradle with SSL fixes | `.\scripts\gradlew-ssl.bat <task>` |

## Security Considerations

### SSL Configuration

The project includes SSL workarounds for corporate environments:
- Certificate validation disabled in development
- Uses system trust store
- Alternative for restricted networks

### Trust Store Management

```powershell
# Verify trust store location
dir "C:\jdk-21.0.7+6\lib\security\cacerts"

# Import custom certificates if needed (Administrator)
keytool -import -trustcacerts -keystore "C:\jdk-21.0.7+6\lib\security\cacerts" -alias mycert -file certificate.crt
```

This setup provides a robust, maintainable approach to JDK management for the Eclipse project while supporting team collaboration and easy updates.
