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
@PrepareForTest({DetailAST.class, MeaninglessTermsCheck.class})
public class MeaninglessTermsCheckTest {
	
	/*
	 * Unit test for the isMeaninglessTerm function. All assertions should pass.
	 */
	@Test
	public void testIsMeaninglessTerm()
	{
		MeaninglessTermsCheck m = new MeaninglessTermsCheck();
		m.setTerms("foo,bar");
		assertTrue(m.isMeaninglessTerm("foo"));
		assertTrue(m.isMeaninglessTerm("bar"));
		assertFalse(m.isMeaninglessTerm("michael"));
		assertFalse(m.isMeaninglessTerm("duncan"));
	}
	
	/*
	 * Unit test for visitTokenDetailAST function. Checks that the log function is called, which indicates made it through checks and flagging line
	 */
	@Test
	public void testVisitTokenDetailAST() throws Exception {
		DetailAST mockRootAST = PowerMock.createMock(DetailAST.class);
		DetailAST mockChildAST = PowerMock.createMock(DetailAST.class);
		MeaninglessTermsCheck spyMeaninglessTerms = PowerMock.createPartialMock(MeaninglessTermsCheck.class, "log");
		
		// Mock the nodes so the root returns a child with text "foo"
		EasyMock.expect(mockChildAST.getText()).andReturn("foo");
		EasyMock.expect(mockRootAST.findFirstToken(TokenTypes.IDENT)).andReturn(mockChildAST);
		EasyMock.expect(mockRootAST.getLineNo()).andReturn(0);
		
		// Mock the and expect the log so it doesn't throw exceptions
		PowerMock.expectPrivate(spyMeaninglessTerms, "log", EasyMock.anyInt(), EasyMock.anyString()).andVoid();
		PowerMock.replayAll();
		
		// Set terms and run
		spyMeaninglessTerms.setTerms("foo,bar");
		spyMeaninglessTerms.visitToken(mockRootAST);
	}

	/*
	 * Integration testing. Limited because not many public methods.
	 */
	@Test
	public void topDownIntegrationTesting() throws Exception {
		testVisitTokenDetailAST();
		testIsMeaninglessTerm();
	}
	
	/*
	 * System testing. Makes sure that the check runs in under a second so that it won't take forever for large projects
	 */
	@Test
	public void systemTesting()
	{
		MeaninglessTermsCheck m = new MeaninglessTermsCheck();
		m.setTerms("foo,bar,var,par,i,j");
		long start = System.currentTimeMillis();
		m.isMeaninglessTerm("j");
		long end = System.currentTimeMillis();
		assertTrue(end-start < 1000); //runs in less than a second
	}
}
