package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class WebServer extends Thread {
	private String requestMessageLine;
	private String fileName;
	private int port;
	private ServerSocket listenSocket;

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

			requestMessageLine = inFromClient.readLine();
			byte[] bytes = requestMessageLine.getBytes();
			System.out.println(bytes[0]);

			StringTokenizer tokenizedLine = new StringTokenizer(
					requestMessageLine);

			while (tokenizedLine.hasMoreTokens()) {
				System.out.println("Dit: " + tokenizedLine.nextToken());
			}

			// outToClient.writeBytes("HTTP/1.1 200 OK\n\r");
			outToClient.writeBytes("Login ok\n\r\n\r");

			connectionSocket.close();
		} catch (Exception e) {
			System.out.println("Webserver|serve: " + e);
		}
	}
}