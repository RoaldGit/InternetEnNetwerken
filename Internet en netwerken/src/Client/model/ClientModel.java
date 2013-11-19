package Client.model;

import java.util.Observable;

/**
 * De klasse waarin de Clientgegevens in staan.
 * @author Roald en Stef
 * @since 14-11-2013
 * @version 0.1
 */
public class ClientModel extends Observable {
	private boolean connected, loggedIn;

	/**
	 * De constructor voor de ClientModel.
	 */
	public ClientModel() {
		connected = false;
		loggedIn = false;
	}

	/**
	 * Deze methode set of de client geconnect is.
	 * @param b b is een boolean die true of false geset kan worden.
	 */
	public void setConnected(Boolean b) {
		connected = b;
		setChanged();
		notifyObservers("connected");
	}

	/**
	 * Deze methode set of de client ingelogd is.
	 * @param b b is een boolean die true of false geset kan worden.
	 */
	public void setLoggedIn(boolean b) {
		loggedIn = b;
		setChanged();
		notifyObservers("logged");
	}

	/**
	 * Deze method returned of de client connected is.
	 * @return Returned een boolean.
	 */
	public boolean getConnected() {
		return connected;
	}

	/**
	 * Deze method returned of de client ingelogd is.
	 * @return Returned een boolean.
	 */
	public boolean getLoggedIn() {
		return loggedIn;
	}


}
