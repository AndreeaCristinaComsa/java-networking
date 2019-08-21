package com.luxoft.java_networking.example18;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastReceiver {
	private static final int BUFSIZE = 508;

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("The program must get exactly 2 arguments: the multicast address and the port number");
			return;
		}
		
		String multicastAddress = args[0];
		int portNumber = Integer.valueOf(args[1]);

		try (MulticastSocket socket = new MulticastSocket(portNumber)) {
			InetAddress group = InetAddress.getByName(multicastAddress);
			socket.joinGroup(group);

			DatagramPacket packetIn = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);

			while (true) {
				socket.receive(packetIn);
				String received = new String(packetIn.getData(), 0, packetIn.getLength());
				System.out.println(received);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
