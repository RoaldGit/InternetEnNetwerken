package Client.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Client.model.ClientModel;
import Client.model.UserModel;

public class MainView extends JFrame implements Observer {
	private LoginView login;
	private UserModel userModel;
	private ClientModel clientModel;
	private JLabel connected, loggedIn;
	private JPanel background;

	public MainView() {
		JFrame frame = new JFrame("Internet en Netwerken Eindopdracht");
		frame.setBounds(40, 40, 800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		
		background = new JPanel();
		background.setBounds(0, 0, 800, 800);

		userModel = new UserModel();
		clientModel = new ClientModel();

		clientModel.addObserver(this);

		connected = new JLabel("Not connected");
		connected.setBounds(5, 5, 100, 20);

		loggedIn = new JLabel();
		loggedIn.setBounds(5, 25, 100, 20);

		login = new LoginView(userModel, clientModel);
		login.setBounds(300, 300, 200, 100);

		background.setLayout(null);
		background.add(connected);
		background.add(loggedIn);

		frame.add(login);
		frame.add(background);


		frame.setVisible(true);
	}

	public void update(Observable o, Object arg) {
		if (o == clientModel) {
			if (arg.equals("connected")) {
				Boolean con = clientModel.getConnected();
				if (con)
					connected.setText("Connected");
				else
					connected.setText("Not Connected");
			}
			if (arg.equals("loggedIn") && clientModel.getConnected()) {
				Boolean log = clientModel.getLoggedIn();
				if (log)
					loggedIn.setText("Logged in as: " + userModel.getUser());
				else
					loggedIn.setText("Not logged in");
			}
		}
	}
}

