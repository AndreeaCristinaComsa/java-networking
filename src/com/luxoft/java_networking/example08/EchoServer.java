package com.luxoft.java_networking.example08;

import com.luxoft.java_networking.TCPServer;

public class EchoServer {
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println("The program must get exactly 1 argument: the port number");
			return;
		}

		int portNumber = Integer.valueOf(args[0]);

		TCPServer<EchoHandler> server = new TCPServer<>(portNumber, EchoHandler.class);
		Thread t = new Thread(server);
		t.start();
		
		System.out.println("EchoServer started.");
		System.out.println("ENTER will stop the server.");

		System.in.read();

		server.stopServer();
		System.out.println("EchoServer shutting down.");
	}
}
