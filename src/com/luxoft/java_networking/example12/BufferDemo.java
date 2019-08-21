package com.luxoft.java_networking.example12;
import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * 0 <= mark <= position <= limit <= capacity
 */
public class BufferDemo
{
    public static void main(String[] args)
    {
        Buffer buffer = ByteBuffer.allocate(7);
        System.out.println("Capacity: " + buffer.capacity());
        System.out.println("Limit: " + buffer.limit());
        System.out.println("Position: " + buffer.position());
        System.out.println("Remaining: " + buffer.remaining());

        System.out.println();
        System.out.println("Changing buffer limit to 5");
        buffer.limit(5);
        System.out.println("Limit: " + buffer.limit());
        System.out.println("Position: " + buffer.position());
        System.out.println("Remaining: " + buffer.remaining());

        System.out.println();
        System.out.println("Changing buffer position to 3");
        buffer.position(3);
        System.out.println("Position: " + buffer.position());
        System.out.println("Remaining: " + buffer.remaining());
        System.out.println(buffer);

        System.out.println();
        System.out.println("Put data to buffer");

        ByteBuffer buffer2 = ByteBuffer.allocate(7);
        buffer2.put((byte) 10).put((byte) 20).put((byte) 30);

        System.out.println("Capacity = " + buffer2.capacity());
        System.out.println("Limit = " + buffer2.limit());
        System.out.println("Position = " + buffer2.position());
        System.out.println("Remaining = " + buffer2.remaining());

        for (int i = 0; i < buffer2.position(); i++)
            System.out.println(buffer2.get(i));
        System.out.println();
    }

}

