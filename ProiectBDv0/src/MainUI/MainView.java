package MainUI;

import ImageBackground.BackgroundImageJFrame;
import com.mysql.cj.conf.ConnectionUrlParser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Vector;

public class MainView {
    //Declaram elementele care preiau informatii de la utilizator
    public ConnectionUrlParser.Pair<Vector<String>, HashMap<String, Vector<String>>> tablesAndOperations;
    public ConnectionUrlParser.Pair<HashMap<String, Vector<String>>, HashMap<String, Vector<String>>> accessibleColumnsAndTypes;
    public BackgroundImageJFrame mainFrame;
    public JComboBox tableComboBox;
    public JComboBox operationsComboBox;
    public JList columnList;
    public JScrollPane columnListScroll;
    public JButton executeButton;
    public JTable selectTable;
    public JScrollPane tableScroll;
    public JButton logOutButton;
    public DefaultTableModel insertTableModel;
    public JTable insertTable;
    public JScrollPane insertTableScroll;

    /**
     * Constructorul e start-ul si se foloseste de parametru pt a stii toate chestiile la care are access utilizatorul
     * @param accessibleColumnsAndTypes coloanele si tipurile lor
     * @param tablesAndOperations la care are access userul
     */
    public MainView(ConnectionUrlParser.Pair<Vector<String>, HashMap<String, Vector<String>>> tablesAndOperations, ConnectionUrlParser.Pair<HashMap<String, Vector<String>>, HashMap<String, Vector<String>>> accessibleColumnsAndTypes) {
        this.tablesAndOperations = tablesAndOperations;
        this.accessibleColumnsAndTypes = accessibleColumnsAndTypes;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //GridLayout la Frame for now, mai vedem
        mainFrame = new BackgroundImageJFrame("DonutNtNtNt.jpeg");
        mainFrame.setTitle("Los Sistemas Gestores de Bases de Datos");
        mainFrame.setLayoutF(new BorderLayout());   //@@@@@@@setLayoutF@@@@@@ de la BackgroundImageJFrame seteaza layout la JLabel @background
        mainFrame.setSize(screenSize.width, screenSize.height);
        mainFrame.background.setSize(screenSize.width, screenSize.height);
        mainFrame.setLocation((screenSize.width-mainFrame.getWidth())/2, (screenSize.height-mainFrame.getHeight())/2);

        //Panel pt a mentine elementele in ordine
        JPanel queryOptionsPanel = new JPanel();
        queryOptionsPanel.setLayout(new FlowLayout());
        queryOptionsPanel.setOpaque(false);

        //Initializare combobox-uri cu primele valori disponibile(sau Access Denied daca nu sunt disponibile)
        tableComboBox = new JComboBox(tablesAndOperations.left);

        if(tablesAndOperations.right.containsKey(tableComboBox.getSelectedItem()))
            operationsComboBox = new JComboBox(tablesAndOperations.right.get(tableComboBox.getSelectedItem()));
        else {
            String[] a = new String[]{"ACCESS DENIED"};
            operationsComboBox = new JComboBox(a);
        }


        //Creare JList cu coloane
        try {
            if (operationsComboBox.getSelectedItem().toString().contains("SELECT") || operationsComboBox.getSelectedItem().toString().contains("INSERT")) {
                columnList = new JList(accessibleColumnsAndTypes.left.get(tableComboBox.getSelectedItem()));
                columnListScroll = new JScrollPane(columnList);
            } else {
                if (accessibleColumnsAndTypes.left.containsKey(tableComboBox.getSelectedItem().toString()))
                    columnList = new JList(accessibleColumnsAndTypes.left.get(tableComboBox.getSelectedItem().toString()));
                else columnList = new JList();
                columnListScroll = new JScrollPane(columnList);
                columnListScroll.setVisible(false);
            }
        } catch(NullPointerException e) {
            System.out.println("NullPointerException la creare JList in MainView.java");
        }
        columnList.setVisibleRowCount(4);
        columnList.setFixedCellHeight(20);
        columnList.setFixedCellWidth(120);
        columnList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        //Adaugare tot la panelul cel de sus pt flowLayout aka sa fie in linie
        queryOptionsPanel.setLayout(new FlowLayout());
        queryOptionsPanel.add(tableComboBox);
        queryOptionsPanel.add(operationsComboBox);
        queryOptionsPanel.add(columnListScroll);

        executeButton = new JButton("Donut");
        queryOptionsPanel.add(executeButton);

        //Creare tabel de input pt inserturi
        JPanel topAndInsert = new JPanel();
        topAndInsert.setSize(new Dimension(mainFrame.getWidth(), mainFrame.getHeight()/2));
        topAndInsert.setLayout(new GridLayout(2,1));

        insertTable = new JTable();
        insertTableScroll = new JScrollPane(insertTable);
        insertTableScroll.setPreferredSize(new Dimension(mainFrame.getWidth(), insertTable.getRowHeight()*5));
        insertTableScroll.setVisible(false);
        insertTable.setOpaque(false);

        topAndInsert.add(queryOptionsPanel);
        topAndInsert.add(insertTableScroll);
        topAndInsert.setOpaque(false);

        //Initializare tabel si scrollpane goale si ascunse
        JPanel tableAndLogButton = new JPanel();
        tableAndLogButton.setSize(mainFrame.getWidth(), mainFrame.getHeight()/2);
        tableAndLogButton.setLayout(new BorderLayout());

        selectTable = new JTable();
        tableScroll = new JScrollPane(selectTable);
        tableScroll.setVisible(false);

        //facem tabelul de select transparent
        /*selectTable.setOpaque(false);
        ((DefaultTableCellRenderer)selectTable.getDefaultRenderer(Object.class)).setOpaque(false);
        tableScroll.setOpaque(false);
        tableScroll.getViewport().setOpaque(false);*/

        logOutButton = new JButton("LogOut");
        tableAndLogButton.add(tableScroll, BorderLayout.NORTH);
        tableAndLogButton.add(logOutButton, BorderLayout.SOUTH);
        tableAndLogButton.setOpaque(false);

        //Adding everything to mainFrame
        //mainFrame.add(queryOptionsPanel, BorderLayout.NORTH);
        //mainFrame.add(insertTableScroll, BorderLayout.CENTER);
        //@@@@@@@@@@@@@@@@@@aici MODIFICATTT addTWO
        mainFrame.addTwo(topAndInsert, BorderLayout.NORTH);
        mainFrame.addTwo(tableAndLogButton, BorderLayout.SOUTH);

        //Wrapping everything up
        //mainFrame.getContentPane().setBackground(Color.darkGray);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}
