package Client.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Client.control.Tabel;
import Client.model.UserModel;

public class BeursView extends JPanel implements Observer {
	private Tabel porto, buy, sell, buying, selling;
	private JScrollPane portoScroll, buyScroll, sellScroll, buyingScroll,
			sellingScroll;
	private UserModel userModel;
	private String user;
	private String[] portoHeader, buyingHeader, sellingHeader, buyHeader,
			sellHeader;
	private Object[][] dummyPorto, dummyBuying, dummySelling, dummyBuy,
			dummySell;
	private JLabel portoLabel, buyingLabel, sellingLabel, buyLabel, sellLabel;

	public BeursView(UserModel uModel) {
		this.userModel = uModel;
		this.setLayout(null);

		portoHeader = new String[] { "Aandeel", "Aantal", "Aanschaf Prijs",
				"Totale waarde" };
		buyingHeader = new String[] { "Aandeel", "Aantal", "Geboden Prijs",
				"Totale waarde" };
		sellingHeader = new String[] { "Aandeel", "Aantal", "Vraagprijs",
				"Totale waarde" };
		buyHeader = new String[] { "Koper", "Aandeel", "Aantal",
				"Geboden Prijs",
				"Totale waarde" };
		sellHeader = new String[] { "Aanbieder", "Aandeel", "Aantal",
				"Vraagprijs",
				"Totale waarde" };

		portoLabel = new JLabel("Uw Portofeuille:");
		buyingLabel = new JLabel("Uw inkooporders:");
		sellingLabel = new JLabel("Uw verkooporders:");
		buyLabel = new JLabel("Inkooporders voor -aandeel-");
		sellLabel = new JLabel("Verkooporders voor -aandeel-");

		portoLabel.setBounds(5, 80, 200, 20);
		buyingLabel.setBounds(5, 230, 200, 20);
		sellingLabel.setBounds(5, 380, 200, 20);
		buyLabel.setBounds(375, 80, 200, 20);
		sellLabel.setBounds(375, 230, 200, 20);

		add(portoLabel);
		add(buyingLabel);
		add(sellingLabel);
		add(buyLabel);
		add(sellLabel);

		updateTables();
	}

	public void update(Observable arg0, Object arg1) {

	}

	public void updateTables() {
		updateTableData();

		updatePorto();
		updateBuying();
		updateSelling();
		updateBuy();
		updateSell();
	}

	public void updateTableData() {
		user = userModel.getUser();
		dummyPorto = new Object[][] { { "Syntaxis", 10, 5.00, 50.00 },
				{ "Watt", 5, 5.00, 25.00 } };
		dummyBuying = new Object[][] { { "LiNK", 8, 5.00, 40.00 } };
		dummySelling = new Object[][] { { "Syntaxis", 5, 5.10, 25.50 } };
		dummyBuy = new Object[][] { { user, "Syntaxis", 20, 5.00, 100.00 },
				{ "User", "LiNK", 8, 5.00, 40.00 } };
		dummySell = new Object[][] { { user, "Syntaxis", 5, 5.10, 25.50 } };
	}

	public void updatePorto() {
		if (porto != null)
			remove(portoScroll);

		porto = new Tabel(dummyPorto, portoHeader);
		portoScroll = new JScrollPane(porto);
		portoScroll.setBounds(5, 100, 350, 100);

		add(portoScroll);
	}

	public void updateBuying() {
		if (buying != null)
			remove(buyingScroll);

		buying = new Tabel(dummyBuying, buyingHeader);
		buyingScroll = new JScrollPane(buying);
		buyingScroll.setBounds(5, 250, 350, 100);

		add(buyingScroll);
	}

	public void updateSelling() {
		if (selling != null)
			remove(sellingScroll);

		selling = new Tabel(dummySelling, sellingHeader);
		sellingScroll = new JScrollPane(selling);
		sellingScroll.setBounds(5, 400, 350, 100);

		add(sellingScroll);
	}

	public void updateBuy() {
		if (buy != null)
			remove(buyScroll);

		buy = new Tabel(dummyBuy, buyHeader);
		buyScroll = new JScrollPane(buy);
		buyScroll.setBounds(375, 100, 400, 100);

		add(buyScroll);
	}

	public void updateSell() {
		if (sell != null)
			remove(sellScroll);

		sell = new Tabel(dummySell, sellHeader);
		sellScroll = new JScrollPane(sell);
		sellScroll.setBounds(375, 250, 400, 100);

		add(sellScroll);
	}
}
