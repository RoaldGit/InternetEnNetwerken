package database;

import java.sql.Connection;
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
			stat.execute("create table User(userID int primary key auto_increment, userName char(50), password char(50) not null)");
			stat.execute("create table Aandelen(aandeelID int primary key auto_increment, aandeelNaam char(50))");
			stat.execute("create table Portefeulle(portoID int primary key auto_increment, userID int not null, foreign key (userID) references User(userID), aandeelID int not null, foreign key (aandeelID) references Aandelen(aandeelID), aantal int not null)");
			stat.execute("create table KoopOrder(koopOrderID int primary key auto_increment, userID int not null, foreign key (userID) references User(userID), aandeelID int not null, foreign key (aandeelID) references Aandelen(aandeelID), aantal int not null)");
			stat.execute("create table VerkoopOrder(verkoopOrderID int primary key auto_increment, userID int not null, foreign key (userID) references User(userID), aandeelID int not null, foreign key (aandeelID) references Aandelen(aandeelID), aantal int not null)");
		} catch (SQLException e) {
			dbManager.printSQLException(e);
		}
	}
}

