package edu.sjsu.cmpe275.aop;
import java.util.*;

public class TweetStatsImpl implements TweetStats {
	public Map<String, HashSet<String>> userFollowers = new TreeMap<String, HashSet<String>>();
	public Map<String, HashSet<String>> blockedUsers = new TreeMap<String, HashSet<String>>();
	public Map<String, Integer> userTweetsData = new HashMap<String, Integer>();
	public Map<String, Integer> userTweetsProductiveData = new TreeMap<String, Integer>();

	public int longestTweet  = 0;

	@Override
	public void resetStatsAndSystem() {
		userTweetsProductiveData.clear();
		userTweetsData.clear();
		userFollowers.clear();
		blockedUsers.clear();
		longestTweet = 0;
	}
    
	@Override
	public int getLengthOfLongestTweetAttempted() {
		return longestTweet;
	}

	@Override
	public String getMostFollowedUser() {
		int mostNoOfFollowers = 0;
		String mostFollowedUser = null;
		int temp = 0;
		for(String s : userFollowers.keySet()){
			temp = userFollowers.get(s).size();
			if(mostNoOfFollowers < temp){
				mostNoOfFollowers = temp;
				mostFollowedUser = s;
			}
		}
		return mostFollowedUser;
	}

	@Override
	public String getMostProductiveUser() {
		int maxTweetsTotalLength = 0;
		String mostProductiveUser = null;
		int length = 0;
		for (String s : userTweetsProductiveData.keySet()){
			length = userTweetsProductiveData.get(s);
			if(length > maxTweetsTotalLength){
				mostProductiveUser = s;
				maxTweetsTotalLength = length;
			}
		}
		return mostProductiveUser;
	}

	@Override
	public String getMostBlockedFollower() {
		String mostBlockedUser = null;
		int mostNoOfTimesBlocked = 0;
		int temp = 0;
		for(String s : blockedUsers.keySet()){
			temp = blockedUsers.get(s).size();
			if(mostNoOfTimesBlocked < temp){
				mostNoOfTimesBlocked = temp;
				mostBlockedUser = s;
			}
		}
		return mostBlockedUser;
	}
}



