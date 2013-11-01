package client.model;

import java.util.Observable;

public class UserModel extends Observable {
	private String user, password;

	public UserModel() {
		user = "";
		password = "";
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
}
