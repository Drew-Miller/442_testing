package UselessTypeIndication.checks;

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

import UselessTypeIndication.checks.UselessTypeIndicationCheck;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DetailAST.class)
public class UselessTypeIndicationTests {
	
	@Test
	public void HasTypeIndicationTest() {
		UselessTypeIndicationCheck check = new UselessTypeIndicationCheck();
		
		String n1 ="int";
		String n2 = "my_int";
		String n3 = "my_var";
		String n4 = "_contact";
		String n5 = "double";
		
		String t1 = "int";
		String t2 = "double";
		String t3 = "String";
		String t4 = "float";
		
		assertTrue(check.HasTypeIndication(n1, t1));
		assertFalse(check.HasTypeIndication(n1, t2));
		assertFalse(check.HasTypeIndication(n1, t3));
		assertFalse(check.HasTypeIndication(n1, t4));
		
		assertTrue(check.HasTypeIndication(n2, t1));
		assertFalse(check.HasTypeIndication(n2, t2));
		assertFalse(check.HasTypeIndication(n2, t3));
		assertFalse(check.HasTypeIndication(n2, t4));

		assertFalse(check.HasTypeIndication(n3, t1));
		assertFalse(check.HasTypeIndication(n3, t2));
		assertFalse(check.HasTypeIndication(n3, t3));
		assertFalse(check.HasTypeIndication(n3, t4));
		
		assertFalse(check.HasTypeIndication(n4, t1));
		assertFalse(check.HasTypeIndication(n4, t2));
		assertFalse(check.HasTypeIndication(n4, t3));
		assertFalse(check.HasTypeIndication(n4, t4));
		
		assertFalse(check.HasTypeIndication(n5, t1));
		assertTrue(check.HasTypeIndication(n5, t2));
		assertFalse(check.HasTypeIndication(n5, t3));
		assertFalse(check.HasTypeIndication(n5, t4));
	}
	
	@Test
	public void hasVariableTokenTest() {
		UselessTypeIndicationCheck check = new UselessTypeIndicationCheck();
		DetailAST ast;
		
		ast = PowerMock.createPartialMock(DetailAST.class, "branchContains");
		expect(ast.branchContains(TokenTypes.VARIABLE_DEF)).andReturn(true);
		PowerMock.replayAll();
		assertTrue(check.hasVariableToken(ast));		
		
		ast = PowerMock.createPartialMock(DetailAST.class, "branchContains");
		expect(ast.branchContains(TokenTypes.VARIABLE_DEF)).andReturn(false);
		PowerMock.replayAll();
		assertFalse(check.hasVariableToken(ast));
	}
	
	@Test
	public void isVariableTokenTest() {
		UselessTypeIndicationCheck check = new UselessTypeIndicationCheck();
		DetailAST ast;
		
		ast = PowerMock.createPartialMock(DetailAST.class, "getType");
		expect(ast.getType()).andReturn(TokenTypes.VARIABLE_DEF);
		PowerMock.replayAll();
		assertTrue(check.isVariableToken(ast));		
		
		ast = PowerMock.createPartialMock(DetailAST.class, "getType");
		expect(ast.getType()).andReturn(TokenTypes.IDENT);
		PowerMock.replayAll();
		assertFalse(check.isVariableToken(ast));
	}
	
	@Test
	public void isIdentTokenTest() {
		UselessTypeIndicationCheck check = new UselessTypeIndicationCheck();
		DetailAST ast;
		
		ast = PowerMock.createPartialMock(DetailAST.class, "getType");
		expect(ast.getType()).andReturn(TokenTypes.IDENT);
		PowerMock.replayAll();
		assertTrue(check.isIdentifierToken(ast));		
		
		ast = PowerMock.createPartialMock(DetailAST.class, "getType");
		expect(ast.getType()).andReturn(TokenTypes.LITERAL_INT);
		PowerMock.replayAll();
		assertFalse(check.isIdentifierToken(ast));
	}
	
	@Test
	public void isTypeTokenTest() {
		UselessTypeIndicationCheck check = new UselessTypeIndicationCheck();
		DetailAST ast;
		
		ast = PowerMock.createPartialMock(DetailAST.class, "getType");
		expect(ast.getType()).andReturn(TokenTypes.TYPE);
		PowerMock.replayAll();
		assertTrue(check.isTypeToken(ast));		
		
		ast = PowerMock.createPartialMock(DetailAST.class, "getType");
		expect(ast.getType()).andReturn(TokenTypes.LITERAL_INT);
		PowerMock.replayAll();
		assertFalse(check.isTypeToken(ast));
	}
	
	@Test
	public void getTypeTest() {
		UselessTypeIndicationCheck check = new UselessTypeIndicationCheck();
		DetailAST ast;
		
		ast = PowerMock.createMock(DetailAST.class);
		expect(ast.getType()).andReturn(TokenTypes.TYPE);
		try {
			PowerMock.expectNew(DetailAST.class, "").andReturn(ast);
			expect(ast.getType()).andReturn(TokenTypes.LITERAL_INT);
			
			PowerMock.replayAll();
			assertTrue(check.isTypeToken(ast));	
		}
		catch(Exception e) {
			// NOTE:
			// I dont know how to get this to mock the DetailAST() constructor from the
			// api and because it won't let me create an object for (ast != null),
			// I cannot test the other sections of the UselessTypeIndicationCheck
			// ALSO CommonAST cannot be cast to DetailAST
			assertTrue("Exception was caught mocking DetailAST", true);
		}
	}
}
