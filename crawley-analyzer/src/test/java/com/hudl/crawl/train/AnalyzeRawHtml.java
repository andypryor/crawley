package com.hudl.crawl.train;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AnalyzeRawHtml 
{
	private static final String INPUT_FILE = "c:\\input\\urls.txt";
	
	public static void main(String[] args) throws Exception
	{
		File input = new File(INPUT_FILE);
		String urls = readFile(input);
		for(String url : urls.split("\n"))
		{
			System.out.println(unreverseUrl(url.trim()));
		}
	}
	
	 public static String unreverseUrl(String reversedUrl) {
	     StringBuilder buf = new StringBuilder(reversedUrl.length() + 2);
	 
	     int pathBegin = reversedUrl.indexOf('/');
	     if (pathBegin == -1)
	       pathBegin = reversedUrl.length();
	     String sub = reversedUrl.substring(0, pathBegin);
	 
	     String[] splits = sub.split(":"); // {<reversed host>, <port>, <protocol>}
	 
	     buf.append(splits[1]); // add protocol
	     buf.append("://");
	     reverseAppendSplits(splits[0].split("\\."), buf); // splits[0] is reversed
	     // host
	     if (splits.length == 3) { // has a port
	       buf.append(':');
	       buf.append(splits[2]);
	     }
	     buf.append(reversedUrl.substring(pathBegin));
	     return buf.toString();
	}
 
	 private static void reverseAppendSplits(String[] splits, StringBuilder buf) 
	 {
		     for (int i = splits.length - 1; i > 0; i--) {
		       buf.append(splits[i]);
		       buf.append('.');
		     }
		     buf.append(splits[0]);
	 }
	
	
	
	public static void writeFile(File file, String contents) throws Exception
	{
		PrintWriter writer = new PrintWriter(file);
		writer.write(contents);
		writer.flush();
		writer.close();
	}
	
	public static String readFile(File file) throws Exception
	{
		 byte[] encoded = Files.readAllBytes(Paths.get(file.toURI()));
		 return new String(encoded, "UTF-8");
	}

}
