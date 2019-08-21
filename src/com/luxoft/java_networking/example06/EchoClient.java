package com.luxoft.java_networking.example06;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoClient {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("The program must get exactly 2 arguments: the host and the port number");
			return;
		}
		
		String host = args[0];
		int port = Integer.valueOf(args[1]);

		try (Socket socket = new Socket(host, port); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 PrintWriter out = new PrintWriter(socket.getOutputStream(), true); BufferedReader input = new BufferedReader(new InputStreamReader(System.in))) {

			String message = in.readLine();
			System.out.println(message);

			String line;
			while (true) {
				line = input.readLine();
				if (line == null || line.equals("quit"))
					break;
				out.println(line);
				System.out.println(in.readLine());
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
