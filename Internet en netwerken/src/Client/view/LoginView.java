package Client.view;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import Client.control.LoginControl;
import Client.model.ClientModel;
import Client.model.UserModel;

public class LoginView extends JPanel {
	private UserModel userModel;
	private ClientModel clientModel;

	public LoginView(UserModel uModel, ClientModel cModel) {
		userModel = uModel;
		clientModel = cModel;

		setSize(250, 100);

		add(new LoginControl(userModel, clientModel));

		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
	}
}