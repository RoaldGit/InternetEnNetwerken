package Client.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import Client.model.ClientModel;
import Client.model.UserModel;

public class MainView extends JFrame implements Observer {
	private LoginView login;
	private BeursView beurs;
	private UserModel userModel;
	private ClientModel clientModel;
	private JLabel connected, loggedIn, saldo;
	private JPanel sideBar;

	public MainView() {
		JFrame frame = new JFrame("Internet en Netwerken Eindopdracht");
		frame.setBounds(40, 40, 800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		
		sideBar = new JPanel();
		sideBar.setBounds(0, 0, 125, 75);
		sideBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		userModel = new UserModel();
		clientModel = new ClientModel();

		clientModel.addObserver(this);

		connected = new JLabel("Not connected");
		connected.setBounds(5, 5, 100, 20);

		loggedIn = new JLabel("Not logged in");
		loggedIn.setBounds(5, 25, 100, 20);

		saldo = new JLabel();
		saldo.setBounds(5, 45, 100, 20);

		login = new LoginView(userModel, clientModel);
		login.setBounds(300, 300, 200, 100);

		beurs = new BeursView(userModel);
		beurs.setBounds(5, 100, 400, 400);

		sideBar.setLayout(null);
		sideBar.add(connected);
		sideBar.add(loggedIn);
		sideBar.add(saldo);

		frame.add(login);
		frame.add(sideBar);
		frame.add(beurs);

		clientModel.addObserver(this);
		userModel.addObserver(this);

		clientModel.setConnected(true);

		login.setVisible(true);
		beurs.setVisible(false);

		frame.setVisible(true);
	}

	public void update(Observable o, Object arg) {
		if (o == clientModel) {
			if (arg.equals("connected")) {
				if (clientModel.getConnected()) {
					connected.setText("Connected");
					loggedIn.setText("Not logged in");
				}
				else
					connected.setText("Not Connected");
			}
		}

		if (o == userModel) {
			if (arg.equals("loggedIn") && clientModel.getConnected()) {
				if (clientModel.getLoggedIn()) {
					loggedIn.setText("Logged in as: " + userModel.getUser());
					String saldoString = String.format("Huidig saldo: %.2f",
							userModel.getSaldo());
					saldo.setText(saldoString);

					login.setVisible(false);
					beurs.setVisible(true);
					sideBar.setVisible(true);
				}
				else
					loggedIn.setText("Not logged in");
			}
		}
	}
}