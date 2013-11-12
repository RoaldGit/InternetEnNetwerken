package Client.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import Client.model.BeursModel;

public class AandelenLijst extends JComboBox {
	private BeursModel beursModel;

	public AandelenLijst(String[] aandelen, BeursModel bModel) {
		super(aandelen);
		this.beursModel = bModel;
		beursModel.setSelectedAandeel(this.getSelectedItem().toString());
		this.addActionListener(new ComboListener());
	}

	class ComboListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox combo = (JComboBox) e.getSource();
			beursModel.setSelectedAandeel(combo.getSelectedItem().toString());
		}
	}
}
