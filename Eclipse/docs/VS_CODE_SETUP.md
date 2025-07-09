# VS Code Setup Guide

This document explains how to run batch scripts and tasks in VS Code for the Eclipse Lunar Phase Monitor project.

## Quick Access Methods

### 1. Command Palette (Recommended)

- Press `Ctrl+Shift+P` to open the Command Palette
- Type "Tasks: Run Task" and press Enter
- Select from the available tasks:

**Main Tasks:**

- 🌙 **Run Lunar Phase Monitor** - Launch the GUI application
- 🧪 **Test Swiss Ephemeris API** - Run API tests
- ⚙️ **Run Setup** - Run project setup
- 🔐 **Setup SSL Gradlew** - Run Gradle with SSL

**Build Tasks:**

- 🔨 **Build with Gradle** - Compile the project (Default build: `Ctrl+Shift+B`)
- 🏃 **Run with Gradle** - Run using Gradle
- 🧹 **Clean Build** - Clean build artifacts
- 📦 **Create JAR** - Build JAR for distribution

**Testing:**

- 🔍 **Run All Tests** - Run unit tests (Default test)
- ☕ **Manual Compile (Backup)** - Fallback compilation

### 2. Keyboard Shortcuts

- `Ctrl+Shift+R` - Run Lunar Phase Monitor
- `Ctrl+Shift+T` - Test Swiss Ephemeris API  
- `Ctrl+Shift+B` - Build with Gradle (default)
- `F5` - Run with Gradle (when editing Java files)

### 3. VS Code Task Menu

- Go to **Terminal** → **Run Task...**
- Select the desired task from the list

### 4. Status Bar

- Click on the build status indicator in the status bar
- Select a task to run

## Batch Script Integration

### Available Scripts in `scripts/` folder:

1. **run.bat** - Launch the Lunar Phase Monitor GUI
2. **test-api.bat** - Test Swiss Ephemeris calculations
3. **setup.bat** - Project setup and initialization
4. **gradlew-ssl.bat** - Gradle with SSL configuration

### How Tasks Work:

- Tasks automatically navigate to the correct directories
- They handle both JAR files (from `artifacts/`) and compiled classes (from `build/`)
- Error handling and user feedback included
- Console output shown in VS Code terminal

## Task Configuration Details

### Task Types:

- **Build Tasks**: Compilation and project building
- **Test Tasks**: Running tests and API validation  
- **Run Tasks**: Executing the application
- **Utility Tasks**: Setup, cleanup, and maintenance

### Output Handling:

- Each task opens in its own terminal panel
- Build tasks share a terminal for related operations
- Problem matchers detect compilation errors and link to source files
- Clear error highlighting in VS Code

### Custom Task Settings:

- `presentation.reveal`: "always" - Shows terminal when task runs
- `presentation.panel`: "new"/"shared" - Terminal management
- `group`: Organizes tasks by type (build/test)
- `problemMatcher`: Links errors to source files

## Troubleshooting

### If Tasks Don't Appear:

1. Ensure `.vscode/tasks.json` exists in the workspace root
2. Reload VS Code window: `Ctrl+Shift+P` → "Developer: Reload Window"
3. Check workspace folder is correct

### If Batch Scripts Fail:

1. Verify Java is installed: Open terminal and run `java -version`
2. Check file paths in batch scripts match your project structure
3. Ensure batch scripts have execute permissions

### Build Issues:

1. Try "🧹 Clean Build" first
2. Use "☕ Manual Compile (Backup)" if Gradle has issues
3. Check that source files are in `src/main/java/`

### Path Issues:

- All tasks use `${workspaceFolder}` for relative paths
- Batch scripts use `%~dp0` for script-relative paths
- If paths fail, check that VS Code opened the correct workspace folder

## Advanced Usage

### Running Multiple Tasks:

1. Use `Ctrl+Shift+P` → "Tasks: Run Task"
2. Select multiple tasks to run sequentially
3. Each opens in its own terminal for monitoring

### Custom Task Modification:

- Edit `.vscode/tasks.json` to customize tasks
- Add new batch scripts to `scripts/` folder
- Create new tasks pointing to your scripts

### Integration with Extensions:

- Java Extension Pack: Enhanced Java support
- Gradle Extension: Better Gradle integration
- PowerShell Extension: Enhanced batch script editing

## Best Practices

1. **Always build before running**: Use `Ctrl+Shift+B` then `F5`
2. **Test regularly**: Use `Ctrl+Shift+T` to validate changes
3. **Clean builds for issues**: Use "🧹 Clean Build" when strange errors occur
4. **Monitor terminal output**: Check for compilation warnings and errors
5. **Use keyboard shortcuts**: Faster than menu navigation

## Quick Start Workflow

1. **Open project in VS Code**: File → Open Folder → Select Eclipse project
2. **Build**: `Ctrl+Shift+B` (Build with Gradle)
3. **Run**: `Ctrl+Shift+R` (Run Lunar Phase Monitor)
4. **Test**: `Ctrl+Shift+T` (Test Swiss Ephemeris API)

This setup provides a complete development environment for the Eclipse Lunar Phase Monitor project with easy access to all build, test, and run operations.
