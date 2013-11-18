package Client.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Client.model.BeursModel;
import Client.model.ClientModel;
import Client.model.UserModel;

/**
 * De klasse die afhandelt wat er gebeurt als je een bepaalde knop op de GUI indrukt.
 * 
 * @author Roald en Stef
 * @since 10-11-2013
 * @version 0.1
 */
public class ButtonControl implements ActionListener {
	private String type;
	private ClientConnection connection;
	private BeursControl beursControl;
	private BeursModel beursModel;
	private UserModel userModel;
	private ClientModel clientModel;
	
	/**
	 * De constructor van ButtonControl
	 * @param button De naam van de button, die bepaalt welke button het is.
	 * @param con De connectie tussen het programma en de server.
	 * @param bControl De beurscontrol die de aandelen ophaalt.
	 * @param bModel Het beursmodel dat gebruikt wordt.
	 * @param uModel Het usermodel dat gebruikt wordt.
	 */
	public ButtonControl(String button, ClientConnection con,
			BeursControl bControl, BeursModel bModel, UserModel uModel,
			ClientModel cModel) {
		type = button;
		connection = con;
		beursControl = bControl;
		beursModel = bModel;
		userModel = uModel;
		clientModel = cModel;
	}
	
	/**
	 * De methode die een actie uitvoert op basis van welke knop er is ingedrukt en dat doorstuurt naar de connectie op te communiceren met de server.
	 */
	public void actionPerformed(ActionEvent e) {
		if (type.equals("logOut"))
			clientModel.setLoggedIn(false);
		else {
			String userName = userModel.getUser();
			String password = userModel.getPassword();
			String aandeel = beursModel.getAandeel();
			String aantal = beursModel.getAantalAandelen();

			boolean done = false;

			switch (type) {
			case "buy":
				done = connection.executeTransaction("AandeelKoop", userName,
						password, aandeel, aantal);
				break;
			case "sell":
				done = connection.executeTransaction("AandeelVerkoop",
						userName, password, aandeel, aantal);
				break;
			case "change":
				done = connection.executeTransaction("WijzigOrder", userName,
						password, aandeel, aantal);
				break;
			case "cancel":
				done = connection.executeTransaction("VerwijderOrder",
						userName, password, aandeel, aantal);
			}

			beursControl.retreiveAlleAandelen();
			beursControl.retreiveSaldo(userName);
			beursControl.retreivePortoWaarde(userName);

			beursModel.refreshSelect();

			if (done)
				JOptionPane.showMessageDialog(new JFrame(),
						"Transactie Geslaagd", "Transactie Geslaagd",
						JOptionPane.OK_OPTION);
			else
				JOptionPane.showMessageDialog(new JFrame(),
						"Transactie Mislukt", "Transactie Mislukt",
						JOptionPane.ERROR_MESSAGE);
		}

	}

}
