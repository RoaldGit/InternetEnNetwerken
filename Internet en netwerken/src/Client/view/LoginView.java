package Client.view;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import Client.control.BeursControl;
import Client.control.ClientConnection;
import Client.control.LoginControl;
import Client.model.BeursModel;
import Client.model.ClientModel;
import Client.model.UserModel;

public class LoginView extends JPanel {
	private UserModel userModel;
	private ClientModel clientModel;
	private ClientConnection connection;
	private BeursModel beursModel;

	public LoginView(UserModel uModel, ClientModel cModel,
			ClientConnection con, BeursModel bModel) {
		userModel = uModel;
		clientModel = cModel;
		connection = con;
		beursModel = bModel;

		setSize(250, 100);

		add(new LoginControl(userModel, clientModel, connection, beursModel,
				new BeursControl(beursModel, userModel, connection)));

		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
	}
}