package com.hudl.crawl.html;

import org.jsoup.Jsoup;

/**
 * 
 * @author andrew.pryor
 */
public class Analyzer 
{
    private static final String EMAIL_PATTERN = "([^.@\\s]+)(\\.[^.@\\s]+)*@([^.@\\s]+\\.)+([^.@\\s]+)";
    private static final String PHONE_PATTERN = "\\d{10}|\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}|\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}|\\(\\d{3}\\)-\\d{3}-\\d{4}";

	
	public static String analyzeText(String rawHtml)
	{
		String text = Jsoup.parse(rawHtml).text();
		String output = text.replaceAll(EMAIL_PATTERN, "__email__");
        output = output.replaceAll(PHONE_PATTERN, "__phone__");		
		
		return output;
	}
}
