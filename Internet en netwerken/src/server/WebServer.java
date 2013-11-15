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
		try {

				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream()));
	
				DataOutputStream outToClient = new DataOutputStream(
						connectionSocket.getOutputStream());

			while (!connectionSocket.isClosed()) {
				requestMessageLine = inFromClient.readLine();

				StringTokenizer tokenizedLine = new StringTokenizer(
						requestMessageLine);

				//TODO Meerdere filters toevoegen, Data ophalen etc.
				if (tokenizedLine.hasMoreTokens()) {
					String firstToken = tokenizedLine.nextToken();
					switch (firstToken) {
					case "Login":
						checkLogin(tokenizedLine, outToClient);
						break;
					case "Aandeel":
						getPorto(tokenizedLine, outToClient);
						// TODO Aandelen van de user ophalen
						// Request bevat username en (waarschijnlijk) soort van
						// identifier
						break;
					case "Koop":
						koopAandeel(tokenizedLine, outToClient);
						// TODO Aandeel order plaatsen
						// Fancy implementatie kijkt ook naar bestaande verkoop
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
					case "Done":
						outToClient.writeBytes("Ack\n\r\n\r");
						connectionSocket.close();
						System.out.println("Webserver: Connection closed");
						break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Webserver|serve: " + e);
		}
	}

	public void checkLogin(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		String username, password;
		username = tokenizedLine.nextToken();
		password = tokenizedLine.nextToken();
		try {
			if (user.equals(username) && pass.equals(password))
				outToClient.writeBytes("Login ok\n\r\n\r");
			// TODO DB checken voor login gegevens;
			else
				outToClient.writeBytes("Login incorrect\n\r\n\r");
		} catch (Exception e) {
			System.out.println("Webserver|checkLogin: " + e);
		}
	}
	
	public String[][] getPorto(StringTokenizer tokenizedLine,
			DataOutputStream outToClient) {
		//TODO Huidige porto ophalen van User
		return null;
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
}