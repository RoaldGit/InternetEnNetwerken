package Client.model;

import java.util.Observable;

import Client.view.Tabel;

public class BeursModel extends Observable {
	private String aandeelSelect;
	private Tabel tabelSelect = null;
	private Object[][] porto, buying, selling, buy, sell;
	private String user;

	public BeursModel() {

	}

	public void setSelectedAandeel(String select) {
		aandeelSelect = select;
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

		selected();
	}

	public void selected() {
		setChanged();
		notifyObservers("select");
	}

	public void setUser(String user) {
		this.user = user;
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

	public String getUser() {
		return user;
	}
}
