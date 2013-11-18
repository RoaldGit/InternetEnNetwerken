package Client.control;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import Client.model.BeursModel;
/**
 * Deze class haalt op wat er in het textfield gezet wordt
 * 
 * @author Roald en Stef
 * @since 14-11-2013
 * @version 0.1
 */
public class TextFieldEvent implements DocumentListener {
	private BeursModel beursModel;

	/**
	 * De constructor voor de textfieldevent
	 * @param bModel Het beursmodel wat gebruikt wordt.
	 */
	public TextFieldEvent(BeursModel bModel) {
		beursModel = bModel;
	}
	/**
	 * Een methode die aangeeft dat er iets verandert is.
	 */
	public void changedUpdate(DocumentEvent e) {
		beursModel.selected();
	}

	/**
	 * Een methode die aangeeft dat er een aantal ingevoerd is. 
	 */
	public void removeUpdate(DocumentEvent e) {
		// beursModel.selected();
		update(e);
	}
	
	/**
	 * Een methode die aangeeft dat er een aantal ingevoerd is.
	 */
	public void insertUpdate(DocumentEvent e) {
		// beursModel.selected();
		update(e);
	}

	public void update(DocumentEvent e) {
		beursModel.selected();
		try {
			Document textFieldText = e.getDocument();
			String text = textFieldText.getText(0, textFieldText.getLength());
			beursModel.setAantal(text, true);
		} catch (Exception ex) {
			// beursModel.setAantal("error", false);
		}
	}
}
