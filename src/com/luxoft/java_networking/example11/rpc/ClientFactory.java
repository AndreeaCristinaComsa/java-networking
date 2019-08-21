package com.luxoft.java_networking.example11.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ClientFactory {
	private RPCClient client;

	public ClientFactory(RPCClient client) {
		this.client = client;
	}

	public Object newInstance(Class<?> c) {
		ClassLoader loader = client.getClass().getClassLoader();
		Class<?>[] interfaces = new Class<?>[] { c };
		Handler handler = new Handler(client);
		return Proxy.newProxyInstance(loader, interfaces, handler);
	}

	private class Handler implements InvocationHandler {
		private Object object;

		public Handler(Object object) {
			this.object = object;
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Object result = ((RPCClient) this.object).call(method.getName(), args);
			return result;
		}
	}
}
