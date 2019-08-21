package com.luxoft.java_networking.example10;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient extends JFrame implements Runnable, ActionListener {

	private static final long serialVersionUID = -2327021060382191189L;
	private final static int width = 500;
	private final static int hight = 400;

	private JLabel label;
	private JTextArea area;
	private JTextField text;
	private JButton button;

	private String host;
	private int port;
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;

	private Thread t;
	private boolean login = false;
	private String name;

	public ChatClient(String host, int port) {
		super("Chat-Client");

		this.host = host;
		this.port = port;

		label = new JLabel();
		JPanel top = new JPanel();
		top.add(label);

		area = new JTextArea();
		area.setFont(new Font("Monospaced", Font.PLAIN, 14));
		area.setLineWrap(true);
		area.setEditable(false);

		text = new JTextField(48);
		text.setFont(new Font("Monospaced", Font.PLAIN, 14));
		text.addActionListener(this);

		button = new JButton("Login");
		button.addActionListener(this);

		JPanel input = new JPanel();
		input.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		input.add(text);
		input.add(button);

		add(top, BorderLayout.NORTH);
		add(new JScrollPane(area), BorderLayout.CENTER);
		add(input, BorderLayout.SOUTH);

		setSize(width, hight);
		setVisible(true);
		text.requestFocus();
	}

	public void actionPerformed(ActionEvent event) {
		Object obj = event.getSource();
		String cmd = event.getActionCommand();

		try {
			if (obj == button) {
				if (cmd.equals("Login")) {
					name = text.getText();
					if (name.length() != 0) {
						login();
					}
				} else {
					logout();
				}
			}

			if (obj == text) {
				if (login) {
					out.write(text.getText());
					out.newLine();
					out.flush();
				}
			}
		} catch (IOException ex) {
			area.append(ex.getMessage() + "\n");
			logout();
		} finally {
			text.setText("");
			text.requestFocus();
		}
	}

	private void login() throws IOException {
		socket = new Socket(host, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

		out.write(name);
		out.newLine();
		out.flush();

		login = true;
		label.setText(name);
		button.setText("Logout");

		t = new Thread(this);
		t.start();
	}

	public void logout() {
		if (login) {
			try {
				login = false;
				label.setText(" ");
				button.setText("Login");
				t = null;
				if (socket != null)
					socket.close();
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}
	}

	@Override
	public void run() {
		try {
			while (true) {
				String msg = in.readLine();
				if (msg == null)
					break;

				area.append(msg + "\n");
			}
		} catch (IOException e) {

		}
	}

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("The program must get exactly 2 arguments: the host and the port number");
			return;
		}

		String host = args[0];
		int port = Integer.valueOf(args[1]);

		ChatClient client = new ChatClient(host, port);

		client.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				client.logout();
				System.exit(0);
			}
		});
	}
}
