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
	private UserModel userModel;
	private ClientModel clientModel;
	private Socket connectionSocket;
	private InputStream in;
	private 

	public ClientConnection(){
		try {
			connectionSocket = new Socket("localhost", 800);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {

			InputStream in = connectionSocket.getInputStream();

			BufferedReader inFromClient = new BufferedReader(
					new InputStreamReader(in));

			DataOutputStream outToClient = new DataOutputStream(
					connectionSocket.getOutputStream());

			//outToClient.writeBytes("GET HTTP/1.1\n\r\n\r");
			outToClient.writeBytes("Aandeel\n\r\n\r");

			String requestMessageLine;

			while (!connectionSocket.isClosed()) {
				if (in.available() > 0) {
					requestMessageLine = inFromClient.readLine();
					System.out.println("Response: "
							+ requestMessageLine);
				}
			}

		} catch (Exception ex) {
			System.out.println("ClientConnection|Run: " + ex);
		}
	}

}
