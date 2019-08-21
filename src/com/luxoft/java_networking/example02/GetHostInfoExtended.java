package com.luxoft.java_networking.example02;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetHostInfoExtended  {
    public static void main(String[] args) throws UnknownHostException {
        if (args.length != 1) {
            System.err.println("The program must get exactly 1 argument: the host");
            return;
        }

        InetAddress addresses [] = InetAddress.getAllByName("www.yahoo.com");
        System.out.println("Exercitiul nr 1");
        for (InetAddress adr : addresses) {
            System.out.println("IP_address_cautat_este: " + adr.getHostAddress() );
            try{
                System.out.println(adr.isReachable(1060));

            }
            catch (IOException e){
                        System.out.println("Error");
            }
        }





    }
    }


