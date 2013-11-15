package Client.control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Client.model.BeursModel;
import Client.view.Tabel;

public class ClickEvent extends MouseAdapter {
	private BeursModel beursModel;

	public ClickEvent(BeursModel bModel) {
		beursModel = bModel;
	}

	public void mouseClicked(MouseEvent e) {
		Tabel source = (Tabel) e.getSource();
		int row = source.getSelectedRow();
		int cols = source.getColumnCount();

		beursModel.setSelectedTable(source);
	}
}
