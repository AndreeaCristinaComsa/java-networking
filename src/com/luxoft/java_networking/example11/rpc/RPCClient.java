package com.luxoft.java_networking.example11.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RPCClient {
	private String host;
	private int port;

	public RPCClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public Object call(String name, Object[] params) throws Exception {
		try (Socket socket = new Socket(host, port); 
			 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) 
		{
			out.writeObject(name);
			out.writeObject(params);
			out.flush();

			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			Object ret = in.readObject();

			if (ret instanceof Exception) {
				throw (Exception) ret;
			}
			
			return ret;
		}
	}
}
