package com.luxoft.java_networking.example11.test;
import java.util.Date;
import java.util.List;

import com.luxoft.java_networking.example11.rpc.RPCClient;

public class TestClient1 {
	public static void main(String[] args) {
		String host = args[0];
		int port = Integer.valueOf(args[1]);

		RPCClient rpcClient = new RPCClient(host, port);

		try {
			Object[] params = { "Hello" };
			String s = (String) rpcClient.call("getEcho", params);
			System.out.println("getEcho: " + s);
		} catch (Exception e) {
			System.err.println(e);
		}

		try {
			Object[] params = { 12, 29 };
			int sum = (Integer) rpcClient.call("getSum", params);
			System.out.println("getSum: " + sum);
		} catch (Exception e) {
			System.err.println(e);
		}

		try {
			Date date = (Date) rpcClient.call("getDate", null);
			System.out.println("getDate: " + date);
		} catch (Exception e) {
			System.err.println(e);
		}

		try {
			Object[] params = { "Test message." };
			rpcClient.call("sendMessage", params);
		} catch (Exception e) {
			System.err.println(e);
		}

		try {
			Object[] params = { "file.txt" };
			@SuppressWarnings("unchecked")
			List<String> messagesList = (List<String>) rpcClient.call("getMessages", params);
			System.out.println("getMessages:");
			for (String message : messagesList) {
				System.out.println(message);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
