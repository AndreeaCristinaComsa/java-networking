package com.luxoft.java_networking.example11.test;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.luxoft.java_networking.example11.rpc.ClientFactory;
import com.luxoft.java_networking.example11.rpc.RPCClient;
import com.luxoft.java_networking.example11.service.DemoService;

public class TestClient2 {
	public static void main(String[] args) {
		String host = args[0];
		int port = Integer.valueOf(args[1]);

		RPCClient rpcClient = new RPCClient(host, port);
		ClientFactory factory = new ClientFactory(rpcClient);
		DemoService demoService = (DemoService) factory.newInstance(DemoService.class);
		
		System.out.println(demoService.getClass().getName());

		String s = demoService.getEcho("Hello");
		System.out.println("getEcho: " + s);

		int sum = demoService.getSum(12, 29);
		System.out.println("getSum: " + sum);

		Date date = demoService.getDate();
		System.out.println("getDate: " + date);

		demoService.sendMessage("Test message.");

		try {
			List<String> messagesList = demoService.getMessages("file.txt");
			System.out.println("getMessages:");
			for (String message : messagesList) {
				System.out.println(message);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
