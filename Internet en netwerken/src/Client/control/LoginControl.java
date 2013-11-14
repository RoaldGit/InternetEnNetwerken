package Client.control;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Client.model.ClientModel;
import Client.model.UserModel;

public class LoginControl extends JPanel{
	private JTextField user, password;
	private JButton login = new JButton("Login");
	private UserModel userModel;
	private ClientModel clientModel;
	private ClientConnection connection;

	public LoginControl(UserModel userModel, ClientModel clientModel, ClientConnection connection) {
		setLayout(new GridLayout(3, 2));
		setSize(250, 100);

		user = new JTextField();
		JLabel userLabel = new JLabel("Username");
		password = new JTextField();
		JLabel passwordLabel = new JLabel("Password");

		this.userModel = userModel;
		this.clientModel = clientModel;
		this.connection = connection;

		add(userLabel);
		add(user);
		add(passwordLabel);
		add(password);
		add(new JLabel(""));
		add(login);

		login.addActionListener(new ButtonListener());
	}

	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// TODO Check login info with webserver, before setting userModel
			// vars. Ook: Saldo en aandelen ophalen voor de user
			// Thread myThread = new Thread(new Runnable() {
			// public void run() {
			//			try {
			//				Socket connectionSocket = new Socket("localhost", 800);
			//
			//				InputStream in = connectionSocket.getInputStream();
			//
			//				BufferedReader inFromClient = new BufferedReader(
			//						new InputStreamReader(in));
			//
			//				DataOutputStream outToClient = new DataOutputStream(
			//						connectionSocket.getOutputStream());
			//
			//				outToClient.writeBytes("GET $ HTTP/1.1\n\r\n\r");
			//
			//				String requestMessageLine;
			//
			//				while (!connectionSocket.isClosed()) {
			//					if (in.available() > 0) {
			//						requestMessageLine = inFromClient.readLine();
			//						System.out.println("Response: "
			//								+ requestMessageLine);
			//					}
			//					else
			//						break;
			//				}
			//
			//			} catch (Exception ex) {
			//				System.out.println("LoginControl|Connecting: " + ex);
			//			}
			// }
			// });
			// myThread.start();

			clientModel.setLoggedIn(true);
			userModel.setUserDetails(password.getText(), user.getText());

			System.out.println("LoginControl: Button Pressed");
		}
	}
}