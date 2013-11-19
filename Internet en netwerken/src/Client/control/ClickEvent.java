package Client.control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Client.model.BeursModel;
import Client.view.Tabel;

/**
 * Deze klasse dient als een clickevent voor de Tabel klasse.
 * 
 * @author Roald en Stef
 * @since 11-11-2013
 * @version 0.1
 */
public class ClickEvent extends MouseAdapter {
	private BeursModel beursModel;

	/**
	 * De constructor voor ClickEvent
	 * @param bModel Het beursmodel dat gebruikt wordt.
	 */
	public ClickEvent(BeursModel bModel) {
		beursModel = bModel;
	}

	/**
	 * De method die mouseclicks afhandelt.
	 */
	public void mouseClicked(MouseEvent e) {
		Tabel source = (Tabel) e.getSource();
		int row = source.getSelectedRow();
		int cols = source.getColumnCount();

		beursModel.setSelectedTable(source);
	}
}
