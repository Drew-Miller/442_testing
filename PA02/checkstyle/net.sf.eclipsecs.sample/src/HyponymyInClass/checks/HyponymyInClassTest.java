package HyponymyInClass.checks;

import static org.junit.Assert.*;

import org.junit.Test;


import static org.junit.Assert.*;
import org.mockito.Matchers;
import static org.mockito.Mockito.*;

import org.easymock.EasyMock;
import org.easymock.Mock;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.easymock.EasyMock.verify;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.expectNew;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import HyponymyInClass.checks.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DetailAST.class)
public class HyponymyInClassTest {

	@Test
	public void checkIfHyponymTest() {
		assertTrue(true);
	}
	
	/*
	 * Use powermockito to mock the partial classes
	 */
	@Test
	public void hasClassTokenTest() {
		HyponymyInClassCheck hypo = new HyponymyInClassCheck();
		DetailAST ast = PowerMock.createPartialMock(DetailAST.class, "branchContains");
		
		// expectations here
		expect(ast.branchContains(TokenTypes.CLASS_DEF)).andReturn(true);
		expect(ast.branchContains(TokenTypes.CLASS_DEF)).andReturn(false);
		
		PowerMock.replayAll();
		
		// assertions here
		assertTrue(hypo.hasClassToken(ast));
		assertFalse(hypo.hasClassToken(ast));
		
		PowerMock.verifyAll();
	}
	
	@Test
	public void isClassTokenTest() {
		HyponymyInClassCheck hypo = new HyponymyInClassCheck();
		DetailAST ast = PowerMock.createPartialMock(DetailAST.class, "getType");
		
		// expectations here
		expect(ast.getType()).andReturn(TokenTypes.CLASS_DEF);
		expect(ast.getType()).andReturn(TokenTypes.IDENT);
		
		PowerMock.replayAll();
		
		// assertions here
		assertTrue(hypo.isClassToken(ast));
		assertFalse(hypo.isClassToken(ast));
		
		PowerMock.verifyAll();
	}
	
	@Test
	public void checkIfHypernymTest() {
		HyponymyInClassCheck hypo = mock(HyponymyInClassCheck.class);
		DetailAST ast = PowerMock.createPartialMock(DetailAST.class, "getText");
		
		// expectations here
		expect(ast.getText()).andReturn("dog");
		// expect(ast.getText()).andReturn("canine");
		
		PowerMock.replayAll();
		
		hypo.checkIfHypernym(ast, ast);
				
		PowerMock.verifyAll();
	}
}
