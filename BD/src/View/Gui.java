package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

@SuppressWarnings("Duplicates")
public class Gui {
    private DataBaseHandler handler;

    private JFrame mainframe;
    private JPanel leftpanel;
    private JPanel rightpanel;
    private JPanel tablePanel;
    private int framew;
    private int frameh;
    private int leftfw;
    private int rightfw;

    private JPanel buttonpanel;
    private int buttonheight;

    private JPanel inputpanel;
    private int inputheight;

    private JPanel patientsPanel;
    private JPanel doctorsPanel;
    private JPanel diagnosesPanel;
    private JPanel notesPanel;
    private JPanel insurancesPanel;
    private JPanel hospitalsPanel;
    private JPanel queriesPanel;


/*SELECT * from patiens;
SELECT * from doctors;
SELECT * from diagnosis;
SELECT * from notes;
select * from insurances;
select * from hospitals;
select * from docthosp;
select * from pathosp;
select * from patinsur;*/

    private JButton patb, docb, diagnb, notb, insurb, hospb, queriesb;
    private JTable table;
    private int butsize;
    public  Gui(DataBaseHandler _handler){
        handler = _handler;
        initPanels();
        mainframe.setVisible(true);
    }

    public void changeTable(DefaultTableModel _table){
        table.setModel(_table);
        rightpanel.repaint();
    }

    private void initPanels(){

        mainframe = new JFrame();
        mainframe.setLayout(new BorderLayout());
        mainframe.setBackground(Color.WHITE);
        mainframe.setIconImage(Toolkit.getDefaultToolkit().createImage("frog.png"));
        mainframe.setTitle("Health Insurance Support System");
        framew = 1000;//(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()*3/4;
        frameh = 600;//(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()*3/4;
        rightfw = (int) (0.75*framew);
        leftfw = framew - rightfw;
        mainframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainframe.setResizable(false);
        leftpanel = new JPanel();
        leftpanel.setLayout(new BorderLayout());
        leftpanel.setSize(leftfw,frameh);

        rightpanel = new JPanel();
        rightpanel.setSize(leftfw,frameh);
        rightpanel.setBackground(Color.WHITE);
        rightpanel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2)));

        configureTabs();

        configureButtonPanel();

        configureInputPanel();

        leftpanel.add(inputpanel, BorderLayout.SOUTH);
        leftpanel.add(buttonpanel, BorderLayout.NORTH);

        leftpanel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2)));
        buttonpanel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2)));
        inputpanel.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2)));
        rightpanel.setPreferredSize(new Dimension(rightfw, frameh));
        mainframe.add(leftpanel, BorderLayout.WEST);
        mainframe.add(rightpanel, BorderLayout.EAST);

        showPatients();
        //mainframe.setLocationRelativeTo(null);
        mainframe.pack();
    }
    private void configureButtonPanel() {
        buttonpanel = new JPanel();
        buttonheight = (int)(frameh * 0.4);
        buttonpanel.setBackground(Color.WHITE);

        patb = new JButton("Patients");
        docb = new JButton("Doctors");
        diagnb = new JButton("Diagnoses");
        notb = new JButton("Notes");
        insurb = new JButton("Insurances");
        hospb = new JButton("Hospitals");
        queriesb = new JButton("Queries");


        butsize = (int)(buttonheight * 0.11);
        patb.setPreferredSize(new Dimension(leftfw, butsize));
        docb.setPreferredSize(new Dimension(leftfw, butsize));
        diagnb.setPreferredSize(new Dimension(leftfw, butsize));
        notb.setPreferredSize(new Dimension(leftfw, butsize));
        insurb.setPreferredSize(new Dimension(leftfw, butsize));
        hospb.setPreferredSize(new Dimension(leftfw, butsize));
        queriesb.setPreferredSize(new Dimension(leftfw, butsize));
        buttonpanel.setLayout(new FlowLayout());

        buttonpanel.add(patb);
        buttonpanel.add(docb);
        buttonpanel.add(diagnb);
        buttonpanel.add(notb);
        buttonpanel.add(insurb);
        buttonpanel.add(hospb);
        buttonpanel.add(queriesb);
        buttonpanel.setPreferredSize(new Dimension(leftfw, buttonheight));

        patientsPanel = configureTablePanel("patiens");
        doctorsPanel = configureTablePanel("doctors");
        diagnosesPanel = configureTablePanel("diagnosis");
        notesPanel = configureTablePanel("notes");
        insurancesPanel = configureTablePanel("insurances");
        hospitalsPanel = configureTablePanel("hospitals");
        configureQueriesPanel();

        patb.addActionListener(e -> showPatients());
        docb.addActionListener(e -> showDoctors());
        diagnb.addActionListener(e -> showDiagnoses());
        notb.addActionListener(e -> showNotes());
        insurb.addActionListener(e -> showInsurances());
        hospb.addActionListener(e -> showHospitals());
        queriesb.addActionListener(e -> showQueries());
    }

    private void configureInputPanel() {
        inputpanel = new JPanel();
        inputheight = frameh - buttonheight;
        inputpanel.setPreferredSize(new Dimension(leftfw, inputheight));
        inputpanel.setBackground(Color.WHITE);
    }

    private void showPatients() {
        tablePanel.removeAll();
        tablePanel.add(patientsPanel);
        tablePanel.revalidate();
    }

    private void showDoctors() {
        tablePanel.removeAll();
        tablePanel.add(doctorsPanel);
        tablePanel.repaint();
        tablePanel.revalidate();
    }

    private void showDiagnoses() {
        tablePanel.removeAll();
        tablePanel.add(diagnosesPanel);
        tablePanel.revalidate();
    }

    private void showNotes() {
        tablePanel.removeAll();
        tablePanel.add(notesPanel);
        tablePanel.revalidate();
    }

    private void showInsurances() {
        tablePanel.removeAll();
        tablePanel.add(insurancesPanel);
        tablePanel.revalidate();
    }

    private void showHospitals() {
        tablePanel.removeAll();
        tablePanel.add(hospitalsPanel);
        tablePanel.revalidate();
    }

    private void showQueries() {
        inputpanel.removeAll();
        inputpanel.add(queriesPanel);
        inputpanel.revalidate();
    }

    private JPanel configureTablePanel(String table_name) {
        JPanel commonPanel = new JPanel();
        commonPanel.setLayout(new BorderLayout());
        JPanel inputPane = new JPanel();
        inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.X_AXIS));
        JButton showTableButton = new JButton("Refresh table");
        JLabel limitLabel = new JLabel("Limit:");
        JLabel offsetLabel = new JLabel("Offset:");
        JTextField limitField = new JTextField("100", 5);
        JTextField offsetField = new JTextField("0", 5);
        JButton showPagedTableButton = new JButton("Show Table with pagination");

        inputPane.add(Box.createRigidArea(new Dimension(50, 0)));
        inputPane.add(showTableButton);
        inputPane.add(Box.createRigidArea(new Dimension(100, 0)));
        inputPane.add(limitLabel);
        inputPane.add(limitField);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(offsetLabel);
        inputPane.add(offsetField);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(showPagedTableButton);


        final JScrollPane[] scrollPane = new JScrollPane[1];
        showTableButton.addActionListener(e -> {
            JTable table = new JTable(handler.getSelectTable(table_name));
            if (commonPanel.getComponentCount() != 1)
                commonPanel.remove(scrollPane[0]);
            scrollPane[0] = new JScrollPane(table);
            commonPanel.add(scrollPane[0], BorderLayout.CENTER);
            commonPanel.revalidate();
        });
        showPagedTableButton.addActionListener(e -> {
            int limit, offset;
            try {
                limit = Integer.valueOf(limitField.getText());
                offset = Integer.valueOf(offsetField.getText());

                if (limit < 0 || limit > 10000)
                    limit = 100;
                if (offset < 0 || offset > 10000)
                    offset = 0;
            } catch (NumberFormatException ex) {
                limit = 100;
                offset = 0;
            }
            JTable table = new JTable(handler.getSelectTable(table_name + " LIMIT " +  limit + " OFFSET " + offset));
            if (commonPanel.getComponentCount() != 1)
                commonPanel.remove(scrollPane[0]);
            scrollPane[0] = new JScrollPane(table);
            commonPanel.add(scrollPane[0], BorderLayout.CENTER);
            commonPanel.revalidate();

        });
        commonPanel.add(inputPane, BorderLayout.NORTH);
        showTableButton.doClick();
        return commonPanel;
    }

    private void configureTabs() {
        final JTabbedPane tabbedPane = new JTabbedPane();
        tablePanel = new JPanel();
        //insertPane = new JPanel();
        //updatePane = new JPanel();
        //addClientWithCasePane = new JPanel();
        //sortedFilteredPane = new JPanel();
        tabbedPane.addTab("Table", tablePanel);
        //tabbedPane.addTab("Insert", insertPane);
        //tabbedPane.addTab("Update", updatePane);
        //tabbedPane.addTab("Add Client with Case", addClientWithCasePane);
        //tabbedPane.addTab("S-F", sortedFilteredPane);
        rightpanel.add(tabbedPane);
    }

    private void configureQueriesPanel() {
        queriesPanel = new JPanel();
        queriesPanel.setLayout(new BorderLayout());
        //queriesPanel.setPreferredSize(new Dimension(leftfw, inputheight));
        JPanel verticalPane = new JPanel();
        verticalPane.setLayout(new BoxLayout(verticalPane, BoxLayout.Y_AXIS));

        JPanel firstQueryPane = new JPanel();
        firstQueryPane.setMaximumSize(new Dimension(500, 25));
        firstQueryPane.setLayout(new BoxLayout(firstQueryPane, BoxLayout.X_AXIS));
        firstQueryPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        JLabel lawyersNameLabel = new JLabel("1:");
        JTextField lawyersNameField = new JTextField("1", 3);
        JButton lawyersNameButton = new JButton("Perform");
        firstQueryPane.add(lawyersNameLabel);
        firstQueryPane.add(Box.createRigidArea(new Dimension(10, 0)));
        firstQueryPane.add(lawyersNameField);
        firstQueryPane.add(lawyersNameButton);

        JPanel secondQueryPane = new JPanel();
        secondQueryPane.setLayout(new BoxLayout(secondQueryPane, BoxLayout.X_AXIS));
        secondQueryPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        JLabel secondLabel = new JLabel("2:");
        JButton secondButton = new JButton("Perform");
        secondQueryPane.add(secondLabel);
        secondQueryPane.add(Box.createRigidArea(new Dimension(10, 0)));
        secondQueryPane.add(secondButton);

        JPanel thirdQueryPane = new JPanel();
        thirdQueryPane.setLayout(new BoxLayout(thirdQueryPane, BoxLayout.X_AXIS));
        thirdQueryPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        JLabel thirdLabel = new JLabel("3:");
        JButton thirdButton = new JButton("Perform");
        thirdQueryPane.add(thirdLabel);
        thirdQueryPane.add(Box.createRigidArea(new Dimension(10, 0)));
        thirdQueryPane.add(thirdButton);

        JPanel fourthQueryPane = new JPanel();
        fourthQueryPane.setLayout(new BoxLayout(fourthQueryPane, BoxLayout.X_AXIS));
        fourthQueryPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        JLabel fourthLabel = new JLabel("4:");
        JButton fourthButton = new JButton("Perform");
        fourthQueryPane.add(fourthLabel);
        fourthQueryPane.add(Box.createRigidArea(new Dimension(10, 0)));
        fourthQueryPane.add(fourthButton);

        JPanel fifthQueryPane = new JPanel();
        fifthQueryPane.setLayout(new BoxLayout(fifthQueryPane, BoxLayout.X_AXIS));
        fifthQueryPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        JLabel fifthLabel = new JLabel("5:");
        JButton fifthButton = new JButton("Perform");
        fifthQueryPane.add(fifthLabel);
        fifthQueryPane.add(Box.createRigidArea(new Dimension(10, 0)));
        fifthQueryPane.add(fifthButton);

        JPanel sixthQueryPane = new JPanel();
        sixthQueryPane.setLayout(new BoxLayout(sixthQueryPane, BoxLayout.X_AXIS));
        sixthQueryPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        JLabel sixthLabel = new JLabel("6:");
        JButton sixthButton = new JButton("Perform");
        sixthQueryPane.add(sixthLabel);
        sixthQueryPane.add(Box.createRigidArea(new Dimension(10, 0)));
        sixthQueryPane.add(sixthButton);

        JPanel seventhQueryPane = new JPanel();
        seventhQueryPane.setLayout(new BoxLayout(seventhQueryPane, BoxLayout.X_AXIS));
        seventhQueryPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        JLabel seventhLabel = new JLabel("7:");
        JButton seventhButton = new JButton("Perform");
        seventhQueryPane.add(seventhLabel);
        seventhQueryPane.add(Box.createRigidArea(new Dimension(10, 0)));
        seventhQueryPane.add(seventhButton);

        JPanel eighthQueryPane = new JPanel();
        eighthQueryPane.setLayout(new BoxLayout(eighthQueryPane, BoxLayout.X_AXIS));
        eighthQueryPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        JLabel eighthLabel = new JLabel("8:");
        JButton eighthButton = new JButton("Perform");
        eighthQueryPane.add(eighthLabel);
        eighthQueryPane.add(Box.createRigidArea(new Dimension(10, 0)));
        eighthQueryPane.add(eighthButton);

        JPanel ninethQueryPane = new JPanel();
        ninethQueryPane.setLayout(new BoxLayout(ninethQueryPane, BoxLayout.X_AXIS));
        ninethQueryPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        JLabel ninethLabel = new JLabel("9:");
        JButton ninethButton = new JButton("Perform");
        ninethQueryPane.add(ninethLabel);
        ninethQueryPane.add(Box.createRigidArea(new Dimension(10, 0)));
        ninethQueryPane.add(ninethButton);

        JPanel tenthQueryPane = new JPanel();
        tenthQueryPane.setLayout(new BoxLayout(tenthQueryPane, BoxLayout.X_AXIS));
        tenthQueryPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        JLabel tenthLabel = new JLabel("10:");
        JButton tenthButton = new JButton("Perform");
        tenthQueryPane.add(tenthLabel);
        tenthQueryPane.add(Box.createRigidArea(new Dimension(10, 0)));
        tenthQueryPane.add(tenthButton);

        verticalPane.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPane.add(firstQueryPane, BorderLayout.WEST);
        verticalPane.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPane.add(secondQueryPane, BorderLayout.WEST);
        verticalPane.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPane.add(thirdQueryPane, BorderLayout.WEST);
        verticalPane.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPane.add(fourthQueryPane, BorderLayout.WEST);
        verticalPane.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPane.add(fifthQueryPane, BorderLayout.WEST);
        verticalPane.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPane.add(sixthQueryPane, BorderLayout.WEST);
        verticalPane.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPane.add(seventhQueryPane, BorderLayout.WEST);
        verticalPane.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPane.add(eighthQueryPane, BorderLayout.WEST);
        verticalPane.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPane.add(ninethQueryPane, BorderLayout.WEST);
        verticalPane.add(Box.createRigidArea(new Dimension(0, 10)));
        verticalPane.add(tenthQueryPane, BorderLayout.WEST);
        queriesPanel.add(verticalPane, BorderLayout.WEST);


        /*lawyersNameButton.addActionListener(e -> {
            int id;
            try {
                id = Integer.valueOf(lawyersNameField.getText());
                if (id < 0)
                    id = 1;
            }
            catch (NumberFormatException ex) {
                id = 1;
            }
            openLawyersNameDialog(id);
        });
        secondButton.addActionListener(e -> openQueryDialog(1));
        thirdButton.addActionListener(e -> openQueryDialog(2));
        fourthButton.addActionListener(e -> openQueryDialog(3));
        fifthButton.addActionListener(e -> openQueryDialog(4));
        sixthButton.addActionListener(e -> openQueryDialog(5));
        seventhButton.addActionListener(e -> openQueryDialog(6));
        eighthButton.addActionListener(e -> openQueryDialog(7));
        ninethButton.addActionListener(e -> openQueryDialog(8));
        tenthButton.addActionListener(e -> openQueryDialog(9));
        subQueryButton.addActionListener(e -> openQueryDialog(10));*/
    }

}
