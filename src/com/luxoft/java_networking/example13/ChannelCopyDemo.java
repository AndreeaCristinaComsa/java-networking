package com.luxoft.java_networking.example13;
import java.io.IOException;

import java.nio.ByteBuffer;

import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class ChannelCopyDemo {

    public static void main(String[] args) {
        try (ReadableByteChannel src = Channels.newChannel(System.in);
             WritableByteChannel dest = Channels.newChannel(System.out);) {

            copy(src, dest); // or copyAlt(src, dest);

        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }

    }

    static void copy(ReadableByteChannel src, WritableByteChannel dest) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(2048);
        while (src.read(buffer) != -1) {
            buffer.flip();
            dest.write(buffer);

            // because write() might not completely drain the buffer,
            // compact() is called to compact the buffer before the next read.
            buffer.compact();
            // compact():
            // The bytes between the buffer's current position and its limit,
            // if any, are copied to the beginning of the buffer.
            // Position is set to limit() so that buffer can be filled further

            // Compaction ensures that unwritten buffer content isn't
            // overwritten during the next read operation.
        }
        buffer.flip();
        while (buffer.hasRemaining())
            dest.write(buffer);
    }

    // this version is simpler but less optimal because needs more I/O operations
    static void copyAlt(ReadableByteChannel src, WritableByteChannel dest) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocateDirect(2048);
        while (src.read(buffer) != -1) {
            buffer.flip();
            // writing the remaining bytes and clear the buffer
            while (buffer.hasRemaining())
                dest.write(buffer);
            buffer.clear(); // now buffer is empty and can be filled
        }
    }
}