package com.luxoft.java_networking.example14;
import java.io.IOException;

import java.net.InetSocketAddress;
import java.net.ServerSocket;

import java.nio.ByteBuffer;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import java.util.Iterator;

/**
 * Task: send message from the server with the text "hello from server"
 * Use same selector for accepting and sending the message, for this:
 * 1) register channel for writing after receiving connection;
 * 2) set channel to non-blocking mode by using configureBlocking()
 */
public class SelectorServer {
	
    static ByteBuffer byteBuffer = ByteBuffer.allocateDirect(8);

    public static void main(String[] args) throws IOException {
    	if (args.length != 1) {
			System.err.println("The program must get exactly 1 argument: the port number");
			return;
		}
    	
        int portNumber = Integer.valueOf(args[0]);
        System.out.println("Server starting ... listening on port " + portNumber);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(portNumber));
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            int n = selector.select(); // selecting some channel
            if (n == 0) 
            	continue; // nothing was selected
            Iterator<?> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                if (key.isAcceptable()) {
                    SocketChannel socketChannel;
                    // non-blocking accept
                    socketChannel = ((ServerSocketChannel) key.channel()).accept();
                    if (socketChannel == null) 
                    	continue; // no pending connections
                    System.out.println("Receiving connection");
                    byteBuffer.clear(); // prepare buffer for writing, set position to 0
                    byteBuffer.putLong(System.currentTimeMillis());
                    byteBuffer.flip(); // set limit to position, position to 0
                    System.out.println("Writing current time");
                    // write buffer to channel
                    while (byteBuffer.hasRemaining()) socketChannel.write(byteBuffer);
                    socketChannel.close();
                }
                iterator.remove(); // remove if communication is done
            }
        }
    }
}