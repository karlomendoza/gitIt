package mx.somethingsomething.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class SendTweet {

	private static Boolean testRun = true;

	public static void postToTwitter(String tweet) throws FileNotFoundException, IOException {
		postToTwitter(tweet, null);
	}

	public static void postToTwitter(String tweet, File file) throws FileNotFoundException, IOException {

		try {
			Twitter twitter = new TwitterFactory().getInstance();

//				twitter.getRetweetsOfMe()

			StatusUpdate status = new StatusUpdate(tweet);
			if (file != null && file.isFile()) {
				status.setMedia(file);
			}
			if (!testRun) {
				twitter.updateStatus(status);
			} else {
				System.out.println("tweet: " + tweet + " file: " + file.getName());
			}

		} catch (TwitterException te) {
			te.printStackTrace();
		}
	}
}
