import server.WebServer;
import Client.view.MainView;

public class Apl {
	private static boolean serverTest = true;
	private static boolean clientTest = false;
	private static boolean databaseTest = false;

	public static void main(String args[]) {
		if (serverTest) {
			// NanoHTTPDchild server = new NanoHTTPDchild(8080);
			// try {
			// server.start();
			// } catch (Exception e) {
			// System.out.println(e);
			// System.out.println("Server boot");
			// }
			WebServer server = new WebServer(800);
		}

		if (clientTest) {
			MainView client = new MainView();
		}

		if (databaseTest)
			System.out.println("lol");
	}
}