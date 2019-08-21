package com.luxoft.java_networking.example15;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class EchoUdpClient {
	private static final int BUFSIZE = 508;
	private static final int TIMEOUT = 2000;

	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("The program must get exactly 3 arguments: the host, the port number and the data to be sent");
			return;
		}
		
		String host = args[0];
		int portNumber = Integer.valueOf(args[1]);
		byte[] data = args[2].getBytes();

		try (DatagramSocket socket = new DatagramSocket()) {
			socket.setSoTimeout(TIMEOUT);

			InetAddress addr = InetAddress.getByName(host);
			DatagramPacket packetOut = new DatagramPacket(data, data.length, addr, portNumber);
			socket.send(packetOut);

			DatagramPacket packetIn = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);
			socket.receive(packetIn);

			String received = new String(packetIn.getData(), 0,	packetIn.getLength());
			System.out.println("Received: " + received);

		} catch (SocketTimeoutException e) {
			System.err.println("Timeout: " + e.getMessage());
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}
