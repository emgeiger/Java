# Markdown Linting Guidelines

This document provides comprehensive guidelines for writing properly formatted Markdown that passes linting checks.

## Core Principles

### 1. Blank Lines Around Headings

**Rule**: Always add blank lines before and after headings.

**❌ Incorrect:**

```markdown
Some text here.
## Heading
More text immediately after.
```

**✅ Correct:**

```markdown
Some text here.

## Heading

More text with proper spacing.
```

### 2. Blank Lines Around Lists

**Rule**: Always add blank lines before and after lists.

**❌ Incorrect:**

```markdown
Here are the items:
- Item 1
- Item 2
- Item 3
The list continues...
```

**✅ Correct:**

```markdown

Here are the items:

- Item 1
- Item 2
- Item 3

The list continues...
```

### 3. Blank Lines Around Fenced Code Blocks

**Rule**: Always add blank lines before and after fenced code blocks.

**✅ Correct:**
```markdown
Here's some code:

```bash
command --help
```

More text after code.

```

### 4. Language Specification for Code Blocks

**Rule**: Always specify the language for fenced code blocks.

**✅ Correct:**

````markdown

```bash
echo "Hello World"
```
````

### 5. Heading Punctuation

**Rule**: Don't end headings with punctuation (colons, periods).

**✅ Correct:**

```markdown

## Configuration Options
### Setup Steps
```

### 6. Ordered List Numbering

**Rule**: Use consistent numbering (1, 2, 3) instead of restarting.

**✅ Correct:**

```markdown

1. First step
2. Second step
3. Third step
```

## Common Patterns in Technical Documentation

### File Structure Documentation

**✅ Correct Format:**

```markdown

## Project Structure

The project follows this organization:

```

├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
└── docs/

```

This structure provides clear separation of concerns.
```

### Command Examples

**✅ Correct Format:**

```markdown

## Installation Steps

Follow these commands to install:

```powershell
# Download the package
wget https://example.com/package.zip

# Extract the files
unzip package.zip

# Run the installer
./install.sh
```

The installation process is now complete.

```

### Feature Lists

**✅ Correct Format:**
```markdown
## Key Features

The application provides these capabilities:

- **Real-time Updates**: Automatic data refresh
- **Offline Mode**: Works without internet connection
- **Cross-platform**: Runs on Windows, Mac, and Linux

These features make the application versatile and reliable.
```

### Status Indicators

**✅ Correct Format:**

```markdown

## Build Status

Current project status:

- ✅ Compilation: Successful
- ✅ Tests: All passing
- ⚠️ Documentation: In progress
- ❌ Deployment: Needs configuration

Overall status: Ready for testing.
```

## VS Code Integration

### Recommended Extensions

Install these VS Code extensions for Markdown linting:

- **markdownlint** (David Anson) - Primary linting extension
- **Markdown All in One** - Enhanced Markdown support
- **Markdown Preview Enhanced** - Better preview with linting

### Configuration

Add to your `settings.json`:

```json
{
  "markdownlint.config": {
    "MD013": false,
    "MD026": true,
    "MD033": false,
    "MD041": false
  }
}
```

### Auto-formatting

Enable auto-formatting on save:

```json
{
  "[markdown]": {
    "editor.formatOnSave": true,
    "editor.defaultFormatter": "DavidAnson.vscode-markdownlint"
  }
}
```

## Common Fixes for Eclipse Project Documentation

### Before: Typical Issues

```markdown
### Quick Start
- Step 1: Download
- Step 2: Install
- Step 3: Configure
### Advanced Setup
For advanced users:

```bash
npm install --advanced

```

You can now proceed.

```

### After: Properly Formatted

```markdown
### Quick Start

Follow these steps:

- Step 1: Download
- Step 2: Install  
- Step 3: Configure

### Advanced Setup

For advanced users:

```bash
npm install --advanced
```

You can now proceed with advanced configuration.

```

## Project-Specific Guidelines

### Documentation Standards for Eclipse Project

1. **Headers**: Use `##` for main sections, `###` for subsections
2. **Code Blocks**: Always specify language (powershell, java, bash, json)
3. **Lists**: Use `-` for unordered lists, `1.` for ordered lists
4. **Emphasis**: Use `**bold**` for important terms, `*italic*` for emphasis
5. **Links**: Use descriptive text, not raw URLs

### File Naming

Use consistent naming for documentation:

- `SETUP.md` - Installation and setup instructions
- `USAGE.md` - How to use the application
- `API.md` - API documentation
- `TROUBLESHOOTING.md` - Common issues and solutions

### Cross-References

Link between documentation files properly:

```markdown
For installation instructions, see [Setup Guide](SETUP.md).

For API details, refer to the [API Documentation](API.md#authentication).
```

## Automation

### Pre-commit Hooks

Add Markdown linting to pre-commit hooks:

```yaml
# .pre-commit-config.yaml
repos:
  - repo: https://github.com/igorshubovych/markdownlint-cli
    rev: v0.37.0
    hooks:
      - id: markdownlint
```

### GitHub Actions

Automate linting in CI/CD:

```yaml
name: Markdown Lint
on: [push, pull_request]
jobs:
  markdownlint:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: articulate/actions-markdownlint@v1
```

This ensures all documentation meets quality standards consistently.
