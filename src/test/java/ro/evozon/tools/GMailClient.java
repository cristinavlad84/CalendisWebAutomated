package ro.evozon.tools;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Flags.Flag;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimeUtility;
import javax.mail.search.FlagTerm;

public class GMailClient {

	private String userName;
	private String password;

	private String receivingHost;

	//     private int receivingPort;
	public void setAccountDetails(String userName, String password) {
		// sender's email can also use as User Name
		this.userName = userName;
		this.password = password;

	}

	public void readGmail() {
		this.receivingHost = "imap.gmail.com";
		Properties props2 = System.getProperties();
		props2.setProperty("mail.store.protocol", "imaps");
		Session session2 = Session.getDefaultInstance(props2, null);
		try {
			Store store = session2.getStore("imaps");
			System.out.println("Us" + this.userName + "PSW " + this.password);
			store.connect(this.receivingHost, this.userName, this.password);

			Folder folder = store.getFolder("INBOX");// get inbox
			folder.open(Folder.READ_ONLY);// open folder only to read
			Message message[] = folder.getMessages();
			for (int i = 0; i < message.length; i++) {
				// print subjects of all mails in the inbox

				System.out.println(message[i].getSubject());

			}

			folder.close(true);
			store.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	public String getEmailMessageBySubject(String subject) {
		String buff = null;

		this.receivingHost = "imap.gmail.com";
		Properties props2 = System.getProperties();
		props2.setProperty("mail.store.protocol", "imaps");
		Session session2 = Session.getDefaultInstance(props2, null);

		try {
			Store store = session2.getStore("imaps");
			System.out.println("Us" + this.userName + "PSW " + this.password);
			store.connect(this.receivingHost, this.userName, this.password);

			Folder inbox = store.getFolder("INBOX");// get inbox
			// int count = folder.getNewMessageCount();
			// System.out.println("noi mesaje " + count);
			inbox.open(Folder.READ_ONLY);// open folder only to read
			/* Get the messages which is unread in the Inbox */
			Message messages[] = inbox.search(new FlagTerm(
					new Flags(Flag.SEEN), false));

			/* Use a suitable FetchProfile */
			FetchProfile fp = new FetchProfile();
			fp.add(FetchProfile.Item.ENVELOPE);
			fp.add(FetchProfile.Item.CONTENT_INFO);
			inbox.fetch(messages, fp);

			try {
				// printAllMessages(messages);
				buff = searchMessages(messages, subject);
				System.out.println(buff);

				inbox.close(true);
				store.close();
			} catch (Exception ex) {
				System.out.println("Exception arise at the time of read mail");
				ex.printStackTrace();
			}
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (MessagingException e) {
			e.printStackTrace();
			System.exit(2);
		}
		return buff;
	}

	private String getTextFromMessage(Message message) throws IOException,
			MessagingException {
		String result = "";
		String contentType = message.getContentType().toLowerCase();
		System.out.println("type" + contentType);
		if (contentType.contains("text/plain")
				|| contentType.contains("text/html")) {
			System.out.println("type +textplain/html");
			try {
				InputStream is = message.getInputStream();
				String s = encodeCorrectly(is);

				result = s;

			} catch (Exception ex) {
				result = "[Error downloading content]";
				ex.printStackTrace();
			}
		} else if (message.isMimeType("multipart/*")) {
			System.out.println("type +multipart");
			MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
			result = getTextFromMimeMultipart(mimeMultipart);
		}
		return result;
	}

	private String getTextFromMimeMultipart(MimeMultipart mimeMultipart)
			throws IOException, MessagingException {

		int count = mimeMultipart.getCount();
		if (count == 0)
			throw new MessagingException(
					"Multipart with no body parts not supported.");
		boolean multipartAlt = new ContentType(mimeMultipart.getContentType())
				.match("multipart/alternative");
		if (multipartAlt)
			// alternatives appear in an order of increasing
			// faithfulness to the original content. Customize as req'd.
			return getTextFromBodyPart(mimeMultipart.getBodyPart(count - 1));
		String result = "";
		for (int i = 0; i < count; i++) {
			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
			result += getTextFromBodyPart(bodyPart);
		}
		return result;
	}

	private String getTextFromBodyPart(BodyPart bodyPart) throws IOException,
			MessagingException {

		String result = "";
		if (bodyPart.isMimeType("text/plain")) {
			result = (String) bodyPart.getContent();
		} else if (bodyPart.isMimeType("text/html")) {
			String html = (String) bodyPart.getContent();
			result = org.jsoup.Jsoup.parse(html).text();
		} else if (bodyPart.getContent() instanceof MimeMultipart) {
			result = getTextFromMimeMultipart((MimeMultipart) bodyPart
					.getContent());
		}
		return result;
	}

	/*
	 * search mesahe by subject and return its position (zero based)-> last one
	 * (if there are many with same subject)
	 */
	public String searchMessages(Message[] message, String subject)
			throws Exception {
		String s = "";
		int index = 0;
		for (int i = 0; i < message.length; i++) {
			if (ConfigUtils.removeAccents(message[i].getSubject())
					.contentEquals(subject)) {
				index = i;

			}

		}
		if (index > 0) {
			s = getTextFromMessage(message[index]);
		} else
			throw new Exception("There is no unread message with subject"
					+ subject + "to match");
		return s;
	}

	public void printAllMessages(Message[] msgs) throws Exception {
		for (int i = 0; i < msgs.length; i++) {
			System.out.println("MESSAGE #" + (i + 1) + ":");
			printEnvelope(msgs[i]);
		}
	}

	/* Print the envelope(FromAddress,ReceivedDate,Subject) */
	public void printEnvelope(Message message) throws Exception {
		Address[] a;
		// FROM
		if ((a = message.getFrom()) != null) {
			for (int j = 0; j < a.length; j++) {
				System.out.println("FROM: " + a[j].toString());
			}
		}
		// TO
		if ((a = message.getRecipients(Message.RecipientType.TO)) != null) {
			for (int j = 0; j < a.length; j++) {
				System.out.println("TO: " + a[j].toString());
			}
		}
		String subject = message.getSubject();
		Date receivedDate = message.getReceivedDate();
		String content = message.getContent().toString();
		System.out.println("Subject : " + subject);
		System.out.println("Received Date : " + receivedDate.toString());
		// System.out.println("Content : " + content);
		String s = getContent(message);
		System.out.println(s);
	}

	private String encodeCorrectly(InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is,
				StandardCharsets.UTF_8.toString()).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	public String getContent(Message msg) throws IOException,
			MessagingException {
		String content = "";
		Object msgContent = null;
		try {
			msgContent = msg.getContent();
		} catch (UnsupportedEncodingException uex) {

			if (msgContent instanceof String) {
				content = (String) msg.getContent();
			} else {

				Multipart mp = (Multipart) msg.getContent();

				if (mp.getCount() > 0) {
					Part bp = mp.getBodyPart(0);
					try {
						content = dumpPart(bp);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		// content = getText(bodyPart); // the changed code
		return content.trim();
	}

	private String dumpPart(Part p) throws Exception {

		InputStream is = p.getInputStream();
		// If "is" is not already buffered, wrap a BufferedInputStream
		// around it.
		if (!(is instanceof BufferedInputStream)) {
			is = new BufferedInputStream(is);
		}
		return getStringFromInputStream(is);
	}

	private String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

}
