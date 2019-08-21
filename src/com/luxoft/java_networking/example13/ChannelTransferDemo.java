package com.luxoft.java_networking.example13;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class ChannelTransferDemo
{
    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream("file.txt")) {
            FileChannel inChannel = fis.getChannel();
            WritableByteChannel outChannel = Channels.newChannel(System.out);
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
    }
}