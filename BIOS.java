//import jacob.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

// import javafx.scene.control.TextArea;
// import javafx.scene.text.Text;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BIOS extends JFrame implements ActionListener
{
	private JCheckBox autoReboot = new JCheckBox("Auto Reboot");
    private JCheckBox drivers = new JCheckBox("Drivers");
    private JCheckBox bios = new JCheckBox("BIOS");
    private JButton start = new JButton("Start");
    private JTextArea jTextArea = new JTextArea();

    public BIOS()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createContents();
        pack();
        setSize(400, 200);
        setVisible(true);
    }

    public void createContents()
    {
//        JButton run = new JButton()
//        JFileChooser jFileChooser = new JFileChooser("\\\\okcpwonsite01\\drivers$\\Script Files\\Drivers");
//        File file = new File("\\\\okcpwonsite01\\drivers$\\Script Files\\Drivers");
//        JFileChooser fileChooser = new JFileChooser(file);
        JPanel jPanel = new JPanel();
        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JPanel checkBoxes = new JPanel();
        JPanel textArea = new JPanel();
try
{
        URL url = new URL("https://www.dell.com/support/home/us/en/04/product-support/servicetag/" ); //+  + "/drivers");
        URLConnection urlConnection = url.openConnection();
//        urlConnection = url.openStream();
        InputStream inputStream = urlConnection.getInputStream();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line = bufferedReader.readLine();

        while (!(line.isEmpty())) // != null)
        {
//            jTextArea.setText(line);
        }
}
catch(MalformedURLException malformedUrl)
{
		System.out.println("Malformed URL");
		System.err.println(malformedUrl.getMessage());
}
catch(IOException ioe)
{
    System.err.println(ioe.getMessage());
}
        BoxLayout boxLayout = new BoxLayout(checkBoxes, BoxLayout.Y_AXIS);
        textArea.setLayout(new BorderLayout());

//      JCheckBox jCheckBox = new JCheckBox("Auto Reboot");
//        JCheckBox BIOS = new JCheckBox("");\

        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);

//        jTextArea.setText(System.getProperty(""));

        start.addActionListener(this);
        bios.addActionListener(this);

        checkBoxes.setLayout(boxLayout);

        checkBoxes.add(drivers);
        checkBoxes.add(bios);
        checkBoxes.add(autoReboot);
        checkBoxes.add(start);

        textArea.add(jTextArea);

        jSplitPane.add(checkBoxes);
        jSplitPane.add(textArea);
//        add(jTextArea);

        jSplitPane.setEnabled(false);
        add(jSplitPane);
    }

    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getSource() == bios)
        {
            if(bios.isSelected())
            {
                autoReboot.setSelected(true);
                autoReboot.setEnabled(false);
            }
            else
            {
//                autoReboot.setSelected(true);
                autoReboot.setEnabled(true);
            }
//          validate();
//          revalidate();
        }
		try
		{
			Process biosProcess;
			ProcessBuilder biosProcessBuilder;
			if(ae.getSource() == start)
			{
				if(drivers.isSelected())
				{
					ProcessBuilder processBuilder = new ProcessBuilder();
					processBuilder.command("\\\\okcpwonsite01\\drivers$\\Script Files\\Drivers\\AutoDrivers-2.exe");
					processBuilder.start();
					Process process = new ProcessBuilder("\\\\okcpwonsite01\\drivers$\\Script Files\\Drivers\\AutoDrivers-2.exe").start();
				}
				else if (bios.isSelected())
				{
                    biosProcess = new ProcessBuilder("\\\\okcpwonsite01\\drivers$\\Script Files\\Drivers\\bBIOS_update.bat").start();
				}
				else if (bios.isSelected() && drivers.isSelected())
				{
					Process process = new ProcessBuilder("\\\\okcpwonsite01\\drivers$\\Script Files\\Drivers\\AutoDrivers-2.exe").start();
					biosProcess = new ProcessBuilder("\\\\okcpwonsite01\\drivers$\\Script Files\\Drivers\\BIOS_update.bat").start();
				}
			}
		}
		catch(IOException ioe)
		{
            JOptionPane.showMessageDialog(this, "The requested operation requires elevation", "Error", JOptionPane.ERROR_MESSAGE);
 //           JOptionPane.showMessageDialog(this, "An error has occured.", "Error", JOptionPane.ERROR_MESSAGE);
 //           JOptionPane.showMessageDialog(this, ioe.getMessage());
 //           jTextArea.setText(ioe.getMessage());
//            JOptionPane.showMessageDialog(this, "An error has occured.");
		}
    }

    public static void main(String[] args)
    {
        new BIOS();
    }
}
