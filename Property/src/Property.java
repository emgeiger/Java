import javax.swing.JButton;
import javax.swing.*;
import java.awt.Component;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import javax.swing.DefaultListModel;
import java.util.*;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.InternetAddress;

public class Property extends JFrame implements ActionListener
{
    private ArrayList<String> barCodes = new ArrayList<String>();
   // private ArrayList<String> serialNumbers = new ArrayList<String>();

   // private final DefaultListModel<String> model;

    private JTextArea barCodesTextArea;
    private JTextArea serialNumbersTextArea;
    private JTextArea reasontTextArea;

    private JList barCodesList;
    // private JList serialNumbersList;

    private JButton addBarCode;
    private JButton addSerialNumber;
    private JButton deleteBarCode;
    private JButton deleteSerialNumber;
    private JButton retrieveBarCode;
    private JButton retrieveSerialNumber;

    private JButton clear;
    private JButton send;

    private JComboBox fromRoom;

    private JComboBox movedToRoom;
    private JComboBox equipmentTypeComboBox;
    private JComboBox manufacturerComboBox;
    private JComboBox emailFromComboBox;

    private JRadioButton excessYes;
    private JRadioButton excessNo;
    private JRadioButton typeOfMovePermanent;
    private JRadioButton typeOfMoveTemp1Week;
    private JRadioButton typeOfMoveTemp2Week;
    private JRadioButton typeOfMoveTemp1Month;
    private JRadioButton typeOfMoveTemp2Month;

    private JTextField moveFromField;
    private JTextField moveToField;
    private JTextField eqiupmentTypeTextField;
    private JTextField manufacturerField;
    private JTextField emailFromField;

    private String[] rooms = {"ACAD B10", "ACAD114F", "ACAD128A", "ACAD131", "AT102", "AT103", "AT104", "AT156", "AT157", "AT158", "RTF102", "RTF103", "RTF104", "RTF107",
    "RTF110", "RTF114C", "RTF114N", "RTF114S", "RTF115", "RTF115B", "RTF118", "RTF203", "RTF204", "RTF214N", "RTF214S", "RTF215", "RTF215B", "RTF218", "RTFB05",
    "RTFB08", "RTFB09", "RTFB09-VAULT", "RTFB13A", "RTFB13B", "RTFB13C", "TPS109", "TPS114", "TPS143", "TPS152", "TPS159", "TPS160", "TPS161", "TPS165", "TPS166", "TPS167",
    "TPS168", "TPS213C", "TPS213D", "TPS213F", "TPS213G", "TPSB15", "TPSB16", "ACADB08", "ACADB30", "RTF116", "Other"};

    private String[] equipmentTypes = {"CPU", "SWITCH", "WORKSTATION", "MULTIMEDIA COMPONENT", "PTT", 
        "USB Storage Device", "SERVER", "Other"};
    private String[] manufacturers = {"DELL", "G-Vision", "HP", "Plantronics", "OTHER"};
    private String[] emailsFrom = {"Ahmad.C.Hajmirsadeghi@faa.gov", "Neal.Wilson@faa.gov", "thomas.o.foster@faa.gov", "Blain.L.Hodgen@faa.gov", 
        "George.T-CTR.Morris@faa.gov", "Martin.A-CTR.Sanchez@faa.gov", "William.R-CTR.Watson@faa.gov", 
        "Eric M-CTR Geiger", "Carl.CTR.Dodd@faa.gov", "Seth.A-CTR.Hilliard@faa.gov"};

    private String[] emailsTo = {"Ahmad.C.Hajmirsadeghi@faa.gov", "Neal.Wilson@faa.gov", "thomas.o.foster@faa.gov", "Blain.L.Hodgen@faa.gov", 
        "George.T-CTR.Morris@faa.gov", "Martin.A-CTR.Sanchez@faa.gov", "William.R-CTR.Watson@faa.gov", 
        "Eric M-CTR Geiger", "Carl.CTR.Dodd@faa.gov", "Seth.A-CTR.Hilliard@faa.gov"};

    public Property()
    {
        createContents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setTitle("Property Movement Report");
        setVisible(true);
        pack();
    }

    public void createContents()
    {
        // TODO Auto-generated method stub

        Border blackLine = BorderFactory.createLineBorder(Color.BLACK);

        JLabel other = new JLabel("Select \"other\" from the list to enter one that is not inn the List.");
        JLabel temporary = new JLabel("Temporary, Not to Exceed:");
        JLabel reasonLabel = new JLabel("Reason:");

        JPanel mainPanel = new JPanel();
        JPanel southPanel = new JPanel();

        barCodesTextArea = new JTextArea();
        serialNumbersTextArea = new JTextArea();

        barCodesList = new JList(barCodes.toArray());
       // serialNumbersList = new JList(serialNumbers.toArray());

        barCodesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        barCodesList.setLayoutOrientation(JList.VERTICAL);
        
       // serialNumbersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
       // serialNumbersList.setLayoutOrientation(JList.VERTICAL);

        // Add the Top buttons
        addBarCode = new JButton("Add");
        addSerialNumber = new JButton("Add");
        retrieveBarCode = new JButton("Retrieve");
        retrieveSerialNumber = new JButton("Retrieve");
        deleteBarCode = new JButton("Delete");
        deleteSerialNumber = new JButton("Delete");

        JPanel northPanel = new JPanel();

        JPanel barCodesPanel = new JPanel();
        JPanel inputBarCodesPanel = new JPanel();
        TitledBorder barCodesBorder = BorderFactory.createTitledBorder(blackLine, "Barcodes");
        barCodesPanel.setBorder(barCodesBorder);

        JPanel serialNumPanel = new JPanel();
        JPanel inputSerialNumPanel = new JPanel();
        TitledBorder serialNumBorder = BorderFactory.createTitledBorder(blackLine, "Serial Numbers");
        serialNumPanel.setBorder(serialNumBorder);

        JPanel barCodesButtonPanel = new JPanel();
        JPanel serialNumButtonPanel = new JPanel();

        barCodesButtonPanel.setLayout(new BoxLayout(barCodesButtonPanel, BoxLayout.Y_AXIS));
        serialNumButtonPanel.setLayout(new BoxLayout(serialNumButtonPanel, BoxLayout.Y_AXIS));
        inputBarCodesPanel.setLayout(new BoxLayout(inputBarCodesPanel, BoxLayout.Y_AXIS));
        inputSerialNumPanel.setLayout(new BoxLayout(inputSerialNumPanel, BoxLayout.Y_AXIS));
        
        // Instantiate the JLists
        barCodesList = new JList(barCodes.toArray());
        // serialNumbersList = new JList(serialNumbers.toArray());

        barCodesList.setPreferredSize(new Dimension((barCodesPanel.getWidth()/2), barCodesPanel.getHeight()));
        // serialNumbersList.setPreferredSize(new Dimension((serialNumPanel.getWidth()/2), serialNumPanel.getHeight()));

        // Instantiate the TextAreas
        barCodesTextArea = new JTextArea(5, 10);
        // serialNumbersTextArea = new JTextArea(5, 10);

        barCodesTextArea.setLineWrap(true);
        serialNumbersTextArea.setLineWrap(true);
        barCodesTextArea.setWrapStyleWord(true);
        serialNumbersTextArea.setWrapStyleWord(true);

        //Add the barcode buttons to the panels
        barCodesButtonPanel.add(addBarCode);
        barCodesButtonPanel.add(retrieveBarCode);
        barCodesButtonPanel.add(deleteBarCode);
        
        // Add the barcode components to the Panel
        barCodesPanel.add(new JScrollPane(barCodesList));
        
        inputBarCodesPanel.add(new JScrollPane(barCodesTextArea));
        inputBarCodesPanel.add(barCodesButtonPanel);
       // barCodesPanel.add(inputBarCodesPanel);

        //Add the serialNumber buttons to the panel
        // serialNumButtonPanel.add(addSerialNumber);
        // serialNumButtonPanel.add(retrieveSerialNumber);
        // serialNumButtonPanel.add(deleteSerialNumber);

        // Add the serialNumber components to the Panel
        // serialNumPanel.add(new JScrollPane(serialNumbersList));
        
        // inputSerialNumPanel.add(new JScrollPane(serialNumbersTextArea));
        // inputSerialNumPanel.add(serialNumButtonPanel);
        // serialNumPanel.add(inputSerialNumPanel);

        northPanel.add(barCodesPanel);
        // northPanel.add(serialNumPanel);

        add(northPanel,  BorderLayout.NORTH);  // Add to the North side of the frame

        // Add the bottom buttons
        clear = new JButton("Clear");
        send = new JButton("Send");

        // Add the ComboBoxes
        fromRoom = new JComboBox(rooms);
        movedToRoom = new JComboBox(rooms);
        equipmentTypeComboBox = new JComboBox(equipmentTypes);
        manufacturerComboBox = new JComboBox(manufacturers);
        emailFromComboBox = new JComboBox(emailsFrom);

        LineBorder lineBorder = new LineBorder(Color.BLACK);

        //Instantiate the TextFields
        moveFromField = new JTextField(8);
        moveToField = new JTextField(8);
        eqiupmentTypeTextField = new JTextField(8);
        manufacturerField = new JTextField(8);
        emailFromField = new JTextField(8);

        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));

        JPanel moveToRoomPanel = new JPanel();
        moveToRoomPanel.add(Box.createHorizontalGlue());
        TitledBorder moveToRoomTitledBorder = BorderFactory.createTitledBorder(blackLine, "Move To Room");
        moveToRoomPanel.setBorder(moveToRoomTitledBorder);

        // moveToField.setEnabled(false);
        moveToField.setEditable(false);

        moveToRoomPanel.add(movedToRoom);
        moveToRoomPanel.add(moveToField);

        JPanel  equipmentTypePanel = new JPanel();
        equipmentTypePanel.add(Box.createHorizontalGlue());
        TitledBorder equipmentTypeTitledBorder = BorderFactory.createTitledBorder(blackLine, "Equipment Type");
        equipmentTypePanel.setBorder(equipmentTypeTitledBorder);

        // eqiupmentTypeTextField.setEnabled(false);
        eqiupmentTypeTextField.setEditable(false);

        equipmentTypePanel.add(equipmentTypeComboBox);
        equipmentTypePanel.add(eqiupmentTypeTextField);

        JPanel manufacturerPanel = new JPanel();
        manufacturerPanel.add(Box.createHorizontalGlue());
        TitledBorder manufacturerTitledBorder = BorderFactory.createTitledBorder(blackLine, "Manufacturer");
        manufacturerPanel.setBorder(manufacturerTitledBorder);

         // manufacturerField.setEnabled(false);
         manufacturerField.setEditable(false);

        manufacturerPanel.add(manufacturerComboBox);
        manufacturerPanel.add(manufacturerField);

        JPanel emailFromPanel = new JPanel();
        TitledBorder emailFromTitledBorder = BorderFactory.createTitledBorder(blackLine, "eMail From");
        emailFromPanel.setBorder(emailFromTitledBorder);

        // emailFromField.setEnabled(false);
        emailFromField.setEditable(false);

        emailFromPanel.add(emailFromComboBox);
        emailFromPanel.add(emailFromField);

        JPanel moveFromRoomPanel = new JPanel();
        TitledBorder moveFromRoomTitledBorder = BorderFactory.createTitledBorder(blackLine, "From:");
        moveFromRoomPanel.setBorder(moveFromRoomTitledBorder);

        // moveFromField.setEnabled(false);
        moveFromField.setEditable(false);

        moveFromRoomPanel.add(fromRoom);
        moveFromRoomPanel.add(moveFromField);

        // westPanel.add(moveFromRoomPanel);
        westPanel.add(moveToRoomPanel);
        westPanel.add(equipmentTypePanel);
        westPanel.add(manufacturerPanel);
        westPanel.add(emailFromPanel);
        westPanel.add(moveFromRoomPanel);

        // southPanel.
        add(westPanel, BorderLayout.WEST); // Add to the West side of the frame

        JPanel eastPanel = new JPanel();

        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));

        JPanel excessPanel = new JPanel();
        TitledBorder excessTitledBorder = BorderFactory.createTitledBorder("Excess?");
        excessPanel.setBorder(excessTitledBorder);

        JPanel typeOfMovePanel = new JPanel();
        TitledBorder typeOfMoveTitledBorder = BorderFactory.createTitledBorder("Type of Move");
        typeOfMovePanel.setBorder(typeOfMoveTitledBorder);

        excessYes = new JRadioButton("Yes");
        excessNo = new JRadioButton("No");

        ButtonGroup excess = new ButtonGroup();
        excess.add(excessYes);
        excess.add(excessNo);
        excessNo.setSelected(true);

        excessPanel.add(excessNo);
        excessPanel.add(excessYes);

        JPanel typeOfMoveTempPanel = new JPanel();
        typeOfMoveTempPanel.setLayout(new BoxLayout(typeOfMoveTempPanel, BoxLayout.Y_AXIS));

        typeOfMovePermanent = new JRadioButton("Permanent");
        typeOfMoveTemp1Week = new JRadioButton("1 Week");
        typeOfMoveTemp2Week = new JRadioButton("2 Week");
        typeOfMoveTemp1Month = new JRadioButton("1 Month");
        typeOfMoveTemp2Month = new JRadioButton("2 Month");

        ButtonGroup typeOfMove = new ButtonGroup();
        typeOfMove.add(typeOfMovePermanent);

        typeOfMove.add(typeOfMoveTemp1Week);
        typeOfMove.add(typeOfMoveTemp2Week);

        typeOfMove.add(typeOfMoveTemp1Month);
        typeOfMove.add(typeOfMoveTemp2Month);

        typeOfMovePermanent.setSelected(true);

        typeOfMovePanel.add(typeOfMovePermanent);

        typeOfMoveTempPanel.add(temporary, BorderLayout.EAST);
        typeOfMoveTempPanel.add(typeOfMoveTemp1Week, BorderLayout.EAST);
        typeOfMoveTempPanel.add(typeOfMoveTemp2Week, BorderLayout.EAST);
        typeOfMoveTempPanel.add(typeOfMoveTemp1Month, BorderLayout.EAST);
        typeOfMoveTempPanel.add(typeOfMoveTemp2Month, BorderLayout.EAST);

        typeOfMovePanel.add(typeOfMoveTempPanel, BorderLayout.CENTER);

        JPanel reasonPanel = new JPanel();
        TitledBorder reasonTitledBorder = BorderFactory.createTitledBorder("Reason");
        reasonPanel.setBorder(reasonTitledBorder);
        // reasonPanel.setBorder(lineBorder);

        reasontTextArea = new JTextArea(5, 20);

        reasontTextArea.setLineWrap(true);
        reasontTextArea.setWrapStyleWord(true);

        reasonPanel.add(reasonLabel);
        reasonPanel.add(reasontTextArea);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(send);
        buttonPanel.add(clear);

        eastPanel.add(excessPanel);
        eastPanel.add(typeOfMovePanel);
        eastPanel.add(reasonPanel);
        eastPanel.add(buttonPanel);

        // southPanel.
        add(eastPanel, BorderLayout.EAST); // Add to the East side of the frame

//        mainPanel.add(southPanel); // Add to the South side of the frame

//        add(mainPanel);

        inputList(barCodesList);

        // Add the Top Button Action Listeners
        addBarCode.addActionListener(this);
       // addSerialNumber.addActionListener(this);

        retrieveBarCode.addActionListener(this);
       // retrieveSerialNumber.addActionListener(this);

        deleteBarCode.addActionListener(this);
       // deleteSerialNumber.addActionListener(this);

        // Add the Bottom Buttton Action Listeners
        clear.addActionListener(this);
        send.addActionListener(this);

        movedToRoom.addActionListener(this);
        equipmentTypeComboBox.addActionListener(this);
        manufacturerComboBox.addActionListener(this);
        emailFromComboBox.addActionListener(this);
        fromRoom.addActionListener(this);
	}

    public void actionPerformed(ActionEvent ae)
    {
        if (ae.getSource() == addBarCode)
        {
            // Need to add to the arrayList
            barCodesList.setListData(barCodesTextArea.getText().split("\n"));
           // barCodesTextArea.setText("");
        }
        else if (ae.getSource() == retrieveBarCode)
        {
           // barCodesTextArea.append(barCodesList.getSelectedValue() + "\n");
           // barCodes.remove(barCodesList.getSelectedIndex());
           // barCodesList.setListData(barCodes);
        }
        else if (ae.getSource() == deleteBarCode)
        {
            barCodes.remove(barCodesList.getSelectedIndex());
           // barCodesList.setListData(barCodes);
        }
        else if (ae.getSource() == addSerialNumber)
        {
            // Need to add to the arrayList
           // serialNumbersList.setListData(serialNumbersTextArea.getText().split("\n"));
           // serialNumbersTextArea.setText("");
        }
        else if (ae.getSource() == retrieveSerialNumber)
        {
           // serialNumbersTextArea.append(serialNumbersList.getSelectedValue() + "\n");
           // serialNumbers.remove(serialNumbersList.getSelectedIndex());
           // serialNumbersList.setListData(serialNumbers);
        }
        else if (ae.getSource() == deleteSerialNumber)
        {
           // serialNumbers.remove(serialNumbersList.getSelectedIndex());
           // serialNumbersList.setListData(serialNumbers);
        }
        else if (ae.getSource() == clear)
        {
            barCodes.clear();
           // serialNumbers.clear();
           // barCodesList.setListData(barCodes);
         //   serialNumbersList.setListData(serialNumbers);

            // barCodesTextArea.setText("");
            // serialNumbersTextArea.setText("");

            movedToRoom.setSelectedIndex(0);
            equipmentTypeComboBox.setSelectedIndex(0);
            manufacturerComboBox.setSelectedIndex(0);
            emailFromComboBox.setSelectedIndex(0);
            fromRoom.setSelectedIndex(0);

            moveFromField.setText("");
            moveToField.setText("");
            eqiupmentTypeTextField.setText("");
            manufacturerField.setText("");
            emailFromField.setText("");

            excessNo.setSelected(true);
            typeOfMovePermanent.setSelected(true);
            
            reasontTextArea.setText("");
        }
        else if (ae.getSource() == send)
        {
        }
        
        if (movedToRoom.getActionCommand().equalsIgnoreCase("Other") || movedToRoom.getSelectedItem().toString().equalsIgnoreCase("Other"))
        {
            // moveToField.setEnabled(true);
            moveToField.setEditable(true);
        }
        else
        {
            // moveToField.setEnabled(false);
            moveToField.setEditable(false);
            moveToField.setText(movedToRoom.getSelectedItem().toString());
        }

        if (equipmentTypeComboBox.getActionCommand().equalsIgnoreCase("Other") || equipmentTypeComboBox.getSelectedItem().toString().equalsIgnoreCase("Other"))
        {
            // eqiupmentTypeTextField.setEnabled(true);
            eqiupmentTypeTextField.setEditable(true);
        }    
        else
        {
            // eqiupmentTypeTextField.setEnabled(false);
            eqiupmentTypeTextField.setEditable(false);
            eqiupmentTypeTextField.setText(equipmentTypeComboBox.getSelectedItem().toString());
        }

        if (manufacturerComboBox.getActionCommand().equalsIgnoreCase("Other") || manufacturerComboBox.getSelectedItem().toString().equalsIgnoreCase("Other"))
        {
            // manufacturerField.setEnabled(true);
            manufacturerField.setEditable(true);
        }    
        else
        {
            // manufacturerField.setEnabled(false);
            manufacturerField.setEditable(false);
            manufacturerField.setText(manufacturerComboBox.getSelectedItem().toString());
        }

        if (emailFromComboBox.getActionCommand().equalsIgnoreCase("Other") || emailFromComboBox.getSelectedItem().toString().equalsIgnoreCase("Other"))
        {
            // emailFromField.setEnabled(true);
            emailFromField.setEditable(true);
        }    
        else
        {
            // emailFromField.setEnabled(false);
            emailFromField.setEditable(false);
            emailFromField.setText(emailFromComboBox.getSelectedItem().toString());
        }

        if (fromRoom.getActionCommand().equalsIgnoreCase("Other") || fromRoom.getSelectedItem().toString().equalsIgnoreCase("Other"))
        {
            // moveFromField.setEnabled(true);
            moveFromField.setEditable(true);
        }    
        else
        {
            // moveFromField.setEnabled(false);
            moveFromField.setEditable(false);
            moveFromField.setText(fromRoom.getSelectedItem().toString());
        }
    } 
    
    public void inputList(JList<String> list)
    {    
        DefaultListModel<String> model = new DefaultListModel<>();
        list.setModel(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellEditor(new ListCellEditor() {
            private final JTextField field = new JTextField();
    
            @Override
            public Object getCellEditorValue() {
                return field.getText();
            }
    
            @Override
            public Component getListCellEditorComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                field.setText(value != null ? value.toString() : "");
                field.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            int selectedIndex = list.getSelectedIndex();
                            if (selectedIndex < model.getSize() - 1) {
                                list.setSelectedIndex(selectedIndex + 1);
                            } else {
                                model.addElement("");
                                list.setSelectedIndex(model.getSize() - 1);
                            }
                            list.editCellAt(list.getSelectedIndex(), 0);
                        }
                    }
                });
                return field;
            }
        });
    }

   public static void sendEmail(String senderEmail, String senderPassword, String recipientEmail, String subject, String body) throws Exception
   {
      
      //Set the host SMTP server
      Properties properties = System.getProperties();
      properties.setProperty("mail.smtp.host", "smtp.office365.com");
      properties.setProperty("mail.smtp.port", "587");
      properties.setProperty("mail.smtp.starttls.enable", "true");
      properties.setProperty("mail.smtp.auth", "false");

      //Create the session
      /*
      Session session = Session.getDefaultInstance(properties, new Authenticator() {
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(senderEmail, senderPassword);
         }
      }); */

      //Create the message
      // MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress("9-AMC-AMA-520-Notifications@faa.gov"));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
      message.setSubject(subject);
      message.setText(body);

      //Send the message
      Transport.send(message);
      System.out.println("Message sent successfully.");
   }

    /*
    public void inputList(JList<String> list)
    {
        DefaultListModel<String> model = new DefaultListModel<>();
        model = new DefaultListModel<>();
        // model = new DefaultListModel<>();
       // list = new JList<>(model);
        list.setModel(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellEditor(new ListCellEditor()
        {
            private final JTextField field = new JTextField();
            
            @Override
            public Object getCellEditorValue()
            {
                return field.getText();
            }

            @Override
            public Component getListCellEditorComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
            {
                field.setText(value.toString());
                field.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                    }
                    @Override
                    public void keyPressed(KeyEvent e) {
                    }
                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                });
                return field;
            }
        } 
        
        // model.addElement("");
        // list = new JList<>(model);
        // list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellEditor(new ListCellEditor()
        {
            private final JTextField field = new JTextField();
            
            @Override
            public Object getCellEditorValue()
            {
                return field.getText();
            }

            @Override
            public Component getListCellEditorComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
            {
                field.setText(value.toString());
                field.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            model.addElement("");
                            list.editCellAt(model.getSize()-1, e);
                        }
                    }
                    @Override
                    public void keyPressed(KeyEvent e) {
                    }
                    @Override
                    public void keyReleased(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            model.addElement("");
                            list.editCellAt(model.getSize()-1, e);
                        }
                    }
                });
                return field;
            }

            @Override
            public boolean isCellEditable(EventObject anEvent) {
                return true;
            }

            @Override
            public boolean shouldSelectCell(EventObject anEvent) {
                return true;
            }

            @Override
            public boolean stopCellEditing() {
                return true;
            }

            @Override
            public void cancelCellEditing() {
            }
        });
    }
     */
}