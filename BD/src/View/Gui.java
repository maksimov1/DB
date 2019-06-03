package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

@SuppressWarnings("Duplicates")
public class Gui {
    private DataBaseHandler handler;
    private Connection connection;

    private JFrame mainframe;
    private JPanel leftpanel;
    private JPanel rightpanel;

    private JPanel tablePanel;
    private JPanel insertPane;
    private JPanel updatePane;
    private JPanel perfectInputPane;
    private JPanel perfectOutputPane;

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

    private JButton patb, docb, diagnb, notb, insurb, hospb, queriesb;
    private boolean updated = false;
    private String hospital_name;

    private int butsize;
    public  Gui(DataBaseHandler _handler){
        handler = _handler;
        connection = handler.getConnection();
        initPanels();
        mainframe.setVisible(true);
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
        configurePerfectInputTab();
        configurePerfectOutputTab();

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

        patientsPanel = configureTablePanel("patients");
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
        insertPane = new JPanel();
        updatePane = new JPanel();
        perfectInputPane = new JPanel();
        perfectOutputPane = new JPanel();
        tabbedPane.addTab("Table", tablePanel);
        tabbedPane.addTab("Insert", insertPane);
        tabbedPane.addTab("Update", updatePane);
        tabbedPane.addTab("Perfect Input", perfectInputPane);
        tabbedPane.addTab("Perfect Output", perfectOutputPane);
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

    class DemoModelItem {
        public String objectName;
        public DemoModelItem(String objectName){
            this.objectName = objectName;
        }

        public String toString(){
            return objectName;
        }
    }

    private DefaultComboBoxModel buildComboBoxModel() {
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel();
        String SQL = "SELECT namehospital from hospitals";
        try {
            PreparedStatement ps = connection.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                comboBoxModel.addElement(new DemoModelItem(rs.getString("namehospital")));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comboBoxModel;
    }

    private int getHospitalIdByName (String name) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT idhospital FROM hospitals WHERE namehospital = ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int id = rs.getInt("idhospital");
            ps.close();
            return id;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void openAddHospitalDialog() {
        JDialog addHospitalDialog = new JDialog();
        addHospitalDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addHospitalDialog.setTitle("Add New Hospital");
        addHospitalDialog.setModal(true);
        addHospitalDialog.setPreferredSize(new Dimension(500, 200));

        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BorderLayout());
        JPanel inputPane = new JPanel();
        inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.X_AXIS));

        JLabel nameHospitalLabel = new JLabel("Hospital Name:");
        JTextField nameHospitalField = new JTextField();
        nameHospitalField.setPreferredSize(new Dimension(200, 30));
        nameHospitalField.setMaximumSize(new Dimension(200, 30));

        inputPane.add(nameHospitalLabel);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(nameHospitalField);

        JButton insertButton = new JButton("Add");

        insertButton.addActionListener(e -> {
            try {
                String sql = "INSERT INTO hospitals (idhospital, namehospital) VALUES (nextval('hospitalsSQ'), ?)";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, nameHospitalField.getText());
                ps.executeUpdate();
                updated = true;
                ps.close();
                hospital_name = nameHospitalField.getText();
                addHospitalDialog.dispose();
            }
            catch (SQLException ex) {
                JOptionPane.showMessageDialog(mainframe, ex.getMessage(),
                        "SQL error", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        inputPane.add(insertButton);

        mainPane.add(inputPane, BorderLayout.CENTER);
        addHospitalDialog.add(mainPane);
        addHospitalDialog.pack();
        addHospitalDialog.setLocationRelativeTo(mainframe);
        addHospitalDialog.setVisible(true);
    }

    private void configurePerfectInputTab() {
        JPanel inputPane = new JPanel();
        JLabel nameLabel = new JLabel("Patient's Name:");
        JTextField nameField = new JTextField("",3);
        JLabel ageLabel = new JLabel("Patient's Age:");
        JTextField ageField = new JTextField("", 3);
        JLabel hospitalLabel = new JLabel("Select Hospital:");
        JComboBox caseBox = new JComboBox(buildComboBoxModel());
        caseBox.setPreferredSize(new Dimension(200, 30));
        ((JLabel) caseBox.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        JButton insertButton = new JButton("Create");

        nameField.addActionListener(e -> {
            if (!nameField.getText().matches("^[ A-Za-z]+$"))
                nameField.setText("");
        });

        ageField.addActionListener(e -> {
            try {
                Integer.valueOf(ageField.getText());
            }
            catch (NumberFormatException ex) {
                ageField.setText("");
            }
        });

        insertButton.addActionListener(e -> {
            int age;
            try {
                if (!nameField.getText().matches("^[ A-Za-z]+$")) {
                    JOptionPane.showMessageDialog(mainframe, "Incorrect Name",
                            "Input Error", JOptionPane.INFORMATION_MESSAGE);
                    nameField.setText("");
                }
                else {
                    age = Integer.valueOf(ageField.getText());
                    if (age < 18) {
                        JOptionPane.showMessageDialog(mainframe, "Incorrect Age",
                                "Input Error", JOptionPane.INFORMATION_MESSAGE);
                        ageField.setText("");

                    }
                    else {
                        if (caseBox.getSelectedItem() != null)
                        {
                            String first = "INSERT INTO patients (idpatient, namepatient, age, insuranceid) VALUES (nextval('patientsSQ'), ?, ?, ?);";
                            String second = "INSERT INTO pathosp (idpatient, idhospital) VALUES (currval('patientsSQ'), ?);";
                            connection.setAutoCommit(false);
                            PreparedStatement ps1 = connection.prepareStatement(first);
                            PreparedStatement ps2 = connection.prepareStatement(second);
                            ps1.setString(1, nameField.getText());
                            ps1.setInt(2, age);
                            ps1.setInt(3, 1);
                            ps1.executeUpdate();
                            ps2.setInt(1, getHospitalIdByName(caseBox.getSelectedItem().toString()));
                            ps2.executeUpdate();
                            connection.commit();
                            ps1.close();
                            ps2.close();
                        }
                    }
                }
            }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainframe, "Incorrect Age",
                        "Input Error", JOptionPane.INFORMATION_MESSAGE);
                ageField.setText("");
            }
            catch (NullPointerException eex) {
                JOptionPane.showMessageDialog(mainframe, "You Should Select Hospital ID",
                        "Input Error", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (SQLException ex) {
                JOptionPane.showMessageDialog(mainframe, ex.getMessage(),
                        "SQL error", JOptionPane.INFORMATION_MESSAGE);
                try {
                    connection.rollback();
                } catch (SQLException exc) {
                    exc.printStackTrace();
                    System.out.println("Rollback Error");
                }
            }
            finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println("Setting AutoCommit Error");
                }
            }
        });

        caseBox.setSelectedItem(null);
        inputPane.add(nameLabel);
        inputPane.add(nameField);
        inputPane.add(ageLabel);
        inputPane.add(ageField);
        inputPane.add(hospitalLabel);
        inputPane.add(caseBox);
        inputPane.add(insertButton);

        JPanel addCasePane = new JPanel();
        JButton addCaseButton = new JButton("Add Hospital");
        addCasePane.add(Box.createRigidArea(new Dimension(270, 30)));
        addCasePane.add(addCaseButton);

        addCaseButton.addActionListener(e -> {
            openAddHospitalDialog();
            if (updated) {
                caseBox.addItem(hospital_name);
                updated = false;
            }
        });

        perfectInputPane.setLayout(new GridLayout(10,2));
        perfectInputPane.add(inputPane, BorderLayout.CENTER);
        perfectInputPane.add(addCasePane, BorderLayout.LINE_END);
    }

    private void configurePerfectOutputTab() {
        perfectOutputPane.setLayout(new BorderLayout());
        perfectOutputPane.setPreferredSize(new Dimension(500, 500));
        JPanel inputPane = new JPanel();
        inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.X_AXIS));
        JButton showTableButton = new JButton("Show Table");
        JLabel limitLabel = new JLabel("Limit:");
        JLabel offsetLabel = new JLabel("Offset:");
        JTextField limitField = new JTextField("100");
        JTextField offsetField = new JTextField("0");
        JButton showPagedTableButton = new JButton("Show Table with pagination");

        JLabel entityLabel = new JLabel("Entity: ");
        JRadioButton nameRadioButton = new JRadioButton("Name");
        JRadioButton idRadioButton = new JRadioButton("Id");
        ButtonGroup bg = new ButtonGroup();
        bg.add(nameRadioButton);
        bg.add(idRadioButton);
        nameRadioButton.setSelected(true);
        idRadioButton.setSelected(false);

        JLabel nameOrderLabel = new JLabel("Order: ");
        JRadioButton nameOrderRadioButton = new JRadioButton("A-Z");
        JRadioButton descNameOrderRadioButton = new JRadioButton("Z-A");
        ButtonGroup name_bg = new ButtonGroup();
        name_bg.add(nameOrderRadioButton);
        name_bg.add(descNameOrderRadioButton);
        nameOrderRadioButton.setSelected(true);
        descNameOrderRadioButton.setSelected(false);

        JLabel idOrderLabel = new JLabel("Order: ");
        JRadioButton idOrderRadioButton = new JRadioButton("Low-High");
        JRadioButton descIdOrderRadioButton = new JRadioButton("High-Low");
        ButtonGroup age_bg = new ButtonGroup();
        age_bg.add(idOrderRadioButton);
        age_bg.add(descIdOrderRadioButton);
        idOrderRadioButton.setSelected(true);
        descIdOrderRadioButton.setSelected(false);

        JLabel filterLabel = new JLabel("Filter: ");
        JTextField filterField = new JTextField("%", 5);
        filterField.setHorizontalAlignment(JTextField.CENTER);

        JPanel sfPane = new JPanel();
        sfPane.add(entityLabel);
        sfPane.add(nameRadioButton);
        sfPane.add(idRadioButton);
        sfPane.add(nameOrderLabel);
        sfPane.add(nameOrderRadioButton);
        sfPane.add(descNameOrderRadioButton);
        sfPane.add(idOrderLabel);
        sfPane.add(idOrderRadioButton);
        sfPane.add(descIdOrderRadioButton);
        sfPane.add(filterLabel);
        sfPane.add(filterField);

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
            String filter = filterField.getText();
            String sort;
            if (nameRadioButton.isSelected()) {
                filter = "WHERE namehospital LIKE '" + filter + "' ";
                if (nameOrderRadioButton.isSelected())
                    sort = "ORDER BY namehospital ";
                else sort = "ORDER BY namehospital DESC ";
            }
            else {
                filter = "";
                if (idOrderRadioButton.isSelected())
                    sort = "ORDER BY idhospital ";
                else sort = "ORDER BY idhospital DESC ";
            }
            //System.out.println("clients " + filter + sort);
            JTable table = new JTable(handler.getSelectTable("hospitals " + filter + sort));
            if (perfectOutputPane.getComponentCount() != 2)
                perfectOutputPane.remove(scrollPane[0]);
            scrollPane[0] = new JScrollPane(table);
            perfectOutputPane.add(scrollPane[0], BorderLayout.CENTER);
            perfectOutputPane.revalidate();

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
            String filter = filterField.getText();
            String sort;
            if (nameRadioButton.isSelected()) {
                filter = "WHERE namehospital LIKE '" + filter + "' ";
                if (nameOrderRadioButton.isSelected())
                    sort = "ORDER BY namehospital ";
                else sort = "ORDER BY namehospital DESC ";
            }
            else {
                filter = "";
                if (idOrderRadioButton.isSelected())
                    sort = "ORDER BY idhospital ";
                else sort = "ORDER BY idhospital DESC ";
            }
            JTable table = new JTable(handler.getSelectTable("hospitals " + filter
                    + sort + "LIMIT " +  limit + " OFFSET " + offset));
            if (perfectOutputPane.getComponentCount() != 2)
                perfectOutputPane.remove(scrollPane[0]);
            scrollPane[0] = new JScrollPane(table);
            perfectOutputPane.add(scrollPane[0], BorderLayout.CENTER);
            perfectOutputPane.revalidate();

        });
        perfectOutputPane.add(inputPane, BorderLayout.PAGE_START);
        perfectOutputPane.add(sfPane, BorderLayout.AFTER_LAST_LINE);
    }

    /*private void configureInsertTab() {
        JPanel inputPane = new JPanel();
        JButton firmsButton = new JButton("Firms");
        JButton lawyersButton = new JButton("Lawyers");
        JButton casesButton = new JButton("Cases");
        JButton clientsButton = new JButton("Clients");
        JButton clients_casesButton = new JButton("Clients-Cases");
        JButton bankAccountsButton = new JButton("Bank Accounts");
        JButton transactionsButton = new JButton("Transactions");
        JButton batchInsertTransactionsButton = new JButton("Batch Insert Transactions");

        firmsButton.addActionListener(e -> openInsertDialog("firms"));
        lawyersButton.addActionListener(e -> openInsertDialog("lawyers"));
        casesButton.addActionListener(e -> openInsertDialog("cases"));
        clientsButton.addActionListener(e -> openInsertDialog("clients"));
        clients_casesButton.addActionListener(e -> openInsertDialog("clients_cases"));
        bankAccountsButton.addActionListener(e -> openInsertDialog("bank_accounts"));
        transactionsButton.addActionListener(e -> openInsertDialog("transactions"));
        batchInsertTransactionsButton.addActionListener(e -> transactionsBatchInsert());

        inputPane.add(firmsButton);
        inputPane.add(lawyersButton);
        inputPane.add(casesButton);
        inputPane.add(clientsButton);
        inputPane.add(clients_casesButton);
        inputPane.add(bankAccountsButton);
        inputPane.add(transactionsButton);
        inputPane.add(batchInsertTransactionsButton);

        insertPane.add(Box.createRigidArea(new Dimension(100, 150)));
        insertPane.add(inputPane);
    }

    private void configureUpdateTab() {
        JPanel inputPane = new JPanel();
        JButton firmsButton = new JButton("Firms");
        JButton lawyersButton = new JButton("Lawyers");
        JButton casesButton = new JButton("Cases");
        JButton clientsButton = new JButton("Clients");
        JButton bankAccountsButton = new JButton("Bank Accounts");
        JButton transactionsButton = new JButton("Transactions");
        JButton batchDeleteTransactionsButton = new JButton("Batch Delete Transactions");

        firmsButton.addActionListener(e -> openUpdateDialog("firms", "firm_id", "firm_profit"));
        lawyersButton.addActionListener(e -> openUpdateDialog("lawyers", "lawyer_id", "lawyer_age"));
        casesButton.addActionListener(e -> openUpdateDialog("cases", "case_id", "case_cost"));
        clientsButton.addActionListener(e -> openUpdateDialog("clients", "client_id", "client_age"));
        bankAccountsButton.addActionListener(e -> openUpdateDialog("bank_accounts", "bank_account_id", "bank_account_balance"));
        transactionsButton.addActionListener(e -> openUpdateDialog("transactions", "transaction_id", "amount"));
        batchDeleteTransactionsButton.addActionListener(e -> transactionsBatchDelete());

        inputPane.add(firmsButton);
        inputPane.add(lawyersButton);
        inputPane.add(casesButton);
        inputPane.add(clientsButton);
        inputPane.add(bankAccountsButton);
        inputPane.add(transactionsButton);
        inputPane.add(batchDeleteTransactionsButton);

        updatePane.add(Box.createRigidArea(new Dimension(100, 150)));
        updatePane.add(inputPane);
    }*/

}
