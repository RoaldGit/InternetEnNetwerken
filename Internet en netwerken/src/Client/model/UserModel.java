package Client.model;

import java.util.Observable;

public class UserModel extends Observable {
	private String user, password;
	private double saldo, portoWaarde;

	public UserModel() {
		user = "";
		password = "";
		saldo = 0;
		portoWaarde = 0;
	}

	public void setUserDetails(String password, String user) {
		this.user = user;
		this.password = password;
	}

	public void setSaldo(double d) {
		saldo = d;

		setChanged();
		notifyObservers("saldo");
	}
	
	public void changeSaldo(double d) {
		saldo += d;

		setChanged();
		notifyObservers("saldo");
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
	
	public double getSaldo() {
		return saldo;
	}

	public double getPortoWaarde() {
		return portoWaarde;
	}
}
