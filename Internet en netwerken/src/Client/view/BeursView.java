package Client.view;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import Client.control.AandelenLijst;
import Client.control.BeursControl;
import Client.control.ButtonControl;
import Client.control.ClientConnection;
import Client.control.TextFieldEvent;
import Client.model.BeursModel;
import Client.model.ClientModel;
import Client.model.UserModel;
/**
 * Deze klasse bouwt de beursview op.
 * @author Roald en Stef
 * @since 9-11-2013
 * @version 0.1
 */
public class BeursView extends JPanel implements Observer {
	private Tabel porto, buy, sell, buying, selling, selected;
	private JScrollPane portoScroll, buyScroll, sellScroll, buyingScroll,
			sellingScroll;
	private UserModel userModel;
	private ClientModel clientModel;
	private BeursModel beursModel;
	private ClientConnection connection;
	private String[] portoHeader, buyingHeader, sellingHeader, buyHeader,
			sellHeader, aandelen;
	private Object[][] dummyPorto, dummyBuying, dummySelling, dummyBuy,
			dummySell;
	private JLabel portoLabel, buyingLabel, sellingLabel, buyLabel, sellLabel,
			aandeelLabel, prijsLabel, aantalLabel,totaalLabel;;
	private JTextField aandeelVeld, prijsVeld, totaalVeld, aantalVeld;
	private JPanel details;
	private BeursControl beursControl;
	private JButton buyButton, sellButton, changeButton, cancelButton,
			logOutButton;
	private TextFieldEvent textFieldEvent;

	/**
	 * De constructor voor de beursview.
	 * @param uModel Het Usermodel dat gebruikt wordt.
	 * @param bModel Het beursmodel dat gebruikt wordt.
	 * @param con De connectie met de server die gebruikt wordt.
	 * @param bControl Het beurscontrol dat gebruikt wordt.
	 */
	public BeursView(UserModel uModel, BeursModel bModel, ClientConnection con,
			BeursControl bControl, ClientModel cModel) {
		setLayout(null);

		userModel = uModel;
		clientModel = cModel;
		beursModel = bModel;
		connection = con;
		beursControl = bControl;

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

		aandelen = connection.getAandelen();

		portoLabel = new JLabel("Uw Portofeuille:");
		buyingLabel = new JLabel("Uw inkooporders:");
		sellingLabel = new JLabel("Uw verkooporders:");
		buyLabel = new JLabel("Inkooporders voor -aandeel-");
		sellLabel = new JLabel("Verkooporders voor -aandeel-");

		portoLabel.setBounds(5, 80, 200, 20);
		buyingLabel.setBounds(5, 230, 200, 20);
		sellingLabel.setBounds(5, 380, 200, 20);
		buyLabel.setBounds(365, 230, 200, 20);
		sellLabel.setBounds(365, 380, 200, 20);

		porto = new Tabel(dummyPorto, portoHeader, beursModel, "porto");
		buying = new Tabel(dummyBuying, buyingHeader, beursModel, "buying");
		selling = new Tabel(dummySelling, sellingHeader, beursModel, "selling");
		buy = new Tabel(dummyBuy, buyHeader, beursModel, "buy");
		sell = new Tabel(dummySell, sellHeader, beursModel, "sell");

		portoScroll = new JScrollPane(porto);
		buyingScroll = new JScrollPane(buying);
		sellingScroll = new JScrollPane(selling);
		buyScroll = new JScrollPane(buy);
		sellScroll = new JScrollPane(sell);

		portoScroll.setBounds(5, 100, 700, 100);
		buyingScroll.setBounds(5, 250, 350, 100);
		sellingScroll.setBounds(5, 400, 350, 100);
		buyScroll.setBounds(365, 250, 415, 100);
		sellScroll.setBounds(365, 400, 415, 100);

		details = new JPanel(null);
		details.setBounds(5, 500, 780, 260);
		details.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

		aandeelLabel = new JLabel("Aandeel: ");
		prijsLabel = new JLabel("Prijs per aandeel: ");
		aantalLabel = new JLabel("Aantal aandelen: ");
		totaalLabel = new JLabel("Totaal prijs: ");

		aandeelVeld = new JTextField("");
		prijsVeld = new JTextField("");
		aantalVeld = new JTextField("");
		totaalVeld = new JTextField("");

		buyButton = new JButton("Koop dit aandeel");
		sellButton = new JButton("Verkoop dit aandeel");
		changeButton = new JButton("Verander order");
		cancelButton = new JButton("Verwijder order");
		logOutButton = new JButton("Log uit");

		buyButton.addActionListener(new ButtonControl("buy", connection,
				beursControl, beursModel, userModel, clientModel));
		sellButton.addActionListener(new ButtonControl("sell", connection,
				beursControl, beursModel, userModel, clientModel));
		changeButton.addActionListener(new ButtonControl("change", connection,
				beursControl, beursModel, userModel, clientModel));
		cancelButton.addActionListener(new ButtonControl("cancel", connection,
				beursControl, beursModel, userModel, clientModel));
		logOutButton.addActionListener(new ButtonControl("logOut", connection,
				beursControl, beursModel, userModel, clientModel));

		aandeelVeld.setEditable(false);
		prijsVeld.setEditable(false);
		aantalVeld.setEditable(false);
		totaalVeld.setEditable(false);

		aandeelLabel.setBounds(5, 5, 200, 20);
		prijsLabel.setBounds(5, 30, 200, 20);
		aantalLabel.setBounds(5, 55, 200, 20);
		totaalLabel.setBounds(5, 80, 200, 20);

		aandeelVeld.setBounds(175, 5, 100, 20);
		prijsVeld.setBounds(175, 30, 100, 20);
		aantalVeld.setBounds(175, 55, 100, 20);
		totaalVeld.setBounds(175, 80, 100, 20);

		buyButton.setBounds(300, 10, 400, 20);
		sellButton.setBounds(300, 35, 400, 20);
		changeButton.setBounds(300, 60, 400, 20);
		cancelButton.setBounds(300, 85, 400, 20);
		logOutButton.setBounds(600, 10, 100, 20);

		buyButton.setEnabled(false);
		sellButton.setEnabled(false);
		changeButton.setEnabled(false);
		cancelButton.setEnabled(false);

		textFieldEvent = new TextFieldEvent(beursModel);
		aantalVeld.getDocument().addDocumentListener(textFieldEvent);

		AandelenLijst aandelenLijst = new AandelenLijst(aandelen, beursModel,
				connection, userModel);
		aandelenLijst.setBounds(365, 210, 200, 20);

		details.add(aandeelLabel);
		details.add(prijsLabel);
		details.add(aantalLabel);
		details.add(totaalLabel);

		details.add(aandeelVeld);
		details.add(prijsVeld);
		details.add(aantalVeld);
		details.add(totaalVeld);

		details.add(buyButton);
		details.add(sellButton);
		details.add(changeButton);
		details.add(cancelButton);

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

		add(details);
		add(aandelenLijst);
		add(logOutButton);

		updateTables();

		portoSelected();
	}

	/**
	 * Deze method wordt aangeroepen als de observable aangeeft dat er een verandering heeft plaats gevonden.
	 */
	public void update(Observable obs, Object obj) {
		if (obs == beursModel) {
			if (obj instanceof String) {
				if (obj.equals("aandeelSelect")) {
					String aandeel = beursModel.getSelectedAandeel();
					buyLabel.setText("Inkooporders voor " + aandeel);
					sellLabel.setText("Verkooporders voor " + aandeel);
					aandeelVeld.setText(aandeel);
					aantalVeld.setText("");
				}
				if (obj.equals("Aandelen"))
					updateTables();
				if (obj.equals("select")) {
					Tabel selected = beursModel.getSelectedTabel();
					if (selected != null) {
						aantalVeld.setEditable(false);

						if (selected == porto)
							portoSelected();
						if (selected == buying)
							buyingSelected();
						if (selected == selling)
							sellingSelected();
						if (selected == buy)
							buySelected();
						if (selected == sell)
							sellSelected();

						int col = 1;
						int row = selected.getSelectedRow();

						if (selected == buy || selected == sell)
							col = 1;
						else
							col = 0;

						try {
							aandeelVeld.setText((String) selected.getValueAt(row,
									col++));
							prijsVeld.setText(String.format("€ %,.2f", Double
									.parseDouble((String) selected.getValueAt(row,
											++col))));
						} catch(Exception e) {
							aandeelVeld.setText("");
							prijsVeld.setText(String.format("€ %,.2f",
									beursModel.getAandeelPrijs()));
							aantalVeld.setText("");
							clearSelected();
						}

						if (!aandeelVeld.getText().equals(""))
							aantalVeld.setEditable(true);

						double prijs = 0;
						try {
							prijs = Double.parseDouble((String) selected
								.getValueAt(row, col));
						} catch (Exception e) {
							// prijs = Double.parseDouble(prijsVeld.getText());
						}

						int aantal = 0;
						String text = aantalVeld.getText();

						try {
							aantalVeld.setBackground(Color.white);
							aantal = Integer.parseInt(text);
						} catch (Exception e) {
							aantalVeld.setBackground(Color.red);
						}
						totaalVeld.setText(String.format("€ %,.2f", prijs
								* aantal));
					} else
						portoSelected();
					if (obj.equals("selectClear")) {
						aandeelVeld.setText("");
						prijsVeld.setText("");
						aantalVeld.setText("");
						clearSelected();
					}
				}
			}

			if (obj instanceof Tabel) {
				if (obj != null)
					((Tabel) obj).clearSelection();
			}
		}

		if (obs == userModel)
			if (obj.equals("loggedIn"))
				updateTables();
	}

	public void clearSelected() {
		buyButton.setEnabled(false);
		sellButton.setEnabled(false);
		changeButton.setEnabled(false);
		cancelButton.setEnabled(false);
	}

	public void sellSelected() {
		clearSelected();
		buyButton.setEnabled(true);
	}

	public void buySelected() {
		clearSelected();
		sellButton.setEnabled(true);
	}

	public void sellingSelected() {
		clearSelected();
		changeButton.setEnabled(true);
		cancelButton.setEnabled(true);
	}

	public void buyingSelected() {
		clearSelected();
		changeButton.setEnabled(true);
		cancelButton.setEnabled(true);
	}

	public void portoSelected() {
		clearSelected();
		buyButton.setEnabled(true);
		sellButton.setEnabled(true);
	}

	public void updateTables() {
		updatePorto();
		updateBuying();
		updateSelling();
		updateBuy();
		updateSell();
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
