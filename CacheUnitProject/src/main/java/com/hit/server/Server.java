package com.hit.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.hit.services.CacheUnitController;
import com.hit.utils.CLI;

public class Server implements java.util.Observer {
	private boolean serverAlive;
	ServerSocket mysocket;
	private CacheUnitController<String> controller;

	@Override
	public void update(Observable o, Object arg) {
		CLI currentCLI = (CLI) o;
		switch (currentCLI.getCommand()) {
		case "start":
			if (serverAlive) {
				currentCLI.write("Server is allready running");
			} else {
				currentCLI.write("Starting server...");
				start();
			}
			break;
		case "stop":
			if (serverAlive) {
				currentCLI.write("Shutdown server");
				try {
					mysocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				currentCLI.write("Server is down");
			}
			break;
		}
	}

	public void start() {
		try {
			mysocket = new ServerSocket(5555);
			controller = new CacheUnitController<String>();
			serverAlive = true;
			while (serverAlive) {
				Socket connectionSocket = mysocket.accept();
				HandleRequest<String> req = new HandleRequest<String>(connectionSocket, controller);
				Executor executor = Executors.newCachedThreadPool();
				executor.execute(req);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
