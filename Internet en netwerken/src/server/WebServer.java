package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

import database.DBmanager;
import database.DatabaseApl;

public class WebServer extends Thread {
	private String requestMessageLine;
	private ServerSocket listenSocket;
	private String user = "a", pass = "b";
	private DBmanager dbManager;

	public WebServer(int port, String naam, String user, String pass) {
		dbManager = DBmanager.getInstance(naam, user, pass);
		DatabaseApl apl = new DatabaseApl(dbManager, naam);

		try {
			listenSocket = new ServerSocket(port);
		} catch (Exception e) {
			System.out.println("Webserver|Consctructor: " + e);
		}
	}

	public WebServer(int port) {
		try {
			listenSocket = new ServerSocket(port);
		} catch (Exception e) {
			System.out.println("Webserver|Consctructor: " + e);
		}
	}

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

				if (requestMessageLine.contains("VerwijderOrder"))
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

	private void verwijderOrder(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String method = tokenizedLine.nextToken();
		String userName = tokenizedLine.nextToken();
		tokenizedLine.nextToken();
		String aandeel = tokenizedLine.nextToken();
		String aantal = tokenizedLine.nextToken();
		boolean succes = dbManager.verAnderOrder(method, userName, aandeel,
				aantal);
		if (succes)
			sendToClient(outToClient, "VerwijderOrder ok");
		else
			sendToClient(outToClient, "VerwijderOrder error");
	}

	private void wijzigOrder(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String method = tokenizedLine.nextToken();
		String userName = tokenizedLine.nextToken();
		tokenizedLine.nextToken();
		String aandeel = tokenizedLine.nextToken();

		boolean succes = dbManager.verwijderOrder(method, userName, aandeel);
		if (succes)
			sendToClient(outToClient, "WijzigOrder ok");
		else
			sendToClient(outToClient, "WijzigOrder error");
	}

	private void sendAandeelPrijs(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String aandeel = tokenizedLine.nextToken();
		double prijs = dbManager.retreivePrijs(aandeel);

		sendToClient(outToClient, "AandeelPrijs " + prijs);
	}

	private void sendSaldo(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String user = tokenizedLine.nextToken();

		double saldo = dbManager.retreiveSaldo(user);

		sendToClient(outToClient, "Saldo " + saldo);
	}

	private void sendPortoWaarde(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String user = tokenizedLine.nextToken();

		double waarde = dbManager.retreivePortoWaarde(user);

		sendToClient(outToClient, "PortoWaarde " + waarde);
	}

	private void sendAandeelNamen(DataOutputStream outToClient) {
		String aandelen = dbManager.retreiveAandelen();

		sendToClient(outToClient, "Aandelen: " + aandelen);
	}

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
	
	public void getPorto(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String username = tokenizedLine.nextToken();
		Object[][] data = dbManager.retreivePorto(username);

		getAandelen(outToClient, data);
	}

	public void getBuy(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String aandeel = "";

		if(tokenizedLine.hasMoreTokens())
			aandeel = tokenizedLine.nextToken();
		
		Object[][] data = dbManager.retreiveBuy(aandeel);

		getAandelen(outToClient, data);
	}

	public void getSell(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String aandeel = "";

		if (tokenizedLine.hasMoreTokens())
			aandeel = tokenizedLine.nextToken();

		Object[][] data = dbManager.retreiveSell(aandeel);

		getAandelen(outToClient, data);
	}

	public void getBuying(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String username = tokenizedLine.nextToken();

		Object[][] data = dbManager.retreiveBuying(username);

		getAandelen(outToClient, data);
	}

	public void getSelling(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String username = tokenizedLine.nextToken();

		Object[][] data = dbManager.retreiveSelling(username);

		getAandelen(outToClient, data);
	}

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
	
	public void stortGeld(StringTokenizer tokenizedLine,
			DataOutputStream outToClient){
		//TODO Geld storten voor de user
	}

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
