package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBmanager {
	private static DBmanager uniqueInstance = null;
	private String userName, password;
	private static Connection con = null;

	private DBmanager(String naam, String user, String pass) {
		userName = user;
		password = pass;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			if (!dbExists(naam)) {
				connect();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static synchronized DBmanager getInstance(String naam, String user,
			String pass) {
		if (uniqueInstance == null) {
			uniqueInstance = new DBmanager(naam, user, pass);
		}
		return uniqueInstance;
	}

	public Boolean dbExists(String naam) {
		Boolean exists = true;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/" + naam,
					userName, password);
		} catch (SQLException e) {
			exists = false;
		}
		return (exists);
	}

	public Boolean connect() {
		Boolean connected = true;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/",
					userName, password);
		} catch (SQLException e) {
			System.out.println("DBmanager|connect: " + e);
			connected = false;
		}
		return (connected);
	}

    public void close() {
		try {
			con.close();
			uniqueInstance = null;
			con = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    public Connection getConnection() {
		return con;
	}

	public void printSQLException(SQLException se) {
		while (se != null) {

			System.out.print("SQLException: State:   " + se.getSQLState());
			System.out.println(" Severity: " + se.getErrorCode());
			System.out.println(se.getMessage());

			se = se.getNextException();
		}
	}
}