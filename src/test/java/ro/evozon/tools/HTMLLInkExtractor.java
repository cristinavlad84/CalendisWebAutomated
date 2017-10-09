package ro.evozon.tools;

import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLLInkExtractor {
	private Pattern patternTag, patternLink;
	private Matcher matcherTag, matcherLink;
	private static final String HTML_A_TAG_PATTERN = "(?i)<a([^>]+)>(.+?)</a>";
	private static final String HTML_A_HREF_TAG_PATTERN = "\\s*(?i)href\\s*=\\s*(\"([^\"]*\")|'[^']*'|([^'\">\\s]+))";

	public HTMLLInkExtractor() {
		patternTag = Pattern.compile(HTML_A_TAG_PATTERN);
		patternLink = Pattern.compile(HTML_A_HREF_TAG_PATTERN);
	}

	public ArrayList<HtmlLink> grabHTMLLinks(final String html) {

		// Vector<HtmlLink> result = new Vector<HtmlLink>();
		ArrayList<HtmlLink> result = new ArrayList<HtmlLink>();
		matcherTag = patternTag.matcher(html);

		while (matcherTag.find()) {

			String href = matcherTag.group(1); // href
			String linkText = matcherTag.group(2); // link text

			matcherLink = patternLink.matcher(href);

			while (matcherLink.find()) {

				String link = matcherLink.group(1); // link
				HtmlLink obj = new HtmlLink();
				obj.setLink(link);
				obj.setLinkText(linkText);
				obj.replaceInvalidChar(linkText);
				result.add(obj);

			}

		}

		return result;

	}

	public class HtmlLink {

		String link;
		String linkText;

		HtmlLink() {
		}

        // @Override
		// public String toString() {
		// return new StringBuffer("Link : ").append(this.link)
		// .append(" Link Text : ").append(this.linkText).toString();
		// }

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = replaceInvalidChar(link);
		}

		public String getLinkText() {
			return linkText;
		}

		public void setLinkText(String linkText) {
			this.linkText = linkText;
		}

		private String replaceInvalidChar(String link) {
			link = link.replaceAll("'", "");
			link = link.replaceAll("\"", "");
			// int i = link.indexOf(" ");
			// String first = link.substring(0, i);
			// String rest = link.substring(i);
			// System.out.println("esteeee"+first);
			return link;
		}

	}

	public String getMatchedLink(ArrayList<HtmlLink> links, String textBody,
			String linkMatchExpression) {
		String s = new String();

		int count = 0;
		for (HtmlLink temp : links) {
			s = temp.getLink();

			if (s.contains(linkMatchExpression)) {
				count++;
				s = s.replaceAll("(\\s+)?<a.+?/a>(\\s+)?", "");

				System.out.println("Linkul este " + s);
				System.out.println("_______________________________");
				break;
			}

		}
		if (count == 0) {
			try {
				throw new Exception("There is no link matching expression "
						+ linkMatchExpression);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return s;
	}
}
