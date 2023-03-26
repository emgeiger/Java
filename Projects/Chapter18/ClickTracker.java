import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class ClickTracker extends JFrame implements ActionListener
{
	private ArrayList<JButton> buttonArray;
	private JButton[] buttons;
	private BevelBorder bevelBorder;

	public ClickTracker()
	{
		setSize(300, 200);
		setTitle("Click Tracker");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		createContents();
		setVisible(true);
	}

	private void createContents()
	{
		buttons = new JButton[9];
		JButton button;

		JPanel panels = new JPanel(new FlowLayout(FlowLayout.CENTER));

//		char c;
		JLabel labels = new JLabel();

//		JPanel mainPanel = new JPanel(); //new GridLayout(2, 1));
		JPanel labelPanel = new JPanel(new GridLayout(1, 3));
		JPanel bottomPanel = new JPanel(new GridLayout(3, 3, 25, 25));

 		bevelBorder = new BevelBorder(BevelBorder.RAISED);

		for(char c = 'A'; c < 'D' || c <= 'C'; c++)//int i = 65; i < 68; i++)
		{
			labels = new JLabel(Character.toString(c)); //, SwingConstants.CENTER);
//			labels.setAlignmentX(Component.CENTER_ALIGNMENT);
//			labels.setAlignmentX(SwingConstants.CENTER);
			labels.setHorizontalAlignment(SwingConstants.CENTER);
			labelPanel.add(labels);
//			panels.add(labels);
//			labelPanel.add(panels);
//			panels.add(labels);
		}

		for(int i = 0; i < 9; i++)
		{
			buttonArray = new ArrayList<JButton>();
			button = new JButton(Integer.toString(i+1));
			buttons[i] = button;
//			buttons[i].setText(Integer.toString(i));
			buttons[i].setBorder(bevelBorder);
			buttons[i].addActionListener(this);

			bottomPanel.add(buttons[i]);
		}

//		mainPanel.add(labelPanel, BorderLayout.NORTH);
//		mainPanel.add(bottomPanel, BorderLayout.CENTER);

//		mainPanel.add(labelPanel);
//		mainPanel.add(bottomPanel);

//		add(mainPanel);

		add(labelPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent ae)
	{
		bevelBorder = new BevelBorder(BevelBorder.LOWERED);

		for(int i = 0; i < buttons.length; i++)
		{
			if(ae.getSource() == buttons[i])
			{
				buttons[i].setBorder(bevelBorder);
				buttons[i].setText("Clicked!");
			}
		}

		if(ae.getActionCommand().equals("Clicked!"))
		{
			JOptionPane.showMessageDialog(this, "You've alerady clicked that!");
		}
	}

	public static void main(String[] args)
	{
		new ClickTracker();
	}
}