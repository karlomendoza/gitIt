package mx.somethingsomething.pojo;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class PeriodicallyScheduledTweet {
	private Integer id;
	private String tweet;
	private TimeUnit timeUnit;
	private Integer gapDuration;
	private File file;

	public PeriodicallyScheduledTweet(int id, String tweet, TimeUnit timeUnit, int gapDuration, File file) {
		this.id = id;
		this.tweet = tweet;
		this.timeUnit = timeUnit;
		this.gapDuration = gapDuration;
		this.file = file;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTweet() {
		return tweet;
	}

	public void setTweet(String tweet) {
		this.tweet = tweet;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	public Integer getGapDuration() {
		return gapDuration;
	}

	public void setGapDuration(Integer gapDuration) {
		this.gapDuration = gapDuration;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
