 

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class Inventory extends JFrame implements ActionListener, LayoutManager
{
//    private static final long serialVersionUID;
    private JButton add = new JButton("+");
    private JButton remove;
//    private JTextField jTextField;
//    private SpinnerModel spinnerModel;

    //    private JSpinner jSpinner;
    private JPanel main = new JPanel();
    private JScrollPane jScrollPanel = new JScrollPane(main);
    private Box box;
    private JPanel jPanel;

    public Inventory()
    {
		super("Inventory"); // sets the title
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//       JScrollPane jScrollPane = new JScrollPane(main);
        main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));

        JTextField jTextField = new JTextField(8);
        SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 9999, 1);
        JSpinner jSpinner = new JSpinner(spinnerModel);

 //       JButton jButton = new JButton("+");

//        jPanel = new JPanel();


//        setLayout(new VerticalFlowLayout()); // set the layout of the frame

//        main.setLayout(new VerticalFlowLayout());

//      jScrollPanel.setLayout(new FlowLayout());
//        jScrollPanel.setSize(getWidth(), getHeight());

// ****************************************************************************

//        add = new JButton("+");
//        add.setText("+");
//        jPanel.add(add); // add  the "add" button
//        add.addActionListener(this);

//        remove = new JButton("-");
//        remove.setText("-");
//        jPanel.add(remove);
//        remove.setVisible(false);

        jPanel.add(jTextField);
        jPanel.add(jSpinner);
        jPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

//        box = Box.createVerticalBox();
//        box.add(jPanel);
//        main.add(box);

//		main.add(new JScrollPane(jPanel));
//		jScrollPanel.add(jPanel);

//		main.add(jScrollPanel);
//		main.add(new JScrollPanel(dynamicAddPanel()));

        add(main);

        add(jScrollPanel);

 //       add(jSpinner); // attempt to add the components, directly to the frame.

        pack();
        setVisible(true);
    }

    public JPanel dynamicAddPanel()
    {
		jPanel = new JPanel();
        JTextField jTextField = new JTextField(8);
        SpinnerModel spinnerModel = new SpinnerNumberModel(0, 0, 9999, 1);
        JSpinner jSpinner = new JSpinner(spinnerModel);

        add.setText("+");
        jPanel.add(add); // add  the "add" button
        add.addActionListener(this);

        if (main.getComponentCount() > 1)
        {
            remove = new JButton("-");
            remove.setText("-");
            jPanel.add(remove);
            remove.addActionListener(this);
        }

        jPanel.add(jTextField);
        jPanel.add(jSpinner);
        jPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        box = Box.createVerticalBox();
        box.add(jPanel);
        main.add(box);
//		main.add(new JScrollPane(jPanel));
//        validate();
//        revalidate();
        pack();
        return jPanel;
    }

    @Override
    public void addLayoutComponent(String name, Component comps)
    {
//        Component[] compPanel = {};

//        for (Component comp : comps)
        {
            
        }
//        comps = new JButton(name);
//        comps = new JPanel();
//        comps = new JScrollPane();
//        comps = new JTextField(8);
    }

    @Override
    public void removeLayoutComponent(Component comp)
    {

    }
    @Override
    public Dimension preferredLayoutSize(Container parent)
    {
        return null;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent)
    {
        return null;
    }

    @Override
    public void layoutContainer(Container parent)
    {
        boolean laidOut = false;
        for(Component child : parent.getComponents())
        {
            if(child.isVisible() && !laidOut)
            {
                child.setSize(child.getPreferredSize());
            }
        }
    }

//    public void saveData(String key, String value)
    {
//        try (OutputStream outPutStream = new FileOutputStream("./configData.properties")
        {
//            Properties prop = new Properties();

//            prop.setProperty(key, value);

//            prop.store(outPutStream, null);
        }
//        catch(IOException io)
        {
//            io.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae)
    {
//        JButton jButton = (JButton) ae.getSource();

        if (ae.getSource() == add)
        {
            dynamicAddPanel();
        }
        else if(ae.getSource() == remove)
        {

        }
    }

    public static void main(String[] args)
    {
        new Inventory();
    }
}