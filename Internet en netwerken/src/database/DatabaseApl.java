package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseApl {
	private DBmanager dbManager;
	private Connection connection;
	private String dbNaam;

	public DatabaseApl(DBmanager manager, String naam) {
		dbManager = manager;
		dbNaam = naam;
		connection = dbManager.getConnection();

		if (manager.dbExists(naam))
			removeDatabase();


		createDatabase();
		createTables();
		createUsers();
		createAandelen();
		createPorto();
		createBuy();
		createSell();
	}

	public void removeDatabase() {
		try {
			Statement stat = connection.createStatement();
			stat.execute("drop database " + dbNaam);
		} catch (SQLException e) {
			dbManager.printSQLException(e);
		}
	}
	public void createDatabase() {
		try {
			Statement stat = connection.createStatement();
			stat.execute("create database " + dbNaam);
			stat.execute("use " + dbNaam);
		} catch (SQLException e) {
			dbManager.printSQLException(e);
		}
	}
	
	public void createTables() {
		try {
			Statement stat = connection.createStatement();
			stat.execute("create table User(userID int primary key auto_increment, userName char(50), password char(50) not null, saldo double default 0)");
			stat.execute("create table Aandelen(aandeelID int primary key auto_increment, aandeelNaam char(50) not null, prijs double default 5)");
			stat.execute("create table Portefeuille(portoID int primary key auto_increment, userID int not null, foreign key (userID) references User(userID), aandeelID int not null, foreign key (aandeelID) references Aandelen(aandeelID), aantal int not null)");
			stat.execute("create table KoopOrder(koopOrderID int primary key auto_increment, userID int not null, foreign key (userID) references User(userID), aandeelID int not null, foreign key (aandeelID) references Aandelen(aandeelID), aantal int not null)");
			stat.execute("create table VerkoopOrder(verkoopOrderID int primary key auto_increment, userID int not null, foreign key (userID) references User(userID), aandeelID int not null, foreign key (aandeelID) references Aandelen(aandeelID), aantal int not null)");
		} catch (SQLException e) {
			dbManager.printSQLException(e);
		}
	}

	public void createUsers() {
		Object[][] users = new Object[][] { { "a", "b", 100 }, { "c", "d", 0 },
				{ "Roald", "Roald", 1000 }, { "Stef", "Stef", 1000 },
				{ "Syntaxis", "Syntaxis", 10000 }, { "LiNK", "LiNK", 10000 },
				{ "Watt", "Watt", 10000 } };

		try {
			PreparedStatement pst = connection
					.prepareStatement("insert into User(userName,password, saldo) values(?,?,?)");

			for (int i = 0; i < users.length; i++) {
				pst.setObject(1, users[i][0]);
				pst.setObject(2, users[i][1]);
				pst.setObject(3, users[i][2]);

				pst.execute();
			}
		} catch (SQLException e) {
			dbManager.printSQLException(e);
		}
	}
	
	public void createAandelen() {
		String[] aandelen = new String[] { "Syntaxis", "LiNK", "Watt" };

		try {
			PreparedStatement pst = connection
					.prepareStatement("insert into Aandelen(aandeelNaam) values(?)");

			for (int i = 0; i < aandelen.length; i++) {
				pst.setObject(1, aandelen[i]);

				pst.execute();
			}
		} catch (SQLException e) {
			dbManager.printSQLException(e);
		}
	}

	public void createPorto() {
		Object[][] porto = new Object[][] { { 5, 1, 1000 }, { 6, 2, 1000 },
				{ 7, 3, 1000 } };

		try {
			PreparedStatement pst = connection
					.prepareStatement("insert into Portefeuille(userID, aandeelID, aantal) values(?,?,?)");

			for (int i = 0; i < porto.length; i++) {
				pst.setObject(1, porto[i][0]);
				pst.setObject(2, porto[i][1]);
				pst.setObject(3, porto[i][2]);

				pst.execute();
			}
		} catch (SQLException e) {
			dbManager.printSQLException(e);
		}
	}

	private void createBuy() {
		Object[][] buy = new Object[][] { { 5, 2, 10 }, { 6, 3, 100 },
				{ 7, 1, 25 } };

		try {
			PreparedStatement pst = connection
					.prepareStatement("insert into kooporder(userID, aandeelID, aantal) values(?,?,?)");

			for (int i = 0; i < buy.length; i++) {
				pst.setObject(1, buy[i][0]);
				pst.setObject(2, buy[i][1]);
				pst.setObject(3, buy[i][2]);

				pst.execute();
			}
		} catch (SQLException e) {
			dbManager.printSQLException(e);
		}
	}

	private void createSell() {
		Object[][] sell = new Object[][] { { 5, 1, 8 }, { 6, 2, 5 },
				{ 7, 3, 20 } };

		try {
			PreparedStatement pst = connection
					.prepareStatement("insert into verkooporder(userID, aandeelID, aantal) values(?,?,?)");

			for (int i = 0; i < sell.length; i++) {
				pst.setObject(1, sell[i][0]);
				pst.setObject(2, sell[i][1]);
				pst.setObject(3, sell[i][2]);

				pst.execute();
			}
		} catch (SQLException e) {
			dbManager.printSQLException(e);
		}
	}
}

