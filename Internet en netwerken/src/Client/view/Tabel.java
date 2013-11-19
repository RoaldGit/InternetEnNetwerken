package Client.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Client.control.ClickEvent;
import Client.model.BeursModel;
/**
 * Deze klasse bouwt de tables op.
 * @author Roald en Stef
 * @since 9-11-2013
 * @version 0.1
 */
public class Tabel extends JTable {
	private BeursModel beursModel;
	private Object[][] rowData;
	private String[] colData;
	TableColumnModel tcm;

	/**
	 * De constructor voor de klasse Tabel.
	 */
	public Tabel() {
		super();

		addMouseListener(null);
	}

	/**
	 * Een tweede constructor
	 * @param row De data voor de rijen
	 * @param col De data voor de kolommen
	 * @param bModel Het beursmodel
	 * @param name De naam voor de tabel.
	 */
	public Tabel(Object[][] row, String[] col, BeursModel bModel, String name) {
		super(row, col);
		this.setName(name);

		rowData = row;
		colData = col;

		beursModel = bModel;

		setupRenderer();

		addMouseListener(new ClickEvent(beursModel));
	}

	/**
	 * De method rendert de kolommen.
	 */
	private void setupRenderer() {
		int cols = colData.length - 1;

		tcm = this.getColumnModel();

		tcm.getColumn(cols - 1).setCellRenderer(new NumberRenderer());
		tcm.getColumn(cols).setCellRenderer(new NumberRenderer());
	}

	/**
	 * Deze method geeft aan of een cel editbaar is of niet.
	 */
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	/**
	 * Met deze functie kun je de data aanpassen in de tabel.
	 * @param data Een 2D-array met data die in de rowData moet komen.
	 */
	public void changeData(Object[][] data) {
		rowData = data;

		DefaultTableModel dtm = new DefaultTableModel(rowData, colData);
		setModel(dtm);

		setupRenderer();
	}

	class NumberRenderer extends DefaultTableCellRenderer {
		NumberFormat formatter;

		public NumberRenderer() {
			super();
		}

		public void setValue(Object value) {
			Double number = 0.0;
			if (value != null)
				number = Double.parseDouble(value.toString());
			setText((value == null) ? "" : String.format("€ %,.2f", number));
		}
	}
}
