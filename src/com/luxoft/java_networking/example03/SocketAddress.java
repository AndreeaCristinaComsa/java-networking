package com.luxoft.java_networking.example03;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class SocketAddress {
	public static void main(String[] args) {
		InetSocketAddress socketAddress;
		String LUXOFT_COM = "www.luxoft.com";
		
		socketAddress = new InetSocketAddress(80);
		output(socketAddress);
		
		try {
			InetAddress address = InetAddress.getByName(LUXOFT_COM);
			socketAddress = new InetSocketAddress(address, 80);
			output(socketAddress);
		} catch (UnknownHostException e) {
			System.out.println(e);
		}
		
		socketAddress = new InetSocketAddress(LUXOFT_COM, 80);
		output(socketAddress);
		
		socketAddress = new InetSocketAddress("localhost", 80);
		output(socketAddress);
	}
	
	private static void output(InetSocketAddress socketAddress) {
		System.out.println(socketAddress.getAddress());
		System.out.println(socketAddress.getHostName());
		System.out.println(socketAddress.getPort());
		System.out.println();
	}
}
