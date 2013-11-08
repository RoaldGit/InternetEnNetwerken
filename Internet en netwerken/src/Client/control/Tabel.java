package Client.control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

public class Tabel extends JTable {
	public Tabel() {
		super();

		addMouseListener(new ClickEvent());
	}

	public Tabel(Object[][] row, String[] col) {
		super(row, col);
		int cols = col.length - 1;
		TableColumnModel tcm = this.getColumnModel();
		tcm.getColumn(cols - 1).setCellRenderer(new NumberRenderer());
		tcm.getColumn(cols).setCellRenderer(new NumberRenderer());

		addMouseListener(new ClickEvent());
	}

	public boolean isCellEditable(int row, int column) {
		return false;
	}

	class NumberRenderer extends DefaultTableCellRenderer {
		NumberFormat formatter;

		public NumberRenderer() {
			super();
		}

		public void setValue(Object value) {
			if (formatter == null) {
				formatter = NumberFormat.getCurrencyInstance(Locale.GERMANY);
			}
			setText((value == null) ? "" : String.format("€ %.2f", value));
		}
	}

	class ClickEvent extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			JTable source = (JTable) e.getSource();
			int row = source.getSelectedRow();
			int cols = source.getColumnCount();

			for (int i = 0; i < cols; i++)
				System.out.println(source.getColumnName(i) + ": "
						+ source.getValueAt(row, i));
		}
	}
}
