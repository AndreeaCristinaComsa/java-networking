package com.luxoft.java_networking.example18;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.UUID;

public class MulticastSender {
	private static final int BUFSIZE = 508;
	private static final int LOOPS = 10;

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("The program must get exactly 2 arguments: the multicast address and the port number");
			return;
		}
		
		String multicastAddress = args[0];
		int portNumber = Integer.valueOf(args[1]);

		try (DatagramSocket socket = new DatagramSocket()) {
			InetAddress addr = InetAddress.getByName(multicastAddress);
			DatagramPacket packet = new DatagramPacket(new byte[BUFSIZE], BUFSIZE, addr, portNumber);

			for (int i = 0; i < LOOPS; i++) {
			    String uuid = UUID.randomUUID().toString();
			    byte[] data = uuid.getBytes();
				
				packet.setData(data);
				packet.setLength(data.length);
				socket.send(packet);
				Thread.sleep(1000);
			}
		} catch (InterruptedException | IOException e) {
			System.err.println(e);
		}
	}
}
