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
	
	public String getUser() {
		return user.getText();
	}

	public String getPass() {
		return password.getText();
	}

	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String response = connection.login(user.getText(),
					password.getText());

			if (response.equals("200 OK")) {
				clientModel.setLoggedIn(true);
				userModel.setUserDetails(password.getText(), user.getText());
			}
 else
				System.out.println("Login not correct");

			System.out.println("LoginControl: Button Pressed");
		}
	}
}
