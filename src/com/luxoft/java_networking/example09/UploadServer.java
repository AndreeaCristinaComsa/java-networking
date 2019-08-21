package com.luxoft.java_networking.example09;

import com.luxoft.java_networking.TCPServer;

public class UploadServer {
	public static String FOLDER_NAME;
	
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("The program must get exactly 2 arguments: the port number and the folder");
			return;
		}
		
		int portNumber = Integer.valueOf(args[0]);
		FOLDER_NAME = args[1];
		
		TCPServer<UploadHandler> server = new TCPServer<>(portNumber, UploadHandler.class);
		Thread t = new Thread(server);
		t.start();
		
		System.out.println("EchoServer started.");
		System.out.println("ENTER will stop the server.");

		System.in.read();

		server.stopServer();
		System.out.println("EchoServer shutting down.");
	}
}
