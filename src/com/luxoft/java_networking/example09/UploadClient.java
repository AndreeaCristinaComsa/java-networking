package com.luxoft.java_networking.example09;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class UploadClient {
	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("The program must get exactly 3 arguments: the host, the port number and the file");
			return;
		}
		
		String host = args[0];
		int portNumber = Integer.parseInt(args[1]);
		String fileName = args[2];
		
		try (Socket socket = new Socket(host, portNumber); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 OutputStream out = socket.getOutputStream()) 
		{
			try (InputStream is = new FileInputStream(fileName)) {
				byte[] buffer = new byte[8192];
				int c;
				while ((c = is.read(buffer)) != -1) {
					out.write(buffer, 0, c);
				}
			}
			
			socket.shutdownOutput();
			
			String msg = in.readLine();
			System.out.println(msg);
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
