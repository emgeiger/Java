 

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Templates extends JFrame implements ActionListener, ItemListener
{
    private JFrame jFrame;
    private JMenuBar menuBar;
    private JMenu menu;
//    private JMenuItem hideWhenMini;
    private JCheckBoxMenuItem checkBoxMenuItem;
    private JCheckBoxMenuItem hideWhenMini;
    private JMenuItem menuItem;

    private JPanel cardPanel;
    private JPanel comboBoxPanel;
    private JPanel newHirePanel;
    private JPanel newHiresPanel;
    private JPanel refreshPanel;
    private JPanel refreshesPanel;
    private JPanel peripheralPanel;
    private JPanel peripheralsPanel;
    private JPanel checkBoxPanel;
    private JPanel buttonPanel;
    private JPanel submitButtonPanel;
    private JTabbedPane tabbedPanel;

//    private JPanel jPanel;
    private JComboBox typeOfForm;
//    private JComboBox comboBox;
    private JComboBox systemType;
    private JLabel nameLabel;
    private JTextField name;
    private JLabel dateProcessed;
    private JTextField paperworkProcessedDate;
    private JTextField assetTag;
    private JLabel badgeNumber = new JLabel("Badge Number: ");
    private JTextField badge;
    private JLabel taskNumber = new JLabel("Task #: ");
    private JTextField workOrder;
    private JLabel itemType = new JLabel("Item Type: ");
    private JComboBox item;
    private JLabel serviceTagLabel = new JLabel("Service Tag: "); // Service Tag Label
    private JTextField serviceTag; // = new JTextField(8); // Service Tag
    private JLabel assetTagLabel = new JLabel("Asset Tag: "); // Asset Tag label
    private JLabel typeOfAsset = new JLabel("Type of asset:"); // Type of asset label
    private JCheckBox dockingStation;
    private JCheckBox monitor;
    private JCheckBox headset;
    private JCheckBox lock;
    private JCheckBox adapter;
    private JButton submit = new JButton("Submit");
    private JButton submitButton = new JButton("Submit");

    private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    private Date date = new Date();
    private int assetTagInteger;
    private String serviceTagString;
    private String nameString;
    private String typeOfAssetString;
    private String[] typesOfAssets = {"Latitude 5490", "Latitude 5480",
    "Latitude 7390 2-in-1", "Precision 5530",
    "Precision 5530 2-in-1", "XPS 13 9370", "Latitude 5290"};
    private String[] processedBy = { "PP", "FD", "DV", "DC" };

    private final static String newHire = "New Hire";
    private final static String refresh = "Refresh";
    private final static String peripherals = "Peripherals and Accessories";

    private final static int NewHire = 0;
    private final static int Refresh = 1;
    private final static int Peripherals = 2;

    private final static int NEW_HIRE = 0;
    private final static int REFRESH = 1;
    private final static int PERIPHERALS = 2;

    public Templates()
    {
        createContent();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);

        jFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//        jFrame.pack();
//        jFrame.setSize(getPreferredSize());
//        jFrame.setSize(getWidth(), getMinimumSize().height);
         jFrame.setSize(getWidth(), getBounds().height);
//        jFrame.setLocation(p);
        jFrame.setLocation(getWidth(), 0);
        jFrame.setVisible(true);
    }

    private void createContent()
    {
//        jFrame = new JFrame("JFrame");
        jFrame = new JFrame();
        menu = new JMenu("Options");
        hideWhenMini = new JCheckBoxMenuItem("Hide When Minimized");
        hideWhenMini.addActionListener(this);
        menu.add(hideWhenMini);

        menuBar = new JMenuBar();
        menuBar.add(menu);
        setJMenuBar(menuBar);

//        JPanel test = new JPanel(new CardLayout());
        cardPanel = new JPanel(new CardLayout());
        tabbedPanel = new JTabbedPane();
        newHirePanel = new JPanel();
        refreshPanel = new JPanel();
        peripheralPanel = new JPanel();
        checkBoxPanel = new JPanel(new GridLayout(0, 1));

        FlowLayout flowLayout = new FlowLayout();
        GridBagLayout gridBagLayout = new GridBagLayout();
        BorderLayout borderLayout = new BorderLayout();

        JPanel windowPanel = new JPanel(new BorderLayout());
        JPanel mainPanel = new JPanel(new GridLayout(0, 1));
        JPanel boxPanel = new JPanel();
        comboBoxPanel = new JPanel();

        // jPanel =new JPanel();
        buttonPanel = new JPanel();
        submitButtonPanel = new JPanel();

        BoxLayout mainBoxLayout = new BoxLayout(boxPanel, BoxLayout.Y_AXIS);
        // windowPanel.setLayout(new BorderLayout());
        mainPanel.setLayout(borderLayout);
        windowPanel.setLayout(new GridLayout(0, 1));
        // jPanel.setLayout(new GridLayout(3, 2));
        // BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
        // boxPanel.setLayout(boxLayout);

        String[] typeOfForms = {"New Hire", "Refresh", "Peripheral and Accessories"};

        newHiresPanel = new JPanel();
        refreshesPanel = new JPanel();
        peripheralsPanel = new JPanel();

        JLabel typeOfFormLabel = new JLabel("Type of form: "); // type of form label
        typeOfForm = new JComboBox<>(typeOfForms); //  type of form drop down
        nameLabel = new JLabel("Name: "); // name label
        name = new JTextField(8); // name
        badge = new JTextField(8);
        serviceTag = new JTextField(8);
        assetTag = new JTextField(8); // Asset Tag
        workOrder = new JTextField(8);
        dateProcessed = new JLabel("Date processed: "); // Date processedl labal
        paperworkProcessedDate = new JTextField(dateFormat.format(date)); // Date processed
        paperworkProcessedDate.setEditable(false);
        systemType = new JComboBox<>(typesOfAssets);

        // Checkboxes

        dockingStation = new JCheckBox("Type C Docking station,and Adapters");
        monitor = new JCheckBox("24\" Monitor");
        headset = new JCheckBox("USB headset");
        lock = new JCheckBox("Cable Lock");
        adapter = new JCheckBox("USB Type C 6-in-1 Adapter");

        checkBoxPanel.add(dockingStation);
        checkBoxPanel.add(monitor);
        checkBoxPanel.add(headset);
        checkBoxPanel.add(lock);
        checkBoxPanel.add(adapter);

        submit.addActionListener(this);
        submitButton.addActionListener(this);

//        jPanel.add(typeOfFormLabel);
//        jPanel.add(typeOfForm);

        //The first window

//        cardPanel.add(typeOfFormLabel);
//        cardPanel.add(typeOfForm);

        comboBoxPanel.add(typeOfFormLabel);
        comboBoxPanel.add(typeOfForm);

        newHirePanel.add(nameLabel);
        newHirePanel.add(name);
        newHirePanel.add(badgeNumber);
        newHirePanel.add(badge);
        newHirePanel.add(serviceTagLabel);
        newHirePanel.add(serviceTag);
        newHirePanel.add(assetTagLabel);
        newHirePanel.add(assetTag);
        newHirePanel.add(taskNumber);
        newHirePanel.add(workOrder);
        newHirePanel.add(dateProcessed);
        newHirePanel.add(paperworkProcessedDate);
        newHirePanel.add(typeOfAsset);
        newHirePanel.add(systemType);

        refreshPanel.add(nameLabel);
        refreshPanel.add(name);
        refreshPanel.add(taskNumber);
        refreshPanel.add(workOrder);
        refreshPanel.add(dateProcessed);
        refreshPanel.add(paperworkProcessedDate);

        peripheralPanel.add(nameLabel);
        peripheralPanel.add(name);

        // The second window

        newHiresPanel.add(nameLabel);
        newHiresPanel.add(name);
        newHiresPanel.add(badgeNumber);
        newHiresPanel.add(badge);
        newHiresPanel.add(serviceTagLabel);
        newHiresPanel.add(serviceTag);
        newHiresPanel.add(assetTagLabel);
        newHiresPanel.add(assetTag);
        newHiresPanel.add(taskNumber);
        newHiresPanel.add(workOrder);
        newHiresPanel.add(dateProcessed);
        newHiresPanel.add(paperworkProcessedDate);
        newHiresPanel.add(typeOfAsset);
        newHiresPanel.add(systemType);

        refreshesPanel.add(nameLabel);
        refreshesPanel.add(name);
        refreshesPanel.add(taskNumber);
        refreshesPanel.add(workOrder);
        refreshesPanel.add(dateProcessed);
        refreshesPanel.add(paperworkProcessedDate);

        peripheralsPanel.add(nameLabel);
        peripheralsPanel.add(name);

        tabbedPanel.addTab(newHire, newHire());
        tabbedPanel.addTab(refresh, refresh());
        tabbedPanel.addTab(peripherals, peripherals());

//        tabbedPanel.addTab(newHire, newHirePanel);
//        tabbedPanel.addTab(refresh, refreshPanel);
//        tabbedPanel.addTab(peripherals, peripheralPanel);

//        jPanel.add(nameLabel);
//        jPanel.add(name);
//        jPanel.add(dateProcessed);
//        jPanel.add(paperworkProcessedDate);

        buttonPanel.add(submit);
        submitButtonPanel.add(submitButton);

//        mainPanel.add(jPanel);

//        mainPanel.add(comboBoxPanel, BorderLayout.PAGE_START);
        add(comboBoxPanel, BorderLayout.PAGE_START);
        mainPanel.add(newHirePanel);
        mainPanel.add(refreshPanel);
        mainPanel.add(peripheralPanel);
        mainPanel.add(checkBoxPanel);
//        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

//        mainPanel.add(cardPanel, BorderLayout.CENTER);
//        mainPanel.add(tabbedPanel, BorderLayout.CENTER);
        windowPanel.add(tabbedPanel, BorderLayout.CENTER);
//        windowPanel.add(jPanel, BorderLayout.CENTER);
//        windowPanel.add(cardPanel);
        windowPanel.add(submitButtonPanel, BorderLayout.SOUTH);
//        jFrame.add(submitButtonPanel, BorderLayout.SOUTH);
//        windowPanel.add(mainPanel);
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        jFrame.add(windowPanel);

        nameString = name.getText();
        serviceTagString = serviceTag.getText().toUpperCase();
    }

//    private void hideWhenMinimized()
    {

    }

    private JPanel newHire()
    {
        systemType = new JComboBox<String>(typesOfAssets); // type of asset

        newHirePanel.add(nameLabel);
        newHirePanel.add(name);
        newHirePanel.add(badgeNumber);
        newHirePanel.add(badge);
        newHirePanel.add(serviceTagLabel);
        newHirePanel.add(serviceTag);
        newHirePanel.add(assetTagLabel);
        newHirePanel.add(assetTag);
        newHirePanel.add(taskNumber);
        newHirePanel.add(workOrder);
        newHirePanel.add(dateProcessed);
        newHirePanel.add(paperworkProcessedDate);
        newHirePanel.add(typeOfAsset);
        newHirePanel.add(systemType);

        return newHirePanel;
    }

    private JPanel refresh()
    {
        systemType = new JComboBox<String>(typesOfAssets); // type of asset

        refreshPanel.add(nameLabel);
        refreshPanel.add(name);
        refreshPanel.add(taskNumber);
        refreshPanel.add(workOrder);
        refreshPanel.add(dateProcessed);
        refreshPanel.add(paperworkProcessedDate);

        return refreshPanel;
    }

    private JPanel peripherals()
    {
        peripheralPanel.add(nameLabel);
        peripheralPanel.add(name);

        return peripheralPanel;
    }

    public void actionPerformed(ActionEvent ae)
    {
        JLabel orderedByLabel = new JLabel("Odered By:");
        JTextField orderedBy;
        JLabel newHireManager = new JLabel("New Hire Manager: ");
        JTextField manager;
        JLabel startDate = new JLabel("Start date: "); // Start date label
        JTextField newHireStartDate;

        String[] peripherals = {};

        systemType = new JComboBox<String>(typesOfAssets); // type of asset

        if (ae.getSource() == submit)
        {
            File file = new File(".");
            FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter(".docx", "docx");
            typeOfAssetString = systemType.getName();
            String fileName;
            try{
                assetTagInteger = Integer.parseInt(assetTag.getText());
            }
            catch(NumberFormatException nfe)
            {
                nfe.printStackTrace();
            }
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(fileNameExtensionFilter);
            fileChooser.showSaveDialog(this);
            fileName = fileChooser.getSelectedFile().getName();
        }
        else if (ae.getActionCommand().equals("New Hire"))
        {
            newHireStartDate = new JTextField(dateFormat.format(date));

//        	jPanel.setLayout(new GridLayout(8, 2));

//            systemType = new JComboBox<String>(typesOfAssets); // type of asset

//			jPanel.add(badgeNumber);
//			jPanel.add(badge);
//	        jPanel.add(serviceTagLabel);
//	        jPanel.add(serviceTag);
//	        jPanel.add(assetTagLabel);
//	        jPanel.add(assetTag);
//	        jPanel.add(taskNumber);
//	        jPanel.add(workOrder);
//	        jPanel.add(typeOfAsset);
//	        jPanel.add(systemType);
        }
        else if (ae.getActionCommand().equals("Refresh"))
        {
//            jPanel.add(systemType);
        }
        else if (ae.getActionCommand().equals("Peripheral and Accessories"))
        {

        }
        if (hideWhenMini.isSelected())
        {
            setDefaultCloseOperation(HIDE_ON_CLOSE);
        }
        else
        {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }

        pack();
    }

    public void itemStateChanged(ItemEvent ie)
    {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, (String) ie.getItem());

        cl = (CardLayout) comboBoxPanel.getLayout();
        cl.show(comboBoxPanel, (String) ie.getItem());
    }

//    private class intFilter extends DocumentFilter implements DocumentListener
    {

    }

    public static void main(String[] args)
    {
		new Templates();
    }
}