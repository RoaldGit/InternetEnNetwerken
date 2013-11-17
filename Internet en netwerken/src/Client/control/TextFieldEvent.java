package Client.control;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

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
		try {
			Document textFieldText = e.getDocument();
			String text = textFieldText.getText(0, textFieldText.getLength());
			text = "" + Integer.parseInt(text);
			beursModel.setAantal(text);
		} catch (Exception e1) {
			beursModel.setAantal("error");
		}
	}

	public void insertUpdate(DocumentEvent e) {
		beursModel.selected();
		try {
			Document textFieldText = e.getDocument();
			String text = textFieldText.getText(0, textFieldText.getLength());
			text = "" + Integer.parseInt(text);
			beursModel.setAantal(text);
		} catch (Exception e1) {
			beursModel.setAantal("error");
		}
	}
}
