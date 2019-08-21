package com.luxoft.java_networking.example06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class KnockServer {
    private int port;
    private int backlog;

    public KnockServer(int port, int backlog) {
        this.port = port;
        this.backlog = backlog;
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port, backlog)) {
            System.out.println("KnockKnockServer started on " + serverSocket.getLocalSocketAddress());
            process(serverSocket);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void process(ServerSocket server) throws IOException {
        while (true) {
            SocketAddress socketAddress = null;

            try (Socket socket = server.accept(); BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                socketAddress = socket.getRemoteSocketAddress();
                System.out.println("Received connection from " + socketAddress);

                out.println("Knock! Knock!");

                String input;
                while ((input = in.readLine()) != null) {
                    String reply;
                    reply = KnockKnockProtocol.KnockKnock(input);
                    out.println(reply);

                    System.out.println("Sending back to the client: " + input);
                }
            } catch (IOException e) {
                System.err.println(e);
            } finally {
                System.out.println("Connection closed from " + socketAddress);
            }
        }
    }

    public static void main(String[] args) {
        if ((args.length != 1) && (args.length != 2)) {
            System.err.println("The program must get 1 or 2 arguments: the port number and (optionally) the backlog ");
            return;
        }

        int port = Integer.valueOf(args[0]);
        int backlog = 50;
        if (args.length == 2)
            backlog = Integer.valueOf(args[1]);

        new KnockServer(port, backlog).startServer();
    }
}
