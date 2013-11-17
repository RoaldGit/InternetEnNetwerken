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
	private String aandeelSelect, aantalAandelen;
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
		setChanged();
		notifyObservers("aandeelSelect");
		aantalAandelen = "0";
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

	public void refreshSelect() {
		tabelSelect.setRowSelectionInterval(row, row);
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
		int cols = tabelSelect.getColumnCount();
		int row = tabelSelect.getSelectedRow();
		int col = 0;

		if (cols == 5)
			col = 1;

		return (String) tabelSelect.getValueAt(row, col);
	}

	/**
	 * Deze functie set de porto.
	 * @param data Data is een 2D-array met daarin de data voor de porto.
	 */
	public void setPorto(Object[][] data) {
		porto = data;

		setChanged();
		notifyObservers("Aandelen");
	}

	/**
	 * Deze methode set de data voor het inkopen.
	 * @param data Data is een 2D-array met daarin de data voor de inkoop.
	 */
	public void setBuying(Object[][] data) {
		buying = data;

		setChanged();
		notifyObservers("Aandelen");
	}

	/**
	 * Deze methode set de data voor het verkopen.
	 * @param data Data is een 2D-array met daarin de data voor de verkoop
	 */
	public void setSelling(Object[][] data) {
		selling = data;

		setChanged();
		notifyObservers("Aandelen");
	}

	/**
	 * Deze methode set de data voor het kopen
	 * @param data Data is een 2D-array met daarin de data voor de koop
	 */
	public void setBuy(Object[][] data) {
		buy = data;

		setChanged();
		notifyObservers("Aandelen");
	}

	/**
	 * Deze methode set de data voor wat er verkocht is.
	 * @param data Data is een 2D-array met daarin de data voor wat er verkocht is.
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
	 * Deze method returned geselecteerd aandelen.
	 * @return Returned een geselecteerd aandeel.
	 */
	public String getSelectedAandeel() {
		return aandeelSelect;
	}

	/**
	 * Deze methode returned een 2D-array met de porto data.
	 * @return Returned een 2D-array met de porto data.
	 */
	public Object[][] getPorto() {
		return porto;
	}

	/**
	 * Deze methode returned een 2D-array met de aandelen die gekocht kunnen worden.
	 * @return Returned een 2D-array met de aandelen die gekocht kunnen worden.
	 */
	public Object[][] getBuying() {
		return buying;
	}

	/**
	 * Deze methode returned een 2D-array met de aandelen die verkocht worden.
	 * @return Returned een 2D-array met de aandelen die verkocht worden.
	 */
	public Object[][] getSelling() {
		return selling;
	}

	/**
	 * Deze methode returned een 2D-array van aandelen die gekocht kunnen worden
	 * @return Returned een 2D-array van aandelen die gekocht kunnen worden
	 */
	public Object[][] getBuy() {
		return buy;
	}

	/**
	 * Deze methode returned een 2D-array van verkochte aandelen
	 * @return Returned een 2D-array van verkochte aandelen.
	 */
	public Object[][] getSell() {
		return sell;
	}

	/**
	 * Deze methode set het aantal aandelen.
	 */
	public void setAantal(String text) {
		aantalAandelen = text;
	}

	/**
	 * Deze methode returned het aantal aandelen.
	 * @return Returned een string met het aantal aandelen.
	 */
	public String getAantalAandelen() {
		return aantalAandelen;
	}
}
