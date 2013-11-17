package Client.control;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import Client.model.BeursModel;

public class TextFieldEvent implements DocumentListener {
	private BeursModel beursModel;

	public TextFieldEvent(BeursModel bModel) {
		beursModel = bModel;
	}
	public void changedUpdate(DocumentEvent e) {
		beursModel.selected();
	}

	public void removeUpdate(DocumentEvent e) {
		beursModel.selected();
	}

	public void insertUpdate(DocumentEvent e) {
		beursModel.selected();
	}
}
