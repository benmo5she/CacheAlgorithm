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
			command = reader.nextLine();
			if(command.equals("start") || command.equals("stop"))
			{
		        setChanged();
		        notifyObservers();
			}
			else
			{
				write("Not a valid command.");
			}
		}
	}

	public void write(String string) {
		writer.println(string);
	}

}
