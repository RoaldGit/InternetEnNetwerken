package Client.control;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import Client.model.ClientModel;

/**
 * De klasse die de connection maakt met de server en alles afhandelt qua communicatie.
 * 
 * @author Roald en Stef
 * @since 9-10-2013
 * @version 0.1
 */
public class ClientConnection {
	private Socket connectionSocket;
	private InputStream in;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	private ClientModel clientModel;

	/**
	 * De constructor van ClientConnection, waarin meteen een connection gemaakt wordt met de server.
	 * @param cModel Het ClientModel dat gebruikt wordt
	 */
	public ClientConnection(ClientModel cModel) {
		clientModel = cModel;
		connect();
	}
	
	/**
	 * De method die probeert te connecten met de server.
	 */
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
	
	/**
	 * De method om de connectie met de server te sluiten, het probeert dan de socket te sluiten, zodat het niet meer open is.
	 */
	public void close(){
		try {
			outToClient.writeBytes("Done");
			connectionSocket.close();

			clientModel.setConnected(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * De method om in te loggen op de server.
	 * @param user De username
	 * @param password De password
	 * @return De method returned een response van de server.
	 */
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

	/**
	 * De method om aandelen van de server op te halen
	 * @param user De user
	 * @param method De method bepaalt wat er opgehaald moet worden van de server.
	 * @return
	 */
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

	/**
	 * Deze method voert een transactie uit, waarbij het aangeeft wat de client wil 
	 * @param transactie De transactie geeft aan wat de client wil doen (kopen of verkopen).
	 * @param user De user.
	 * @param password De wachtwoord van de user.
	 * @param aandeel Het betreffende aandeel.
	 * @param aantal Het aantal van de aandelen.
	 * @return returned een boolean of het geslaagd is of niet.
	 */
	public boolean executeTransaction(String transactie, String user,
			String password,
			String aandeel, String aantal) {
		if (connectionSocket.isClosed())
			connect();

		String response = "";
		String request = transactie + " " + user + " " + password + " "
				+ aandeel + " " + aantal;
		boolean succes = false;

		try {
			outToClient.writeBytes(request + "\n\r");

			while (!response.contains(transactie)
					&& !response.contains("Error")) {
				response = inFromClient.readLine();
			}

			if (response.equals(transactie + " ok"))
				succes = true;
		} catch (Exception e) {
			System.out.println("ClientConnection|executeTransaction: " + e);
		}
		return succes;
	}

	/**
	 * De method die de saldo ophaalt van de server voor de user.
	 * @param user De user.
	 * @return Returned een double, oftewel de saldo van de user.
	 */
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
	
	public double getPortoWaarde(String user) {
		String response = "";
		String request = "PortoWaarde " + user;
		double waarde = 0;

		try {
			outToClient.writeBytes(request + "\n\r");
			while (!response.contains("PortoWaarde")) {
				response = inFromClient.readLine();

				if (!response.equals("")) {
					StringTokenizer tokenizedLine = new StringTokenizer(
							response);
					tokenizedLine.nextToken();
					waarde = Double.parseDouble(tokenizedLine.nextToken());
				}
			}
		} catch (Exception e) {
			System.out.println("ClientConnection|getPortoWaarde: " + e);
		}
		return waarde;
	}
	/**
	 * De method die de size terug haalt van de response van de server.
	 * @param response De response die de server terug stuurt
	 * @return returned een integer van hoe groot de array is.
	 */
	public int retrieveSize(String response) {
		StringTokenizer tokenizedLine = new StringTokenizer(response);

		tokenizedLine.nextToken();
		tokenizedLine.nextToken();

		int arraySize = Integer.parseInt(tokenizedLine.nextToken());

		return arraySize;
	}

	/**
	 * Deze method returned aandelen in een array.
	 * @param response De response van de server
	 * @return Returned een array van objects
	 */
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

	/**
	 * De methode returned een array van aandelen.
	 * @return Returned een array van aandelen.
	 */
	public String[] getAandelen() {
		String[] aandeel = new String[1];
		try {
			outToClient.writeBytes("Aandelen\n\r");

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

	public double getAandeelPrijs(String selected) {
		double prijs = 0;
		try {
			outToClient.writeBytes("AandeelPrijs " + selected + "\n\r");

			String response = "";

			while (!response.contains("AandeelPrijs")
					&& !response.contains("Error"))
				response = inFromClient.readLine();

			StringTokenizer tokenizedLine = new StringTokenizer(response);

			tokenizedLine.nextToken();

			prijs = Double.parseDouble(tokenizedLine.nextToken());
		} catch (Exception e) {
			System.out.println("ClientConnection|getAandeelPrijs: " + e);
		}
		return prijs;
	}
}
