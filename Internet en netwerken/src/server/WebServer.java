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
			while(!connectionSocket.isClosed()) {
				BufferedReader inFromClient = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream()));
	
				DataOutputStream outToClient = new DataOutputStream(
						connectionSocket.getOutputStream());
	
				requestMessageLine = inFromClient.readLine();
				byte[] bytes = requestMessageLine.getBytes();
	
				StringTokenizer tokenizedLine = new StringTokenizer(
						requestMessageLine);

				if (tokenizedLine.nextToken().equals("Login")) {
					if (tokenizedLine.nextToken().equals(user)) {
						if (tokenizedLine.nextToken().equals(pass)) {
							outToClient.writeBytes("Login ok\n\r\n\r");
						}
					}
 else {
						outToClient.writeBytes("Login oak\n\r\n\r");
					}
				}
				
				if(requestMessageLine.contains("Done"))				
					connectionSocket.close();
			}
		} catch (Exception e) {
			System.out.println("Webserver|serve: " + e);
		}
	}
}