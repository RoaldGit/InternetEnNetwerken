package Client.model;

import java.util.Observable;

/**
 * Deze klasse heeft gegevens over de user van het programma.
 * @author Roald en Stef
 * @since 14-11-2013
 * @version 0.1
 */
public class UserModel extends Observable {
	private String user, password;
	private double saldo, portoWaarde;

	/**
	 * De constructor voor de UserModel.
	 */
	public UserModel() {
		user = "";
		password = "";
		saldo = 0;
		portoWaarde = 0;
	}
	
	/**
	 * Deze method set de userdetails van de user.
	 * @param password Het wachtwoord
	 * @param user De username.
	 */
	public void setUserDetails(String password, String user) {
		this.user = user;
		this.password = password;
	}

	/**
	 * Deze method set de saldo van de user.
	 * @param d d is een double met daarin de saldo dat de user heeft.
	 */
	public void setSaldo(double d) {
		saldo = d;

		setChanged();
		notifyObservers("saldo");
	}
	
	/**
	 * Deze method kan de saldo van een user veranderen.
	 * @param d d is een double met de het bedrag dat bij het saldo wordt opgeteld.
	 */
	public void changeSaldo(double d) {
		saldo += d;

		setChanged();
		notifyObservers("saldo");
	}

	/**
	 * Deze method returned een String met de username.
	 * @return Returned een String met de username.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Deze method returned een String met de password.
	 * @return Returned een String met de password.
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Deze method returned een double met de saldo.
	 * @return Returned een een double met de saldo.
	 */
	public double getSaldo() {
		return saldo;
	}

	/**
	 * Deze method returned een double met de portowaarde.
	 * @return Returned een een double met de portowaarde.
	 */
	public double getPortoWaarde() {
		return portoWaarde;
	}
}
