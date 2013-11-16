package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class WebServer extends Thread {
	private String requestMessageLine;
	private ServerSocket listenSocket;
	private String user= "a", pass="b";

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

				if (!requestMessageLine.equals(""))
				System.out.println("Webserver|Received request: "
						+ requestMessageLine);

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
						// TODO Aandelen van de user ophalen
						// Request bevat username en (waarschijnlijk) soort
						// van
						// identifier
						break;
					case "Koop":
						koopAandeel(tokenizedLine, outToClient);
						// TODO Aandeel order plaatsen
						// Fancy implementatie kijkt ook naar bestaande
						// verkoop
						// orders
						break;
					case "Verkoop":
						verkoopAandeel(tokenizedLine, outToClient);
						// TODO Aandeel order plaatsen
						// Fancy implementatie kijkt ook naar bestaande koop
						// orders
						break;
					case "Stort":
						stortGeld(tokenizedLine, outToClient);
						break;
					case "Aandelen":
						sendAandeelNamen(outToClient);
						break;
					case "KoopOrder":
						break;
					case "VerkoopOrder":
						break;
					case "AandeelKoop":
						break;
					case "AandeelVerkoop":
						break;
					case "Done":
						sendToClient(outToClient, "Ack\n\r\n\r");
						connectionSocket.close();
						System.out.println("Webserver: Connection closed");
						break;
					default:
						sendToClient(outToClient, "Unknown Error\n\r\n\r");
						break;
					}
				}
			}
		} catch (Exception e) {

			System.out.println("Webserver|serve: " + e);
		}
	}

	private void sendAandeelNamen(DataOutputStream outToClient) {
		sendToClient(outToClient, "Aandelen: Syntaxis LiNK WaTT\n\r\n\r");
	}

	public void checkLogin(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String username, password;
		username = tokenizedLine.nextToken();
		password = tokenizedLine.nextToken();

		if (user.equals(username) && pass.equals(password))
			sendToClient(outToClient, "Login ok\n\r\n\r");
		else
			sendToClient(outToClient, "Login incorrect\n\r\n\r");
	}
	
	public void getAandelen(DataOutputStream outToClient,
			Object[][] data) {
		sendToClient(outToClient, "Aandeel: Size: " + data.length + "\n\r");

		for (int i = 0; i < data.length; i++) {
			String aandeel = "";
			for (int j = 0; j < data[0].length; j++)
				aandeel += (data[i][j] + " ");

			sendToClient(outToClient, "Aandeel: " + aandeel + "\n\r");
		}

		sendToClient(outToClient, "Aandeel: Done\n\r\n\r");
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
			outToClient.writeBytes(message);
		} catch (Exception e) {
			sendToClient(outToClient, "Sending Error\n\r\n\r");
			System.out.println("Webserver|sentToClient: " + e);
			System.out.println(Thread.currentThread().getStackTrace());
		}
	}
}