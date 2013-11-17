package Client.control;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import Client.model.ClientModel;

public class ClientConnection {
	private Socket connectionSocket;
	private InputStream in;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	private ClientModel clientModel;

	public ClientConnection(ClientModel cModel) {
		clientModel = cModel;
		connect();
	}

	public void connect(){
		try {
			connectionSocket = new Socket("localhost", 800);

			in = connectionSocket.getInputStream();

			inFromClient = new BufferedReader(
					new InputStreamReader(in));

			outToClient = new DataOutputStream(
					connectionSocket.getOutputStream());

			System.out.println("ClientConnection: connect");

			clientModel.setConnected(false);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			outToClient.writeBytes("Done");
			connectionSocket.close();

			clientModel.setConnected(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String login(String user, String password) {
		if (connectionSocket.isClosed())
			connect();

		String response = "";
		String request = "Login " + user + " " + password;

		try {
			outToClient.writeBytes(request + "\n\r");

			while (!response.contains("Login") && !response.contains("Error")) {
				response = inFromClient.readLine();
			}

		} catch (Exception e) {
			System.out.println("ClientConnection|login: " + e);
		}
		return response;
	}

	public Object[][] getAandelen(String user, String method) {
		if (connectionSocket.isClosed())
			connect();

		String response = "";
		String request = method + " " + user;

		int arraySize = 0;
		int pos = 0;
		Object[][] porto = new Object[1][];

		try {
			outToClient.writeBytes(request + "\n\r");

			while (!response.contains("Done")) {
				response = inFromClient.readLine();

				if (response.contains("Size:")) {
					arraySize = retrieveSize(response);
					porto = new Object[arraySize][];
				}

				if (!response.equals("")) {
					if (!response.contains("Done")
							&& !response.contains("Size")) {
						porto[pos] = retrieveAandeel(response);
						pos++;
					}
				}
			}

		} catch (Exception e) {
			System.out.println("ClientConnection|login: " + e);
		}
		return porto;
	}

	public boolean executeTransaction(String transactie, String user,
			String password,
			String aandeel, String aantal) {
		String response = "";
		String request = transactie + " " + user + " " + password + " "
				+ aandeel + " " + aantal;
		boolean succes = false;

		try {
			outToClient.writeBytes(request + "\n\r");

			while (!response.contains(transactie)) {
				response = inFromClient.readLine();
			}

			if (response.equals(transactie + " ok"))
				succes = true;

		} catch (Exception e) {
			System.out.println("ClientConnection|executeTransaction: " + e);
		}
		return succes;
	}

	public double getSaldo(String user) {
		String response = "";
		String request = "Saldo " + user;
		double saldo = 0;
		
		try{
			outToClient.writeBytes(request + "\n\r");
			while (!response.contains("Saldo")) {
				response = inFromClient.readLine();

				if (!response.equals("")) {
					StringTokenizer tokenizedLine = new StringTokenizer(
							response);
					tokenizedLine.nextToken();
					saldo = Double.parseDouble(tokenizedLine.nextToken());
				}
			}
		}catch(Exception e) {
			System.out.println("ClientConnection|getSaldo: " + e);
		}
		return saldo;
	}
	public int retrieveSize(String response) {
		StringTokenizer tokenizedLine = new StringTokenizer(response);

		tokenizedLine.nextToken();
		tokenizedLine.nextToken();

		int arraySize = Integer.parseInt(tokenizedLine.nextToken());

		return arraySize;
	}

	public Object[] retrieveAandeel(String response) {
		StringTokenizer tokenizedLine = new StringTokenizer(response);
		tokenizedLine.nextToken();

		int size = tokenizedLine.countTokens();

		Object[] aandeel = new Object[size];

		for (int i = 0; i < size; i++) {
			aandeel[i] = tokenizedLine.nextToken();
		}

		return aandeel;
	}

	public String[] getAandelen() {
		String[] aandeel = new String[1];
		try {
			outToClient.writeBytes("Aandelen b\n\r");

			String response = "";

			while (!response.contains("Aandelen")
					&& !response.contains("Error"))
				response = inFromClient.readLine();

			StringTokenizer tokenizedLine = new StringTokenizer(response);

			tokenizedLine.nextToken();
			
			int size = tokenizedLine.countTokens();
			
			aandeel = new String[size];
			
			for (int i = 0; i < size; i++) {
				aandeel[i] = tokenizedLine.nextToken();
			}
		} catch (Exception e) {
			System.out.println("ClientConnection|getAandelen: " + e);
		}

		return aandeel;
	}
}
