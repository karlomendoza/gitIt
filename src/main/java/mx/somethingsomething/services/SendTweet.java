package mx.somethingsomething.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class SendTweet {

	private static Boolean testRun = false;

	public static void postToTwitter(String tweet) throws FileNotFoundException, IOException {
		postToTwitter(tweet, null);
	}

	public static void postToTwitter(String tweet, File filePath) throws FileNotFoundException, IOException {
		if (!testRun) {
			try {
				Twitter twitter = new TwitterFactory().getInstance();

//				twitter.getRetweetsOfMe()

				StatusUpdate status = new StatusUpdate(tweet);
				if (filePath != null && filePath.isFile()) {
					status.setMedia(filePath);
				}
				twitter.updateStatus(status);

			} catch (TwitterException te) {
				te.printStackTrace();
			}
		}
	}
}
