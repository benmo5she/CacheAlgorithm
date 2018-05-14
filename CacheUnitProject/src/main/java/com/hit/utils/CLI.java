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
		while(true)
		{
			//Wait for user's input.
			command = reader.nextLine();
			//If supported command let the registered servers know and act accordingly.
			if(command.equals("start") || command.equals("stop"))
			{
		        setChanged();
		        notifyObservers();
			}
			//Let the user know the command is invalid.
			else
			{
				write("Not a valid command,system only supports: start or stop.");
			}
		}
	}

	public void write(String string) {
		writer.println(string);
		writer.flush();
	}

}
