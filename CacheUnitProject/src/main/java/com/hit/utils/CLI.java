package com.hit.utils;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class CLI extends java.util.Observable implements java.lang.Runnable {
	private Scanner reader;
	private PrintWriter writer;
	private String command;

	public CLI(java.io.InputStream in, java.io.OutputStream out) {
		reader = new Scanner(new InputStreamReader(in));
		writer = new PrintWriter(new OutputStreamWriter(out));
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	@Override
	public void run() {
		while (true) {
			// Wait for user's input.
			command = reader.nextLine();
			//As we want to allow the client to keep using the CLI
			//regardless of the server processing time for last command
			//We will be using new thread to execute every command.
			new Thread(() -> {
				// If supported command let the registered servers know and act accordingly.
				if (command.equals("start") || command.equals("stop")) {
					setChanged();
					notifyObservers();
				}
				// Let the user know the command is invalid.
				else {
					write("Invalid command,system only supports: start or stop.");
				}
			}).start();
		}
	}

	public void write(String string) {
		writer.println(string);
		writer.flush();
	}

}
