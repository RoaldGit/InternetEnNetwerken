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

				if (!requestMessageLine.equals("")) {
					System.out.println("Webserver|requestMessageLine: "
							+ requestMessageLine);

					StringTokenizer tokenizedLine = new StringTokenizer(
							requestMessageLine);

					if (tokenizedLine.hasMoreTokens()) {
						if (tokenizedLine.nextToken().equals("Login")) {
							if (checkLogin(tokenizedLine)) {
								outToClient.writeBytes("Login ok\n\r\n\r");
								System.out.println("Response sent");
							}
							else
								outToClient.writeBytes("Login oak\n\r\n\r");
						}
	
						else if (requestMessageLine.contains("Done"))
							connectionSocket.close();


					}
				}
				outToClient.flush();
			}
		} catch (Exception e) {
			System.out.println("Webserver|serve: " + e);
		}
	}

	public boolean checkLogin(StringTokenizer tokenizedLine) {
		String username, password;
		username = tokenizedLine.nextToken();
		password = tokenizedLine.nextToken();

		System.out
				.println("Webserver|checkLogin: " + username + "|" + password);

		if (user.equals(username) && pass.equals(password)) {
			System.out.println("Webserver|checkLogin: Login Correct");
			return true;
		} else {
			System.out.println("Webserver|checkLogin: Login Incorrect");
			return false;
		}
	}
}