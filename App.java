package com.awesome.twit;

import twitter4j.*;
import twitter4j.auth.*;
import java.util.Scanner;

/**
 * What up, G.
 *
 */
public class App 
{
    public static void main( String[] args ) throws TwitterException
    {
        TwitterFactory twiggy = new TwitterFactory();
        Twitter twizzler = twiggy.getInstance();
        
        
        boolean keepItGoin = true;
        do {
            Scanner woot = new Scanner(System.in);
            System.out.print("\n-----------------\n"+
                    "1. Timeline\n2. Tweet\nQ. Quit\n"+
                    "\n-----------------\n> ");
            String choice = woot.nextLine();

            if (choice.equals("1")) {
                User user = twizzler.verifyCredentials();
                System.out.println("@"+user.getScreenName()+"'s home timeline:");
                for (Status status : twizzler.getHomeTimeline()) {
                    System.out.println("\n@"+status.getUser().getScreenName()+": "+status.getText());
                }
                
            } else if (choice.equals("2")) {
                
                System.out.print("Tweet: ");
                String tweet = woot.nextLine();
                Status status = twizzler.updateStatus(tweet);
                System.out.print("Just tweeted: "+status.getText());
                
            } else if (choice.equalsIgnoreCase("A")) { // get access token
                
                try {
                    RequestToken reqToken = twizzler.getOAuthRequestToken();
                    System.out.println("Request token: " + reqToken.getToken() +
                            "\nRequest token secret: " + reqToken.getTokenSecret());
                    AccessToken accessToken = null;

                    while (accessToken == null) {
                        System.out.print("Open the this URL in a browser: " +
                                reqToken.getAuthorizationURL() +
                                "\nGrant access, then enter the PIN here: ");
                        String pin = woot.nextLine();
                        try {
                            accessToken = twizzler.getOAuthAccessToken(reqToken, pin);
                        } catch (TwitterException te) {
                            System.out.println("Invalid PIN.");
                        }
                    }
                    System.out.println("Access token: " + accessToken.getToken() +
                            "\nAccess token secret: " + accessToken.getTokenSecret() +
                            "Success!");
                    
                } catch (IllegalStateException ise) {
                    System.out.println("Access token already available.");
                }
                
            } else if (choice.equalsIgnoreCase("Q")) {
                
                keepItGoin = false;
                
            } else {
                System.out.println("you stink");
            }


        } while (keepItGoin == true);
        
    }
}
