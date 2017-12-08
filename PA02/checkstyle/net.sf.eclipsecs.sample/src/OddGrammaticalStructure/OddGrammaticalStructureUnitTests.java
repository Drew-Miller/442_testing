package OddGrammaticalStructure;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class OddGrammaticalStructureUnitTests {
	@Test
	public void TestCountNoun() {
		OddGrammaticalStructureCheck test = new OddGrammaticalStructureCheck();
		ArrayList<String> testList = new ArrayList<String>();
		testList.add("cat");
		testList.add("turtle");
		testList.add("for");
		
		assertEquals(2, test.CountNouns(testList));
		
		testList.add("to");
		
		assertEquals(2, test.CountNouns(testList));
		
		testList.add("dog");
		testList.add("computer");
		
		assertEquals(4, test.CountNouns(testList));
	}
	
	@Test
	public void TestCountVerb() {
		OddGrammaticalStructureCheck test = new OddGrammaticalStructureCheck();
		ArrayList<String> testList = new ArrayList<String>();
		testList.add("cat");
		testList.add("turtle");
		testList.add("for");
		testList.add("running");
		testList.add("pasta");
		testList.add("be");
		
		assertEquals(4, test.CountVerbs(testList));
	}
}
