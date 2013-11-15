package Client.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Client.control.AandelenLijst;
import Client.control.ClientConnection;
import Client.model.BeursModel;
import Client.model.UserModel;

public class BeursView extends JPanel implements Observer {
	private Tabel porto, buy, sell, buying, selling;
	private JScrollPane portoScroll, buyScroll, sellScroll, buyingScroll,
			sellingScroll;
	private UserModel userModel;
	private BeursModel beursModel;
	private ClientConnection connection;
	private String user;
	private String[] portoHeader, buyingHeader, sellingHeader, buyHeader,
			sellHeader, aandelen;
	private Object[][] dummyPorto, dummyBuying, dummySelling, dummyBuy,
			dummySell;
	private JLabel portoLabel, buyingLabel, sellingLabel, buyLabel, sellLabel;

	public BeursView(UserModel uModel, BeursModel bModel, ClientConnection con) {
		setLayout(null);

		userModel = uModel;
		beursModel = bModel;
		connection = con;

		beursModel.addObserver(this);
		userModel.addObserver(this);
		
		portoHeader = new String[] { "Aandeel", "Aantal", "Waarde",
				"Totale waarde" };
		buyingHeader = new String[] { "Aandeel", "Aantal", "Waarde",
				"Totale waarde" };
		sellingHeader = new String[] { "Aandeel", "Aantal", "Waarde",
				"Totale waarde" };
		buyHeader = new String[] { "Koper", "Aandeel", "Aantal", "Waarde",
				"Totale waarde" };
		sellHeader = new String[] { "Aanbieder", "Aandeel", "Aantal", "Waarde",
				"Totale waarde" };
		aandelen = new String[] { "Syntaxis", "LiNK", "WaTT" };

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
		


		porto = new Tabel(dummyPorto, portoHeader, beursModel);
		buying = new Tabel(dummyBuying, buyingHeader, beursModel);
		selling = new Tabel(dummySelling, sellingHeader, beursModel);
		buy = new Tabel(dummyBuy, buyHeader, beursModel);
		sell = new Tabel(dummySell, sellHeader, beursModel);

		portoScroll = new JScrollPane(porto);
		buyingScroll = new JScrollPane(buying);
		sellingScroll = new JScrollPane(selling);
		buyScroll = new JScrollPane(buy);
		sellScroll = new JScrollPane(sell);

		portoScroll.setBounds(5, 100, 350, 100);
		buyingScroll.setBounds(5, 250, 350, 100);
		sellingScroll.setBounds(5, 400, 350, 100);
		buyScroll.setBounds(375, 100, 400, 100);
		sellScroll.setBounds(375, 250, 400, 100);

		AandelenLijst aandelenLijst = new AandelenLijst(aandelen, beursModel);
		aandelenLijst.setBounds(400, 50, 200, 20);

		add(portoScroll);
		add(buyingScroll);
		add(sellingScroll);
		add(buyScroll);
		add(sellScroll);

		add(portoLabel);
		add(buyingLabel);
		add(sellingLabel);
		add(buyLabel);
		add(sellLabel);

		add(aandelenLijst);

		updateTables();
	}

	public void update(Observable obs, Object obj) {
		if (obs == beursModel) {
			if (obj instanceof String) {
				if (obj.equals("aandeelSelect")) {
					String aandeel = beursModel.getSelectedAandeel();
					buyLabel.setText("Inkooporders voor " + aandeel);
					sellLabel.setText("Verkooporders voor " + aandeel);
				}
			}
			if (obj instanceof Tabel) {
				if (obj != null)
					((Tabel) obj).clearSelection();
			}
		}
		if (obs == userModel) {
			if (obj.equals("loggedIn")) {
				updateTables();
			}
		}
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
	}

	public void updatePorto() {
		porto.changeData(beursModel.getPorto());
	}

	public void updateBuying() {
		buying.changeData(beursModel.getBuying());
	}

	public void updateSelling() {
		selling.changeData(beursModel.getSelling());
	}

	public void updateBuy() {
		buy.changeData(beursModel.getBuy());
	}

	public void updateSell() {
		sell.changeData(beursModel.getSell());
	}
}
