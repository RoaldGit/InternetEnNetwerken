package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class WebServer {
	public WebServer(int port) {
		//
		// public static void main(String argv[]) throws Exception {
		try {
			String requestMessageLine;
			String fileName;

			ServerSocket listenSocket = new ServerSocket(port);
			Socket connectionSocket = listenSocket.accept();

			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(connectionSocket.getInputStream()));

			DataOutputStream outToClient = new DataOutputStream(
					connectionSocket.getOutputStream());

			requestMessageLine = inFromClient.readLine();
			System.out.println(requestMessageLine);

			StringTokenizer tokenizedLine = new StringTokenizer(
					requestMessageLine);

			String token = tokenizedLine.nextToken();

			while (!token.equals("\r\n")) {
				System.out.println(token);
				token = tokenizedLine.nextToken();
			}

			// if (tokenizedLine.nextToken().equals("GET")) {
			// fileName = tokenizedLine.nextToken();
			// if (fileName.startsWith("/") == true)
			// fileName = fileName.substring(1);
			//
			// File file = new File(fileName);
			// int numOfBytes = (int) file.length();
			// FileInputStream inFile = new FileInputStream(fileName);
			// byte[] fileInBytes = new byte[numOfBytes];
			// inFile.read(fileInBytes);
			//
			// outToClient.writeBytes("HTTP/1.0 200 Document Follows\r\n");
			//
			// if (fileName.endsWith(".jpg"))
			// outToClient.writeBytes("Content-Type: image/jpeg\r\n");
			//
			// if (fileName.endsWith(".gif"))
			// outToClient.writeBytes("Content-Type: image/gif\r\n");
			//
			// outToClient
			// .writeBytes("Content-Length: " + numOfBytes + "\r\n");
			// outToClient.writeBytes("\r\n");
			// outToClient.write(fileInBytes, 0, numOfBytes);
			//
			// connectionSocket.close();
			// } else
			// System.out.println("Bad Request Message");
		} catch (Exception e) {
			System.out.println("Webserver: " + e);
		}
	}
}