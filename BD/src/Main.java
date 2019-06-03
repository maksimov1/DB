import View.DataBaseHandler;
import View.Gui;

import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {
        DataBaseHandler handler = DataBaseHandler.getInstance();
        //handler.printAllNotes();
        Gui gui = new Gui(handler);
        /*gui.changeTable(handler.getQueryTable("SELECT idnote, namepatient, age, namedoctor, degree, namehospital, namediagnosis, text FROM (SELECT * FROM (SELECT * FROM (SELECT * FROM notes LEFT JOIN hospitals ON notes.idhospital = hospitals.idhospital)\n" +
                "AS notesh LEFT JOIN patiens ON notesh.idpatient = patiens.idpatient)\n" +
                "AS noteshp LEFT JOIN doctors ON noteshp.iddoctor = doctors.iddoctor)\n" +
                "AS noteshpd LEFT JOIN diagnosis on noteshpd.iddiagnosis=diagnosis.iddiagnosis ORDER BY idnote;"));*/

    }
}
