package com.luxoft.java_networking.example15;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class EchoUdpServer {
	private static final int BUFSIZE = 508;

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("The program must get exactly 1 argument: the port number");
			return;
		}
		
		int portNumber = Integer.valueOf(args[0]);

		try (DatagramSocket socket = new DatagramSocket(portNumber)) {
			DatagramPacket packetIn = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);
			DatagramPacket packetOut = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);

			System.out.println("Server started ... listening on port " + portNumber);

			while (true) {
				socket.receive(packetIn);
				System.out.println("Received: " + packetIn.getLength() + " bytes");

				packetOut.setData(packetIn.getData());
				packetOut.setLength(packetIn.getLength());

				packetOut.setSocketAddress(packetIn.getSocketAddress());

				socket.send(packetOut);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
