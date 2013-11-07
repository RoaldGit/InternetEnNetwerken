package Client.control;

import javax.swing.JTable;

public class Tabel extends JTable {

	public Tabel() {
		super();
	}

	public Tabel(Object[][] row, String[] col) {
		super(row, col);
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}
}
