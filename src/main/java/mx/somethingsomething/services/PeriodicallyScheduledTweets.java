package mx.somethingsomething.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PeriodicallyScheduledTweets extends Thread {

	private String tweet;
	private List<String> tweets;
	private File filesDir;

	@Override
	public void run() {
		proccessAllTweets();
	}

	public PeriodicallyScheduledTweets(String _tweet, File _filesDir) {
		this.tweet = _tweet;
		this.filesDir = _filesDir;
	}

	public PeriodicallyScheduledTweets(List<String> _tweets, File _filesDir) {
		this.tweets = _tweets;
		this.filesDir = _filesDir;
	}

	public void proccessAllTweets() {
		if (tweets != null && tweets.size() > 0) {
			tweet = tweets.get(0);
		}

		if (filesDir.isDirectory()) {
			Path resultsPath = Paths.get(filesDir.getParentFile() + "/processed");
			File file = filesDir.listFiles()[0];

			try {
				SendTweet.postToTwitter(tweet, file);
				resultsPath.toFile().mkdirs();
				Files.move(file.toPath(), Paths.get(resultsPath.toString(), file.getName().toString()));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			File file = filesDir;
			try {
				SendTweet.postToTwitter(tweet, file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		tweets.remove(0);
	}

}
