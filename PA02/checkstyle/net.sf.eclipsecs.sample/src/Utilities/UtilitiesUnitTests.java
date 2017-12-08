package Utilities;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilitiesUnitTests {

	// WordNet Unit Tests
	@Test
	public void IsWordTest() {
		WordNet wn = new WordNet();
		assertEquals(true, wn.isWord("dOG"));
		assertEquals(true, wn.isWord("running"));
		assertEquals(true, wn.isWord("farted"));
		assertEquals(false, wn.isWord("Shmekkles"));
	}
	
	@Test
	public void IsNounTest() {
		WordNet wn = new WordNet();
		assertEquals(true, wn.isNoun("dOG"));
		assertEquals(true, wn.isNoun("CATS"));
		assertEquals(false, wn.isNoun("for"));
		assertEquals(false, wn.isNoun("farted"));
		assertEquals(false, wn.isNoun("Shmekkles"));
	}
	
	@Test
	public void IsVerbTest() {
		WordNet wn = new WordNet();
		assertEquals(false, wn.isVerb("blade"));
		assertEquals(true, wn.isVerb("Thinking"));
		assertEquals(false, wn.isVerb("for"));
		assertEquals(true, wn.isVerb("farted"));
		assertEquals(false, wn.isVerb("Shmekkles"));
	}

	@Test
	public void IsSameWordTest() {
		WordNet wn = new WordNet();
		String s1 = "run";
		String s2 = "running";
		String s3 = "sleep";
		
		assertEquals(true, wn.isSameWord(s1, s1) );
		assertEquals(true, wn.isSameWord(s1, s2) );
		assertEquals(false, wn.isSameWord(s1, s3) );
	}
}
