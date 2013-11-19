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
/**
 * Deze klasse bouwt de loginview op.
 * @author Roald en Stef
 * @since 9-11-2013
 * @version 0.1
 */
public class LoginView extends JPanel {
	private UserModel userModel;
	private ClientModel clientModel;
	private ClientConnection connection;
	private BeursModel beursModel;

	/**
	 * De constructor voor de loginview.
	 * @param uModel Het Usermodel dat gebruikt wordt.
	 * @param cModel Het clientmodel dat gebruikt wordt.
	 * @param con De connectie met de server die gebruikt wordt.
	 * @param bModel Het beursmodel dat gebruikt wordt.
	 * @param bControl Het beurscontrol dat gebruikt wordt.
	 */
	public LoginView(UserModel uModel, ClientModel cModel,
			ClientConnection con, BeursModel bModel, BeursControl bControl) {
		userModel = uModel;
		clientModel = cModel;
		connection = con;
		beursModel = bModel;

		setSize(250, 100);

		add(new LoginControl(userModel, clientModel, connection, beursModel,
				bControl));

		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
	}
}