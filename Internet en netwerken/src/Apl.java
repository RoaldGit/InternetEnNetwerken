import server.WebServer;
import Client.view.MainView;

public class Apl {
	private static boolean databaseTest = false;
	private static boolean serverTest = true;
	private static boolean clientTest = true;

	public static void main(String args[]) {
		if (databaseTest) {
			WebServer server = new WebServer(800, "internet", "root", "a");
			server.start();
		}

		if (serverTest) {
			WebServer server = new WebServer(800);
			server.start();
		}

		if (clientTest) {
			MainView client = new MainView();
		}
	}
}