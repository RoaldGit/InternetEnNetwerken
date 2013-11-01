package server;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import server.NanoHTTPD.Response.Status;
import server.json.JSONObject;

public class NanoHTTPDchild extends NanoHTTPD {
	public NanoHTTPDchild(int port) {
		super(port);
	}

	@Override
	public Response serve(String uri, Method method,
			Map<String, String> header, Map<String, String> parms,
			Map<String, String> files) {
		JSONObject json = new JSONObject();

		json.put("headers", header);
		json.put("parms", parms);
		json.put("method", method);
		json.put("url:", uri);

		Response response = null;
		Method m = method;

		System.out.println(uri);
		// TODO Request handling
		if (m == Method.GET) {
			switch (uri) {
			case "/get":
				response = new Response(Status.OK, "application/json",
						json.toString(8));
				break;
			case "/post":
				response = new Response(Status.OK, MIME_HTML, "Posted");
				break;
			case "/file":
				try {
					InputStream is = new FileInputStream(
							"htdocs/images/zzzz.jpg");
					response = new Response(Status.OK, "image/jpeg", is);
				} catch (Exception e) {

				}
				break;
			default:
				response = new Response(Status.NOT_FOUND, MIME_HTML,
						"404 not found");
				break;
			}
		}

		if (m == Method.POST)
			response = new Response(Status.BAD_REQUEST, MIME_HTML,
					"Bad request");

		return response;
	}

}