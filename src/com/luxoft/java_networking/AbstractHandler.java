package com.luxoft.java_networking;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;

public abstract class AbstractHandler {
	public void handle(final Socket socket, ExecutorService pool) {
		pool.execute(new Runnable() {
			@Override
			public void run() {
				SocketAddress socketAddress = socket.getRemoteSocketAddress();
				System.out.println("Connected to " + socketAddress);

				handleTask(socket);

				try {
					if (socket != null)
						socket.close();
				} catch (IOException e) {
				}

				System.out.println("Connection to " + socketAddress + " closed");
			}
		});
	}

	public abstract void handleTask(Socket socket);
}
