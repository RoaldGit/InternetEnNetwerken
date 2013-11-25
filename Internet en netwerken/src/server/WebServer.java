package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

import database.DBmanager;
import database.DatabaseApl;

/**
 * Deze klasse executeert de server waarmee het programma mee moet verbinden. Het stuurt data heen en weer met de ClientConnection klasse. 
 * @author Roald en Stef
 * @since 9-11-2013
 * @version  0.1
 */
public class WebServer extends Thread {
	private String requestMessageLine;
	private ServerSocket listenSocket;
	private String user = "a", pass = "b";
	private DBmanager dbManager;

	/**
	 * De constructor voor de webserver
	 * @param port De port waar de server op zal runnen
	 * @param naam naam van de database
	 * @param user username van de database
	 * @param pass password van de database
	 */
	public WebServer(int port, String naam, String user, String pass) {
		dbManager = DBmanager.getInstance(naam, user, pass);
		DatabaseApl apl = new DatabaseApl(dbManager, naam);

		try {
			listenSocket = new ServerSocket(port);
		} catch (Exception e) {
			System.out.println("Webserver|Consctructor: " + e);
		}
	}

	/**
	 * 2de constructor zonder een database connectie
	 * @param port port waarop de server zal runnen
	 */
	public WebServer(int port) {
		try {
			listenSocket = new ServerSocket(port);
		} catch (Exception e) {
			System.out.println("Webserver|Consctructor: " + e);
		}
	}

	/**
	 * De methode die continu blijft runnen, waarin de server inkomende connecties accepteert.
	 */
	public void run() {
		while (!listenSocket.isClosed()) {
			try {
				Socket connectionSocket = listenSocket.accept();
				if (!connectionSocket.isClosed())
					serve(connectionSocket);

			} catch (Exception e) {
				System.out.println("Webserver|run: " + e);
			}
		}
	}

	/**
	 * Deze methode ontvangt alle requests van de client connection.
	 * @param connectionSocket De socket van de client die connect met server.
	 */
	public void serve(Socket connectionSocket) {
		BufferedReader inFromClient = null;
		DataOutputStream outToClient = null;

		try {
			inFromClient = new BufferedReader(new InputStreamReader(
					connectionSocket.getInputStream()));

			outToClient = new DataOutputStream(
					connectionSocket.getOutputStream());

			while (!connectionSocket.isClosed()) {
				requestMessageLine = inFromClient.readLine();

				// if (requestMessageLine.contains("VerwijderOrder"))
					System.out.println("Webserver|Received request: "
							+ requestMessageLine);

				StringTokenizer tokenizedLine = new StringTokenizer(
						requestMessageLine);

				if (tokenizedLine.hasMoreTokens()
						&& tokenizedLine.countTokens() >= 1) {
					String firstToken = tokenizedLine.nextToken();
					switch (firstToken) {
					case "Login":
						checkLogin(tokenizedLine, outToClient);
						break;
					case "Porto":
						getPorto(tokenizedLine, outToClient);
						break;
					case "Buy":
						getBuy(tokenizedLine, outToClient);
						break;
					case "Sell":
						getSell(tokenizedLine, outToClient);
						break;
					case "Buying":
						getBuying(tokenizedLine, outToClient);
						break;
					case "Selling":
						getSelling(tokenizedLine, outToClient);
						break;
					case "Stort":
						stortGeld(tokenizedLine, outToClient);
						// TODO database stuff
						break;
					case "Aandelen":
						sendAandeelNamen(outToClient);
						break;
					case "AandeelPrijs":
						sendAandeelPrijs(tokenizedLine, outToClient);
						break;
					case "WijzigOrder":
						wijzigOrder(tokenizedLine, outToClient);
						break;
					case "VerwijderOrder":
						verwijderOrder(tokenizedLine, outToClient);
						break;
					case "AandeelKoop":
						koopAandeel(tokenizedLine, outToClient);
						break;
					case "AandeelVerkoop":
						verkoopAandeel(tokenizedLine, outToClient);
						break;
					case "Saldo":
						sendSaldo(tokenizedLine, outToClient);
						break;
					case "PortoWaarde":
						sendPortoWaarde(tokenizedLine, outToClient);
						break;
					case "Done":
						sendToClient(outToClient, "Ack");
						connectionSocket.close();
						System.out.println("Webserver: Connection closed");
						break;
					default:
						sendToClient(outToClient, "Unknown Error");
						break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Webserver|serve: " + e);
			sendToClient(outToClient, "Error: " + requestMessageLine);
		}
	}

	/**
	 * Een order verwijderen
	 * @param tokenizedLine Dit argument is het overige deel van de request.
	 * @param outToClient Hiermee wordt een response naar een client gestuurd.
	 */
	private void verwijderOrder(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String method = tokenizedLine.nextToken();
		String userName = tokenizedLine.nextToken();
		tokenizedLine.nextToken();
		String aandeel = tokenizedLine.nextToken();
		
		boolean succes = dbManager.verwijderOrder(method, userName, aandeel);
		if (succes)
			sendToClient(outToClient, "VerwijderOrder ok");
		else
			sendToClient(outToClient, "VerwijderOrder error");
	}

	/**
	 * Een order wijzigen
	 * @param tokenizedLine Dit argument is het overige deel van de request.
	 * @param outToClient Hiermee wordt een response naar een client gestuurd.
	 */
	private void wijzigOrder(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String method = tokenizedLine.nextToken();
		String userName = tokenizedLine.nextToken();
		tokenizedLine.nextToken();
		String aandeel = tokenizedLine.nextToken();
		String aantal = tokenizedLine.nextToken();

		boolean succes = dbManager.verAnderOrder(method, userName, aandeel, aantal);
		if (succes)
			sendToClient(outToClient, "WijzigOrder ok");
		else
			sendToClient(outToClient, "WijzigOrder error");
	}

	//
	// private void verwijderOrder(StringTokenizer tokenizedLine,
	// DataOutputStream outToClient) {
	// String method = tokenizedLine.nextToken();
	// String userName = tokenizedLine.nextToken();
	// tokenizedLine.nextToken();
	// String aandeel = tokenizedLine.nextToken();
	// String aantal = tokenizedLine.nextToken();
	//
	// if(method.equals(Buy))
	// dbManager.removeBuyOrder(userID, aandeelID)
	// }

	/**
	 * De prijs van een aandeel ophalen uit de database
	 * @param tokenizedLine Dit argument is het overige deel van de request.
	 * @param outToClient Hiermee wordt een response naar een client gestuurd.
	 */
	private void sendAandeelPrijs(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String aandeel = tokenizedLine.nextToken();
		double prijs = dbManager.retreivePrijs(aandeel);

		sendToClient(outToClient, "AandeelPrijs " + prijs);
	}

	/**
	 * Saldo ophalen uit de database
	 * @param tokenizedLine Dit argument is het overige deel van de request.
	 * @param outToClient Hiermee wordt een response naar een client gestuurd.
	 */
	private void sendSaldo(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String user = tokenizedLine.nextToken();

		double saldo = dbManager.retreiveSaldo(user);

		sendToClient(outToClient, "Saldo " + saldo);
	}

	/**
	 * Portowaarde ophalen uit een database
	 * @param tokenizedLine Dit argument is het overige deel van de request.
	 * @param outToClient Hiermee wordt een response naar een client gestuurd.
	 */
	private void sendPortoWaarde(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String user = tokenizedLine.nextToken();

		double waarde = dbManager.retreivePortoWaarde(user);

		sendToClient(outToClient, "PortoWaarde " + waarde);
	}

	/**
	 * Aandelennamen sturen.
	 * @param outToClient Hiermee wordt een response naar een client gestuurd
	 */
	private void sendAandeelNamen(DataOutputStream outToClient) {
		String aandelen = dbManager.retreiveAandelen();

		sendToClient(outToClient, "Aandelen: " + aandelen);
	}

	/**
	 * De logingegevens checken in de database.
	 * @param tokenizedLine Dit argument is het overige deel van de request.
	 * @param outToClient Hiermee wordt een response naar een client gestuurd.
	 */
	public void checkLogin(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String username, password;
		username = tokenizedLine.nextToken();
		password = tokenizedLine.nextToken();

		if (dbManager.checkLogin(username, password))
			sendToClient(outToClient, "Login ok");
		else
			sendToClient(outToClient, "Login incorrect");
	}

	/**
	 * Aandelen ophalen uit de database.
	 * @param outToClient Hiermee een response naar de client sturen
	 * @param data Een 2D array waarin de data over de aandelen zit.
	 */
	public void getAandelen(DataOutputStream outToClient,
			Object[][] data) {
		sendToClient(outToClient, "Aandeel: Size: " + data.length);

		for (int i = 0; i < data.length; i++) {
			String aandeel = "";
			for (int j = 0; j < data[0].length; j++)
				aandeel += (data[i][j] + " ");

			sendToClient(outToClient, "Aandeel: " + aandeel);
		}

		sendToClient(outToClient, "Aandeel: Done");
	}

	/**
	 * Haal de huidige portofeuille op.
	 * @param tokenizedLine Dit argument is het overige deel van de request.
	 * @param outToClient Hiermee wordt een response naar een client gestuurd.
	 */
	public void getPorto(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String username = tokenizedLine.nextToken();
		Object[][] data = dbManager.retreivePorto(username);

		getAandelen(outToClient, data);
	}

	/**
	 * Inkooporders van de aanbieder.
	 * @param tokenizedLine Dit argument is het overige deel van de request.
	 * @param outToClient Hiermee wordt een response naar een client gestuurd.
	 */
	public void getBuy(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String aandeel = "";

		if(tokenizedLine.hasMoreTokens())
			aandeel = tokenizedLine.nextToken();

		Object[][] data = dbManager.retreiveBuy(aandeel);

		getAandelen(outToClient, data);
	}

	/**
	 * Verkooporders van de aanbieder.
	 * @param tokenizedLine Dit argument is het overige deel van de request.
	 * @param outToClient Hiermee wordt een response naar een client gestuurd.
	 */
	public void getSell(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String aandeel = "";

		if (tokenizedLine.hasMoreTokens())
			aandeel = tokenizedLine.nextToken();

		Object[][] data = dbManager.retreiveSell(aandeel);

		getAandelen(outToClient, data);
	}

	/**
	 * De kooporders van de gebruiker.
	 * @param tokenizedLine Dit argument is het overige deel van de request.
	 * @param outToClient Hiermee wordt een response naar een client gestuurd.
	 */
	public void getBuying(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String username = tokenizedLine.nextToken();
		
		Object[][] data = dbManager.retreiveBuying(username);

		getAandelen(outToClient, data);
	}

	/**
	 * De verkooporders van de user.
	 * @param tokenizedLine Dit argument is het overige deel van de request.
	 * @param outToClient Hiermee wordt een response naar een client gestuurd.
	 */
	public void getSelling(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String username = tokenizedLine.nextToken();

		Object[][] data = dbManager.retreiveSelling(username);

		getAandelen(outToClient, data);
	}

	/**
	 * Een aandeel verkopen
	 * @param tokenizedLine Dit argument is het overige deel van de request.
	 * @param outToClient Hiermee wordt een response naar een client gestuurd.
	 */
	public void verkoopAandeel(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String userName = tokenizedLine.nextToken();
		tokenizedLine.nextToken();
		String aandeel = tokenizedLine.nextToken();
		String aantal = tokenizedLine.nextToken();

		boolean succes = dbManager.sellAandeel(userName, aandeel, aantal);
		if (succes)
			sendToClient(outToClient, "AandeelVerkoop ok");
		else
			sendToClient(outToClient, "AandeelVerkoop error");
	}

	/**
	 * Een aandeel kopen
	 * @param tokenizedLine Dit argument is het overige deel van de request.
	 * @param outToClient Hiermee wordt een response naar een client gestuurd.
	 */
	public void koopAandeel(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String userName = tokenizedLine.nextToken();
		tokenizedLine.nextToken();
		String aandeel = tokenizedLine.nextToken();
		String aantal = tokenizedLine.nextToken();

		boolean succes = dbManager.buyAandeel(userName, aandeel, aantal);
		if (succes)
			sendToClient(outToClient, "AandeelKoop ok");
		else
			sendToClient(outToClient, "AandeelKoop error");
	}

	/**
	 * Geld storten.
	 * @param tokenizedLine Dit argument is het overige deel van de request.
	 * @param outToClient Hiermee wordt een response naar een client gestuurd.
	 */
	public void stortGeld(StringTokenizer tokenizedLine,
			DataOutputStream outToClient){
		//TODO Geld storten voor de user
	}

	/**
	 * Een bericht naar de client gestuurd.
	 * @param outToClient Hiermee wordt een response naar een client gestuurd.
	 * @param message Bericht voor de client.
	 */
	public void sendToClient(DataOutputStream outToClient, String message) {
		try {
			outToClient.writeBytes(message + "\n\r");
		} catch (Exception e) {
			sendToClient(outToClient, "Sending Error");
			System.out.println("Webserver|sentToClient: " + e);
			System.out.println(Thread.currentThread().getStackTrace());
		}
	}
}
