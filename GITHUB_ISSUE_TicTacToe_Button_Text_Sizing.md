# GitHub Issue: TicTacToe Button Text Sizing

## Issue Title
**TicTacToe Button Text Size Too Small - Poor Readability for X and O Characters**

## Problem Description
The TicTacToe game buttons display "X" and "O" characters with the default font size, which results in poor readability and user experience. The game board buttons need larger, more visible text to clearly distinguish between X and O moves.

## Current Behavior
- Buttons use default font size for displaying X and O characters
- Text appears small and hard to read on the game board
- No font styling or size configuration for game buttons
- Inconsistent text presentation across different screen resolutions

## Expected Behavior
- Buttons should display X and O characters with larger, easily readable font
- Text should be bold and centered for clear visibility
- Font size should scale appropriately with button size
- Consistent font styling across all game board buttons

## Technical Analysis
**File**: `Projects/Chapter18/TicTacToe.java`
**Lines of Interest**:
- Line 304: `buttons[i].setText(turnBoolean ? "X" : "O");`
- Line 353: `buttonsArray[i][j].setText(turnBoolean ? "X" : "O");`

**Current Code**:
```java
// Button creation (around line 119)
button = new JButton();
buttons[i] = button;
buttons[i].addActionListener(this);

// Text setting in actionPerformed method
buttons[i].setText(turnBoolean ? "X" : "O");
```

## Proposed Solution
Add font configuration to buttons during creation:
```java
// Enhanced button creation with proper font sizing
button = new JButton();
Font buttonFont = new Font("Arial", Font.BOLD, 36);
button.setFont(buttonFont);
buttons[i] = button;
```

## Acceptance Criteria
- [ ] X and O characters are clearly visible and readable
- [ ] Font size is appropriate for button dimensions (suggested: 36pt, bold)
- [ ] Text is properly centered in buttons
- [ ] Font styling is consistent across all game board buttons
- [ ] Game remains fully functional after font changes

## Priority
**Medium** - Affects user experience but doesn't break functionality

## Labels
- `enhancement`
- `ui/ux`
- `java-swing`
- `tic-tac-toe`

## Additional Context
This enhancement improves the visual appeal and usability of the TicTacToe game by making the X and O characters more prominent and easier to read during gameplay.

## Files to Modify
- `Projects/Chapter18/TicTacToe.java`

## Estimated Effort
**Small** - Simple font configuration addition (~15 minutes)
