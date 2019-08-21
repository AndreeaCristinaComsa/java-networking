package com.luxoft.java_networking.example07;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadPoolingClient {
	
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("The program must get exactly 2 arguments: the host and the port number");
			return;
		}

		String host = args[0];
		int port = Integer.valueOf(args[1]);
		int id = 1;

		for (int i = 0; i < 20; i++) {
			Thread t = new Thread(new Handler(host, port, id++));
			t.start();
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
		}
	}

	private static class Handler implements Runnable {
		private String host;
		private int port;
		private int id;

		public Handler(String host, int port, int id) {
			this.host = host;
			this.port = port;
			this.id = id;
		}

		@Override
		public void run() {
			try (Socket socket = new Socket(host, port); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) 
			{
				out.println(id);
				String response = in.readLine();
				System.out.println(response);
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}
}
