package Client.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import Client.model.BeursModel;
import Client.model.UserModel;

/**
 * Deze klasse maakt een JComboBox aan, waarin een lijst van aandelen wordt weergegeven. Als één van deze aandelen geselecteerd wordt, passen
 * de tabellen onder de JComboBox zich aan aan de geselecteerde keuze.
 * 
 * @author Roald en Stef
 * @since 10-11-2013
 * @version 0.1
 */
public class AandelenLijst extends JComboBox {
	private BeursModel beursModel;
	private ClientConnection clientConnection;
	private UserModel userModel;

	/**
	 * De constructor van aandelenlijst.
	 * @param aandelen De lijst met aandelen die de combobox moet weergeven.
	 * @param bModel Het beursmodel dat gebruikt wordt.
	 * @param con De connectie die gebruikt wordt.
	 * @param uModel De usermodel die gebruikt wordt.
	 */
	public AandelenLijst(String[] aandelen, BeursModel bModel,
			ClientConnection con, UserModel uModel) {
		super(aandelen);

		beursModel = bModel;
		clientConnection = con;
		userModel = uModel;

		setSelectedIndex(0);
		updateSelect();

		addActionListener(new ComboListener());
	}

	/**
	 * Een method om nieuwe aandelen in de Combobox te setten
	 * @param aandelen De nieuwe lijst van aandelen.
	 */
	public void setAandelen(String[] aandelen) {
		setModel(new DefaultComboBoxModel<>(aandelen));
		updateSelect();
	}

	public void updateSelect() {
		String selected = getSelectedItem().toString();

		beursModel.setSelectedAandeel(selected);
		beursModel.setBuy(clientConnection.getAandelen(userModel.getUser(),
				"Buy " + selected));
		beursModel.setSell(clientConnection.getAandelen(userModel.getUser(),
				"Sell " + selected));
		beursModel.setAandeelPrijs(clientConnection.getAandeelPrijs(selected));
	}
	
	/**
	 * De actionlistener die de JComboBox gebruikt voor het afhandelen van een geselecteerde keuze.
	 * @author Roald en Stef
	 *
	 */
	class ComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			updateSelect();
		}
	}
}
