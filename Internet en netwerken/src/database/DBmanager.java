package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

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

			ResultSet result = pst.executeQuery();

			if (result.isBeforeFirst())
				login = true;

		} catch (SQLException e) {
			System.out.println("DBManager|checkLogin");
			printSQLException(e);
		}
		return login;
	}

	public double retreiveSaldo(String user) {
		double saldo = 0;
		try {
			PreparedStatement pst = connection
					.prepareStatement("select saldo from User where userName = ?");

			pst.setString(1, user);

			ResultSet result = pst.executeQuery();

			if (result.next())
				saldo = result.getDouble("saldo");
		} catch (SQLException e) {
			System.out.println("DBManager|retreiveSaldo");
			printSQLException(e);
		}
		return saldo;		
	}

	public String retreiveAandelen() {
		String aandelen = "";

		try {
			PreparedStatement pst = connection
					.prepareStatement("select count(*) from aandelen");
			ResultSet result = pst.executeQuery();

			pst = connection
					.prepareStatement("select aandeelNaam from aandelen");

			result = pst.executeQuery();

			while (result.next())
				aandelen += result.getString("aandeelNaam") + " ";

		} catch (SQLException e) {
			System.out.println("DBManager|retreiveAandelen");
			printSQLException(e);
		}
		return aandelen;
	}

	public Object[][] retreivePorto(String username) {
		Object[][] array = null;
		try {
			PreparedStatement pst = connection
					.prepareStatement("select aandeelnaam, aantal, prijs, aantal*prijs from user natural join aandelen natural join portefeuille where username = ?");
			pst.setString(1, username);

			ResultSet result = pst.executeQuery();

			int size = resultSize("select count(*) from portefeuille natural join user where username = '"
					+ username + "'");

			array = create2DArray(result, size);
		} catch (SQLException e) {
			System.out.println("DBManager|retreivePorto");
			printSQLException(e);
		}
		return array;
	}

	public Object[][] retreiveBuying(String username) {
		Object[][] array = null;
		try {
			PreparedStatement pst = connection
					.prepareStatement("select aandeelnaam, aantal, prijs, aantal*prijs from user natural join aandelen natural join kooporder where username = ?");
			pst.setString(1, username);

			ResultSet result = pst.executeQuery();

			int size = resultSize("select count(*) from kooporder natural join user where username = '"
					+ username + "'");

			array = create2DArray(result, size);
		} catch (SQLException e) {
			System.out.println("DBManager|retreiveBuying");
			printSQLException(e);
		}
		return array;
	}

	public Object[][] retreiveSelling(String username) {
		Object[][] array = null;
		try {
			PreparedStatement pst = connection
					.prepareStatement("select aandeelnaam, aantal, prijs, aantal*prijs from user natural join aandelen natural join verkooporder where username = ?");
			pst.setString(1, username);

			ResultSet result = pst.executeQuery();

			int size = resultSize("select count(*) from verkooporder natural join user where username = '"
					+ username + "'");

			array = create2DArray(result, size);
		} catch (SQLException e) {
			System.out.println("DBManager|retreiveSelling");
			printSQLException(e);
		}
		return array;
	}

	public Object[][] retreiveBuy(String aandeel) {
		Object[][] array = null;
		try {
			PreparedStatement pst = connection
					.prepareStatement("select username, aandeelnaam, aantal, prijs, aantal*prijs from user natural join aandelen natural join kooporder where aandeelnaam = ?");
			pst.setString(1, aandeel);

			ResultSet result = pst.executeQuery();

			int size = resultSize("select count(*) from kooporder natural join aandelen natural join user where aandeelnaam = '"
					+ aandeel + "'");

			array = create2DArray(result, size);
		} catch (SQLException e) {
			System.out.println("DBManager|retreiveBuy");
			printSQLException(e);
		}
		return array;
	}

	public Object[][] retreiveSell(String aandeel) {
		Object[][] array = null;
		try {
			PreparedStatement pst = connection
					.prepareStatement("select username, aandeelnaam, aantal, prijs, aantal*prijs from user natural join aandelen natural join verkooporder where aandeelnaam = ?");
			pst.setString(1, aandeel);

			ResultSet result = pst.executeQuery();

			int size = resultSize("select count(*) from verkooporder natural join aandelen natural join user where aandeelnaam = '"
					+ aandeel + "'");

			array = create2DArray(result, size);
		} catch (SQLException e) {
			System.out.println("DBManager|retreiveBuy");
			printSQLException(e);
		}
		return array;
	}

	public Object[][] create2DArray(ResultSet result, int rows) {
		Object[][] array = null;
		try {
			ResultSetMetaData rsmd = result.getMetaData();
			int cols = rsmd.getColumnCount();

			array = new Object[rows][cols];

			int currentRow = 0;
			int columns = rsmd.getColumnCount();
			while (result.next()) {
				for (int i = 0; i < columns; i++)
					array[currentRow][i] = result.getObject(i + 1);
				currentRow++;
			}
		} catch (SQLException e) {
			printSQLException(e);
			System.out.println("DBManager|create2DArray");
		}
		return array;
	}

	public int resultSize(String query) {
		int size = 0;
		try {
			Statement st = connection.createStatement();
			ResultSet result = st.executeQuery(query);

			result.next();
			size = result.getInt("count(*)");
		} catch (SQLException e) {
			printSQLException(e);
		}
		return size;
	}
}