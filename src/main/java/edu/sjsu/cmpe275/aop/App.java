package edu.sjsu.cmpe275.aop;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        /***
         * Following is a dummy implementation of App to demonstrate bean creation with Application context.
         * You may make changes to suit your need, but this file is NOT part of the submission.
         */

    	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
        TweetService tweeter = (TweetService) ctx.getBean("tweetService");
        TweetStats stats = (TweetStats) ctx.getBean("tweetStats");

        try {
            tweeter.tweet("alex", "first tweet");
            tweeter.tweet("boblo", "Bob's first Tweet");
            tweeter.tweet("bob", "Bob's first Tweet");
            tweeter.tweet("boblo", "Bob's Second Tweet");
            tweeter.follow("bob", "alex");
            tweeter.follow("bob", "boblo");
            tweeter.follow("alex", "bob");
            tweeter.follow("boblo", "alex");
//            tweeter.block("alex", "bob");
//            tweeter.block("alex", "boblo");
            tweeter.block("sdvcsd", "alex");
            tweeter.block("dfvdf", "bob");
//            stats.resetStatsAndSystem();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            tweeter.follow("alex", "bob");
//            tweeter.follow("carl", "bob");
//            tweeter.follow("bob", "alice");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        try{

        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{

        }
        catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("Most productive user: " + stats.getMostProductiveUser());
        System.out.println("Most popular user: " + stats.getMostFollowedUser());
        System.out.println("Length of the longest tweet: " + stats.getLengthOfLongestTweetAttempted());
        System.out.println("Most unpopular follower " + stats.getMostBlockedFollower());
        ctx.close();
    }
}
