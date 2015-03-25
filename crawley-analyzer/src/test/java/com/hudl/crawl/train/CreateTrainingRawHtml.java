package com.hudl.crawl.train;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;

/**
 * 
 * Downloads URLS in train-url.txt and creates files at output dir.
 * 
 * @author andrew.pryor
 */
public class CreateTrainingRawHtml 
{
	private static final String OUTPUT_DIR = "c:\\train\\";
	
	public static void main(String[] args) throws Exception
	{
		String trainURLS = readURLFile();
		for(String url : trainURLS.split("\n"))
		{
			url = url.trim();
			System.out.println("Processing " + url);
			try
			{
				String contents = IOUtils.toString(new URL(url));
				writeFile(url, contents);
			}catch(Exception e)
			{
				System.out.println("Failed to get contents for " + url);
				e.printStackTrace();
			}
		}		
	}
	
	public static void writeFile(String url, String contents) throws Exception
	{
		File file = new File(OUTPUT_DIR + url.replaceAll("\\W+", "") + ".txt");
		file.createNewFile();
		PrintWriter writer = new PrintWriter(file);
		writer.write(contents);
		writer.flush();
		writer.close();
	}
	
	public static String readURLFile() throws Exception
	{
		 byte[] encoded = Files.readAllBytes(Paths.get(CreateTrainingRawHtml.class.getClassLoader().getResource("train-urls.txt").toURI()));
		 return new String(encoded, "UTF-8");
	}
}
