package mx.somethingsomething.services;

import java.io.File;
import java.util.TimerTask;

public class ScheduledTweets extends TimerTask {

	private String tweet;
	private File file;

	@Override
	public void run() {
		proccessTweet();
	}

	public ScheduledTweets(String _tweet, File _file) {
		this.tweet = _tweet;
		this.file = _file;

	}

	public void proccessTweet() {
		try {
			SendTweet.postToTwitter(tweet, file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
