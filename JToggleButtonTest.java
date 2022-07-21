import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JToggleButton;

public class JToggleButtonTest extends JFrame implements ActionListener
{
    public JToggleButtonTest()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createContents();
        pack();
        setVisible(true);
    }

    private void createContents()
    {
        JToggleButton jToggleButton = new JToggleButton("Test");
        add(jToggleButton);
    }

    public void actionPerformed(ActionEvent ae)
    {

    }

    public static void main(String[] args)
    {
        new JToggleButtonTest();
    }
}