package Client.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import Client.control.BeursControl;
import Client.control.ClientConnection;
import Client.model.BeursModel;
import Client.model.ClientModel;
import Client.model.UserModel;

public class MainView extends JFrame implements Observer {
	private LoginView login;
	private BeursView beurs;
	private UserModel userModel;
	private BeursModel beursModel;
	private ClientModel clientModel;
	private JLabel connected, loggedIn, saldo, portoWaarde;
	private JPanel sideBar;
	private ClientConnection connection;

	public MainView() {
		JFrame frame = new JFrame("Internet en Netwerken Eindopdracht");
		frame.setBounds(40, 40, 800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		
		sideBar = new JPanel();
		sideBar.setBounds(0, 0, 200, 75);
		sideBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		userModel = new UserModel();
		beursModel = new BeursModel();
		clientModel = new ClientModel();

		connection = new ClientConnection(clientModel);

		clientModel.addObserver(this);

		connected = new JLabel("Not connected");
		connected.setBounds(5, 5, 100, 20);

		loggedIn = new JLabel("Not logged in");
		loggedIn.setBounds(5, 20, 200, 20);

		saldo = new JLabel();
		saldo.setBounds(5, 35, 200, 20);

		portoWaarde = new JLabel();
		portoWaarde.setBounds(5, 50, 200, 20);

		login = new LoginView(userModel, clientModel, connection, beursModel);
		login.setBounds(300, 300, 200, 100);

		beurs = new BeursView(userModel, beursModel, connection);
		beurs.setBounds(0, 0, 800, 800);

		sideBar.setLayout(null);

		sideBar.add(connected);
		sideBar.add(loggedIn);
		sideBar.add(saldo);
		sideBar.add(portoWaarde);

		frame.add(login);
		frame.add(sideBar);
		frame.add(beurs);

		clientModel.addObserver(this);
		userModel.addObserver(this);

		clientModel.setConnected(true);

		login.setVisible(true);
		beurs.setVisible(false);

		BeursControl test = new BeursControl(beursModel, userModel, connection);
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
					String saldoString = String.format("Huidig saldo: € %.2f",
							userModel.getSaldo());
					String portoString = String.format(
							"Waarde portofeuille: € %.2f",
							userModel.getPortoWaarde());

					saldo.setText(saldoString);
					portoWaarde.setText(portoString);

					login.setVisible(false);
					beurs.setVisible(true);

					beurs.updateTables();
				}
				else
					loggedIn.setText("Not logged in");
			}
		}
	}
}