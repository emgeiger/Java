# =============================================================================
# OneDrive File Unlock and Gradle Properties Security PowerShell Script
# Purpose: Advanced OneDrive file locking resolution and security management
# =============================================================================

param(
    [switch]$Force,
    [switch]$Verbose,
    [switch]$SkipOneDrive
)

# Set error handling
$ErrorActionPreference = "Continue"

# Set verbose preference
if ($Verbose) {
    $VerbosePreference = "Continue"
}

Write-Host "`n===========================================" -ForegroundColor Cyan
Write-Host "OneDrive File Unlock and Security Script (PowerShell)" -ForegroundColor Cyan
Write-Host "===========================================" -ForegroundColor Cyan

# Get script directory
$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Definition
$ProjectDir = $ScriptDir
Write-Host "Project Directory: $ProjectDir" -ForegroundColor Yellow

# Check if running as administrator
$IsAdmin = ([Security.Principal.WindowsPrincipal] [Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole] "Administrator")
if (-not $IsAdmin) {
    Write-Warning "Not running as administrator. Some operations may fail."
    Write-Host "Consider running: Start-Process PowerShell -Verb RunAs" -ForegroundColor Yellow
}

# =============================================================================
# Function: Stop OneDrive Process
# =============================================================================

function Stop-OneDriveProcess {
    Write-Host "`n[STEP 1] Stopping OneDrive processes..." -ForegroundColor Green
    
    $OneDriveProcesses = Get-Process -Name "OneDrive" -ErrorAction SilentlyContinue
    
    if ($OneDriveProcesses) {
        foreach ($process in $OneDriveProcesses) {
            Write-Verbose "Stopping OneDrive process (PID: $($process.Id))"
            try {
                $process.Kill()
                Write-Host "✓ OneDrive process stopped (PID: $($process.Id))" -ForegroundColor Green
            }
            catch {
                Write-Warning "Failed to stop OneDrive process: $($_.Exception.Message)"
            }
        }
        
        # Wait for processes to fully terminate
        Write-Host "Waiting for processes to terminate..." -ForegroundColor Yellow
        Start-Sleep -Seconds 5
    }
    else {
        Write-Host "- OneDrive is not running" -ForegroundColor Gray
    }
}

# =============================================================================
# Function: Unlock File Handles
# =============================================================================

function Unlock-FileHandles {
    param([string]$Path)
    
    Write-Verbose "Unlocking file handles for: $Path"
    
    if (Test-Path $Path) {
        try {
            # Remove read-only attribute
            Get-ChildItem -Path $Path -Recurse -Force | ForEach-Object {
                if ($_.Attributes -band [System.IO.FileAttributes]::ReadOnly) {
                    $_.Attributes = $_.Attributes -bxor [System.IO.FileAttributes]::ReadOnly
                    Write-Verbose "Removed read-only from: $($_.FullName)"
                }
            }
            
            # Reset file permissions
            if ($IsAdmin) {
                icacls $Path /reset /t /c | Out-Null
                Write-Verbose "Reset permissions for: $Path"
            }
            
            return $true
        }
        catch {
            Write-Warning "Failed to unlock $Path : $($_.Exception.Message)"
            return $false
        }
    }
    else {
        Write-Verbose "Path does not exist: $Path"
        return $false
    }
}

# =============================================================================
# Function: Handle OneDrive File Locking
# =============================================================================

function Resolve-OneDriveFileLocking {
    Write-Host "`n[STEP 2] Resolving OneDrive file locking issues..." -ForegroundColor Green
    
    # Critical paths that often get locked
    $CriticalPaths = @(
        "$ProjectDir\build",
        "$ProjectDir\.gradle",
        "$ProjectDir\gradle.properties",
        "$ProjectDir\bin",
        "$ProjectDir\local-gradle"
    )
    
    foreach ($path in $CriticalPaths) {
        if (Test-Path $path) {
            Write-Host "- Unlocking: $(Split-Path -Leaf $path)" -ForegroundColor Yellow
            $result = Unlock-FileHandles -Path $path
            if ($result) {
                Write-Host "  ✓ Successfully unlocked" -ForegroundColor Green
            }
            else {
                Write-Host "  ❌ Failed to unlock" -ForegroundColor Red
            }
        }
    }
    
    # Force unlock using handle.exe if available
    if (Get-Command "handle.exe" -ErrorAction SilentlyContinue) {
        Write-Host "Using handle.exe for advanced file handle detection..." -ForegroundColor Yellow
        $handles = handle.exe -p OneDrive 2>$null
        if ($handles) {
            Write-Host "Found OneDrive file handles - attempting to resolve" -ForegroundColor Yellow
        }
    }
}

# =============================================================================
# Function: Secure Gradle Properties
# =============================================================================

function Secure-GradleProperties {
    Write-Host "`n[STEP 3] Securing gradle.properties file..." -ForegroundColor Green
    
    $GradlePropsPath = "$ProjectDir\gradle.properties"
    $GradleTemplatePath = "$ProjectDir\gradle.properties.template"
    
    if (Test-Path $GradlePropsPath) {
        Write-Host "Analyzing gradle.properties for sensitive content..." -ForegroundColor Yellow
        
        # Read file content
        $content = Get-Content $GradlePropsPath -Raw
        
        # Check for sensitive patterns
        $SensitivePatterns = @(
            "ovintiv",
            "corporate",
            "company",
            "password",
            "secret",
            "token",
            "credential",
            "trustStore",
            "keyStore",
            "\.corp\.",
            "\.local"
        )
        
        $foundSensitive = $false
        foreach ($pattern in $SensitivePatterns) {
            if ($content -match $pattern) {
                $foundSensitive = $true
                Write-Warning "Found sensitive pattern: $pattern"
            }
        }
        
        if ($foundSensitive -or $Force) {
            Write-Host "Creating secure backup and sanitizing file..." -ForegroundColor Yellow
            
            # Create timestamped backup
            $timestamp = Get-Date -Format "yyyyMMdd_HHmmss"
            $backupPath = "$ProjectDir\gradle.properties.backup.$timestamp"
            Copy-Item $GradlePropsPath $backupPath -Force
            Write-Host "✓ Backup created: gradle.properties.backup.$timestamp" -ForegroundColor Green
            
            # Replace with template or create minimal version
            if (Test-Path $GradleTemplatePath) {
                Copy-Item $GradleTemplatePath $GradlePropsPath -Force
                Write-Host "✓ Replaced with sanitized template" -ForegroundColor Green
            }
            else {
                Write-Host "Creating minimal safe gradle.properties..." -ForegroundColor Yellow
                $safeContent = @"
# Safe Gradle Configuration - No sensitive data
org.gradle.daemon=true
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.configureondemand=true
org.gradle.jvmargs=-Xmx2048m -XX:MaxMetaspaceSize=512m -XX:+HeapDumpOnOutOfMemoryError
"@
                Set-Content -Path $GradlePropsPath -Value $safeContent -Encoding UTF8
                Write-Host "✓ Created minimal safe gradle.properties" -ForegroundColor Green
            }
        }
        else {
            Write-Host "✓ No sensitive content detected in gradle.properties" -ForegroundColor Green
        }
    }
    else {
        Write-Host "gradle.properties not found" -ForegroundColor Yellow
        if (Test-Path $GradleTemplatePath) {
            Copy-Item $GradleTemplatePath $GradlePropsPath -Force
            Write-Host "✓ Created gradle.properties from template" -ForegroundColor Green
        }
    }
}

# =============================================================================
# Function: Verify Git Protection
# =============================================================================

function Verify-GitProtection {
    Write-Host "`n[STEP 4] Verifying .gitignore protection..." -ForegroundColor Green
    
    $GitIgnorePath = "$ProjectDir\.gitignore"
    
    if (Test-Path $GitIgnorePath) {
        $gitignoreContent = Get-Content $GitIgnorePath -Raw
        
        if ($gitignoreContent -match "gradle\.properties") {
            Write-Host "✓ gradle.properties is protected in .gitignore" -ForegroundColor Green
        }
        else {
            Write-Warning "gradle.properties is NOT in .gitignore!"
            Write-Host "Adding protection..." -ForegroundColor Yellow
            
            $protectionContent = @"

# Gradle Properties - SECURITY CRITICAL
gradle.properties
gradle.properties.*
*.properties.backup.*
"@
            Add-Content -Path $GitIgnorePath -Value $protectionContent -Encoding UTF8
            Write-Host "✓ Added gradle.properties to .gitignore" -ForegroundColor Green
        }
    }
    else {
        Write-Warning ".gitignore not found!"
    }
}

# =============================================================================
# Function: Clean Git Cache
# =============================================================================

function Clear-GitCache {
    Write-Host "`n[STEP 5] Cleaning Git cache for sensitive files..." -ForegroundColor Green
    
    if (Test-Path "$ProjectDir\.git") {
        Push-Location $ProjectDir
        try {
            Write-Host "Removing gradle.properties from Git cache..." -ForegroundColor Yellow
            git rm --cached gradle.properties 2>$null
            if ($LASTEXITCODE -eq 0) {
                Write-Host "✓ gradle.properties removed from Git cache" -ForegroundColor Green
            }
            else {
                Write-Host "- gradle.properties not in Git cache or already removed" -ForegroundColor Gray
            }
        }
        catch {
            Write-Warning "Git operation failed: $($_.Exception.Message)"
        }
        finally {
            Pop-Location
        }
    }
    else {
        Write-Host "- Not in a Git repository, skipping Git cache cleanup" -ForegroundColor Gray
    }
}

# =============================================================================
# Function: Restart OneDrive
# =============================================================================

function Start-OneDriveProcess {
    if ($SkipOneDrive) {
        Write-Host "`n[STEP 6] Skipping OneDrive restart (SkipOneDrive flag set)" -ForegroundColor Gray
        return
    }
    
    Write-Host "`n[STEP 6] Restarting OneDrive..." -ForegroundColor Green
    
    # Find OneDrive executable
    $OneDrivePaths = @(
        "$env:LOCALAPPDATA\Microsoft\OneDrive\OneDrive.exe",
        "$env:ProgramFiles\Microsoft OneDrive\OneDrive.exe",
        "$env:ProgramFiles(x86)\Microsoft OneDrive\OneDrive.exe"
    )
    
    $OneDriveExe = $null
    foreach ($path in $OneDrivePaths) {
        if (Test-Path $path) {
            $OneDriveExe = $path
            break
        }
    }
    
    if ($OneDriveExe) {
        try {
            Start-Process $OneDriveExe -WindowStyle Hidden
            Write-Host "✓ OneDrive restarted" -ForegroundColor Green
        }
        catch {
            Write-Warning "Failed to start OneDrive: $($_.Exception.Message)"
        }
    }
    else {
        Write-Warning "OneDrive executable not found. Please start OneDrive manually."
    }
}

# =============================================================================
# Function: Final Security Check
# =============================================================================

function Test-FinalSecurity {
    Write-Host "`n[STEP 7] Final security verification..." -ForegroundColor Green
    
    $GradlePropsPath = "$ProjectDir\gradle.properties"
    
    if (Test-Path $GradlePropsPath) {
        $content = Get-Content $GradlePropsPath -Raw
        
        $SensitivePatterns = @(
            "ovintiv",
            "corporate",
            "company",
            "password",
            "secret",
            "token",
            "credential"
        )
        
        $foundSensitive = $false
        foreach ($pattern in $SensitivePatterns) {
            if ($content -match $pattern) {
                $foundSensitive = $true
                Write-Warning "Still contains sensitive pattern: $pattern"
            }
        }
        
        if ($foundSensitive) {
            Write-Host "❌ WARNING: gradle.properties still contains sensitive information!" -ForegroundColor Red
            Write-Host "Please manually review and clean the file." -ForegroundColor Yellow
        }
        else {
            Write-Host "✓ gradle.properties appears to be clean" -ForegroundColor Green
        }
    }
    
    # Check for backup files that might contain sensitive data
    $BackupFiles = Get-ChildItem -Path $ProjectDir -Filter "*.backup.*" -ErrorAction SilentlyContinue
    if ($BackupFiles) {
        Write-Host "`nFound backup files:" -ForegroundColor Yellow
        $BackupFiles | ForEach-Object {
            Write-Host "  - $($_.Name)" -ForegroundColor Gray
        }
        Write-Host "Ensure these backup files are not committed to version control." -ForegroundColor Yellow
    }
}

# =============================================================================
# Main Execution
# =============================================================================

try {
    # Execute all steps
    if (-not $SkipOneDrive) {
        Stop-OneDriveProcess
        Resolve-OneDriveFileLocking
    }
    
    Secure-GradleProperties
    Verify-GitProtection
    Clear-GitCache
    
    if (-not $SkipOneDrive) {
        Start-OneDriveProcess
    }
    
    Test-FinalSecurity
    
    Write-Host "`n===========================================" -ForegroundColor Cyan
    Write-Host "Security Script Complete" -ForegroundColor Cyan
    Write-Host "===========================================" -ForegroundColor Cyan
    
    Write-Host "`nIMPORTANT REMINDERS:" -ForegroundColor Yellow
    Write-Host "1. Never commit gradle.properties with sensitive corporate information" -ForegroundColor White
    Write-Host "2. Always use gradle.properties.template for repository commits" -ForegroundColor White
    Write-Host "3. Review .gitignore to ensure sensitive files are excluded" -ForegroundColor White
    Write-Host "4. Use separate configuration files for different environments" -ForegroundColor White
    Write-Host "5. Consider using environment variables for sensitive configuration" -ForegroundColor White
    
    Write-Host "`nScript Parameters:" -ForegroundColor Gray
    Write-Host "  -Force          : Force sanitization even if no sensitive content detected" -ForegroundColor Gray
    Write-Host "  -Verbose        : Enable verbose output" -ForegroundColor Gray
    Write-Host "  -SkipOneDrive   : Skip OneDrive operations" -ForegroundColor Gray
    
}
catch {
    Write-Error "Script execution failed: $($_.Exception.Message)"
    exit 1
}

Write-Host "`nScript completed successfully." -ForegroundColor Green
