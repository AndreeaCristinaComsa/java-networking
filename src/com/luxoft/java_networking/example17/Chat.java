package com.luxoft.java_networking.example17;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chat implements ActionListener, Runnable {
	private static final int BUFSIZE = 508;

	private String user;
	private int localPort;
	private String remoteHost;
	private int remotePort;

	private DatagramSocket socket;
	private DatagramPacket packetOut;
	private JTextField input;
	private JTextArea output;

	public Chat(String[] args, JFrame frame) throws UnknownHostException, SocketException {
		setParameter(args);
		if (user == null || remoteHost == null || localPort == 0 || remotePort == 0)
			throw new RuntimeException("Missing parameters");

		InetAddress remoteAddr = InetAddress.getByName(remoteHost);

		frame.setTitle("Chat - " + user);

		socket = new DatagramSocket(localPort);
		packetOut = new DatagramPacket(new byte[BUFSIZE], BUFSIZE, remoteAddr, remotePort);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (socket != null)
					socket.close();
				System.exit(0);
			}
		});

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 2));
		input = new JTextField(40);
		input.setFont(new Font("Monospaced", Font.PLAIN, 18));
		panel.add(input);
		JButton send = new JButton("Send");
		send.addActionListener(this);
		panel.add(send);
		frame.add(panel, BorderLayout.NORTH);

		output = new JTextArea(10, 45);
		output.setFont(new Font("Monospaced", Font.PLAIN, 18));
		output.setLineWrap(true);
		output.setWrapStyleWord(true);
		output.setEditable(false);
		frame.add(new JScrollPane(output), BorderLayout.CENTER);

		Thread t = new Thread(this);
		t.start();
	}

	public void run() {
		DatagramPacket packetIn = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);

		while (true) {
			try {
				receive(packetIn);
			} catch (IOException e) {
				break;
			}
		}
	}

	private void receive(DatagramPacket packetIn) throws IOException {
		socket.receive(packetIn);
		String text = new String(packetIn.getData(), 0, packetIn.getLength());
		output.append(text);
		output.append("\n");
	}

	public void actionPerformed(ActionEvent event) {
		try {
			String message = user + ": " + input.getText();
			byte[] data = message.getBytes();
			packetOut.setData(data);
			packetOut.setLength(data.length);
			socket.send(packetOut);
			input.requestFocus();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	private void setParameter(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-user"))
				user = args[i + 1];
			else if (args[i].equals("-localPort"))
				localPort = Integer.valueOf(args[i + 1]);
			else if (args[i].equals("-remoteHost"))
				remoteHost = args[i + 1];
			else if (args[i].equals("-remotePort"))
				remotePort = Integer.valueOf(args[i + 1]);
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();

		try {
			new Chat(args, frame);
		} catch (UnknownHostException | SocketException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}

		frame.pack();
		frame.setVisible(true);
	}
}
