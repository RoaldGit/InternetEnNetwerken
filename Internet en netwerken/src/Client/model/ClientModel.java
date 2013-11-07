package Client.model;

import java.util.Observable;

public class ClientModel extends Observable {
	private boolean connected, loggedIn;

	public ClientModel() {
		connected = false;
		loggedIn = false;
	}

	public void setConnected(Boolean b) {
		connected = b;
		setChanged();
		notifyObservers("connected");
	}

	public void setLoggedIn(boolean b) {
		loggedIn = b;
	}

	public boolean getConnected() {
		return connected;
	}

	public boolean getLoggedIn() {
		return loggedIn;
	}


}