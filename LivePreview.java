import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class LivePreview extends JFrame implements ActionListener, KeyListener
{
    public LivePreview()
    {
        createContents();
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void createContents()
    {
        JSplitPane jSplitPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JTextArea jTextArea = new JTextArea(20, 40);
        JScrollPane jScrollPanel = new JScrollPane(jTextArea);
        JTextPane jTextPanel = new JTextPane();

//        add();
//        jScrollPanel.add(jTextArea);
        jSplitPanel.add(new JScrollPane(jTextArea));
//        jSplitPanel.add(jScrollPanel);
        add(jSplitPanel);
    }

    public void actionPerformed(ActionEvent ae)
    {

    }

    public void keyReleased(KeyEvent ke)
    {

    }

    public void keyPressed(KeyEvent ke)
    {

    }

    public void keyTyped(KeyEvent ke)
    {

    }

    public static void main(String[] args)
    {
        new LivePreview();
    }
}