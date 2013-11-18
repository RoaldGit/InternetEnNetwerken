package Client.control;

import javax.swing.JButton;

import Client.model.BeursModel;
import Client.model.UserModel;

/**
 * Deze klasse geeft door aan de BeursModel welke data er opgehaald moet worden.
 * 
 * @author Roald en Stef
 * @since 10-11-2013
 * @version 0.1
 */
public class BeursControl {
	private BeursModel beursModel;
	private UserModel userModel;
	private ClientConnection connection;
	
	/**
	 * De constructor van BeursControl.
	 * @param bModel Het beursmodel dat gebruikt wordt.
	 * @param uModel Het usermodel dat gebruikt wordt.
	 * @param con De object dat connectie afhandelt met de server.
	 */
	public BeursControl(BeursModel bModel, UserModel uModel,
			ClientConnection con) {
		beursModel = bModel;
		userModel = uModel;
		connection = con;
	}
	
	/**
	 * Deze method haalt alle info van de server op van de huidige user.
	 */
	public void retreiveAlleAandelen() {
		String selectedAandeel = beursModel.getSelectedAandeel();
		retreiveAandelen("Porto");
		retreiveAandelen("Buy " + selectedAandeel);
		retreiveAandelen("Sell " + selectedAandeel);
		retreiveAandelen("Buying");
		retreiveAandelen("Selling");
		System.out.println("Retreiving: " + selectedAandeel);
	}

	/**
	 * De method haalt de saldo op van de meegegeven user.
	 * @param user De user in String formaat waarvan de saldo moet worden opgehaald.
	 */
	public void retreiveSaldo(String user) {
		userModel.setSaldo(connection.getSaldo(user));
	}

	/**
	 * De functie om aandelen op te halen, afhankelijk van welke tabel er moet worden opgehaald.
	 * @param tabel De tabel die moet worden opgehaald en moet worden aangepast.
	 */
	public void retreiveAandelen(String tabel) {
		Object[][] data = connection.getAandelen(userModel.getUser(), tabel);

		switch (tabel) {
		case "Porto":
			beursModel.setPorto(data);
			break;
		case "Buy":
			beursModel.setBuy(data);
			break;
		case "Sell":
			beursModel.setSell(data);
			break;
		case "Buying":
			beursModel.setBuying(data);
			break;
		case "Selling":
			beursModel.setSelling(data);
			break;
		default:
			break;
		}
	}

	public void retreivePortoWaarde (String user) {
		userModel.setPortoWaarde(connection.getPortoWaarde(user));
	}
}
