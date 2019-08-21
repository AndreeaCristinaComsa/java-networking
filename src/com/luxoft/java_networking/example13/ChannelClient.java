package com.luxoft.java_networking.example13;
import java.io.IOException;

import java.net.InetSocketAddress;

import java.nio.ByteBuffer;

import java.nio.channels.SocketChannel;

public class ChannelClient {
    public static void main(String[] args) {
    	if (args.length != 2) {
			System.err.println("The program must get exactly 2 arguments: the host and the port number");
			return;
		}
    	
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            
            String host = args[0];
            int portNumber = Integer.valueOf(args[1]);
            InetSocketAddress address = new InetSocketAddress(host, portNumber);
            // If the connection is established immediately,
            // as can happen with a local connection, then
            // connect() returns true
            socketChannel.connect(address);
            // otherwise it returns false

            // finishConnect():
            // If this channel is already connected then this method will not block
            // and return true.
            // If this channel is in non-blocking mode then this method will
            // return false if the connection process is not yet complete.
            while (!socketChannel.finishConnect())
                System.out.println("waiting to finish connection");

            ByteBuffer buffer = ByteBuffer.allocate(200);
            while (socketChannel.read(buffer) >= 0) {
                buffer.flip();
                while (buffer.hasRemaining())
                    System.out.print((char) buffer.get());
                buffer.clear();
            }
            socketChannel.close();
        }
        catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }
}