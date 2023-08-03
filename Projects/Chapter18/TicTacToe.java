import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToe extends JFrame implements ActionListener
{
	private Container c = getContentPane();
	private JFrame frame;

	private int xWinStreak = 0;
	private int oWinStreak = 0;

	private JLabel next;
	private JLabel went;
	private JLabel instructions;
//	private JLabel winning;
	private JLabel xWins;
	private JLabel oWins;

	private ArrayList<JButton> arrayButton;
	private ArrayList<ArrayList<JButton>> arrayButtons;
	private JButton[][] buttonsArray;
//	private JButton[] buttonArray;
	private JButton[] buttons;
	private JButton button;
//	private JComboBox difficulty;
//	private JButton buttons;

	private boolean turnBoolean;
	private boolean xTurn;
	private boolean oTurn;

	private String turn = "";

	public TicTacToe()
	{
		setSize(600, 600);
		setTitle("TIC-TAC-TOE");
//		setLayout(new GridLayout(3, 3));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		createContents();
		setVisible(true);

		frame.setSize(400, 400);
		frame.setTitle("Buttons Array");
		frame.setLayout(new GridLayout(3, 3));
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
//		frame.setVisible(true);
	}

	private void createContents()
	{
		frame = new JFrame();

		JPanel topPanel = new JPanel();
		JPanel labelPanel = new JPanel(new GridLayout(1, 2));
		JPanel instruction = new JPanel();
		JPanel buttonPanel = new JPanel(new GridLayout(3, 3));
		JPanel winningPanel = new JPanel(new GridLayout(1, 2));

		instructions = new JLabel("TIC-TAC-TOE\nTo start, click one of the squares.");

		turn = turnBoolean ? "X" : "O";
		next = new JLabel("Next: " + turn);
		went = new JLabel("Just went: ");
//		winning = new JLabel();
		xWins = new JLabel();
		oWins = new JLabel();

		buttons = new JButton[9];
//		buttonArray = new JButton[9];
	//	buttonsArray = new JButton[3][3];
//		arrayButtons = new ArrayList<ArrayList<JButton>>();

//***************************************************************
//		next.setAlignmentX(Component.CENTER_ALIGNMENT);
//		next.setAlignmenty(Component.CENTER_ALIGNMENT);
//		went.setAlignmentX(Component.CENTER_ALIGNMENT);
//		went.setAlignmenty(Component.CENTER_ALIGNMENT);

//		next.setAlignmentX(SwingConstants.CENTER);
//		next.setAlignmenty(SwingConstants.CENTER);
//		went.setAlignmentX(SwingConstants.CENTER);
//		went.setAlignmenty(SwingConstants.CENTER);

//		next.setHorizontalAlignment(Component.CENTER_ALIGNMENT);
//		next.setVerticalAlignment(Component.CENTER_ALIGNMENT);
//		went.setHorizontalAlignment(Component.CENTER_ALIGNMENT);
//		went.setVerticalAlignment(Component.CENTER_ALIGNMENT);
//***************************************************************

		next.setHorizontalAlignment(SwingConstants.CENTER);
//		next.setVerticalAlignment(SwingConstants.CENTER);
		went.setHorizontalAlignment(SwingConstants.CENTER);
//		went.setVerticalAlignment(SwingConstants.CENTER);


/*
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
//				buttons = new JButton();
				button = new JButton();
//				buttonsArray = new JButton[i][j];
				buttonsArray[i][j] = button;
	//			arrayButton = new ArrayList<JButton>();
				buttonsArray[i][j].addActionListener(this);

//				frame.add(buttonsArray[i][j]);
			}
		}
*/
/*
		for(int i = 0; i < buttonsArray.length; i++)
		{
			for(int j = 0; j < buttonsArray[i].length; j++)
			{
				buttonsArray[i][j].addActionListener(this);
			}
		}
*/
		for(int i = 0; i < buttons.length; i++)
		{
			button = new JButton();
			buttons[i] = button;
			buttons[i].addActionListener(this);
			buttonPanel.add(buttons[i]);
		}

		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

		labelPanel.add(next);
		labelPanel.add(went);

		instruction.add(instructions);

//		labelPanel.setBorder(new EmptyBorder(60, 60, 60, 60));

		topPanel.add(instruction);
		topPanel.add(labelPanel);

		add(topPanel, BorderLayout.NORTH);

		buttonPanel.setBorder(new EmptyBorder(60, 60, 60, 60));

		add(buttonPanel, BorderLayout.CENTER);

		xWins.setText("X wins: " + Integer.toString(xWinStreak));
		oWins.setText("O wins: " + Integer.toString(oWinStreak));

//***************************************************************
//		xWins.setAlignmentX(Component.CENTER_ALIGNMENT);
//		xWins.setAlignmenty(Component.CENTER_ALIGNMENT);
//		oWins.setAlignmentX(Component.CENTER_ALIGNMENT);
//		oWins.setAlignmenty(Component.CENTER_ALIGNMENT);

//		xWins.setAlignmentX(SwingConstants.CENTER);
//		xWins.setAlignmenty(SwingConstants.CENTER);
//		oWins.setAlignmentX(SwingConstants.CENTER);
//		oWins.setAlignmenty(SwingConstants.CENTER);

//		xWins.setHorizontalAlignment(Component.CENTER_ALIGNMENT);
//		xWins.setVerticalAlignment(Component.CENTER_ALIGNMENT);
//		oWins.setHorizontalAlignment(Component.CENTER_ALIGNMENT);
//		oWins.setVerticalAlignment(Component.CENTER_ALIGNMENT);
//***************************************************************

		xWins.setHorizontalAlignment(SwingConstants.CENTER);
//		xWins.setVerticalAlignment(SwingConstants.CENTER);
		oWins.setHorizontalAlignment(SwingConstants.CENTER);
//		oWins.setVerticalAlignment(SwingConstants.CENTER);


		winningPanel.add(xWins);
		winningPanel.add(oWins);

		add(winningPanel, BorderLayout.SOUTH);
	}

	private boolean win()
	{
//		String[] strings = new String[9];
//		int xTurn[] = new int[5];
//		int oTurn[] = new int[5];

//		int xTurn[][] = new int[3][3];
//		int oTurn[][] = new int[3][3];

		boolean win = false;
/*
		for(int i = 0; i < buttons.length; i++)
		{
			strings[i] = button.getText();
		}
*/

	// Horizontal
		if(buttons[0].getText().equals(buttons[1].getText()) && buttons[1].getText().equals(buttons[2].getText()) &&
			!buttons[0].getText().equals("") && !buttons[1].getText().equals("") && !buttons[2].getText().equals("")) // &&
			// buttons[0].getText() == "" && buttons[1].getText() == "" && buttons[2].getText() == "")
		{
			win = true;
		}
		else if(buttons[3].getText().equals(buttons[4].getText()) && buttons[4].getText().equals(buttons[5].getText()) &&
			!buttons[3].getText().equals("") && !buttons[4].getText().equals("") && !buttons[5].getText().equals("")) // &&
			// buttons[3].getText() == "" && buttons[4].getText() == "" && buttons[5].getText() == "")
		{
			win = true;
		}
		else if(buttons[6].getText().equals(buttons[7].getText()) && buttons[7].getText().equals(buttons[8].getText()) &&
			!buttons[6].getText().equals("") && !buttons[7].getText().equals("") && !buttons[8].getText().equals("")) // &&
			// buttons[6].getText() == "" && buttons[7].getText() == "" && buttons[8].getText() == "")
		{
			win = true;
		}

	// Vertical
		if(buttons[0].getText().equals(buttons[3].getText()) && buttons[3].getText().equals(buttons[6].getText()) && // buttons[3].getText() == buttons[6].getText() &&
			!buttons[0].getText().equals("") && !buttons[3].getText().equals("") && !buttons[6].getText().equals("")) // &&
			// buttons[0].getText() == "" && buttons[3].getText() == "" && buttons[6].getText() == "")
			// buttons[0].getText() != "" && buttons[3].getText() != "" && buttons[6].getText() != "")
		{
			win = true;
		}
		else if(buttons[1].getText().equals(buttons[4].getText()) && buttons[4].getText().equals(buttons[7].getText()) &&
			!buttons[1].getText().equals("") && !buttons[4].getText().equals("") && !buttons[7].getText().equals("")) // &&
			// buttons[1].getText() == "" && buttons[4].getText() == "" && buttons[7].getText() == "")
			// buttons[1].getText() != "" && buttons[4].getText() != "" && buttons[7].getText() != "")
		{
			win = true;
		}
		else if(buttons[2].getText().equals(buttons[5].getText()) && buttons[5].getText().equals(buttons[8].getText()) &&
			!buttons[2].getText().equals("") && !buttons[5].getText().equals("") && !buttons[8].getText().equals("") &&
			// buttons[2].getText() == "" && buttons[5].getText() == "" && buttons[8].getText() == "")
			buttons[2].getText() != "" && buttons[5].getText() != "" && buttons[8].getText() != "")
		{
			win = true;
		}

	// Diagonal
		if(buttons[0].getText() == buttons[4].getText() && buttons[4].getText() == buttons[8].getText() &&
			!buttons[0].getText().equals("") && !buttons[4].getText().equals("") && !buttons[8].getText().equals("")) // &&
			// buttons[0].getText() == "" && buttons[4].getText() == "" && buttons[8].getText() == "")
		{
			win = true;
		}
		else if(buttons[2].getText() == buttons[4].getText() && buttons[4].getText() == buttons[6].getText() &&
			!buttons[2].getText().equals("") && !buttons[4].getText().equals("") && !buttons[6].getText().equals("")) // &&
			// buttons[2].getText() == "" && buttons[4].getText() == "" && buttons[6].getText() == "")
		{
			win = true;
		}		

//		if(!turn) { }

		return win;
	}
/*
	public boolean isDraw() {
		for (int i = 0; i < 9; i++) {
			if (buttons[i].getText().equals("")) {
				return false; // There is an empty cell, game is not a draw
			}
		}
		// All cells are filled, check for a winner
		return !isWinner("X") && !isWinner("O");
	}	
*/
	public void actionPerformed(ActionEvent ae)
	{
		for(int i = 0; i < buttons.length; i++)
		{
			if(ae.getSource() == buttons[i])
			{
				if(buttons[i].getText().isEmpty())
				{
					turn = turnBoolean ? "O" : "X";
					next.setText("Next: " + turn);
					turn = turnBoolean ? "X" : "O";
					went.setText("Went: " + turn);
					buttons[i].setText(turnBoolean ? "X" : "O");

					if(win())
					{
							JOptionPane.showMessageDialog(c, turn + " wins!");
							if(turn.equals("X"))
							{
								xWinStreak++;
								xWins.setText("X wins: " + Integer.toString(xWinStreak));
//								winning.setText(Integer.toString(xWinStreak));
							}
							else if(turn.equals("O"))
							{
								oWinStreak++;
								oWins.setText("O wins: " + Integer.toString(oWinStreak));
//								winning.setText(Integer.toString(oWinStreak));
							}
							break;
					}
/*
					else if(isDraw())
					{
						JOptionPane.showMessageDialog(c, "Cat's game!"); // "Draw!");
						break;
					}
*/					
					else // if(!win())
					{
						turnBoolean = !turnBoolean;
					}
				}
			}
		}
/*
		for(int i = 0; i < buttonsArray.length; i++)
		{
			for(int j = 0; j < buttonsArray[i].length; j++)
			{
				if(ae.getSource() == buttonsArray[i][j])
				{
					if(buttonsArray[i][j].getText().isEmpty())
					{
						turn = turnBoolean ? "O" : "X";
						next.setText("Next: " + turn);
						turn = turnBoolean ? "X" : "O";
						went.setText("Went: " + turn);
						buttonsArray[i][j].setText(turnBoolean ? "X" : "O");

						if(win())
						{
							JOptionPane.showMessageDialog(c, turn + " wins!");
							if(turn.equals("X"))
							{
								xWinStreak++;
								xWins.setText("X wins: " + Integer.toString(xWinStreak));
//								winning.setText(Integer.toString(xWinStreak));
							}
							else if(turn.equals("O"))
							{
								oWinStreak++;
								oWins.setText("O wins: " + Integer.toString(oWinStreak));
//								winning.setText(Integer.toString(oWinStreak));
							}
							break;
						}
						else // if(!win())
						{
							turnBoolean = !turnBoolean;
						}
					}
				}
			}
		}
*/
	}

	public static void main(String[] args)
	{
		new TicTacToe();
	}
}
