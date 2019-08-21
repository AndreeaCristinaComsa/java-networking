package com.luxoft.java_networking.example14;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;

/**
 * Update client to receive a text message and print it
 */
public class SelectorClient {

    static ByteBuffer byteBuffer = ByteBuffer.allocateDirect(8);

    public static void main(String[] args) {
    	if (args.length != 2) {
			System.err.println("The program must get exactly 2 arguments: the host and the port number");
			return;
		}
        
        String host = args[0];
        int portNumber = Integer.valueOf(args[1]);

        try {
            SocketChannel socketChannel = SocketChannel.open();
            InetSocketAddress address = new InetSocketAddress(host, portNumber);
            socketChannel.connect(address);

            long time = 0;
            while (socketChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    time <<= 8;
                    time |= byteBuffer.get() & 255;
                }
                byteBuffer.clear();
            }
            System.out.println(new Date(time));

            socketChannel.close();
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }
    }
}