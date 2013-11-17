package Client.model;

import java.util.Observable;

import Client.view.Tabel;

public class BeursModel extends Observable {
	private String aandeelSelect, aantalAandelen;
	private Tabel tabelSelect = null;
	private Object[][] porto, buying, selling, buy, sell;

	public BeursModel() {

	}

	public void setSelectedAandeel(String select) {
		aandeelSelect = select;
		setChanged();
		notifyObservers("aandeelSelect");
		aantalAandelen = "0";
	}
	
	public void setSelectedTable(Tabel select) {
		Tabel oldSelect = null;

		if (select != tabelSelect)
			oldSelect = tabelSelect;
		
		tabelSelect = select;

		setChanged();
		notifyObservers(oldSelect);

		selected();
	}

	public void selected() {
		setChanged();
		notifyObservers("select");
	}

	public String getAandeel() {
		int cols = tabelSelect.getColumnCount();
		int row = tabelSelect.getSelectedRow();
		int col = 0;

		if (cols == 5)
			col = 1;

		return (String) tabelSelect.getValueAt(row, col);
	}

	public void setPorto(Object[][] data) {
		porto = data;

		setChanged();
		notifyObservers("Aandelen");
	}

	public void setBuying(Object[][] data) {
		buying = data;

		setChanged();
		notifyObservers("Aandelen");
	}

	public void setSelling(Object[][] data) {
		selling = data;

		setChanged();
		notifyObservers("Aandelen");
	}

	public void setBuy(Object[][] data) {
		buy = data;

		setChanged();
		notifyObservers("Aandelen");
	}

	public void setSell(Object[][] data) {
		sell = data;

		setChanged();
		notifyObservers("Aandelen");
	}

	public Tabel getSelectedTabel() {
		return tabelSelect;
	}

	public String getSelectedAandeel() {
		return aandeelSelect;
	}

	public Object[][] getPorto() {
		return porto;
	}

	public Object[][] getBuying() {
		return buying;
	}

	public Object[][] getSelling() {
		return selling;
	}

	public Object[][] getBuy() {
		return buy;
	}

	public Object[][] getSell() {
		return sell;
	}

	public void setAantal(String text) {
		aantalAandelen = text;
	}

	public String getAantalAandelen() {
		return aantalAandelen;
	}
}
