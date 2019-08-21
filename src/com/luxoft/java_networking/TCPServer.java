package com.luxoft.java_networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer<T extends AbstractHandler> implements Runnable {
	private AbstractHandler handler;
	private ServerSocket serverSocket;
	private ExecutorService pool;

	public TCPServer(int port, Class<T> handlerClass) throws Exception {
		handler = handlerClass.newInstance();
		serverSocket = new ServerSocket(port);
		pool = Executors.newCachedThreadPool();
	}
	
	public TCPServer(int port, T handlerObject) throws IOException {
		handler = handlerObject;
		serverSocket = new ServerSocket(port);
		pool = Executors.newCachedThreadPool();
	}

	@Override
	public void run() {
		try {
			while (true) {
				Socket socket = serverSocket.accept();
				handler.handle(socket, pool);
			}
		} catch (IOException e) {

		}
	}

	public void stopServer() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			System.err.println(e);
		}

		pool.shutdown();
	}
}
