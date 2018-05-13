package com.hit.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.Socket;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.hit.dm.DataModel;
import com.hit.services.CacheUnitController;

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
			  reader =
					    new BufferedReader(new InputStreamReader(socket.getInputStream()));
					   writer = new DataOutputStream(socket.getOutputStream());
			Type ref = new TypeToken<Request<DataModel<T>[]>>(){}.getType();
			String req = reader.readLine();
			Request<DataModel<T>[]> userRequest = new Gson().fromJson(req, ref);
			if (userRequest == null || userRequest.getHeaders() == null) {
				writer.writeBytes("JSON is invalid");
				return;
			}
			String action = userRequest.getHeaders().get("action");
			boolean actionCompleted = false;
			switch (action) {
			case "GET":
				GsonBuilder builder = new GsonBuilder();
				Gson gson = builder.create();
				String responseBody = gson.toJson(controller.get(userRequest.getBody()));
				Request<String> response = new Request<String>(userRequest.getHeaders(), responseBody);
				writer.writeBytes(response.toString());
				break;
			case "UPDATE":
				actionCompleted = controller.update(userRequest.getBody());
				writer.writeBytes(String.valueOf(actionCompleted));
				break;
			case "DELETE":
				actionCompleted = controller.delete(userRequest.getBody());
				writer.writeBytes(String.valueOf(actionCompleted));
				break;
			default:
				String error = "Invalid command accepted: " + action
						+ "\nCan only accept: GET,UPDATE,DELETE for this paramter.";
				writer.writeBytes(error);
			}
		} catch (IOException | JsonSyntaxException e) {
			if (writer != null) {
				try {
					writer.writeBytes(e.getMessage());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}
		finally
		{
			if(reader != null)
			{
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(writer != null)
			{
				
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(socket != null)
			{
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
