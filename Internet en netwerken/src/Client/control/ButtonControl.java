package Client.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Client.model.BeursModel;
import Client.model.UserModel;

public class ButtonControl implements ActionListener {
	private String type;
	private ClientConnection connection;
	private BeursControl beursControl;
	private BeursModel beursModel;
	private UserModel userModel;

	public ButtonControl(String button, ClientConnection con,
			BeursControl bControl, BeursModel bModel, UserModel uModel) {
		type = button;
		connection = con;
		beursControl = bControl;
		beursModel = bModel;
		userModel = uModel;
	}

	public void actionPerformed(ActionEvent e) {
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
			done = connection.executeTransaction("AandeelVerkoop", userName,
					password, aandeel, aantal);
			break;
		case "change":
			done = connection.executeTransaction("WijzigOrder", userName,
					password, aandeel, aantal);
			break;
		case "cancel":
			done = connection.executeTransaction("VerwijderOrder", userName,
					password, aandeel, aantal);
			break;
		}

		try {
			beursControl.retreiveSaldo("Silly");
		} catch (Exception exc) {
			System.out.println(exc);
		}

		if (done)
			JOptionPane.showMessageDialog(new JFrame(), "Transactie Geslaagd",
					"Transactie Geslaagd", JOptionPane.OK_OPTION);
		else
			JOptionPane.showMessageDialog(new JFrame(), "Transactie Mislukt",
					"Transactie Mislukt", JOptionPane.ERROR_MESSAGE);


	}

}
