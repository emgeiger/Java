<!-- Use this file to provide workspace-specific custom instructions to Copilot. For more details, visit https://code.visualstudio.com/docs/copilot/copilot-customization#_use-a-githubcopilot-instructions.md-file -->

# {PROJECT_NAME} Project Instructions

{PROJECT_DESCRIPTION}

## Repository Information

### GitHub Repository

- **Repository**: https://github.com/{USERNAME}/{REPOSITORY_NAME}
- **Owner**: {USERNAME}
- **Primary Branch**: {PRIMARY_BRANCH}
- **Remote**: origin (HTTPS)
- **Clone URL**: https://github.com/{USERNAME}/{REPOSITORY_NAME}.git

### Branch Structure

- **{PRIMARY_BRANCH}**: Main development branch with latest features
- **main**: Stable release branch
- **feature/***: Feature branches for new functionality

### Repository Setup Commands

```bash
# Clone repository
git clone https://github.com/{USERNAME}/{REPOSITORY_NAME}.git

# Add remote (if not already configured)
git remote add origin https://github.com/{USERNAME}/{REPOSITORY_NAME}.git

# Switch to primary branch
git checkout {PRIMARY_BRANCH}

# Pull latest changes
git pull origin {PRIMARY_BRANCH}

# Push changes
git push origin {PRIMARY_BRANCH}
```

### GitHub CLI Integration

```bash
# View repository information
gh repo view {USERNAME}/{REPOSITORY_NAME}

# Create feature branch and PR
gh pr create --title "{PROJECT_NAME}: Add new feature" --body "Feature description" --base {PRIMARY_BRANCH}

# View project-related issues
gh issue list --label "{PROJECT_NAME}"

# Create project specific issue
gh issue create --title "{PROJECT_NAME}: Issue title" --body "Issue description" --label "{PROJECT_NAME},bug"

# Check branch status
gh pr status --branch {PRIMARY_BRANCH}

# Merge PR
gh pr merge --merge --delete-branch

# Release management
gh release create v1.0.0 --title "{PROJECT_NAME} v1.0.0" --notes "Release notes"

# Repository analytics
gh repo view {USERNAME}/{REPOSITORY_NAME} --json languages
```

## Build Configuration ⚠️ CRITICAL

### Version Compatibility Requirements

- **{LANGUAGE} Version**: {LANGUAGE_VERSION}
- **Build Tool**: {BUILD_TOOL} {BUILD_VERSION}
- **Package Manager**: {PACKAGE_MANAGER} {PACKAGE_VERSION}
- **Target Environment**: {TARGET_ENVIRONMENT}

### Project Migration Status

- ✅ **Modern Build System**: Using latest build tools and practices
- ✅ **Configuration Cache**: Enabled for faster builds (if applicable)
- ✅ **Clean Project Structure**: Organized and maintainable codebase
- ✅ **Dependency Management**: Up-to-date dependencies

### Build Commands

```bash

# Clean build
{BUILD_CLEAN_COMMAND}

# Development build
{BUILD_DEV_COMMAND}

# Production build
{BUILD_PROD_COMMAND}

# Run tests
{TEST_COMMAND}

# Run with watch mode
{WATCH_COMMAND}

# Lint/format code
{LINT_COMMAND}
```

## Project Architecture

### Core Components

- **{MAIN_ENTRY_POINT}**: {MAIN_DESCRIPTION}
- **{CORE_MODULE_1}**: {MODULE_1_DESCRIPTION}
- **{CORE_MODULE_2}**: {MODULE_2_DESCRIPTION}
- **{CORE_MODULE_3}**: {MODULE_3_DESCRIPTION}

### Package/Directory Structure

```
{PROJECT_ROOT}/
├── {SOURCE_DIR}/
│   ├── {MAIN_MODULE}/              # {MAIN_MODULE_DESCRIPTION}
│   ├── {COMPONENT_DIR}/            # {COMPONENT_DESCRIPTION}
│   └── {UTILITY_DIR}/              # {UTILITY_DESCRIPTION}
├── {TEST_DIR}/                     # Test files
├── {CONFIG_DIR}/                   # Configuration files
└── {DOCS_DIR}/                     # Documentation
```

## Technology Stack

### Core Dependencies

- **{FRAMEWORK_1}** ({VERSION_1}): {FRAMEWORK_1_DESCRIPTION}
- **{FRAMEWORK_2}** ({VERSION_2}): {FRAMEWORK_2_DESCRIPTION}
- **{LIBRARY_1}** ({VERSION_3}): {LIBRARY_1_DESCRIPTION}
- **{LIBRARY_2}** ({VERSION_4}): {LIBRARY_2_DESCRIPTION}

### Development Dependencies

- **{DEV_TOOL_1}**: {DEV_TOOL_1_DESCRIPTION}
- **{DEV_TOOL_2}**: {DEV_TOOL_2_DESCRIPTION}
- **{DEV_TOOL_3}**: {DEV_TOOL_3_DESCRIPTION}

### Testing Framework

- **{TEST_FRAMEWORK}**: {TEST_FRAMEWORK_DESCRIPTION}
- **{MOCK_LIBRARY}**: {MOCK_DESCRIPTION}
- **{E2E_TOOL}**: {E2E_DESCRIPTION}

## Key Implementation Patterns

### {PATTERN_1_NAME}

{PATTERN_1_DESCRIPTION}

### {PATTERN_2_NAME}

```{LANGUAGE_NAME}
// {PATTERN_2_EXAMPLE}
{CODE_EXAMPLE}
```

### {PATTERN_3_NAME}

- **{FEATURE_1}**: {FEATURE_1_DESCRIPTION}
- **{FEATURE_2}**: {FEATURE_2_DESCRIPTION}
- **{FEATURE_3}**: {FEATURE_3_DESCRIPTION}

## Data Management

### {DATA_LAYER_NAME}

- **{DATA_FEATURE_1}**: {DATA_FEATURE_1_DESCRIPTION}
- **{DATA_FEATURE_2}**: {DATA_FEATURE_2_DESCRIPTION}
- **{DATA_FEATURE_3}**: {DATA_FEATURE_3_DESCRIPTION}

### {PERSISTENCE_PATTERN} Implementation

```{LANGUAGE_NAME}
// {PERSISTENCE_EXAMPLE}
class {REPOSITORY_CLASS} {
    {REPOSITORY_METHODS}
}
```

## UI/UX Design Principles

### {UI_FRAMEWORK} Components

- **{COMPONENT_1}**: {COMPONENT_1_DESCRIPTION}
- **{COMPONENT_2}**: {COMPONENT_2_DESCRIPTION}
- **{COMPONENT_3}**: {COMPONENT_3_DESCRIPTION}

### Design System Features

- {DESIGN_FEATURE_1}
- {DESIGN_FEATURE_2}
- {DESIGN_FEATURE_3}
- {DESIGN_FEATURE_4}

## Development Guidelines

### Error Handling Patterns

- {ERROR_PATTERN_1}
- {ERROR_PATTERN_2}
- {ERROR_PATTERN_3}
- {ERROR_PATTERN_4}

### {SECURITY_AREA} Management

- {SECURITY_PRACTICE_1}
- {SECURITY_PRACTICE_2}
- {SECURITY_PRACTICE_3}
- {SECURITY_PRACTICE_4}

### Testing Strategy

- **Unit Tests**: {UNIT_TEST_FOCUS}
- **Integration Tests**: {INTEGRATION_TEST_FOCUS}
- **E2E Tests**: {E2E_TEST_FOCUS}
- **Mock Usage**: {MOCK_STRATEGY}

### Code Quality Standards

- {CODE_STANDARD_1}
- {CODE_STANDARD_2}
- {CODE_STANDARD_3}
- {CODE_STANDARD_4}
- {CODE_STANDARD_5}

## Common Development Tasks

### {TASK_1_NAME}

1. {TASK_1_STEP_1}
2. {TASK_1_STEP_2}
3. {TASK_1_STEP_3}
4. {TASK_1_STEP_4}

### {TASK_2_NAME}

1. {TASK_2_STEP_1}
2. {TASK_2_STEP_2}
3. {TASK_2_STEP_3}
4. {TASK_2_STEP_4}

### {TASK_3_NAME}

1. {TASK_3_STEP_1}
2. {TASK_3_STEP_2}
3. {TASK_3_STEP_3}
4. {TASK_3_STEP_4}

## Testing Commands

### Local Testing

```bash
# Unit tests
{UNIT_TEST_COMMAND}

# Integration tests
{INTEGRATION_TEST_COMMAND}

# E2E tests
{E2E_TEST_COMMAND}

# Test coverage
{COVERAGE_COMMAND}

# Specific test patterns
{SPECIFIC_TEST_COMMAND}
```

### Test Coverage Areas

- ✅ {TEST_AREA_1}
- ✅ {TEST_AREA_2}
- ✅ {TEST_AREA_3}
- ✅ {TEST_AREA_4}
- ✅ {TEST_AREA_5}

## Troubleshooting Guide

### Build Issues

- **{BUILD_ISSUE_1}**: {BUILD_SOLUTION_1}
- **{BUILD_ISSUE_2}**: {BUILD_SOLUTION_2}
- **{BUILD_ISSUE_3}**: {BUILD_SOLUTION_3}

### Runtime Issues

- **{RUNTIME_ISSUE_1}**: {RUNTIME_SOLUTION_1}
- **{RUNTIME_ISSUE_2}**: {RUNTIME_SOLUTION_2}
- **{RUNTIME_ISSUE_3}**: {RUNTIME_SOLUTION_3}

### Testing Issues

- **{TEST_ISSUE_1}**: {TEST_SOLUTION_1}
- **{TEST_ISSUE_2}**: {TEST_SOLUTION_2}
- **{TEST_ISSUE_3}**: {TEST_SOLUTION_3}

## Code Maintenance Notes

### Performance Considerations

- {PERFORMANCE_NOTE_1}
- {PERFORMANCE_NOTE_2}
- {PERFORMANCE_NOTE_3}
- {PERFORMANCE_NOTE_4}

### Security & Privacy

- {SECURITY_NOTE_1}
- {SECURITY_NOTE_2}
- {SECURITY_NOTE_3}
- {SECURITY_NOTE_4}

### Accessibility

- {ACCESSIBILITY_NOTE_1}
- {ACCESSIBILITY_NOTE_2}
- {ACCESSIBILITY_NOTE_3}
- {ACCESSIBILITY_NOTE_4}

## Environment-Specific Configuration

### Development Environment

- {DEV_CONFIG_1}
- {DEV_CONFIG_2}
- {DEV_CONFIG_3}

### Production Environment

- {PROD_CONFIG_1}
- {PROD_CONFIG_2}
- {PROD_CONFIG_3}

### CI/CD Pipeline

- {CI_CONFIG_1}
- {CI_CONFIG_2}
- {CI_CONFIG_3}

---

## Template Usage Instructions

### How to Use This Template:

1. **Replace Placeholders**: Search and replace all `{PLACEHOLDER}` values with your project-specific information
2. **Remove Unused Sections**: Delete sections that don't apply to your project type
3. **Add Project-Specific Sections**: Include additional sections relevant to your technology stack
4. **Update Commands**: Ensure all build/test commands match your project's actual commands
5. **Customize Examples**: Replace code examples with patterns specific to your project

### Common Placeholder Categories:

- **Project Info**: `{PROJECT_NAME}`, `{PROJECT_DESCRIPTION}`, `{USERNAME}`, `{REPOSITORY_NAME}`
- **Technology**: `{LANGUAGE}`, `{FRAMEWORK}`, `{BUILD_TOOL}`, `{PACKAGE_MANAGER}`
- **Structure**: `{SOURCE_DIR}`, `{TEST_DIR}`, `{CONFIG_DIR}`, `{DOCS_DIR}`
- **Commands**: `{BUILD_COMMAND}`, `{TEST_COMMAND}`, `{LINT_COMMAND}`
- **Features**: `{FEATURE_NAME}`, `{COMPONENT_NAME}`, `{PATTERN_NAME}`

### Technology-Specific Templates:

This template can be adapted for:

- **Web Applications**: React, Vue, Angular, Next.js
- **Mobile Apps**: React Native, Flutter, iOS, Android
- **Backend Services**: Node.js, Python, Java, .NET
- **Desktop Applications**: Electron, Tauri, Native apps
- **Data Science**: Python, R, Jupyter notebooks
- **DevOps**: Infrastructure, CI/CD, Kubernetes

### Best Practices:

1. Keep instructions concise but comprehensive
2. Include actual commands that work in your environment
3. Update the template as your project evolves
4. Focus on patterns and practices specific to your team
5. Include troubleshooting for common issues you encounter
