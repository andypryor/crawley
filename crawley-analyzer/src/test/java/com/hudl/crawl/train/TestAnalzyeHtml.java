package com.hudl.crawl.train;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.hudl.crawl.html.Analyzer;

@RunWith(JukitoRunner.class)
public class TestAnalzyeHtml 
{
	@Test
	public void testAnalyze(Analyzer analyzer) throws Exception
	{
		String testHtml = readTestFile();
		
		System.out.println(analyzer.analyzeText(testHtml));
	}
	
	public static String readTestFile() throws Exception
	{
		 byte[] encoded = Files.readAllBytes(Paths.get(CreateTrainingRawHtml.class.getClassLoader().getResource("oksoccerclub.txt").toURI()));
		 return new String(encoded, "UTF-8");
	}
}
