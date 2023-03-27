import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Buttons extends JFrame implements ActionListener
{
    private JButton Left;
    private JButton blue;
    private JButton reset;
    private Color origColor;
    private JPanel panel;
    
    public Buttons()
    {
        Left = new JButton("Left");
        blue = new JButton("Blue");
        reset = new JButton("Reset");

        panel = new JPanel();

        // origColor = getContentPane().getBackground();
        origColor = panel.getBackground();

        createContents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setVisible(true);
    }

    private void createContents()
    {
        panel.add(Left);
        panel.add(blue);
        panel.add(reset);

        Left.addActionListener(this);
        blue.addActionListener(this);
        reset.addActionListener(this);

        // panel.getLayout();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));

        add(panel);
    }

    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getSource() == Left)
        {
            panel.setLayout(new FlowLayout(FlowLayout.LEADING));
        }
        else if (ae.getSource() == blue)
        {
            panel.setBackground(Color.blue);
            panel.setBackground(Color.BLUE);
        }
        else if (ae.getSource() == reset)
        {
            panel.setBackground(origColor);
            panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        }
    }

    public static void main(String[] args)
    {
        new Buttons();
    }
}
