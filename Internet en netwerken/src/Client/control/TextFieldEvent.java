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
	}

	public void insertUpdate(DocumentEvent e) {
		beursModel.selected();
		try {
			Document textFieldText = e.getDocument();
			String text = textFieldText.getText(0, textFieldText.getLength());
			beursModel.setAantal(text);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
