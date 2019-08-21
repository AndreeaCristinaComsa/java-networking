package com.luxoft.java_networking.example05;

import java.net.*;
import java.io.*;

public class GreetingServer implements Runnable {
   private ServerSocket serverSocket;
   
   public GreetingServer(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(30000);
   }

   public void run() {
      while(true) {
         try {
            System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            
            System.out.println("Received connection from " + server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());
            
            System.out.println("Client says: " + in.readUTF());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + ". Bye!");
            server.close();
            
         } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         } catch (IOException e) {
            e.printStackTrace();
            break;
         }
      }
   }
   
   public static void main(String [] args) {
	  if (args.length != 1) {
		 System.err.println("The program must get exactly 1 argument: the port number");
		 return;
	  } 
	   
      int port = Integer.valueOf(args[0]);
      try {
         Thread t = new Thread(new GreetingServer(port));
         t.start();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}