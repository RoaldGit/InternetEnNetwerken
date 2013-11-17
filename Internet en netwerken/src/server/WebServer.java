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
	private String user= "a", pass="b";
	private DBmanager dbManager;

	public WebServer(int port, String naam, String user, String pass) {
		dbManager = DBmanager.getInstance(naam, user, pass);
		DatabaseApl test = new DatabaseApl(dbManager, naam);

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

				// if (!requestMessageLine.equals(""))
				// System.out.println("Webserver|Received request: "
				// + requestMessageLine);

				StringTokenizer tokenizedLine = new StringTokenizer(
						requestMessageLine);

				if (tokenizedLine.hasMoreTokens()
						&& tokenizedLine.countTokens() > 1) {
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
						break;
					case "Aandelen":
						sendAandeelNamen(outToClient);
						break;
					case "WijzigOrder":
						sendToClient(outToClient, "WijzigOrder ok");
						break;
					case "VerwijderOrder":
						sendToClient(outToClient, "VerwijderOrder ok");
						break;
					case "AandeelKoop":
						sendToClient(outToClient, "AandeelKoop ok");
						break;
					case "AandeelVerkoop":
						sendToClient(outToClient, "AandeelVerkoop ok");
						break;
					case "Saldo":
						sendSaldo(tokenizedLine, outToClient);
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
		}
	}

	private void sendSaldo(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		sendToClient(outToClient, "Saldo 1000");
	}

	private void sendAandeelNamen(DataOutputStream outToClient) {
		sendToClient(outToClient, "Aandelen: Syntaxis LiNK WaTT");
	}

	public void checkLogin(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String username, password;
		username = tokenizedLine.nextToken();
		password = tokenizedLine.nextToken();

		if (user.equals(username) && pass.equals(password))
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
		Object[][] data = new Object[][] { { "Syntaxis", 10, 5.00, 50.00 },
				{ "Watt", 5, 5.00, 25.00 } };

		getAandelen(outToClient, data);
	}

	public void getBuy(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String aandeel = "";

		if(tokenizedLine.hasMoreTokens())
			aandeel = tokenizedLine.nextToken();
		
		Object[][] data = new Object[2][];
		
		switch(aandeel) {
		default:
		case "Syntaxis":
			data[0] = new Object[] { "Syntaxis", "Syntaxis", 20, 5.00, 100.00 };
			data[1] = new Object[] { "Syntaxis", "Syntaxis", 200, 5.00, 1000.00 };
			break;
		case "LiNK":
			data[0] = new Object[] { "LiNK", "LiNK", 20, 5.00, 100.00 };
			data[1] = new Object[] { "LiNK", "LiNK", 200, 5.00, 1000.00 };
			break;
		case "WaTT":
			data[0] = new Object[] { "WaTT", "WaTT", 20, 5.00, 100.00 };
			data[1] = new Object[] { "WaTT", "WaTT", 200, 5.00, 1000.00 };
			break;
		}

		getAandelen(outToClient, data);
	}

	public void getSell(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String aandeel = "";

		if (tokenizedLine.hasMoreTokens())
			aandeel = tokenizedLine.nextToken();

		Object[][] data = new Object[2][];

		switch (aandeel) {
		default:
		case "Syntaxis":
			data[0] = new Object[] { "Syntaxis", "Syntaxis", 20, 5.00, 100.00 };
			data[1] = new Object[] { "Syntaxis", "Syntaxis", 200, 5.00, 1000.00 };
			break;
		case "LiNK":
			data[0] = new Object[] { "LiNK", "LiNK", 20, 5.00, 100.00 };
			data[1] = new Object[] { "LiNK", "LiNK", 200, 5.00, 1000.00 };
			break;
		case "WaTT":
			data[0] = new Object[] { "WaTT", "WaTT", 20, 5.00, 100.00 };
			data[1] = new Object[] { "WaTT", "WaTT", 200, 5.00, 1000.00 };
			break;
		}

		getAandelen(outToClient, data);
	}

	public void getBuying(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		Object[][] data = new Object[][] { { "LiNK", 8, 5.00, 40.00 } };

		getAandelen(outToClient, data);
	}

	public void getSelling(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		Object[][] data = new Object[][] { { "Syntaxis", 5, 5.00, 25.50 } };

		getAandelen(outToClient, data);
	}

	public void verkoopAandeel(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		//TODO aandeel verkopen, dingen updaten, boolean returnen
	}
	
	public void koopAandeel(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
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