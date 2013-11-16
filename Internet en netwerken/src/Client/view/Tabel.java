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

public class Tabel extends JTable {
	private BeursModel beursModel;
	private Object[][] rowData;
	private String[] colData;

	public Tabel() {
		super();

		addMouseListener(null);
	}

	public Tabel(Object[][] row, String[] col, BeursModel bModel, String name) {
		super(row, col);
		this.setName(name);

		rowData = row;
		colData = col;
		
		int cols = col.length - 1;

		TableColumnModel tcm = this.getColumnModel();

		tcm.getColumn(cols - 1).setCellRenderer(new NumberRenderer());
		tcm.getColumn(cols).setCellRenderer(new NumberRenderer());

		beursModel = bModel;

		addMouseListener(new ClickEvent(beursModel));
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}

	public void changeData(Object[][] data) {
		rowData = data;

		DefaultTableModel dtm = new DefaultTableModel(rowData, colData);
		setModel(dtm);
	}

	class NumberRenderer extends DefaultTableCellRenderer {
		NumberFormat formatter;

		public NumberRenderer() {
			super();
		}

		public void setValue(Object value) {
			setText((value == null) ? "" : String.format("€ %.2f", value));
		}
	}
}
