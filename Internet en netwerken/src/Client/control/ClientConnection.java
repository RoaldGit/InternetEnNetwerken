package Client.control;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import Client.model.ClientModel;
import Client.model.UserModel;

public class ClientConnection extends Thread{
	private Socket connectionSocket;
	private InputStream in;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;

	public ClientConnection(){
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

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			outToClient.writeBytes("Done");
			connectionSocket.close();
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
			outToClient.writeBytes(request + "\n\r\n\r");

			while (response.equals(""))
				response = inFromClient.readLine();

		} catch (Exception e) {
			System.out.println("ClientConnection|login: " + e);
		}
		return response;
	}

	public Object[][] getAandelenOverzicht(String aandeel) {
		Object[][] result = null;

		return result;
	}
}
