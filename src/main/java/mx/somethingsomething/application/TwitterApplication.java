package mx.somethingsomething.application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TwitterApplication {

	private static Twitter twitter = TwitterFactory.getSingleton();

	private static RequestToken requestToken;

	private TwitterApplication() {
	}

	public static boolean isUserAuthenticated() {

		Properties prop = new Properties();

		try (FileInputStream input = new FileInputStream(
				"twitter4j.properties")) {
			prop.load(input);

			String accessToken = prop.getProperty("oauth.accessToken");
			String accessTokenSecret = prop
					.getProperty("oauth.accessTokenSecret");

			if (accessToken != null && !accessToken.isEmpty()
					&& accessTokenSecret != null
					&& !accessTokenSecret.isEmpty())
				return true;
			return false;

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return false;
	}

	public static String getAuthorizationURL() {
		try {
			requestToken = twitter.getOAuthRequestToken();
		} catch (TwitterException e) {
			// TODO do stuff with this one
			e.printStackTrace();
		}
		return requestToken.getAuthorizationURL();
	}

	public static void authorize(String pin) throws Exception {
		AccessToken accessToken = null;
		try {
			if (pin.length() > 0) {
				accessToken = twitter.getOAuthAccessToken(requestToken, pin);
			} else {
				accessToken = twitter.getOAuthAccessToken();
			}
		} catch (TwitterException te) {
			if (401 == te.getStatusCode()) {
				System.out.println("Unable to get the access token.");
			} else {
				te.printStackTrace();
			}
		}
		// persist to the accessToken for future reference.
		storeAccessToken(twitter.verifyCredentials().getId(), accessToken);
	}

	private static void storeAccessToken(long useId, AccessToken accessToken) {
		Properties prop = new Properties();

		try (FileInputStream input = new FileInputStream(
				"twitter4j.properties")) {
			prop.load(input);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try (OutputStream output = new FileOutputStream(
				"twitter4j.properties");) {

			String accessTokenStr = accessToken.getToken();
			String accessTokenSecret = accessToken.getTokenSecret();
			// set the properties value
			prop.setProperty("oauth.accessToken", accessTokenStr);
			prop.setProperty("oauth.accessTokenSecret", accessTokenSecret);

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException e) {
			// TODO Show error to user
			e.printStackTrace();
		}
	}

	public RequestToken getRequestToken() {
		return requestToken;
	}

	public static Twitter getTwitter() {
		return twitter;
	}
}
