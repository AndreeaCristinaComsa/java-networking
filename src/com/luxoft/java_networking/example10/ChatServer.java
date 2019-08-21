package com.luxoft.java_networking.example10;

import com.luxoft.java_networking.TCPServer;

public class ChatServer {
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println("The program must get exactly 1 argument: the port number");
			return;
		}
		
		int portNumber = Integer.valueOf(args[0]);
		
		TCPServer<ChatHandler> server = new TCPServer<>(portNumber, ChatHandler.class);
		Thread t = new Thread(server);
		t.start();

		System.out.println("ChatServer started.");
		System.out.println("ENTER will stop the server.");

		System.in.read();

		server.stopServer();
		System.out.println("ChatServer shutting down.");
	}
}
