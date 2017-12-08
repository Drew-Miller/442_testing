package Wordnet;

import static org.junit.Assert.*;
import org.mockito.Matchers;
import static org.mockito.Mockito.*;

import org.junit.Test;

public class WordnetTest {	
	
	// UNIT TESTS
	
	@Test
	public void constructorTest() {
		try {
			Wordnet wn = spy(new Wordnet());
			assertTrue(true);
		}
		catch(Exception e) {
			System.out.print(e.getStackTrace());
			assertFalse(true);
		}
	}
	
	@Test
	public void createDictPathTest() {
		try {
			Wordnet wn = spy(new Wordnet());
			
			String path1 = "/path/to/the/dict/";
			String path2 = "/path/to/the/dict";
			String dictPath = "/path/to/the/dict/dict";
				
			// mock the PathExists function
			doReturn(true).when(wn).PathExists(dictPath);
				
			assertTrue(wn.createDictPath(path1).equals(path1 + "dict"));
			assertTrue(wn.createDictPath(path2).equals(path2 + "/dict"));
			assertEquals(wn.createDictPath(path1), wn.createDictPath(path2));
			assertNull(wn.createDictPath(null));
		}
		catch(Exception e) {
			fail("An exception was caught");
		}
	}

	@Test
	public void isWordTest() {
		try {
			Wordnet wn = spy(new Wordnet());
			
			assertTrue(wn.isWord("dog"));
			assertTrue(wn.isWord("house"));
			assertTrue(wn.isWord("xylophone"));

			assertFalse(wn.isWord("asdf"));
			assertFalse(wn.isWord("qwertyuiop"));
			assertFalse(wn.isWord(null));
		}
		catch(Exception e) {
			fail("An exception was caught");
		}
	}
	
	@Test
	public void isNounTest() {	
		try {
			Wordnet wn = spy(new Wordnet());
			
			assertTrue(wn.isNoun("dog"));
			assertTrue(wn.isNoun("cat"));
			assertTrue(wn.isNoun("house"));
			assertTrue(wn.isNoun("human"));
	
			assertFalse(wn.isNoun("ran"));
			assertFalse(wn.isNoun("exists"));
			assertFalse(wn.isNoun("for"));
			assertFalse(wn.isNoun("from"));
	
			assertFalse(wn.isNoun("asdf"));
			assertFalse(wn.isNoun("fdsa"));
			assertFalse(wn.isNoun("qwertuiop"));
			assertFalse(wn.isNoun(null));
		}
		catch(Exception e) {
			fail("An exception was caught");
		}
	}

	@Test
	public void isHypernymTest() {
		try {
			Wordnet wn = spy(new Wordnet());
			
			String c = "dog";
			String s1 = "canine";
			String s2 = "sausage";
			String s3 = "cat";
			String s4 = "mammal";
				
			doReturn(true).when(wn).isNoun(c);
			doReturn(true).when(wn).isNoun(s1);
			doReturn(true).when(wn).isNoun(s2);
			doReturn(true).when(wn).isNoun(s3);
			doReturn(true).when(wn).isNoun(s3);
			
			assertTrue(wn.isHypernym(c, s1));
			assertTrue(wn.isHypernym(c, s2));
			assertFalse(wn.isHypernym(c, s3));
			assertFalse(wn.isHypernym(c, s4));
			assertFalse(wn.isHypernym(c, null));
			assertFalse(wn.isHypernym(null, s1));
			assertFalse(wn.isHypernym(null, null));
		}
		catch(Exception e) {
			fail("An exception was caught");
		}
	}
	
	@Test
	public void ishypernymMissingTest() {
		try {
			Wordnet wn = spy(new Wordnet());
			
			
			// use real inputs to check that the program is working with nouns
			String c = "dog";
			String s1 = "canine";
			String s2 = "cat";
			String notWord = "asdf";
			String notNoun = "flew";
			
			doReturn(true).when(wn).isWord(c);
			doReturn(true).when(wn).isWord(s1);
			doReturn(true).when(wn).isWord(s2);
			doReturn(false).when(wn).isWord(notWord);
			doReturn(true).when(wn).isWord(notNoun);
			
			doReturn(true).when(wn).isNoun(c);
			doReturn(true).when(wn).isNoun(s1);
			doReturn(true).when(wn).isNoun(s2);
			doReturn(false).when(wn).isNoun(notWord);

			doReturn(true).when(wn).isHypernym(c, s1);
			doReturn(false).when(wn).isHypernym(c, s2);

			assertFalse(wn.hypernymMissing(c, s1));
			assertTrue(wn.hypernymMissing(c, s2));
			
			assertFalse(wn.hypernymMissing(c, notWord));
			assertFalse(wn.hypernymMissing(c, notWord));
		}catch(Exception e) {
			fail("An exception was caught");

		}
	}
	
	// INTEGRATION TESTS

	@Test
	public void isHypernymIntegrationTest () {
		try {
			Wordnet wn = new Wordnet();
			
			String c = "dog";
			String s1 = "canine";
			String s2 = "sausage";
			String s3 = "cat";
			String s4 = "mammal";
			
			assertTrue(wn.isHypernym(c, s2));
			assertFalse(wn.isHypernym(c, s3));
			assertFalse(wn.isHypernym(c, s4));
			assertFalse(wn.isHypernym(c, null));
			assertFalse(wn.isHypernym(null, s1));
			assertFalse(wn.isHypernym(null, null));
		}
		catch(Exception e) {
			fail("An exception was caught");
		}
	}
	
	@Test
	public void createDictPathIntegrationTest() {
		try {
			Wordnet wn = spy(new Wordnet());
			
			// can't really mock system environments,
			// but you should have WNHOME variable set by now
			String wnhome = System.getenv("WNHOME");
			String path;
			
			if(wnhome.contains("\\")) {
				path = wnhome + "\\dict";
			}
			else {
				path = wnhome + "/dict";
			}
				
			// mock the PathExists function
			assertTrue(wn.createDictPath(wnhome).equals(path));
			assertNull(wn.createDictPath(null));
		}
		catch(Exception e) {
			fail("An exception was caught");
		}
	}

	// System testing
	// This is the real function we use this wordnet library for,
	// So if the function works on real input, we can use it for its purpose
	@Test
	public void ishypernymMissingIntegrationTest() {
		try {
			Wordnet wn = spy(new Wordnet());
			
			// use real inputs to check that the program is working with nouns
			String c = "dog";
			String s1 = "canine";
			String s2 = "cat";
			String notWord = "asdf";
			String notNoun = "flew";

			assertFalse(wn.hypernymMissing(c, s1));
			assertTrue(wn.hypernymMissing(c, s2));
			
			assertFalse(wn.hypernymMissing(c, notWord));
			assertFalse(wn.hypernymMissing(c, notWord));
		}catch(Exception e) {
			fail("An exception was caught");

		}
	}
}
