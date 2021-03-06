package ro.evozon.tools;

import java.net.URLDecoder;
import java.util.ArrayList;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;

import ro.evozon.tools.HTMLLInkExtractor.HtmlLink;

public class Tools extends GMailClient {
	protected HTMLLInkExtractor extractor = new HTMLLInkExtractor();

	public String getLinkFromEmails(String userName, String psw, String messageSubject, String linkMatchExpression,
			String emailAddressTo) throws Exception {

		String s = new String();
		String finalLink = new String();
		setAccountDetails(userName, psw);

		String messageBody = getEmailMessagesBySubjectAndEmailAddress(messageSubject, emailAddressTo);

		// System.out.println("the text is " + m);

		ArrayList<HtmlLink> links = extractor.grabJsoupHTMLLinks(messageBody);
		System.out.println("Size is ...." + links.size());

		if (links.size() > 0) {

			s = URLDecoder.decode(extractor.getMatchedLink(links, messageBody, linkMatchExpression), "UTF-8")
					.toLowerCase();

			if (s.contains(emailAddressTo.toLowerCase())) {
				System.out.println("decoded url is " + s);
				finalLink = s;
			} else {
				throw new Exception("The email address  " + emailAddressTo + "does not match");
			}
		} else {
			throw new Exception("There is no link in message body ");
		}

		return finalLink;
	}

	public String editBusinessActivationLink(String originalLink, String environement) {
		System.out.println("orihginal link =" + originalLink);
		String s = originalLink.replace("business", environement);
		System.out.println("replaced   link " + s);
		return s;
	}

	public static class RetryOnExceptionStrategy {
		public static final int DEFAULT_RETRIES = 10;
		public static final long DEFAULT_WAIT_TIME_IN_MILLI = 3000;

		private int numberOfRetries;
		private int numberOfTriesLeft;
		private long timeToWait;

		public RetryOnExceptionStrategy() {
			this(DEFAULT_RETRIES, DEFAULT_WAIT_TIME_IN_MILLI);
		}

		public RetryOnExceptionStrategy(int numberOfRetries, long timeToWait) {
			this.numberOfRetries = numberOfRetries;
			this.numberOfTriesLeft = numberOfRetries;
			this.timeToWait = timeToWait;
		}

		/**
		 * @return true if there are tries left
		 */
		public boolean shouldRetry() {
			return numberOfTriesLeft > 0;
		}

		public void errorOccured() throws Exception {
			numberOfTriesLeft--;
			if (!shouldRetry()) {
				throw new Exception("Retry Failed: Total " + numberOfRetries + " attempts made at interval "
						+ getTimeToWait() + "ms");
			}
			waitUntilNextTry();
		}

		public long getTimeToWait() {
			return timeToWait;
		}

		private void waitUntilNextTry() {
			try {
				Thread.sleep(getTimeToWait());
			} catch (InterruptedException ignored) {
			}
		}
	}
}