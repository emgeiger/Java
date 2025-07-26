# Receipt Scanner Android App - Copilot Instructions

<!-- Use this file to provide workspace-specific custom instructions to Copilot. For more details, visit https://code.visualstudio.com/docs/copilot/copilot-customization#_use-a-githubcopilotinstructionsmd-file -->

## Project Overview
This is a Receipt Scanner Android application built with Kotlin that uses:
- **Camera/CameraX** for capturing receipt images
- **ML Kit Text Recognition** for OCR processing
- **Room Database** for local data storage
- **MVVM Architecture** with LiveData and ViewModels
- **Material Design 3** components
- **Gradle** build system with corporate network support

## Development Guidelines

### Code Style
- Use **Kotlin** for all new Android code
- Follow **Material Design 3** guidelines
- Implement **MVVM architecture pattern**
- Use **ViewBinding** for UI interactions
- Apply **coroutines** for asynchronous operations

### Database
- Use **Room** for local data persistence
- Follow **Repository pattern** for data access
- Implement **DAO** interfaces for database operations
- Use **LiveData** for reactive data observation

### Camera & OCR
- Use **CameraX** libraries for camera functionality
- Integrate **ML Kit Text Recognition** for OCR
- Handle **permissions** properly (Camera, Storage)
- Implement **error handling** for camera and OCR operations

### UI/UX
- Follow **Material Design 3** principles
- Use **ConstraintLayout** for complex layouts
- Implement **responsive design** for different screen sizes
- Add **proper accessibility** support

### Testing
- Write **unit tests** for ViewModels and Repository classes
- Create **instrumentation tests** for database operations
- Test **camera functionality** with mock data when possible

### Corporate Environment
- The project includes **corporate network support** via gradle.properties
- Use provided **setup scripts** for SSL/proxy configuration
- Reference the **CORPORATE-NETWORK-GUIDE.md** for troubleshooting

### Key Features to Implement
1. **Receipt Scanning**: Camera capture with real-time preview
2. **OCR Processing**: Extract merchant name, amount, date from receipts
3. **Data Management**: Store, edit, and organize receipt data
4. **Categories**: Organize receipts by expense categories
5. **Analytics**: Generate expense reports and summaries
6. **Export/Share**: Share receipt data and images

When generating code, prioritize:
- **Error handling** and user feedback
- **Performance optimization** for camera and database operations
- **Security** for sensitive financial data
- **Accessibility** and inclusive design
- **Corporate compliance** and data privacy
