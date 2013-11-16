package Client.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import Client.model.BeursModel;

public class AandelenLijst extends JComboBox {
	private BeursModel beursModel;
	private ClientConnection clientConnection;

	public AandelenLijst(String[] aandelen, BeursModel bModel,
			ClientConnection con) {
		super(aandelen);

		beursModel = bModel;
		clientConnection = con;

		beursModel.setSelectedAandeel(this.getSelectedItem().toString());
		this.addActionListener(new ComboListener());
	}

	class ComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox combo = (JComboBox) e.getSource();

			String selected = combo.getSelectedItem().toString();

			beursModel.setSelectedAandeel(selected);

			beursModel.setBuy(clientConnection.getAandelen(
					beursModel.getUser(), "Buy " + selected));
			beursModel.setSell(clientConnection.getAandelen(
					beursModel.getUser(), "Sell " + selected));
		}
	}
}
