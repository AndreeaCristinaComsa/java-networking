package com.luxoft.java_networking.example13;
import java.io.IOException;

import java.net.InetSocketAddress;

import java.nio.ByteBuffer;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ChannelServer {
    public static void main(String[] args) throws IOException {
    	if (args.length != 1) {
			System.err.println("The program must get exactly 1 argument: the port number");
			return;
		}
    	
        System.out.println("Starting server...");
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        
        int portNumber = Integer.valueOf(args[0]);
        serverSocketChannel.socket().bind(new InetSocketAddress(portNumber));
        serverSocketChannel.configureBlocking(false);
        String message = "Hello from server!";
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        while (true) {
            System.out.print(".");
            // non-blocking accept
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) {
                System.out.println();
                System.out.println("Received connection from " + socketChannel.socket().getRemoteSocketAddress());
                buffer.rewind();
                socketChannel.write(buffer);
                socketChannel.close();
            }
            else
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ie) {
                    assert false; 
                }
        }
    }
}