package Client.control;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	public LoginControl(UserModel userModel, ClientModel clientModel) {
		setLayout(new GridLayout(3, 2));
		setSize(250, 100);

		user = new JTextField();
		JLabel userLabel = new JLabel("Username");
		password = new JTextField();
		JLabel passwordLabel = new JLabel("Password");

		this.userModel = userModel;
		this.clientModel = clientModel;

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
			userModel.setPassword(password.getText());
			userModel.setUser(user.getText());
			// TODO Check login info with webserver, before setting userModel
			// vars
		}
	}
}
