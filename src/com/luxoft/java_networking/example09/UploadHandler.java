package com.luxoft.java_networking.example09;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.luxoft.java_networking.AbstractHandler;


public class UploadHandler extends AbstractHandler {
	
	@Override
	public void handleTask(Socket socket) {
		try (InputStream in = socket.getInputStream(); PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) 
		{
			String message;
			String fileName = "file2.txt";
			File file = new File(UploadServer.FOLDER_NAME, fileName);
			try (OutputStream os = new FileOutputStream(file)) {
				byte[] buffer = new byte[8192];
				int c;
				while ((c = in.read(buffer)) != -1) {
					os.write(buffer, 0, c);
				}

				message = "Data saved to " + fileName;
			} catch (IOException e) {
				System.err.println(e);
				message = e.getMessage();
			}

			out.println(message);
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
