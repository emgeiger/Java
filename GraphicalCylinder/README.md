# Graphical Cylinder - 3D Interactive Visualization

A Java Swing application that provides an interactive 3D visualization of a cylinder with rotation capabilities through mouse dragging and slider controls.

## Features Completed ✅

### Core Functionality

- **3D Cylinder Rendering**: Complete mathematical implementation for 3D cylinder visualization
- **Interactive Rotation**: Mouse drag capability for intuitive rotation control
- **Slider Controls**: Elevation and azimuth angle adjustments via GUI sliders
- **Real-time Updates**: Smooth animation and immediate visual feedback

### Enhanced Version Features

- **Color Customization**: RGB sliders for both dark and light cylinder colors
- **Size Controls**: Height and diameter adjustment sliders
- **Status Display**: Real-time display of current angle and size values
- **Reset Functionality**: One-click reset to default view
- **Improved UI**: Better organized control panels with grouped sections

### Technical Implementation

- **Mouse Event Handling**: Proper mouse press/drag detection for rotation
- **Angle Clamping**: Safe angle limits to prevent visual artifacts
- **Thread-Safe GUI**: Proper Swing EDT usage
- **Look & Feel**: System native appearance

## Files Structure

```
src/
├── Cylinder.java                    - Core 3D cylinder rendering class
├── GraphicalCylinder.java           - Basic interactive GUI application  
├── EnhancedGraphicalCylinder.java   - Advanced version with more controls
├── CylinderTest.java                - JUnit tests (requires JUnit dependency)
└── GraphicalCylinderTest.java       - GUI tests (requires JUnit dependency)
```

## How to Run

### Compile and Run

```bash
# Compile the enhanced version
javac EnhancedGraphicalCylinder.java

# Run the application
java EnhancedGraphicalCylinder
```

### Or run the basic version

```bash
# Compile basic version
javac GraphicalCylinder.java

# Run basic version
java GraphicalCylinder
```

## Controls

### Mouse Controls

- **Click and Drag**: Rotate the cylinder in real-time
  - Horizontal drag: Changes azimuth (left-right rotation)
  - Vertical drag: Changes elevation (up-down rotation)

### Slider Controls (Enhanced Version)

- **Elevation**: -90° to +90° (vertical tilt)
- **Azimuth**: -90° to +90° (horizontal rotation)  
- **Height**: 200-600 pixels
- **Diameter**: 100-400 pixels
- **RGB Colors**: 0-255 for both dark and light gradient colors

### Buttons

- **Reset View**: Returns all settings to default values

## Implementation Status

### ✅ Completed Features

1. **3D Mathematics**: Complete transformation matrices for 3D rendering
2. **Mouse Interaction**: Smooth drag-to-rotate functionality
3. **GUI Integration**: Proper Swing component layout and event handling
4. **Color System**: Gradient rendering with customizable colors
5. **Error Handling**: Proper angle clamping and boundary checks
6. **Enhanced UI**: Professional control panel layout
7. **Thread Safety**: Proper EDT usage for GUI updates

### ✅ Fixed Issues

1. **UIManager Error**: Fixed `getSystemLookAndFeelClassName()` method call
2. **Compilation Errors**: All syntax and method call issues resolved
3. **Layout Issues**: Proper BorderLayout and component organization
4. **Event Handling**: Correct listener implementation for sliders and mouse

### 📋 Optional Improvements (Future)

1. **JUnit Testing**: Requires adding JUnit 5 dependency to classpath
2. **Keyboard Controls**: Arrow key navigation for precise control
3. **Animation**: Smooth transitions between angle changes
4. **Export Features**: Save current view as image file
5. **Preset Views**: Quick buttons for common viewing angles
6. **Zoom Functionality**: Mouse wheel or slider-based zoom

## Dependencies

### Required

- Java 8 or higher
- Swing GUI toolkit (included in Java)

### Optional (for testing)

- JUnit 5 (for running unit tests)

## Technical Notes

The application successfully implements:

- **3D projection mathematics** for realistic cylinder rendering
- **Event-driven architecture** for responsive user interaction  
- **Component-based design** for maintainable code structure
- **Cross-platform compatibility** through Java Swing

The cylinder visualization uses proper 3D transformation matrices to project the 3D cylinder onto a 2D screen, with realistic lighting effects and gradient shading for depth perception.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
