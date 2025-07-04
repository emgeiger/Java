# BrainPlotter Java Application

This is the Java version of the BrainPlotter application, converted from the C# Windows Forms implementation. It provides a graphical interface to visualize and process data from an EEG device via Bluetooth.

## Requirements

- Java JDK 8 or higher
- Apache Ant (for building the project)
- JFreeChart library (included in project dependencies)
- BlueCove Bluetooth library (included in project dependencies)
- Java Communications API (javax.comm) - needed for serial port access

## Project Structure

- `src/` - Contains the Java source code
  - `BrainPlotter/` - Main package
    - `MainForm.java` - Main UI class (equivalent to Form1.cs)
    - `BrainPlotterApp.java` - Application entry point

## How to Build and Run

### Using Ant

1. Open a terminal in the BrainPlotter directory
2. Run `ant compile` to compile the code
3. Run `ant run` to run the application
4. (Optional) Run `ant dist` to create a distributable JAR file

### Manually

1. Compile the code:

```
javac -cp ../lib/bluecove-2.1.0.jar:../lib/jfreechart-1.0.19-demo.jar:.. src/BrainPlotter/*.java -d build
```

2. Run the application:

```
java -cp build:../lib/bluecove-2.1.0.jar:../lib/jfreechart-1.0.19-demo.jar:.. BrainPlotter.BrainPlotterApp
```

## Features

- Real-time visualization of EEG data using JFreeChart
- Data logging to CSV file
- Focus detection with configurable thresholds
- Interaction with Arduino via serial port
- Optional mouse click simulation on focus detection

## Configuration

The application uses COM10 for the EEG device and COM4 for Arduino communication by default. You can change these in the `startBT()` and `startArd()` methods.
