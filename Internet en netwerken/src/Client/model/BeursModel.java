package Client.model;

import java.util.Observable;

import Client.view.Tabel;

/**
 * 
 * @author Roald en Stef
 * @since 14-11-2013
 * @version 0.1
 */
public class BeursModel extends Observable {
	private String aandeelSelect, aantalAandelen, selectedTabel;
	private double aandeelPrijs;
	private Tabel tabelSelect = null;
	int row;

	private Object[][] porto, buying, selling, buy, sell;

	/**
	 * De constructor.
	 */
	public BeursModel() {

	}

	/**
	 * De methode geeft aan welk aandeel geselecteerd is.
	 * @param select De naam van het aandeel dat geselecteerd is.
	 */
	public void setSelectedAandeel(String select) {
		aandeelSelect = select;
		aantalAandelen = "0";

		setChanged();
		notifyObservers("aandeelSelect");
	}
	
	/**
	 * Deze methode selecteert een table
	 * @param select select is een table die geselecteerd is.
	 */
	public void setSelectedTable(Tabel select) {
		Tabel oldSelect = null;

		if (select != tabelSelect)
			oldSelect = tabelSelect;
		
		tabelSelect = select;

		row = tabelSelect.getSelectedRow();

		setChanged();
		notifyObservers(oldSelect);

		selected();
	}
	
	public void setSelectedTable(String table) {
		selectedTabel = table;
	}

	public String getSelectedTable() {
		return selectedTabel;
	}

	public void refreshSelect() {
		try {
			tabelSelect.setRowSelectionInterval(row, row);
		} catch (Exception e) {
			clearSelect();
		}
		setChanged();
		notifyObservers("select");
	}

	public void clearSelect() {
		if (tabelSelect != null)
			tabelSelect.clearSelection();
		tabelSelect = null;
		setChanged();
		notifyObservers("select");
	}

	public void selected() {
		setChanged();
		notifyObservers("select");
	}

	/**
	 * Deze methode geeft een aandeel terug.
	 * @return returned een string met daarin de naam van het aandeel.
	 */
	public String getAandeel() {
		try {
			int cols = tabelSelect.getColumnCount();
			int row = tabelSelect.getSelectedRow();
			int col = 0;

			if (cols == 5)
				col = 1;

			return (String) tabelSelect.getValueAt(row, col);
		} catch (Exception e) {
			return aandeelSelect;
		}
	}

	/**
	 * Deze functie set de data van de portefeuille van de user.
	 * 
	 * @param data
	 *            Data is een 2D-array met daarin de data van de portefeuille
	 *            van de user
	 */
	public void setPorto(Object[][] data) {
		porto = data;

		setChanged();
		notifyObservers("Aandelen");
	}

	/**
	 * Deze methode set de data voor kooporders van de user.
	 * 
	 * @param data
	 *            Data is een 2D-array met daarin de data voor kooporders van de
	 *            user.
	 */
	public void setBuying(Object[][] data) {
		buying = data;

		setChanged();
		notifyObservers("Aandelen");
	}

	/**
	 * Deze methode set de data voor verkooporders van de user.
	 * 
	 * @param data
	 *            Data is een 2D-array met daarin de data voor verkooporders van
	 *            de user
	 */
	public void setSelling(Object[][] data) {
		selling = data;

		setChanged();
		notifyObservers("Aandelen");
	}

	/**
	 * Deze methode set de data voor kooporders voor het geselecteerde aandeel
	 * 
	 * @param data
	 *            Data is een 2D-array met daarin de data voor kooporders voor
	 *            het geselecteerde aandeel.
	 */
	public void setBuy(Object[][] data) {
		buy = data;

		setChanged();
		notifyObservers("Aandelen");
	}

	/**
	 * Deze methode set de data voor verkooporders voor het geselecteerde
	 * aandeel
	 * 
	 * @param data
	 *            Data is een 2D-array met daarin de data voor verkooporders
	 *            voor het geselecteerde aandeel.
	 */
	public void setSell(Object[][] data) {
		sell = data;

		setChanged();
		notifyObservers("Aandelen");
	}

	/**
	 * Deze methode returned een geselecteerde tabel.
	 * @return Returned een table.
	 */
	public Tabel getSelectedTabel() {
		return tabelSelect;
	}

	/**
	 * Deze method returned het, met de combobox, geselecteerd aandeel.
	 * 
	 * @return Returned een geselecteerde aandeel.
	 */
	public String getSelectedAandeel() {
		return aandeelSelect;
	}

	/**
	 * Deze methode returned een 2D-array met de portefeuille van de user.
	 * 
	 * @return Returned een 2D-array met de portefeuille van de user
	 */
	public Object[][] getPorto() {
		return porto;
	}

	/**
	 * Deze methode returned een 2D-array met kooporders van de user.
	 * 
	 * @return Returned een 2D-array met de kooporders van de user.
	 */
	public Object[][] getBuying() {
		return buying;
	}

	/**
	 * Deze methode returned een 2D-array met verkooporders van de user.
	 * 
	 * @return Returned een 2D-array met verkooporders van de user.
	 */
	public Object[][] getSelling() {
		return selling;
	}

	/**
	 * Deze methode returned een 2D-array van kooporders
	 * 
	 * @return Returned een 2D-array van kooporders.
	 */
	public Object[][] getBuy() {
		return buy;
	}

	/**
	 * Deze methode returned een 2D-array van verkooporders
	 * 
	 * @return Returned een 2D-array van verkooporders.
	 */
	public Object[][] getSell() {
		return sell;
	}

	/**
	 * Deze methode set het aantal aandelen dat is ingevult in het textfield
	 * (aantalVeld) in beursView.
	 */
	public void setAantal(String text) {
		aantalAandelen = text;
	}

	/**
	 * Deze methode returned het aantal aandelen dat is ingevult in het
	 * textfield (aantalVeld) in beursView.
	 * 
	 * @return Returned een string met het aantal aandelen.
	 */
	public String getAantalAandelen() {
		return aantalAandelen;
	}

	public double getAandeelPrijs() {
		return aandeelPrijs;
	}

	public void setAandeelPrijs(double aandeelPrijs) {
		this.aandeelPrijs = aandeelPrijs;
	}
}
