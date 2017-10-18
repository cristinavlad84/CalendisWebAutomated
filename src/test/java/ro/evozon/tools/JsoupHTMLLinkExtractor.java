package ro.evozon.tools;

import java.net.URLDecoder;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ro.evozon.tools.HTMLLInkExtractor.HtmlLink;

public class JsoupHTMLLinkExtractor {
	public static void main(String[] args) {
		HTMLLInkExtractor extractor= new HTMLLInkExtractor();
		String html="<!doctype html>\n" + 
				"<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n" + 
				"<head>\n" + 
				"    <meta charset=\"UTF-8\">\n" + 
				"    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" + 
				"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" + 
				"    <title>Calendis</title>\n" + 
				"    <style type=\"text/css\">\n" + 
				"\n" + 
				"        body {\n" + 
				"            margin: 0;\n" + 
				"        }\n" + 
				"        body, table, td {\n" + 
				"            font-family: arial;\n" + 
				"        }\n" + 
				"        p {\n" + 
				"            margin-top: 15px;\n" + 
				"            margin-bottom: 15px;\n" + 
				"        }\n" + 
				"        a:hover {\n" + 
				"            text-decoration: underline !important;\n" + 
				"            cursor: pointer;\n" + 
				"        }\n" + 
				"        .calendis-email-wrapper {\n" + 
				"            width: 100%;\n" + 
				"        }\n" + 
				"        .calendis-email-container {\n" + 
				"            max-width: 600px;\n" + 
				"            width: 100%;\n" + 
				"            margin-left: auto;\n" + 
				"            margin-right: auto;\n" + 
				"        }\n" + 
				"        .calendis-email-header {\n" + 
				"            position: relative;\n" + 
				"            padding-top: 37px;\n" + 
				"            padding-bottom: 37px;\n" + 
				"            background-image: url('https://business.calendis.ro/images/emails/email-header.png');\n" + 
				"        }\n" + 
				"        @media only screen and (min-width: 480px) {\n" + 
				"            .calendis-email-header {\n" + 
				"                padding-top: 60px;\n" + 
				"                padding-bottom: 60px;\n" + 
				"            }\n" + 
				"        }\n" + 
				"        .calendis-logo {\n" + 
				"            background-image: url('https://business.calendis.ro/images/emails/calendis-white-logo.png');\n" + 
				"            width: 84px;\n" + 
				"            height: 30px;\n" + 
				"        }\n" + 
				"        .header-img {\n" + 
				"            width: 100%;\n" + 
				"            height: 100%;\n" + 
				"            position: absolute;\n" + 
				"            top: 0;\n" + 
				"            left: 0;\n" + 
				"            z-index: -1;\n" + 
				"        }\n" + 
				"        .greeting-text {\n" + 
				"            font-size: 24px;\n" + 
				"            font-weight: 300;\n" + 
				"        }\n" + 
				"        @media only screen and (min-width: 480px) {\n" + 
				"            .greeting-text {\n" + 
				"                font-size: 34px;\n" + 
				"            }\n" + 
				"        }\n" + 
				"        .main-email-body {\n" + 
				"            color: #666666;\n" + 
				"            font-size: 16px;\n" + 
				"            line-height: 22px;\n" + 
				"            text-align: justify;\n" + 
				"            padding-top: 20px;\n" + 
				"            padding-bottom: 20px;\n" + 
				"        }\n" + 
				"        @media only screen and (min-width: 480px) {\n" + 
				"            .main-email-body {\n" + 
				"                padding: 50px;\n" + 
				"                font-size: 18px;\n" + 
				"                line-height: 28px;\n" + 
				"            }\n" + 
				"        }\n" + 
				"        @media only screen and (max-width: 479px) {\n" + 
				"            .email-content td{\n" + 
				"                padding-left: 20px;\n" + 
				"                padding-right: 20px;\n" + 
				"            }\n" + 
				"        }\n" + 
				"        .grey-section {\n" + 
				"            background-color: #ededed;\n" + 
				"        }\n" + 
				"        @media only screen and (min-width: 480px) {\n" + 
				"            .grey-section {\n" + 
				"                padding-left: 20px;\n" + 
				"                padding-right: 20px;\n" + 
				"            }\n" + 
				"        }\n" + 
				"        .details-section {\n" + 
				"            width: 100%;\n" + 
				"            padding-top: 20px;\n" + 
				"            padding-bottom: 20px;\n" + 
				"            line-height: 22px;\n" + 
				"        }\n" + 
				"        .details-section td {\n" + 
				"            padding-top: 5px;\n" + 
				"            padding-bottom: 5px;\n" + 
				"            font-size: 16px;\n" + 
				"        }\n" + 
				"        .above-footer-section {\n" + 
				"            padding-top: 21px;\n" + 
				"            padding-bottom: 21px;\n" + 
				"            padding-left: 20px;\n" + 
				"            padding-right: 20px;\n" + 
				"            color:  #575555;\n" + 
				"            font-size: 12px;\n" + 
				"            font-style: italic;\n" + 
				"        }\n" + 
				"        @media only screen and (min-width: 480px) {\n" + 
				"            .above-footer-section {\n" + 
				"                padding-top: 18px;\n" + 
				"                padding-bottom: 18px;\n" + 
				"                padding-left: 60px;\n" + 
				"                padding-right: 60px;\n" + 
				"            }\n" + 
				"        }\n" + 
				"        .email-footer td {\n" + 
				"            color: #a5a5a5;\n" + 
				"        }\n" + 
				"        .email-footer {\n" + 
				"            padding-bottom: 30px;\n" + 
				"            padding-top: 25px;\n" + 
				"            background-color: #4c4c4b;\n" + 
				"        }\n" + 
				"        @media only screen and (min-width: 480px) {\n" + 
				"            .email-footer {\n" + 
				"                padding-top: 23px;\n" + 
				"                padding-bottom: 23px;\n" + 
				"                padding-left: 30px;\n" + 
				"                padding-right: 30px;\n" + 
				"            }\n" + 
				"        }\n" + 
				"        .media-link {\n" + 
				"            width: 30px;\n" + 
				"            height: 30px;\n" + 
				"            margin-left: 10px;\n" + 
				"            margin-right: 10px;\n" + 
				"        }\n" + 
				"        @media only screen and (min-width: 480px) {\n" + 
				"            .media-link{\n" + 
				"                margin-left: 18px;\n" + 
				"                margin-right: 18px;\n" + 
				"            }\n" + 
				"        }\n" + 
				"        .footer-copyright {\n" + 
				"            margin: auto !important;\n" + 
				"        }\n" + 
				"        .footer-copyright td {\n" + 
				"            padding-top: 5px;\n" + 
				"            padding-bottom: 5px;\n" + 
				"            padding-left: 10px;\n" + 
				"            padding-right: 10px;\n" + 
				"            font-size: 12px;\n" + 
				"        }\n" + 
				"        .highlight-text {\n" + 
				"            color: #ec2d2b !important;\n" + 
				"            text-decoration: none !important;\n" + 
				"        }\n" + 
				"        .mention-line {\n" + 
				"            font-size: 14px;\n" + 
				"            font-style: italic;\n" + 
				"        }\n" + 
				"        td.phone:hover, td.phone:active, td.phone:focus {\n" + 
				"            text-decoration: underline;\n" + 
				"        }\n" + 
				"        .Object, a {\n" + 
				"            color: inherit !important;\n" + 
				"        }\n" + 
				"        ul {\n" + 
				"            padding-left: 20px;\n" + 
				"        }\n" + 
				"        ul li {\n" + 
				"            color: #ec2d2b;\n" + 
				"        }\n" + 
				"        ul li span {\n" + 
				"            color: #666666;\n" + 
				"        }\n" + 
				"        button {\n" + 
				"            background-color: #ef5655;\n" + 
				"            width: 160px;\n" + 
				"            height: 35px;\n" + 
				"            color: #FFF;\n" + 
				"            outline: 0;\n" + 
				"            border: 0;\n" + 
				"            border-radius: 3px;\n" + 
				"            font-size: 14px;\n" + 
				"            font-weight: bold;\n" + 
				"            text-transform: uppercase;\n" + 
				"            cursor: pointer;\n" + 
				"        }\n" + 
				"        button:hover {\n" + 
				"            background-color: #ec2d2b;\n" + 
				"        }\n" + 
				"        @media screen and (min-width: 480px) {\n" + 
				"            button {\n" + 
				"                width: 300px;\n" + 
				"                height: 50px;\n" + 
				"                font-size: 18px\n" + 
				"            }\n" + 
				"        }\n" + 
				"        .button-wrapper {\n" + 
				"            width: 100%;\n" + 
				"        }\n" + 
				"        .button-wrapper td {\n" + 
				"            padding-bottom: 40px;\n" + 
				"        }\n" + 
				"        @media screen and (min-width: 480px) {\n" + 
				"            .button-wrapper td {\n" + 
				"                padding-bottom: 50px;\n" + 
				"            }\n" + 
				"        }\n" + 
				"    </style>\n" + 
				"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" + 
				"</head>\n" + 
				"<body><!-- begin 33mail tag --><div align=\"center\"><div style=\"text-align: center; width: 600px; border: 1px solid #737578; padding: 0px; background-color:#ffffff; color:#737578; font-size:12px;\">This email was sent to the alias 'debugging@automation.33mail.com' by 'contact@calendis.ro',<br />and 33Mail forwarded it to you. To block all further emails to this alias click <a href=\"http://www.33mail.com/alias/unsub/753777a75fe57d93e533492ef7ada227\">here</a><a href=\"http://www.launchbit.com/az/17278-10977/\"><img width=\"468\" height=\"60\" src=\"http://www.launchbit.com/az-images/17278-10977/?cb=1506648470982\" /></a><br /><small>(<a href=\"http://www.33mail.com/dashboard/choose_account\">Prefer no ads? Upgrade to Premium.</a>)</small><img src=\"http://www.33mail.com/page/opened1/71b3b6b96952ec5c7825ac5db8047eca728d17f8\" width=1 height=1 border=0 /></div></div><br /><!-- end 33mail tag -->\n" + 
				"    <table class=\"calendis-email-wrapper\" cellspacing=\"0\" cellpadding=\"0\" style=\"width:100%;\">\n" + 
				"        <tr>\n" + 
				"            <td>\n" + 
				"                <table align=\"center\" class=\"calendis-email-container\" cellspacing=\"0\" cellpadding=\"0\">\n" + 
				"                    <tr>\n" + 
				"                        <td class=\"calendis-email-header\" align=\"center\" background=\"\">\n" + 
				"                            <div class=\"calendis-logo\"></div>\n" + 
				"                        </td>\n" + 
				"                    </tr>\n" + 
				"                    <tr>\n" + 
				"                        <td class=\"main-email-body\">\n" + 
				"                            \n" + 
				"<table class=\"email-content\">\n" + 
				"    <td>\n" + 
				"        <div class=\"greeting-text\">Salut,</div>\n" + 
				"    </td>\n" + 
				"</table>\n" + 
				"<table class=\"email-content\">\n" + 
				"    <tr>\n" + 
				"        <td>\n" + 
				"            <p>Contul tău Calendis Business a fost creat cu succes.</p>\n" + 
				"            <p>Îndată ce ți-ai activat contul poți începe să folosești Calendis Business \n" + 
				"            <strong class=\"highlight-text\">gratuit o lună</strong>,\n" + 
				"            timp în care beneficiezi de toate funcţionalităţile aplicației: platforma de programări,\n" + 
				"            <strong class=\"highlight-text\">100 de SMS-uri </strong>\n" + 
				"            gratuite pentru notificarea clienţilor și platforma de email marketing.</p>\n" + 
				"        </td>\n" + 
				"    </tr>\n" + 
				"</table>\n" + 
				"<table class=\"email-content button-wrapper\">\n" + 
				"    <tr>\n" + 
				"        <td align=\"center\">\n" + 
				"            <a href=\"https://business.calendis.ro/register?email=debugging@automation.33mail.com\" target=\"_blank\">\n" + 
				"                <button>Activează cont</button>\n" + 
				"            </a>\n" + 
				"        </td>\n" + 
				"    </tr>\n" + 
				"</table>\n" + 
				"<table class=\"email-content\">\n" + 
				"    <tr>\n" + 
				"        <td>\n" + 
				"            Calendis va crește performanța businessului tău prin:\n" + 
				"            <ul>\n" + 
				"                <li><span>Crearea și organizarea flexibilă a programărilor</span></li>\n" + 
				"                <li><span>Notificări SMS trimise clienților tăi pentru mai puține programări ratate</span></li>\n" + 
				"                <li><span>Evidența clară asupra clienților</span></li>\n" + 
				"                <li><span>Rapoarte pe programări, angajați, servicii, clienți, venituri</span></li>\n" + 
				"\n" + 
				"            </ul>\n" + 
				"        </td>\n" + 
				"    </tr>\n" + 
				"</table>\n" + 
				"<table class=\"email-content\">\n" + 
				"    <tr>\n" + 
				"        <td class=\"highlight-text\">\n" + 
				"            Spor la programat!<br/>\n" + 
				"            Echipa Calendis\n" + 
				"        </td>\n" + 
				"    </tr>\n" + 
				"</table>\n" + 
				"\n" + 
				"                        </td>\n" + 
				"                    </tr>\n" + 
				"                    <tr>\n" + 
				"                        <td class=\"above-footer-section grey-section\" align=\"center\">\n" + 
				"                            *În cazul în care adresa de e-mail îți aparține, dar nu ai înregistrat contul te rugăm să ignori acest mesaj. Mulțumim!\n" + 
				"                        </td>\n" + 
				"                    </tr>\n" + 
				"                    <tr>\n" + 
				"                        <td class=\"email-footer\" align=\"center\">\n" + 
				"                            <table cellspacing=\"0\" cellpadding=\"0\">\n" + 
				"                                <tr>\n" + 
				"                                    <td>\n" + 
				"                                        <a href=\"https://www.facebook.com/calendis.ro\" target=\"_blank\">\n" + 
				"                                            <img class=\"media-link\" alt=\"facebook profile\" title=\"Facebook Calendis\" src=\"https://business.calendis.ro/images/emails/icon-facebook.png\" style=\"display:block\">\n" + 
				"                                        </a>\n" + 
				"                                    </td>\n" + 
				"                                    <td>\n" + 
				"                                        <a href=\"https://twitter.com/Calendis\">\n" + 
				"                                            <img class=\"media-link\" alt=\"twitter\" profile title=\"Twitter Calendis\" target=\"_blank\" src=\"https://business.calendis.ro/images/emails/icon-twitter.png\" style=\"display:block\">\n" + 
				"                                        </a>\n" + 
				"                                    </td>\n" + 
				"                                    <td>\n" + 
				"                                        <a href=\"https://www.instagram.com/calendisapp/\" target=\"_blank\">\n" + 
				"                                            <img class=\"media-link\" alt=\"instagra profilem\" title=\"Instagram Calendis\" src=\"https://business.calendis.ro/images/emails/icon-instagram.png\" style=\"display:block\">\n" + 
				"                                        </a>\n" + 
				"                                    </td>\n" + 
				"                                    <td>\n" + 
				"                                        <a href=\"https://www.linkedin.com/company/5046349/\" target=\"_blank\">\n" + 
				"                                            <img class=\"media-link\" alt=\"linkedin profile\" title=\"Linkedin Calendis\"  src=\"https://business.calendis.ro/images/emails/icon-linkedin.png\" style=\"display:block\">\n" + 
				"                                        </a>\n" + 
				"                                    </td>\n" + 
				"                                    <td>\n" + 
				"                                        <a href=\"https://www.youtube.com/channel/UCgZVR0WcxJm9xog_GpkDePQ/featured\" target=\"_blank\">\n" + 
				"                                            <img class=\"media-link\"  alt=\"youtube\" profile title=\"Youtube Calendis\" src=\"https://business.calendis.ro/images/emails/icon-youtube.png\" style=\"display:block\">\n" + 
				"                                        </a>\n" + 
				"                                    </td>\n" + 
				"                                </tr>\n" + 
				"                                <tr>\n" + 
				"                                    <td colspan=\"5\">\n" + 
				"                                        <table class=\"footer-copyright\" align=\"center\">\n" + 
				"                                            <td class=\"phone\">\n" + 
				"                                                0770.901.141\n" + 
				"                                            </td>\n" + 
				"                                            <td>\n" + 
				"                                                &reg;2017\n" + 
				"                                                Calendis\n" + 
				"                                            </td>\n" + 
				"                                        </table>\n" + 
				"                                    </td>\n" + 
				"                                </tr>\n" + 
				"                            </table>\n" + 
				"                        </td>\n" + 
				"                    </tr>\n" + 
				"                </table>\n" + 
				"            </td>\n" + 
				"        </tr>\n" + 
				"    </table>\n" + 
				"<!-- begin 33mail footer tag --><br /><div align=\"center\"><div style=\"text-align: center; width: 600px; border: 1px solid #737578; padding: 0px; background-color:#ffffff; color:#737578; font-size:12px;\">This email was sent to the alias 'debugging@automation.33mail.com' by 'contact@calendis.ro',<br />and 33Mail forwarded it to you. To block all further emails to this alias click <a href=\"http://www.33mail.com/alias/unsub/753777a75fe57d93e533492ef7ada227\">here</a><a href=\"http://www.launchbit.com/az/17278-11226/\">\n" + 
				"<img width=\"468\" height=\"60\" src=\"http://www.launchbit.com/az-images/17278-11226/?cb=1506648470982\" /></a><br /><small>(<a href=\"http://www.33mail.com/dashboard/choose_account\">Prefer no ads? Upgrade to Premium.</a>)</small></div></div><!-- end 33mail footer tag --></body>\n" + 
				"</html>";
		ArrayList<String> allHrefs = new ArrayList<>();
		Document doc=Jsoup.parse(html);
		Elements linkList=doc.select("a");
		for(Element l: linkList) {
			String linkHref=l.attr("href");
			allHrefs.add(linkHref);
		}
		allHrefs.stream().forEach(k->System.out.println(k));
//		ArrayList<HtmlLink> links = extractor.grabHTMLLinks(html);
//		String s = new String();
//		String finalLink = new String();
//		if (links.size() > 0) {
//
//			
//		}

}
}
