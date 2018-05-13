package com.hit.server;

import java.io.IOException;

import com.hit.utils.CLI;

public class CacheUnitServerDriver {

	public static void main(java.lang.String[] args) throws IOException {
		CLI cli = new CLI(System.in, System.out);
		Server server = new Server();
		server.start();
		cli.addObserver(server);
		new Thread(cli).start();
	}
}
