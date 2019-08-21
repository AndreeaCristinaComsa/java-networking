package com.luxoft.java_networking.example06;
import com.luxoft.java_networking.example07.ThreadPoolingServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer2 {
	private int port;
	private ExecutorService pool;

	public EchoServer2(int port) {
		this.port = port;
	}

	public void startServer() {
//		try (ServerSocket serverSocket = new ServerSocket(port)) {
//			System.out.println("EchoServer2 started on " + serverSocket.getLocalSocketAddress());
//			while (true) {
//				Socket socket = serverSocket.accept();
//				Thread t = new Thread(new EchoThread(socket));
//				t.start();
//			}
//		} catch (IOException e) {
//			System.err.println(e);
//		}
//	}

			try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Server started on " + serverSocket.getLocalSocketAddress());
			pool = Executors.newCachedThreadPool();
		//pool = Executors.newSingleThreadExecutor();
		//pool = Executors.newFixedThreadPool(10);

			while (true) {
				Socket socket = serverSocket.accept();
				pool.execute(new EchoServer2.EchoThread(socket));
						}
	} catch (IOException e) {
		System.err.println(e);
		pool.shutdown();
	}
}

	private class EchoThread implements Runnable {
		private Socket socket;

		public EchoThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			SocketAddress socketAddress = socket.getRemoteSocketAddress();
			System.out.println("Received connection from " + socketAddress);

			try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

				out.println("Server ready!");

				String input;
				while ((input = in.readLine()) != null) {
					out.println(input);
					System.out.println("Sending back to the client connecting from " + socketAddress + ": " + input); 
				}
			} catch (IOException e) {
				System.err.println(e);
			} finally {
				System.out.println("Connection closed from " + socketAddress);
			}
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("The program must get exactly 1 argument: the port number");
			return;
		}
		
		int port = Integer.valueOf(args[0]);
		new EchoServer2(port).startServer();
	}
}
