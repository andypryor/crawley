package com.hudl.crawl.html;

import org.jsoup.Jsoup;

/**
 * 
 * @author andrew.pryor
 */
public class Analyzer 
{
	public String analyzeText(String rawHtml)
	{
		String text = Jsoup.parse(rawHtml).text();
		
		
		return text;
	}
}
