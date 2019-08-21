package com.luxoft.java_networking.example06;
import java.util.concurrent.ThreadLocalRandom;

public class KnockKnockProtocol {
    static String possibleNames[]= {"Turnip", "Little Old Lady", "Atch", "Who", "Who"};


    public static String KnockKnock(String replyLine)
    {
        switch(replyLine) {
            case "Who's there?":
                int randomNum = ThreadLocalRandom.current().nextInt(0, 4);
                String aname = possibleNames[randomNum];
                return aname;
            case "Turnip who?":
                return "Turnip the heat, it's cold in here! Want another?  (y/n) ";
            case "Little Old Lady who?":
                return " I didn't know you could yodel! Want another? (y/n) ";
            case "Atch who?":
                return "Bless you! Want another? (y/n) ";
            case "Who who?":
                return "Is there an owl in here? Want another? (y/n)";
            case "y":
                return "Knock! Knock!";
            case "n:":
                return "Bye!";
            default:
                return "I don't know how the answer";
        }
    }
}
