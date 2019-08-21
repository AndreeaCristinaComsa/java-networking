package com.luxoft.java_networking.example08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.luxoft.java_networking.AbstractHandler;

public class EchoHandler extends AbstractHandler {

	@Override
	public void handleTask(Socket socket) {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

			out.println("Server is ready");

			String input;
			while ((input = in.readLine()) != null) {
				out.println(input);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}

}
