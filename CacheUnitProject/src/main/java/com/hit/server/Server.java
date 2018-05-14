package com.hit.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.hit.services.CacheUnitController;
import com.hit.utils.CLI;
//This class as the name implies represents the server that will execute the application(cacheUnit)
public class Server implements java.util.Observer {
	private boolean serverAlive;
	ServerSocket mysocket;
	private CacheUnitController<String> controller;

	@Override
	public void update(Observable o, Object arg) {
		//We expect this method to execute when the observable(one of the registered CLIs)
		//notifies there's been a change(user input) in the CLI state.
		CLI currentCLI = (CLI) o;
		switch (currentCLI.getCommand()) {
		//User is trying to start the server, before taking action check it's current state.
		case "start":
			if (serverAlive) {
				currentCLI.write("Server is allready running");
			} else {
				currentCLI.write("Starting server...");
				start();
			}
			break;
			//User has chosen to stop the server, 
			//before taking action check the server current state.
		case "stop":
			if (serverAlive) {
				currentCLI.write("Shutdown server");
				try {					
					mysocket.close();
					serverAlive = false;
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
//Start the server and listen to requests, when request accepted create new socket accordingly.
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
