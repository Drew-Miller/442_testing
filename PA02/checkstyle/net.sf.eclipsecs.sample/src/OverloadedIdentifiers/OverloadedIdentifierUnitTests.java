package OverloadedIdentifiers;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class OverloadedIdentifierUnitTests {

	@Test
	public void TokenizeIdentifierTest() {
		OverloadedIdentifiersCheck check = new OverloadedIdentifiersCheck();
		String s = "_newTest_StringOne_";
		String[] sArray1 = { "new", "Test", "String", "One" };
		ArrayList<String> sList = check.TokenizeIdentifier(s);
		assertEquals(sArray1[0], sList.get(0));
		assertEquals(sArray1[1], sList.get(1));
		assertEquals(sArray1[2], sList.get(2));
		assertEquals(sArray1[3], sList.get(3));
	} 

	@Test
	public void NumNounsTest() {
		OverloadedIdentifiersCheck check = new OverloadedIdentifiersCheck();
		ArrayList<String> sList = new ArrayList<String>();
		sList.add("new");
		sList.add("Test");
		sList.add("the");
		sList.add("Slamacow");
		assertEquals(1, check.numNouns(sList));
	}
	
	@Test
	public void NumVerbsTest() {
		OverloadedIdentifiersCheck check = new OverloadedIdentifiersCheck();
		ArrayList<String> sList = new ArrayList<String>();
		sList.add("new");
		sList.add("Test");
		sList.add("the");
		sList.add("Slamacow");
		assertEquals(1, check.numVerbs(sList));
	}
	
}
