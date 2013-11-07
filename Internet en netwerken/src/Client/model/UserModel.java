package Client.model;

import java.util.Observable;

public class UserModel extends Observable {
	private String user, password;
	private double saldo;

	public UserModel() {
		user = "";
		password = "";
		saldo = 0;
	}

	public void setUserDetails(String user, String password) {
		this.user = user;
		this.password = password;

		setChanged();
		notifyObservers("loggedIn");
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
}
