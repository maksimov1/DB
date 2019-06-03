package View;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

@SuppressWarnings("Duplicates")
public class DataBaseHandler {
    private static DataBaseHandler instance;
    private Connection connection;
    private DataBaseHandler() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql:mydb",
                            "pancake", "123");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");

    }

    public DefaultTableModel getQueryTable(String query) {
        Vector<Object> columnNames = new Vector<>();
        Vector<Object> data = new Vector<>();

        try {
            //  Read data from a table

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            //  Get column names

            for (int i = 1; i <= columns; i++) {
                columnNames.addElement( md.getColumnName(i) );
            }

            //  Get row data

            while (rs.next()) {
                Vector<Object> row = new Vector<>(columns);

                for (int i = 1; i <= columns; i++) {
                    row.addElement( rs.getObject(i) );
                }
                data.addElement(row);
            }

            rs.close();
            stmt.close();
        }
        catch(Exception e) {
            //System.out.println(e);
            e.printStackTrace();
        }

        //  Create table with database data

        return new DefaultTableModel(data, columnNames)
        {
            @Override
            public Class getColumnClass(int column) {
                for (int row = 0; row < getRowCount(); row++) {
                    Object o = getValueAt(row, column);

                    if (o != null) {
                        return o.getClass();
                    }
                }
                return Object.class;
            }
        };
    }

    public DefaultTableModel getSelectTable(String table_name) {
        Vector<Object> columnNames = new Vector<>();
        Vector<Object> data = new Vector<>();

        try {
            //  Read data from a table

            String sql = "Select * from " + table_name;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery( sql );
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            //  Get column names

            for (int i = 1; i <= columns; i++) {
                columnNames.addElement( md.getColumnName(i) );
            }

            //  Get row data

            while (rs.next()) {
                Vector<Object> row = new Vector<>(columns);

                for (int i = 1; i <= columns; i++) {
                    row.addElement( rs.getObject(i) );
                }
                data.addElement(row);
            }

            rs.close();
            stmt.close();
        }
        catch(Exception e) {
            //System.out.println(e);
            e.printStackTrace();
        }

        //  Create table with database data

        return new DefaultTableModel(data, columnNames)
        {
            @Override
            public Class getColumnClass(int column) {
                for (int row = 0; row < getRowCount(); row++) {
                    Object o = getValueAt(row, column);

                    if (o != null) {
                        return o.getClass();
                    }
                }
                return Object.class;
            }
        };
    }

    public void closeConnection() throws SQLException {
        if (instance != null){
            connection.close();
        }
    }

    public void printAllNotes() throws SQLException {
        Statement stmt = null;
        stmt = connection.createStatement();
        String query = "SELECT idnote, namepatient, age, namedoctor, degree, namehospital, namediagnosis, text FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM notes LEFT JOIN hospitals ON notes.idhospital = hospitals.idhospital)\n" +
                "AS notesh LEFT JOIN patiens ON notesh.idpatient = patiens.idpatient)\n" +
                "AS noteshp LEFT JOIN doctors ON noteshp.iddoctor = doctors.iddoctor)\n" +
                "AS noteshpd LEFT JOIN diagnosis on noteshpd.iddiagnosis=diagnosis.iddiagnosis ORDER BY idnote;";
        ResultSet rs = stmt.executeQuery(query);
//        String sql = "CREATE TABLE COMPANY " +
//                "(ID INT PRIMARY KEY     NOT NULL," +
//                " NAME           TEXT    NOT NULL, " +
//                " AGE            INT     NOT NULL, " +
//                " ADDRESS        CHAR(50), " +
//                " SALARY         REAL)";
//        stmt.executeUpdate(sql);
        while (rs.next()){
            StringBuilder line = new StringBuilder();
            line.append(rs.getString("idnote")).append(" ");
            line.append(rs.getString("namepatient")).append(" ");
            line.append(rs.getString("age")).append(" ");
            line.append(rs.getString("namedoctor")).append(" ");
            line.append(rs.getString("degree")).append(" ");
            line.append(rs.getString("namehospital")).append(" ");
            line.append(rs.getString("namediagnosis")).append(" ");
            line.append(rs.getString("text")).append(" ");

            System.out.println(line);
        }
        stmt.close();

    }

    public void printNotesForPatient(Integer patientid) throws SQLException {
        Statement stmt = null;
        stmt = connection.createStatement();
        String query =
                "SELECT idnote, namepatient, age, namedoctor, degree, namehospital, namediagnosis, text FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM notes LEFT JOIN hospitals ON notes.idhospital = hospitals.idhospital)\n" +
                "AS notesh LEFT JOIN patiens ON notesh.idpatient = patiens.idpatient WHERE patiens.idpatient = " + patientid.toString() + ")\n" +
                "AS noteshp LEFT JOIN doctors ON noteshp.iddoctor = doctors.iddoctor)\n" +
                "AS noteshpd LEFT JOIN diagnosis on noteshpd.iddiagnosis=diagnosis.iddiagnosis ORDER BY idnote;";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()){
            StringBuilder line = new StringBuilder();
            line.append(rs.getString("idnote")).append(" ");
            line.append(rs.getString("namepatient")).append(" ");
            line.append(rs.getString("age")).append(" ");
            line.append(rs.getString("namedoctor")).append(" ");
            line.append(rs.getString("degree")).append(" ");
            line.append(rs.getString("namehospital")).append(" ");
            line.append(rs.getString("namediagnosis")).append(" ");
            line.append(rs.getString("text")).append(" ");

            System.out.println(line);
        }
        stmt.close();

    }

    public void printNotesForDoctor(Integer iddoctor) throws SQLException {
        Statement stmt = null;
        stmt = connection.createStatement();
        String query = "SELECT idnote, namepatient, age, namedoctor, degree, namehospital, namediagnosis, text FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM notes LEFT JOIN hospitals ON notes.idhospital = hospitals.idhospital)\n" +
                "AS notesh LEFT JOIN patiens ON notesh.idpatient = patiens.idpatient)\n" +
                "AS noteshp LEFT JOIN doctors ON noteshp.iddoctor = doctors.iddoctor WHERE noteshp.iddoctor = " + iddoctor.toString() +")\n" +
                "AS noteshpd LEFT JOIN diagnosis on noteshpd.iddiagnosis=diagnosis.iddiagnosis ORDER BY idnote;";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()){
            StringBuilder line = new StringBuilder();
            line.append(rs.getString("idnote")).append(" ");
            line.append(rs.getString("namepatient")).append(" ");
            line.append(rs.getString("age")).append(" ");
            line.append(rs.getString("namedoctor")).append(" ");
            line.append(rs.getString("degree")).append(" ");
            line.append(rs.getString("namehospital")).append(" ");
            line.append(rs.getString("namediagnosis")).append(" ");
            line.append(rs.getString("text")).append(" ");

            System.out.println(line);
        }
        stmt.close();

    }

    public void printNotesForHospital(Integer idhospital) throws SQLException {
        Statement stmt = null;
        stmt = connection.createStatement();
        String query = "SELECT idnote, namepatient, age, namedoctor, degree, namehospital, namediagnosis, text FROM (SELECT * FROM (SELECT * FROM " +
                "(SELECT * FROM notes LEFT JOIN hospitals ON notes.idhospital = hospitals.idhospital WHERE notes.idhospital = "+ idhospital.toString() + ")\n" +
                "AS notesh LEFT JOIN patiens ON notesh.idpatient = patiens.idpatient)\n" +
                "AS noteshp LEFT JOIN doctors ON noteshp.iddoctor = doctors.iddoctor)\n" +
                "AS noteshpd LEFT JOIN diagnosis on noteshpd.iddiagnosis=diagnosis.iddiagnosis ORDER BY idnote;";
        ResultSet rs = stmt.executeQuery(query);
//        String sql = "CREATE TABLE COMPANY " +
//                "(ID INT PRIMARY KEY     NOT NULL," +
//                " NAME           TEXT    NOT NULL, " +
//                " AGE            INT     NOT NULL, " +
//                " ADDRESS        CHAR(50), " +
//                " SALARY         REAL)";
//        stmt.executeUpdate(sql);
        while (rs.next()){
            StringBuilder line = new StringBuilder();
            line.append(rs.getString("idnote")).append(" ");
            line.append(rs.getString("namepatient")).append(" ");
            line.append(rs.getString("age")).append(" ");
            line.append(rs.getString("namedoctor")).append(" ");
            line.append(rs.getString("degree")).append(" ");
            line.append(rs.getString("namehospital")).append(" ");
            line.append(rs.getString("namediagnosis")).append(" ");
            line.append(rs.getString("text")).append(" ");

            System.out.println(line);
        }
        stmt.close();

    }

    public void printDiagnosisCount() throws SQLException {
        Statement stmt = null;
        stmt = connection.createStatement();
        String query = "SELECT diagnosis.iddiagnosis, diagnosis.namediagnosis, count(diagnosis.iddiagnosis) as cf " +
                "FROM (notes left join diagnosis on notes.iddiagnosis = diagnosis.iddiagnosis) " +
                "GROUP BY diagnosis.iddiagnosis " +
                "ORDER BY diagnosis.iddiagnosis;";
        ResultSet rs = stmt.executeQuery(query);
//        String sql = "CREATE TABLE COMPANY " +
//                "(ID INT PRIMARY KEY     NOT NULL," +
//                " NAME           TEXT    NOT NULL, " +
//                " AGE            INT     NOT NULL, " +
//                " ADDRESS        CHAR(50), " +
//                " SALARY         REAL)";
//        stmt.executeUpdate(sql);
        while (rs.next()){
            StringBuilder line = new StringBuilder();
            line.append(rs.getString("iddiagnosis")).append(" ");
            line.append(rs.getString("namediagnosis")).append(" ");
            line.append(rs.getString("cf")).append(" ");

            System.out.println(line);
        }
        stmt.close();

    }

    public static DataBaseHandler getInstance() throws SQLException {
        if (instance == null){
            instance = new DataBaseHandler();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

}
