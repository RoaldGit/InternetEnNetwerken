package Client.control;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import Client.model.ClientModel;
import Client.model.UserModel;

public class ClientConnection extends Thread{
	private UserModel userModel;
	private ClientModel clientModel;

	public ClientConnection(UserModel userModel, ClientModel clientModel){
		this.userModel = userModel;
		this.clientModel = clientModel;
	}

	public void run() {
		try {
			Socket connectionSocket = new Socket("localhost", 800);

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
