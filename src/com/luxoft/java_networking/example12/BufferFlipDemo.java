package com.luxoft.java_networking.example12;
import java.nio.CharBuffer;

public class BufferFlipDemo {
    public static void main(String[] args) {
        String[] poem = {
	        		"Mares eat oats",
	                "Does eat oats",
	                "Little lambs eat ivy",
	                "A kid will eat ivy too"
                };

        CharBuffer buffer = CharBuffer.allocate(50);

        for (int i = 0; i < poem.length; i++) {
            // Fill the buffer
            for (int j = 0; j < poem[i].length(); j++)
                buffer.put(poem[i].charAt(j));

            // Flip the buffer so that its contents can be read
            buffer.flip();

            // flip():
            // 1) set limit to position (to keep how much data is in buf)
            // 2) set position to 0 to start reading
            // same as buffer.limit(buffer.position()).position(0);


            // Drain the buffer
            while (buffer.hasRemaining()) {
                System.out.print(buffer.get());
            }
            
            // Empty the buffer to prevent BufferOverflowException
            buffer.clear();

            System.out.println();
        }
    }
}