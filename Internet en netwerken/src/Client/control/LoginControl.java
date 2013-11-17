package Client.control;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Client.model.BeursModel;
import Client.model.ClientModel;
import Client.model.UserModel;

/**
 * Deze klasse handelt het inloggen vanaf het beginscherm af.
 * 
 * @author Roald en Stef
 * @since 12-11-2013
 * @version 0.1
 */
public class LoginControl extends JPanel{
	private JTextField user, password;
	private JButton login = new JButton("Login");
	private UserModel userModel;
	private ClientModel clientModel;
	private ClientConnection connection;
	private BeursModel beursModel;
	private BeursControl beursControl;

	/**
	 * De constructor voor LoginControl
	 * @param uModel Het usermodel dat gebruikt wordt
	 * @param cModel Het Clientmodel dat gebruikt wordt.
	 * @param con De connectie met de server die gebruikt wordt.
	 * @param bModel Het beursmodel dat gebruikt wordt.
	 * @param bControl Het beurscontrol object dat gebruikt wordt.
	 */
	public LoginControl(UserModel uModel, ClientModel cModel,
			ClientConnection con, BeursModel bModel, BeursControl bControl) {
		setLayout(new GridLayout(3, 2));
		setSize(250, 100);

		user = new JTextField();
		JLabel userLabel = new JLabel("Username");
		password = new JTextField();
		JLabel passwordLabel = new JLabel("Password");

		userModel = uModel;
		clientModel = cModel;
		connection = con;
		beursModel = bModel;
		beursControl = bControl;

		add(userLabel);
		add(user);
		add(passwordLabel);
		add(password);
		add(new JLabel(""));
		add(login);

		login.addActionListener(new ButtonListener());
	}
	
	/**
	 * De method haalt op wat er in de user tekstbox wordt ingevult
	 * @return Returned een string met de usernaam er in.
	 */
	public String getUser() {
		return user.getText();
	}

	/**
	 * De method haalt op wat er in de password tekstbox wordt ingevult
	 * @return Returned een string met de password er in.
	 */
	public String getPass() {
		return password.getText();
	}

	/**
	 * Deze klasse handelt af wat er gebeurt als er op de login knop gedrukt wordt.
	 * @author Roald en Stef
	 *
	 */
	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			String userInput = user.getText();
			String passwordInput = password.getText();
			
			if (userInput.equals("") || passwordInput.equals(""))
				JOptionPane.showMessageDialog(new JFrame(),
						"Please enter your username and password",
						"Error bij login", JOptionPane.ERROR_MESSAGE);
			else {
				String response = connection.login(userInput, passwordInput);

				if (response.equals("Login ok")) {
					clientModel.setLoggedIn(true);
					userModel.setUserDetails(passwordInput, userInput);
					beursControl.retreiveAlleAandelen();
					beursControl.retreiveSaldo(userInput);
				} else
					JOptionPane.showMessageDialog(new JFrame(),
							"Invalid username or password", "Error bij login",
							JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
