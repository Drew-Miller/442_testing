package net.sf.eclipsecs.sample.checks;

import static org.junit.Assert.*;

import org.junit.Test;
import org.easymock.EasyMock;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.mockito.Mockito.*;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DetailAST.class, MisspelledWords.class})

public class MisspelledWordsTest {

	@Test
	public void testVisitTokenDetailAST() throws Exception {
		// Test to see if Check will catch a misspelled word.
		//Then Log results.
		DetailAST mockRootAST = PowerMock.createMock(DetailAST.class);
		DetailAST mockChildAST = PowerMock.createMock(DetailAST.class);
		MisspelledWords spyMisspelledWords = PowerMock.createPartialMock(MisspelledWords.class, "log");
		
		// Mock the nodes so the root returns a child with text "FreindMyself"
		EasyMock.expect(mockChildAST.getText()).andReturn("FreindMyself");
		EasyMock.expect(mockRootAST.findFirstToken(TokenTypes.IDENT)).andReturn(mockChildAST);
		EasyMock.expect(mockRootAST.getLineNo()).andReturn(0);
		
		// Mock the and expect the log so it doesn't throw exceptions
		PowerMock.expectPrivate(spyMisspelledWords, "log", EasyMock.anyInt(), EasyMock.anyString()).andVoid();
		PowerMock.replayAll();
		
	}
	
	@Test
	public void testVisitTokenDetailAST2() throws Exception {
		// Test to see if the spelling is correct and what happens next
		DetailAST mockRootAST = PowerMock.createMock(DetailAST.class);
		DetailAST mockChildAST = PowerMock.createMock(DetailAST.class);
		MisspelledWords spyMisspelledWords = PowerMock.createPartialMock(MisspelledWords.class, "log");
		
		// Mock the nodes so the root returns a child with text "DoingYourself"
		EasyMock.expect(mockChildAST.getText()).andReturn("DoingYourself");
		EasyMock.expect(mockRootAST.findFirstToken(TokenTypes.IDENT)).andReturn(mockChildAST);
		EasyMock.expect(mockRootAST.getLineNo()).andReturn(0);
		
		// Mock the and expect the log so it doesn't throw exceptions
		PowerMock.expectPrivate(spyMisspelledWords, "log", EasyMock.anyInt(), EasyMock.anyString()).andVoid();
		PowerMock.replayAll();
		
	}
	
	@Test
	public void testisNotSpelledRight() throws Exception
	{		//Quick Test to see if spelling is correct
		engDict.initDict();
		assertTrue(MisspelledWords.isNotSpelledRight("asdfgh"));
	}
	
}
