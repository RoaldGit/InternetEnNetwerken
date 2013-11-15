package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class WebServer extends Thread {
	private String requestMessageLine;
	private String fileName;
	private int port;
	private ServerSocket listenSocket;
	private String user= "a", pass="b";

	public WebServer(int port) {
		this.port = port;
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
					if (firstToken.equals("Login")) {
						if (checkLogin(tokenizedLine)) {
							outToClient.writeBytes("Login ok\n\r\n\r");
						} else
							outToClient.writeBytes("Login oak\n\r\n\r");
					}
 else if (firstToken.equals("Aan")) {
						//TODO Aandelen kopen of verkopen
					}
					else if (requestMessageLine.contains("Done"))
						connectionSocket.close();
				}
			}
		} catch (Exception e) {
			System.out.println("Webserver|serve: " + e);
		}
	}

	public boolean checkLogin(StringTokenizer tokenizedLine) {
		String username, password;
		username = tokenizedLine.nextToken();
		password = tokenizedLine.nextToken();

		if (user.equals(username) && pass.equals(password))
			return true;
		else
			return false;
	}
	
	public String getAandeel(StringTokenizer tokenizedLine){
		String aandeel = "";
		//TODO Huidige data van user ophalen
		return aandeel;
	}
	
	public String getPorto(StringTokenizer tokenizedLine){
		String porto = "";
		//TODO Huidige porto ophalen van User
		return porto;
	}
	
	public boolean sellAandeel(StringTokenizer tokenizedLine){
		boolean verkocht = false;
		//TODO aandeel verkopen, dingen updaten, boolean returnen
		return verkocht;
	}
	
	public boolean koopAandeel(StringTokenizer tokenizedLine){
		boolean gekocht = false;
		return gekocht;
	}
}