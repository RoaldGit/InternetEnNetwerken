package Client.model;

import java.util.Observable;

import Client.view.Tabel;

public class BeursModel extends Observable {
	private String aandeelSelect;
	private Tabel tabelSelect = null;
	private Object[][] dummyPorto, dummyBuying, dummySelling, dummyBuy,
			dummySell;
	private String user;

	public BeursModel() {
		dummyPorto = new Object[][] { { "Syntaxis", 10, 5.00, 50.00 },
				{ "Watt", 5, 5.00, 25.00 } };
		dummyBuying = new Object[][] { { "LiNK", 8, 5.00, 40.00 } };
		dummySelling = new Object[][] { { "Syntaxis", 5, 5.00, 25.50 } };
		dummyBuy = new Object[][] { { "", "Syntaxis", 20, 5.00, 100.00 },
				{ "User", "LiNK", 8, 5.00, 40.00 } };
		dummySell = new Object[][] { { "", "Syntaxis", 5, 5.00, 25.50 } };
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
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPorto(Object[][] data) {
		dummyPorto = data;
	}

	public void setBuying(Object[][] data) {
		dummyBuying = data;
	}

	public void setSelling(Object[][] data) {
		dummySelling = data;
	}

	public void setBuy(Object[][] data) {
		dummyBuy = data;
	}

	public void setSell(Object[][] data) {
		dummySell = data;
	}

	public String getSelectedAandeel() {
		return aandeelSelect;
	}

	public Object[][] getPorto() {
		return dummyPorto;
	}

	public Object[][] getBuying() {
		return dummyBuying;
	}

	public Object[][] getSelling() {
		return dummySelling;
	}

	public Object[][] getBuy() {
		return dummyBuy;
	}

	public Object[][] getSell() {
		return dummySell;
	}
}
