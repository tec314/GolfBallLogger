import java.sql.*;
import javax.swing.*;

public class DataEntryClass {
	public static Connection ConnectDB() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\tec31\\eclipse-workspace\\GolfBallLog\\dataform.db");
			return conn;
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}
