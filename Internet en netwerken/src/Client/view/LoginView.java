package Client.view;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import Client.control.ClientConnection;
import Client.control.LoginControl;
import Client.model.ClientModel;
import Client.model.UserModel;

public class LoginView extends JPanel {
	private UserModel userModel;
	private ClientModel clientModel;
	private ClientConnection connection;

	public LoginView(UserModel uModel, ClientModel cModel, ClientConnection connection) {
		userModel = uModel;
		clientModel = cModel;
		this.connection = connection;

		setSize(250, 100);

		add(new LoginControl(userModel, clientModel, connection));

		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
	}
}