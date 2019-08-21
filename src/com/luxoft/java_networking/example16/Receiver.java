package com.luxoft.java_networking.example16;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Receiver {
	private static final int BUFSIZE = 508;

	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("The program must get exactly 3 arguments: the port number, the remote host and the remote port number");
			return;
		}
		
		int portNumber = Integer.valueOf(args[0]);
		String remoteHost = args[1];
		int remotePortNumber = Integer.valueOf(args[2]);

		try (DatagramSocket socket = new DatagramSocket(portNumber)) {
			socket.connect(InetAddress.getByName(remoteHost), remotePortNumber);

			DatagramPacket packet = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);

			while (true) {
				socket.receive(packet);
				String text = new String(packet.getData(), 0, packet.getLength());
				System.out.println(packet.getAddress().getHostAddress() + ":" + packet.getPort() + " > " + text);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
}
