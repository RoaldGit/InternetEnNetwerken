package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBmanager {
	private static DBmanager uniqueInstance = null;
	private String userName, password;
	private static Connection connection = null;

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
			connection = DriverManager.getConnection("jdbc:mysql://localhost/" + naam,
					userName, password);
		} catch (SQLException e) {
			exists = false;
		}
		return (exists);
	}

	public Boolean connect() {
		Boolean connected = true;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost/",
					userName, password);
		} catch (SQLException e) {
			System.out.println("DBmanager|connect: " + e);
			connected = false;
		}
		return (connected);
	}

    public void close() {
		try {
			connection.close();
			uniqueInstance = null;
			connection = null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    public Connection getConnection() {
		return connection;
	}

	public void printSQLException(SQLException e) {
		while (e != null) {

			System.out.print("SQLException: State:   " + e.getSQLState());
			System.out.println(" Severity: " + e.getErrorCode());
			System.out.println(e.getMessage());

			e = e.getNextException();
		}
	}

	public boolean checkLogin(String user, String pass) {
		boolean login = false;
		try {
			PreparedStatement pst = connection
					.prepareStatement("select * from User where userName = ? and password = ?");

			pst.setString(1, user);
			pst.setString(2, pass);

			ResultSet rs = pst.executeQuery();

			if (rs.isBeforeFirst())
				login = true;

		} catch (SQLException e) {
			printSQLException(e);
		}
		return login;
	}
}