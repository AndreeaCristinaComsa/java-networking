package com.luxoft.java_networking.example11.rpc;

import com.luxoft.java_networking.TCPServer;

public class RPCServer {
	public static void main(String[] args) throws Exception {
		int portNumber = Integer.parseInt(args[0]);
		String service = args[1];

		Class<?> serviceClass = Class.forName(service);
		Object serviceObject = serviceClass.newInstance();
		RPCHandler handlerObject = new RPCHandler(serviceObject);
		
		TCPServer<RPCHandler> server = new TCPServer<>(portNumber, handlerObject);
		Thread t = new Thread(server);
		t.start();

		System.out.println("RPCServer started.");
		System.out.println("ENTER will stop the server.");

		System.in.read();

		server.stopServer();
		System.out.println("RPCServer shutting down.");
	}
}
