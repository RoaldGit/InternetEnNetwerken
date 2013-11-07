package Client.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Client.model.UserModel;

public class BeursView extends JPanel {
	private JTable porto, buy, sell, buying, selling;
	private JScrollPane portoScroll, buyScroll, sellScroll, buyingScroll,
			sellingScroll;
	private UserModel userModel;

	public BeursView(UserModel uModel) {
		this.userModel = uModel;
		this.setLayout(null);

		String[] portoHeader = { "Aandeel", "Aantal", "Aanschaf Prijs",
				"Totale waarde" };
		String[] buyingHeader = { "Aandeel", "Aantal", "Geboden Prijs",
				"Totale waarde" };
		String[] sellingHeader = { "Aandeel", "Aantal", "Vraagprijs",
				"Totale waarde" };
		String[] buyHeader = { "Koper", "Aandeel", "Aantal", "Geboden Prijs",
				"Totale waarde" };
		String[] sellHeader = { "Aanbieder", "Aandeel", "Aantal", "Vraagprijs",
				"Totale waarde" };

		Object[][] dummyPorto = { { "Syntaxis", 10, 5.00, 50.00 },
				{ "Watt", 5, 5.00, 25.00 } };
		Object[][] dummyBuying = { { "LiNK", 8, 5.00, 40.00 } };
		Object[][] dummySelling = { { "Syntaxis", 5, 5.10, 25.50 } };
		Object[][] dummyBuy = {
				{ userModel.getUser(), "Syntaxis", 20, 5.00, 100.00 },
				{ "User", "LiNK", 8, 5.00, 40.00 } };
		Object[][] dummySell = { { "User", "Syntaxis", 5, 5.10, 25.50 } };

		porto = new JTable(dummyPorto, portoHeader);
		buying = new JTable(dummyBuying, buyingHeader);
		selling = new JTable(dummySelling, sellingHeader);
		buy = new JTable(dummyBuy, buyHeader);
		sell = new JTable(dummySell, sellHeader);

		portoScroll = new JScrollPane(porto);
		buyingScroll = new JScrollPane(buying);
		sellingScroll = new JScrollPane(selling);
		buyScroll = new JScrollPane(buy);
		sellScroll = new JScrollPane(sell);

		portoScroll.setBounds(5, 100, 300, 100);
		buyingScroll.setBounds(5, 250, 300, 100);
		sellingScroll.setBounds(5, 400, 300, 100);
		buyScroll.setBounds(325, 10, 300, 100);
		sellScroll.setBounds(325, 125, 300, 100);

		add(portoScroll);
		add(buyingScroll);
		add(sellingScroll);
		add(buyScroll);
		add(sellScroll);
	}
}
