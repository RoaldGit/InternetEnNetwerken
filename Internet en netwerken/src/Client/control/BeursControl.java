package Client.control;

import javax.swing.JButton;

import Client.model.BeursModel;
import Client.model.UserModel;

public class BeursControl {
	private BeursModel beursModel;
	private UserModel userModel;
	private ClientConnection connection;
	
	public BeursControl(BeursModel bModel, UserModel uModel,
			ClientConnection con) {
		beursModel = bModel;
		userModel = uModel;
		connection = con;
	}
	
	public void retreiveAlleAandelen() {
		retreiveAandelen("Porto");
		retreiveAandelen("Buy");
		retreiveAandelen("Sell");
		retreiveAandelen("Buying");
		retreiveAandelen("Selling");
	}

	public JButton getBuy() {
		return null;
	}

	public JButton getSell() {
		return null;
	}

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
}
