package com.hit.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Observable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.hit.services.CacheUnitController;
import com.hit.utils.CLI;

//This class as the name implies represents the server that will execute the application(cacheUnit)
public class Server implements java.util.Observer {
	private boolean serverAlive;
	ServerSocket serverSocket;
	private CacheUnitController<String> controller;
	private static final int PORT_NUMBER = 5555;

	@Override
	public void update(Observable o, Object arg) {
		// We expect this method to execute when the observable(one of the registered
		// CLIs)
		// notifies there's been a change(user input) in the CLI state.
		CLI currentCLI = (CLI) o;
		switch (currentCLI.getCommand()) {
		// User is trying to start the server, before taking action check it's current
		// state.
		case "start":
			if (serverAlive) {
				currentCLI.write("Server is allready running");
			} else {
				currentCLI.write("Starting server...");
				start();
			}
			break;
		// User has chosen to stop the server,
		// before taking action check the server current state.
		case "stop":
			if (serverAlive) {
				currentCLI.write("Shutdown server");
				try {
					serverSocket.close();
					serverAlive = false;
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				currentCLI.write("Server is down");
			}
			break;
		}
	}

	// Start the server and listen to requests, when request accepted create new
	// socket accordingly.
	public void start() {
		Socket clientSocket = null;
		try {
			serverSocket = new ServerSocket(PORT_NUMBER);
			controller = new CacheUnitController<String>();
			serverAlive = true;
			while (serverAlive) {
				clientSocket = serverSocket.accept();
				HandleRequest<String> req = new HandleRequest<String>(clientSocket, controller);
				Executor executor = Executors.newCachedThreadPool();
				executor.execute(req);
			}
		} catch (SocketException ex) {
			// This exception is expected to occur when the CLI sends command to
			// shut down the server, as such no need for any further operations
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (clientSocket != null) {
					clientSocket.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
