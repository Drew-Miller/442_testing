package net.sf.eclipsecs.sample.checks;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class engDict {
	static HashSet<String> InDict; //Dictionary
	
	static boolean IsInit = false; //Static Class, so we only need one
	
	public static void initDict() //Initialize Dictionary Once
	{	
		if(!IsInit)
		{
			InDict = new HashSet<String>();
			MyReader(InDict);
		}
		IsInit = true;
	}
	
	 public static boolean DoContain(String arg) //Checks to see if string is in the Dictionary
	 {
		 //System.out.println(arg + " " + InDict.contains(arg));
		return  InDict.contains(arg);
	 }
	 
	public static void MyReader(HashSet<String> Me) //Populates Dictionary 
	{
		final String myFile = "taggers/words.txt";
		try(BufferedReader br = new BufferedReader(new FileReader(myFile)))
		{
			String sCurrentLine;
			while((sCurrentLine = br.readLine())!=null)
			{
				Me.add(sCurrentLine.toLowerCase());
				//System.out.println(sCurrentLine);
			}
		}
		catch(IOException e)
		{e.printStackTrace();}
		//System.out.println("The Dict is Empty? " + Me.isEmpty()+ ", Word Count: " + Me.size());
	};
}
