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

	public double retreivePortoWaarde(String user) {
		double waarde = 0;
		try {
			PreparedStatement pst = connection
					.prepareStatement("select aantal*prijs from portefeuille natural join aandelen natural join	user where username = ?");

			pst.setString(1, user);

			ResultSet result = pst.executeQuery();

			if (result.next())
				waarde = result.getDouble("aantal*prijs");
		} catch (SQLException e) {
			System.out.println("DBManager|retreivePortoWaarde");
			printSQLException(e);
		}
		return waarde;
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

	public boolean buyAandeel(String userName, String aandeel, String aantal) {
		int userID = getUserID(userName);
		int aandeelID = getAandeelID(aandeel);
		int buyAantal = Integer.parseInt(aantal);
		
		double prijs = getAandeelPrijs(aandeelID);
		double saldo = retreiveSaldo(userName);
		double newSaldo = saldo - buyAantal * prijs;

		boolean portoExists = checkPorto(userID, aandeelID);
		boolean succes = false;

		if (newSaldo > 0) {
			try {
				PreparedStatement pst = null;
				if (portoExists) {
					int oldAantal = getAantalAandelen(userID, aandeelID);
					int newAantal = oldAantal + buyAantal;

					pst = connection
							.prepareStatement("update portefeuille set aantal = ? where userID = ? and aandeelID = ?");
					pst.setInt(1, newAantal);
					pst.setInt(2, userID);
					pst.setInt(3, aandeelID);
				} else {
					pst = connection
							.prepareStatement("insert into portefeuille(userID, aandeelID, aantal) values (?, ?, ?)");
					pst.setInt(1, userID);
					pst.setInt(2, aandeelID);
					pst.setString(3, aantal);
				}
				pst.execute();

				pst = connection
						.prepareStatement("update user set saldo = ? where userID = ?");
				pst.setDouble(1, newSaldo);
				pst.setInt(2, userID);

				pst.execute();

				succes = true;
			} catch (SQLException e) {
				System.out.println("DBManager|retreiveBuy");
				printSQLException(e);
			}
		}
		return succes;
	}

	public boolean sellAandeel(String userName, String aandeel, String aantal) {
		int userID = getUserID(userName);
		int aandeelID = getAandeelID(aandeel);
		int sellAantal = Integer.parseInt(aantal);

		double prijs = getAandeelPrijs(aandeelID);
		double saldo = retreiveSaldo(userName);
		double newSaldo = saldo + sellAantal * prijs;

		boolean portoExists = checkPorto(userID, aandeelID);
		boolean succes = false;

		try {
			PreparedStatement pst = null;
			if (portoExists) {
				int oldAantal = getAantalAandelen(userID, aandeelID);
				int newAantal = oldAantal - sellAantal;
				if (newAantal >= 0) {
					pst = connection
							.prepareStatement("update portefeuille set aantal = ? where userID = ? and aandeelID = ?");
					pst.setInt(1, newAantal);
					pst.setInt(2, userID);
					pst.setInt(3, aandeelID);

					pst = connection
							.prepareStatement("update user set saldo = ? where userID = ?");
					pst.setDouble(1, newSaldo);
					pst.setInt(2, userID);

					pst.execute();
					succes = true;
				}
				if (newAantal == 0) {
					pst = connection
							.prepareStatement("delete from portefeuille where userID = ? and aandeelID = ?");
					pst.setInt(1, userID);
					pst.setInt(2, aandeelID);

					pst.execute();
					succes = true;
				}
			}
		} catch (SQLException e) {
			System.out.println("DBManager|retreiveBuy");
			printSQLException(e);
		}
		return succes;
	}

	public boolean checkPorto(int userID, int aandeelID) {
		boolean found = false;
		try {
			PreparedStatement pst = connection
					.prepareStatement("select * from portefeuille where userID = ? and aandeelID = ?");
			pst.setInt(1, userID);
			pst.setInt(2, aandeelID);

			ResultSet result = pst.executeQuery();

			if (result.next())
				found = true;
			// System.out.println(result.getObject(1));

		} catch (SQLException e) {
			System.out.println("DBManager|checkPorto");
			printSQLException(e);
		}
		return found;
	}

	private int getAandeelID(String aandeel) {
		int aandeelID = 0;
		try {
			PreparedStatement pst = connection
					.prepareStatement("select aandeelID from aandelen where aandeelnaam = ?");
			pst.setString(1, aandeel);

			ResultSet result = pst.executeQuery();

			if (result.next())
				aandeelID = result.getInt("aandeelID");

		} catch (SQLException e) {
			System.out.println("DBManager|getAandeelID");
			printSQLException(e);
		}
		return aandeelID;
	}

	private double getAandeelPrijs(int aandeelID) {
		double prijs = 0.0;
		try {
			PreparedStatement pst = connection
					.prepareStatement("select prijs from aandelen where aandeelid = ?");
			pst.setInt(1, aandeelID);

			ResultSet result = pst.executeQuery();

			if (result.next())
				prijs = result.getDouble("prijs");
		} catch (SQLException e) {
			System.out.println("DBManager|getAandeelID");
			printSQLException(e);
		}
		return prijs;
	}

	public int getUserID(String userName) {
		int userID = 0;
		try {
			PreparedStatement pst = connection
					.prepareStatement("select userid from user where username = ?");
			pst.setString(1, userName);

			ResultSet result = pst.executeQuery();

			if (result.next())
				userID = result.getInt("userID");

		} catch (SQLException e) {
			System.out.println("DBManager|getUserID");
			printSQLException(e);
		}
		return userID;
	}

	public int getAantalAandelen(int userID, int aandeelID) {
		int aantal = 0;
		try {
			PreparedStatement pst = connection
					.prepareStatement("select aantal from portefeuille where userid = ? and aandeelID = ?");
			pst.setInt(1, userID);
			pst.setInt(2, aandeelID);

			ResultSet result = pst.executeQuery();

			if (result.next())
				aantal = result.getInt("aantal");

		} catch (SQLException e) {
			System.out.println("DBManager|getUserID");
			printSQLException(e);
		}
		return aantal;
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