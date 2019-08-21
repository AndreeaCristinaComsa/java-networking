package com.luxoft.java_networking.example11.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

import com.luxoft.java_networking.AbstractHandler;

public class RPCHandler extends AbstractHandler {
	private Object serviceObject;
	
	public RPCHandler(Object serviceObject) {
		this.serviceObject = serviceObject;
	}
	
	@Override
	public void handleTask(Socket socket) {
		try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) 
		{
			String name = (String) in.readObject();
			Object[] params = (Object[]) in.readObject();

			Object ret = null;
			try {
				Class<?>[] types = null;
				
				if (params != null) {
					types = new Class[params.length];
					for (int i = 0; i < params.length; i++)
						types[i] = params[i].getClass();
				}
				
				Method m = serviceObject.getClass().getMethod(name, types);
				ret = m.invoke(serviceObject, params);
			} catch (InvocationTargetException e) {
				ret = e.getTargetException();
			} catch (Exception e) {
				ret = e;
			}

			out.writeObject(ret);
			out.flush();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
