package mx.somethingsomething.services;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class PeriodicallyScheduledTweets extends Thread {

	private String tweet;
	private File tweetFile;
	private File filesDir;

	@Override
	public void run() {
		proccessAllTweets();
	}

	public PeriodicallyScheduledTweets(String _tweet, File _filesDir) {
		this.tweet = _tweet;
		this.filesDir = _filesDir;
	}

	public PeriodicallyScheduledTweets(File _tweetFile, File _filesDir) {
		this.tweetFile = _tweetFile;
		this.filesDir = _filesDir;
	}

	public void proccessAllTweets() {
		if (tweetFile != null && tweetFile.isFile()) {
			List<String> readLines;
			try {
				readLines = FileUtils.readLines(tweetFile, Charset.defaultCharset());
				String fileConfigContext = FileUtils.readFileToString(tweetFile, Charset.defaultCharset());
				fileConfigContext = fileConfigContext.replaceAll(readLines.get(0) + System.lineSeparator(), "");
				FileUtils.writeStringToFile(tweetFile, fileConfigContext, Charset.defaultCharset());
				tweet = readLines.get(0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	}

}
