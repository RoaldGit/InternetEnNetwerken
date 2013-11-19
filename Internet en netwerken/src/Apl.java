import server.WebServer;
import Client.view.MainView;

/**
 * Deze klasse start alles op, zowel de server als het client programma.
 * 
 * @author Roald en Stef
 * @since 10-11-2013
 * @version 0.1
 */
public class Apl {
	private static boolean databaseTest = true;
	private static boolean serverTest = true;
	private static boolean clientTest = true;
	private static String username = "root", pass = "a";

	public static void main(String args[]) {
		if (databaseTest) {
			WebServer server = new WebServer(800, "internet", username, pass);
			server.start();
		}

		else if (serverTest) {
			WebServer server = new WebServer(800);
			server.start();
		}

		if (clientTest) {
			MainView client = new MainView();
		}
	}
}