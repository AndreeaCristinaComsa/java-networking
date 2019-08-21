package com.luxoft.java_networking.example07;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolingServer {
	private int port;
	private ExecutorService pool;

	public ThreadPoolingServer(int port) {
		this.port = port;
	}

	public void startServer() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {

			System.out.println("Server started on " + serverSocket.getLocalSocketAddress());

			pool = Executors.newCachedThreadPool();

			while (true) {
				Socket socket = serverSocket.accept();
				pool.execute(new Task(socket));
			}
		} catch (IOException e) {
			System.err.println(e);
			pool.shutdown();
		}
	}

	private class Task implements Runnable {
		private Socket socket;

		public Task(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) 
			{

				String input = in.readLine();

				if (input != null) {
					int delay = 3000 + (int) (Math.random() * 7000);

					long begin = System.currentTimeMillis();
					System.out.printf("Starting job %3s: %tT, Thread; %s%n", input, new Date(begin), Thread.currentThread().getName());
					Thread.sleep(delay);
					long end = System.currentTimeMillis();
					System.out.printf("Ending job %3s: %tT, Thread; %s%n", input, new Date(end), Thread.currentThread().getName());

					out.printf("Job %3s, Start: %tT, End: %tT%n", input, new Date(begin), new Date(end));
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("The program must get exactly 1 argument: the port number");
			return;
		}
		int port = Integer.valueOf(args[0]);
		new ThreadPoolingServer(port).startServer();
	}
}
