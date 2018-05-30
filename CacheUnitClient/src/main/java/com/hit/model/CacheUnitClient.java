package com.hit.model;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class CacheUnitClient {
	String hostAddress;
	int port;

	public CacheUnitClient() {
		this.hostAddress = "127.0.0.1";
		this.port = 5555;
	}

	public CacheUnitClient(String host, int port) {
		this.hostAddress = host;
		this.port = port;
	}

	public java.lang.String send(String request) {
		String response;
		Socket clientSocket = null;
		DataOutputStream outToServer = null;
		BufferedReader inFromServer = null;
		try {
			clientSocket = new Socket(this.hostAddress, this.port);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			outToServer.writeBytes(request + '\n');
			response = inFromServer.readLine();
			clientSocket.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			response = "Encountered an error while querying the server.";
		} finally {
			if (clientSocket != null) {
				try {
					clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outToServer != null) {
				try {
					outToServer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inFromServer != null) {
				try {
					inFromServer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}
}
