package net.sf.eclipsecs.sample.checks;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DetailAST.class, OverloadedIdentifiersCheck.class})
public class OverloadedIdentifiersCheckTest {

	/*
	 * Unit test for followsRules function. All assertions should pass
	 */
	@Test
	public void testFollowsRules()
	{
		OverloadedIdentifiersCheck o = new OverloadedIdentifiersCheck();
		//Method checks (max 1 verb)
		assertTrue(o.followsRules("isTrue", true));
		assertTrue(o.followsRules("hasItems", true));
		assertFalse(o.followsRules("isDumbAndIsTesting", true));
		assertFalse(o.followsRules("hasAndIsAndWas", true));
		//Variable checks
		assertTrue(o.followsRules("speed", false));
		assertTrue(o.followsRules("isHouse", false));
		assertFalse(o.followsRules("javaIsGarbage", false));
		assertFalse(o.followsRules("isBirdOrPlanOrSuperman", false));		
	}
	
	/*
	 * Unit test for visitTokenDetailAST function. Expects call to log function, showing that check worked and flaggin line.
	 */
	@Test
	public void testVisitTokenDetailAST() throws Exception {
		DetailAST mockRootAST = PowerMock.createMock(DetailAST.class);
		DetailAST mockChildAST = PowerMock.createMock(DetailAST.class);
		OverloadedIdentifiersCheck spyOverloadedIdent = PowerMock.createPartialMock(OverloadedIdentifiersCheck.class, "log");
		
		// Mock the nodes so the root is a method def that returns a child with text "eclipseIsGarbage"
		EasyMock.expect(mockRootAST.getType()).andReturn(TokenTypes.METHOD_DEF);
		EasyMock.expect(mockChildAST.getText()).andReturn("eclipseIsGarbage"); //has 2 nouns, violates rule.
		EasyMock.expect(mockRootAST.findFirstToken(TokenTypes.IDENT)).andReturn(mockChildAST);
		EasyMock.expect(mockRootAST.getLineNo()).andReturn(0);
		
		// Mock the and expect the log so it doesn't throw exceptions
		spyOverloadedIdent.init();
		PowerMock.expectPrivate(spyOverloadedIdent, "log", EasyMock.anyInt(), EasyMock.anyString()).andVoid();
		PowerMock.replayAll();
		
		// Set terms and run
		spyOverloadedIdent.visitToken(mockRootAST);
	}
	
	/*
	 * Integration testing. Limited because not many public methods.
	 */
	@Test
	public void topDownIntegrationTesting() throws Exception
	{
		testVisitTokenDetailAST();
		testFollowsRules();
	}

	/*
	 * System testing. Makes sure that the check runs in under a second so that it won't take forever for large projects
	 */
	@Test
	public void systemTesting()
	{
		OverloadedIdentifiersCheck o = new OverloadedIdentifiersCheck();
		o.init();
		long start = System.currentTimeMillis();
		o.followsRules("eclipseIsGarbage", false);
		long end = System.currentTimeMillis();
		assertTrue(end-start < 1000); //runs in less than a second
	}
}
