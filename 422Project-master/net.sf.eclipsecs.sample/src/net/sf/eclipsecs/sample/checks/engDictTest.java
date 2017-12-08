package net.sf.eclipsecs.sample.checks;

import static org.junit.Assert.*;

import org.junit.Test;

public class engDictTest {

	@Test
	public void DoContainTest() { //Test to see if after Insert word exists
	
		engDict.initDict();
		engDict.InDict.add("dog");
		assertTrue(engDict.DoContain("dog"));
	}
	@Test
	public void DefaultTest() //Test if the Dictionary is Initialized
	{
		assertTrue(engDict.IsInit);
	}

	@Test
	public void MyReaderTest() //Is the Dictionary empty after insert?
	{
		engDict.InDict.add("Hello");
		assertFalse(engDict.InDict.isEmpty());
		
	}
}
