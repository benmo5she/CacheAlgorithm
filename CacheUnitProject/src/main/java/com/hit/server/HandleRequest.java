package com.hit.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.hit.dm.DataModel;
import com.hit.services.CacheUnitController;

//This class will try to parse a Request object from the input stream accepted from the socket.
//If said parsing is successful, it will operate it's controller according to the headers accepted in the request.
public class HandleRequest<T> implements java.lang.Runnable {
	private CacheUnitController<T> controller;
	private Socket socket;

	public HandleRequest(Socket s, CacheUnitController<T> controller) {
		this.controller = controller;
		this.socket = s;
	}

	@Override
	public void run() {
		BufferedReader reader = null;
		DataOutputStream writer = null;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new DataOutputStream(socket.getOutputStream());
			Type ref = new TypeToken<Request<DataModel<T>[]>>() {
			}.getType();
			// Start reading the input accepted from the socket input stream.
			String req = reader.readLine();
			// Try parsing said input to proper Request object
			logMessage("Accepted message from client:\n" + req);
			Request<DataModel<T>[]> userRequest = new Gson().fromJson(req, ref);
			if (userRequest == null || userRequest.getHeaders() == null) {
				writer.writeBytes("JSON is invalid");
				return;
			}
			String action = userRequest.getHeaders().get("action");
			boolean actionCompleted = false;
			// Decide which action should the controller take.
			switch (action) {
			case "GET":
				GsonBuilder builder = new GsonBuilder();
				Gson gson = builder.create();
				String responseBody = gson.toJson(controller.get(userRequest.getBody()));
				// Since the format of the response is identical to the request,
				// we can reuse the Request object to represent the response returned
				// from the controller
				Request<String> response = new Request<String>(userRequest.getHeaders(), responseBody);
				writeToStreamAndLog(writer, response.toString());
				break;
			case "UPDATE":
				actionCompleted = controller.update(userRequest.getBody());
				// Return to the output stream the response from the controller as
				// string(true/false)
				writeToStreamAndLog(writer, String.valueOf(actionCompleted));
				break;
			case "DELETE":
				actionCompleted = controller.delete(userRequest.getBody());
				writeToStreamAndLog(writer, String.valueOf(actionCompleted));
				break;
			// If we reached this point, it means the user has sent unsupported command in
			// the header.
			// Return appropriate message to the client providing current supported actions.
			default:
				String error = "Invalid command accepted: " + action
						+ "\nCan only accept: GET,UPDATE,DELETE for this paramter.";
				System.out.println("Message to client:\n" + error);
				writeToStreamAndLog(writer, error);
			}
		} catch (IOException | JsonSyntaxException e) {
			if (writer != null) {
				writeToStreamAndLog(writer, e.getMessage());
			}
			// Start cleaning up
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (writer != null) {

				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void writeToStreamAndLog(DataOutputStream outputStream, String message) {
		logMessage("Message to client:\n" + message);
		try {
			outputStream.writeBytes(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void logMessage(String message)
	{
		System.out.println("[" + LocalDateTime.now() + "] " + message);
	}
}
