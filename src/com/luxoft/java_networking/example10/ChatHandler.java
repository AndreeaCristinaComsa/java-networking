package com.luxoft.java_networking.example10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.luxoft.java_networking.AbstractHandler;

public class ChatHandler extends AbstractHandler {
	private final static int TIMEOUT = 300000;
	private List<PrintWriter> printWriterList = Collections.synchronizedList(new ArrayList<>());

	@Override
	public void handleTask(Socket socket) {
		String name = null;
		BufferedReader in = null;
		PrintWriter out = null;

		try {
			socket.setSoTimeout(TIMEOUT);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
			System.out.println(socket.getOutputStream().toString());

			printWriterList.add(out);
			name = in.readLine();
			sendMessage(name + " has logged in.");

			String message;
			while ((message = in.readLine()) != null) {
				sendMessage(name + ": " + message);
			}
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			printWriterList.remove(out);
			sendMessage(name + " has logged out.");
		}
	}

	private void sendMessage(String message) {
		for (PrintWriter out : printWriterList) {
			synchronized (out) {
				out.println(message);
			}
		}
	}
}
