# GitHub Issue: TicTacToe Button Text Too Small on Larger Screens

## Issue Details

**Title:** TicTacToe: Button text too small on larger screens

**Labels:** enhancement, ui/ux, accessibility

**Priority:** Medium

---

## Problem Description

The TicTacToe game buttons use default font sizing, making the X and O text very small and hard to read on larger screens or high-DPI displays.

## Current Behavior
- Buttons are created with default JButton font settings in `createContents()` method
- X and O text appears very small on bigger screens (lines 121-127 in TicTacToe.java)
- Text readability decreases proportionally with screen size
- No font scaling or responsive design considerations

## Expected Behavior
- Button text should scale appropriately with screen size
- X and O should be clearly visible and readable regardless of display size
- Font size should be responsive to display scaling preferences
- Improved accessibility for users with visual impairments

## Code Location
**File:** `Projects/Chapter18/TicTacToe.java`  
**Lines:** Around 121-127 (button creation loop)

```java
// Current problematic code:
for(int i = 0; i < buttons.length; i++)
{
    button = new JButton();
    buttons[i] = button;
    buttons[i].addActionListener(this);
    buttonPanel.add(buttons[i]);
}
```

## Suggested Solutions

### Option 1: Fixed Large Font Size
```java
for(int i = 0; i < buttons.length; i++) {
    button = new JButton();
    buttons[i] = button;
    
    // Add large, bold font for better visibility
    Font buttonFont = new Font(Font.SANS_SERIF, Font.BOLD, 48);
    buttons[i].setFont(buttonFont);
    
    buttons[i].addActionListener(this);
    buttonPanel.add(buttons[i]);
}
```

### Option 2: Dynamic Font Sizing (Preferred)
```java
// Calculate font size based on screen dimensions
Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
int fontSize = Math.max(24, (int)(screenSize.width / 40)); // Responsive scaling

Font buttonFont = new Font(Font.SANS_SERIF, Font.BOLD, fontSize);

for(int i = 0; i < buttons.length; i++) {
    button = new JButton();
    buttons[i] = button;
    buttons[i].setFont(buttonFont);
    buttons[i].addActionListener(this);
    buttonPanel.add(buttons[i]);
}
```

### Option 3: User Preferences
```java
// Allow user to configure font size
private static final int DEFAULT_FONT_SIZE = 36;
private int userFontSize = DEFAULT_FONT_SIZE;

// Add menu option for font size selection
// Apply user preference to buttons
```

## Alternative Considerations
1. **Calculate font size based on button dimensions** - Scale font relative to actual button size
2. **Use system font scaling preferences** - Respect OS-level display scaling
3. **Implement user-configurable font size settings** - Add preferences dialog
4. **Consider minimum/maximum font size limits** - Ensure readability across all displays

## Impact Assessment
- **User Experience:** Significantly improves game usability on modern displays
- **Accessibility:** Better support for users with visual impairments
- **Compatibility:** Should work across different screen sizes and resolutions
- **Performance:** Minimal impact, one-time font calculation

## Testing Checklist
- [ ] Test on small screens (1366x768)
- [ ] Test on medium screens (1920x1080)
- [ ] Test on large screens (2560x1440, 4K)
- [ ] Test on high-DPI displays
- [ ] Verify text remains readable at all sizes
- [ ] Check layout doesn't break with larger fonts

## Related Issues
- Consider similar font sizing issues in other UI components
- Evaluate overall application scaling and responsive design

---

**Created:** July 8, 2025  
**Reporter:** Development Team  
**Assignee:** TBD  

## Instructions for Manual Submission
1. Copy this content to your GitHub repository's Issues section
2. Create a new issue with the title above
3. Paste the content (starting from "Problem Description")
4. Add the suggested labels: `enhancement`, `ui/ux`, `accessibility`
5. Assign appropriate priority and milestone if needed
