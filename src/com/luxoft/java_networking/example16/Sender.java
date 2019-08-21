package com.luxoft.java_networking.example16;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Sender {
	private static final int BUFSIZE = 508;

	public static void main(String[] args) throws Exception {
		if (args.length != 4) {
			System.err.println("The program must get exactly 4 arguments: the local port, the host, the remote port and the message to be sent");
			return;
		}
		
		int localPort = Integer.valueOf(args[0]);
		String host = args[1];
		int remotePortNumber = Integer.valueOf(args[2]);
		String message = args[3];

		try (DatagramSocket socket = new DatagramSocket(localPort)) {
			InetAddress address = InetAddress.getByName(host);
			DatagramPacket packet = new DatagramPacket(new byte[BUFSIZE], BUFSIZE, address, remotePortNumber);
			byte[] data = message.getBytes();
			packet.setData(data);
			packet.setLength(data.length);
			socket.send(packet);
			System.out.println("Message sent:" + new String(data));
		}
	}
}
