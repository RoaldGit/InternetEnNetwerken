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
		// retreivePorto();
	}
	
	public void retreivePorto() {
		Object[][] porto = connection.getPorto(userModel.getUser());
	}
}
