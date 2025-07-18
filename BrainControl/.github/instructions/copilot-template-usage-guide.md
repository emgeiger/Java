# Copilot Instructions Template Usage Guide

This guide explains how to use the generic copilot instructions template for different types of projects.

## Quick Start

1. **Copy the template**: `copilot-instructions-template.md`
2. **Rename it**: `.github/copilot-instructions.md` in your project
3. **Replace placeholders**: Find and replace all `{PLACEHOLDER}` values
4. **Customize sections**: Add/remove sections based on your project type
5. **Add to .gitignore**: Include `.github/copilot-instructions.md` in your `.gitignore`

## Project Type Examples

### React/Next.js Web Application

```markdown
# MyApp Web Application Instructions

Modern React application with Next.js framework for server-side rendering and optimal performance.

## Repository Information
- **Repository**: https://github.com/username/myapp
- **Primary Branch**: main
- **Clone URL**: https://github.com/username/myapp.git

## Build Configuration
### Version Compatibility Requirements
- **Node.js Version**: 18.x or higher
- **Build Tool**: Next.js 14.x
- **Package Manager**: npm 10.x
- **Target Environment**: Web browsers (ES2020+)

### Build Commands
```bash
# Development build
npm run dev

# Production build
npm run build

# Run tests
npm test

# Lint code
npm run lint
```

## Technology Stack
### Core Dependencies
- **React** (18.x): UI framework
- **Next.js** (14.x): Full-stack React framework
- **TypeScript** (5.x): Type safety
- **Tailwind CSS** (3.x): Utility-first CSS framework
```

### Python Data Science Project

```markdown
# Data Analysis Pipeline Instructions

Python-based data analysis and machine learning pipeline for customer behavior analysis.

## Repository Information
- **Repository**: https://github.com/username/data-pipeline
- **Primary Branch**: main

## Build Configuration
### Version Compatibility Requirements
- **Python Version**: 3.9+
- **Build Tool**: pip/conda
- **Package Manager**: pip 23.x
- **Target Environment**: Jupyter notebooks, production servers

### Build Commands
```bash
# Install dependencies
pip install -r requirements.txt

# Run analysis
python src/main.py

# Run tests
pytest tests/

# Generate reports
python scripts/generate_report.py
```

## Technology Stack
### Core Dependencies
- **pandas** (2.x): Data manipulation
- **numpy** (1.24.x): Numerical computing
- **scikit-learn** (1.3.x): Machine learning
- **matplotlib** (3.7.x): Data visualization
```

### Node.js Backend API

```markdown
# API Server Instructions

RESTful API server built with Express.js and MongoDB for user management and data processing.

## Repository Information
- **Repository**: https://github.com/username/api-server
- **Primary Branch**: develop

## Build Configuration
### Version Compatibility Requirements
- **Node.js Version**: 20.x LTS
- **Build Tool**: npm/yarn
- **Package Manager**: npm 10.x
- **Target Environment**: Server (Node.js runtime)

### Build Commands
```bash
# Development server
npm run dev

# Production build
npm run build

# Run tests
npm test

# Database migrations
npm run migrate
```

## Technology Stack
### Core Dependencies
- **Express.js** (4.x): Web framework
- **MongoDB** (6.x): NoSQL database
- **Mongoose** (7.x): MongoDB ODM
- **JWT** (9.x): Authentication
```

## Customization Guidelines

### 1. Project-Specific Sections

Add sections relevant to your project type:

**Mobile Apps:**
```markdown
## Device Testing
### iOS Testing
- Xcode Simulator configurations
- Physical device testing setup
- App Store deployment process

### Android Testing
- Android emulator setup
- Google Play Console deployment
- Device compatibility testing
```

**DevOps Projects:**
```markdown
## Infrastructure Management
### AWS Resources
- EC2 instances configuration
- S3 bucket policies
- RDS database setup

### Kubernetes Deployment
- Helm charts configuration
- Service mesh setup
- Monitoring and logging
```

### 2. Technology-Specific Patterns

**For React Projects:**
```markdown
## React Patterns
### Component Structure
- Use functional components with hooks
- Implement proper prop types
- Follow component composition patterns

### State Management
- Use Context API for global state
- Implement Redux for complex applications
- Use SWR for data fetching
```

**For Python Projects:**
```markdown
## Python Patterns
### Code Organization
- Follow PEP 8 style guide
- Use virtual environments
- Implement proper logging

### Testing Patterns
- Use pytest for unit tests
- Implement fixture patterns
- Mock external dependencies
```

### 3. Environment-Specific Configuration

**Development Environment:**
```markdown
### Development Setup
- Local database configuration
- Environment variables setup
- Debug mode settings
- Hot reload configuration
```

**Production Environment:**
```markdown
### Production Deployment
- Environment variable management
- SSL certificate setup
- CDN configuration
- Monitoring and alerting
```

## Template Maintenance

### Regular Updates
1. **Keep dependencies current**: Update version numbers as you upgrade
2. **Add new patterns**: Document new coding patterns you adopt
3. **Update troubleshooting**: Add solutions for common issues
4. **Refine commands**: Ensure all commands work in your environment

### Team Collaboration
1. **Share patterns**: Document team-agreed coding standards
2. **Common troubleshooting**: Include solutions for team-wide issues
3. **Onboarding**: Use instructions to help new team members
4. **Review process**: Regular review and updates of instructions

## Best Practices

### 1. Keep It Current
- Update instructions when you change build tools
- Document new patterns as you adopt them
- Remove outdated information regularly

### 2. Be Specific
- Use actual commands that work in your environment
- Include version numbers for critical dependencies
- Provide context for why certain patterns are used

### 3. Focus on Common Tasks
- Document frequently performed operations
- Include troubleshooting for common issues
- Provide shortcuts for routine tasks

### 4. Make It Scannable
- Use clear headings and bullet points
- Include code examples with syntax highlighting
- Use emojis or icons for visual organization

## Advanced Customization

### Dynamic Content
For projects with multiple environments or configurations:

```markdown
## Environment-Specific Instructions

### Development (Local)
{DEV_SPECIFIC_INSTRUCTIONS}

### Staging Environment
{STAGING_SPECIFIC_INSTRUCTIONS}

### Production Environment
{PROD_SPECIFIC_INSTRUCTIONS}
```

### Multi-Language Projects
For projects using multiple programming languages:

```markdown
## Backend (Python)
{PYTHON_SPECIFIC_INSTRUCTIONS}

## Frontend (TypeScript)
{TYPESCRIPT_SPECIFIC_INSTRUCTIONS}

## Mobile (React Native)
{REACT_NATIVE_SPECIFIC_INSTRUCTIONS}
```

### Integration with AI Tools
Optimize for AI assistants:

```markdown
## AI Assistant Context
- Primary language: {LANGUAGE}
- Framework: {FRAMEWORK}
- Architecture pattern: {PATTERN}
- Key dependencies: {DEPENDENCIES}
- Common tasks: {TASKS}
```

This template system helps maintain consistency across your projects while providing Copilot with the context it needs to provide better assistance.
