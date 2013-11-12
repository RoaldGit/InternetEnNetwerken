package Client.model;

import java.util.Observable;

import Client.control.Tabel;

public class BeursModel extends Observable {
	private String aandeelSelect;
	private Tabel tabelSelect = null;

	public BeursModel() {

	}

	public void setSelectedAandeel(String select) {
		aandeelSelect = select;
		System.out.println("BeursModel|setSelected: " + select);
		setChanged();
		notifyObservers("aandeelSelect");
	}
	
	public void setSelectedTable(Tabel select) {
		Tabel oldSelect = null;

		if (select != tabelSelect)
			oldSelect = tabelSelect;
		
		tabelSelect = select;
		setChanged();

		notifyObservers(oldSelect);
	}

	public String getSelectedAandeel() {
		return aandeelSelect;
	}
}
