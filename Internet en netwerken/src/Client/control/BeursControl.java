package Client.control;

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
	
	public void retreivePorto() {
		connection.getPorto(userModel.getUser());
	}
}
