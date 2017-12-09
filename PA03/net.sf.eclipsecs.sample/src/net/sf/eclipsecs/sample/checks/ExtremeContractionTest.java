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


@RunWith(PowerMockRunner.class)
@PrepareForTest({DetailAST.class, ExtremeContraction.class})
public class ExtremeContractionTest {

	@Test
	public void testVisitTokenDetailAST() throws Exception {
		//Check contraction to see if Extreme or just bad wording and then log results.
		DetailAST mockRootAST = PowerMock.createMock(DetailAST.class);
		DetailAST mockChildAST = PowerMock.createMock(DetailAST.class);
		ExtremeContraction spyExtremeContraction = PowerMock.createPartialMock(ExtremeContraction.class, "log");
		
		// Mock the nodes so the root returns a child with text ""
		EasyMock.expect(mockChildAST.getText()).andReturn("wrdActor");
		EasyMock.expect(mockRootAST.findFirstToken(TokenTypes.IDENT)).andReturn(mockChildAST);
		EasyMock.expect(mockRootAST.getLineNo()).andReturn(0);
		
		// Mock the and expect the log so it doesn't throw exceptions
		PowerMock.expectPrivate(spyExtremeContraction, "log", EasyMock.anyInt(), EasyMock.anyString()).andVoid();
		PowerMock.replayAll();
		
	}

	@Test
	public void testVisitTokenDetailAST2() throws Exception {
		//Test to see what happens to good contraction and log.
		DetailAST mockRootAST = PowerMock.createMock(DetailAST.class);
		DetailAST mockChildAST = PowerMock.createMock(DetailAST.class);
		ExtremeContraction spyExtremeContraction = PowerMock.createPartialMock(ExtremeContraction.class, "log");
		
		// Mock the nodes so the root returns a child with text ""
		EasyMock.expect(mockChildAST.getText()).andReturn("msgActor");
		EasyMock.expect(mockRootAST.findFirstToken(TokenTypes.IDENT)).andReturn(mockChildAST);
		EasyMock.expect(mockRootAST.getLineNo()).andReturn(0);
		
		// Mock the and expect the log so it doesn't throw exceptions
		PowerMock.expectPrivate(spyExtremeContraction, "log", EasyMock.anyInt(), EasyMock.anyString()).andVoid();
		PowerMock.replayAll();
		
	}
	
	@Test
	public void testIsShortWord() throws Exception
	{		//Quick test to see if word is short
		engDict.initDict();
		assertTrue(ExtremeContraction.isShortWord("go"));
	}
}
