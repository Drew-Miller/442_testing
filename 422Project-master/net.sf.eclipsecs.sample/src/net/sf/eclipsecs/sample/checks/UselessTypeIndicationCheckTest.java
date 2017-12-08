package net.sf.eclipsecs.sample.checks;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import org.easymock.EasyMock;
import org.junit.runner.RunWith; 
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DetailAST.class)
public class UselessTypeIndicationCheckTest {
	UselessTypeIndicationCheck testCheck = new UselessTypeIndicationCheck();
	
	/*
	 * unit test for boolean isUselessType
	 * should return true if ident string contains type string
	 */
	@Test
	public void testIsUselessType() {
		String ident0 = "containsINT";
		String ident1 = "containsInt";
		String ident2 = "containsint";
		String ident3 = "containsNOTHING";
		assertTrue(testCheck.isUselessType(ident0, "int"));
		assertTrue(testCheck.isUselessType(ident1, "int"));
		assertTrue(testCheck.isUselessType(ident2, "int"));
		assertFalse(testCheck.isUselessType(ident3, "int"));
	}
	
	/*
	 * unit test for getIdent
	 * should return getText() value from first token of type IDENT
	 */
	@Test 
	public void testGetIdent() {
		final DetailAST testAST = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST identAST = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(testAST.findFirstToken(TokenTypes.IDENT)).andReturn(identAST);
		EasyMock.expect(identAST.getText()).andReturn("test");
		PowerMock.replayAll();
		assertEquals(testCheck.getIdent(testAST), "test");
	}
	
	/*
	 * unit test for getDataType
	 * returns detailAST, first token from ast of type TYPE
	 */
	@Test
	public void testGetDataType() {
		final DetailAST testAST = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST typeAST = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(testAST.findFirstToken(TokenTypes.TYPE)).andReturn(typeAST);
		PowerMock.replayAll();
		assertEquals(testCheck.getDataType(testAST), typeAST);
	}
	
	/*
	 * unit test for getDataTypeStrings
	 * should return string value corresponding to the data type of DetailAST def
	 */
	@Test
	public void testGetDataTypeStrings() {
		final DetailAST mockAST0 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(mockAST0.findFirstToken(TokenTypes.LITERAL_BOOLEAN)).andReturn(new DetailAST());
		PowerMock.replayAll();
		assertTrue(testCheck.getDataTypeStrings(mockAST0).contains("bool"));

		final DetailAST mockAST1 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(mockAST1.findFirstToken(TokenTypes.LITERAL_BYTE)).andReturn(new DetailAST());
		PowerMock.replayAll();
		assertTrue(testCheck.getDataTypeStrings(mockAST1).contains("byte"));
				
		final DetailAST mockAST2 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(mockAST2.findFirstToken(TokenTypes.LITERAL_CHAR)).andReturn(new DetailAST());
		PowerMock.replayAll();
		assertTrue(testCheck.getDataTypeStrings(mockAST2).contains("char"));
		
		final DetailAST mockAST3 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(mockAST3.findFirstToken(TokenTypes.LITERAL_DOUBLE)).andReturn(new DetailAST());
		PowerMock.replayAll();
		assertTrue(testCheck.getDataTypeStrings(mockAST3).contains("double"));
		
		final DetailAST mockAST4 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(mockAST4.findFirstToken(TokenTypes.LITERAL_FLOAT)).andReturn(new DetailAST());
		PowerMock.replayAll();
		assertTrue(testCheck.getDataTypeStrings(mockAST4).contains("float"));
		
		final DetailAST mockAST5 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(mockAST5.findFirstToken(TokenTypes.LITERAL_INT)).andReturn(new DetailAST());
		PowerMock.replayAll();
		assertTrue(testCheck.getDataTypeStrings(mockAST5).contains("int"));
		
		final DetailAST mockAST6 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(mockAST6.findFirstToken(TokenTypes.LITERAL_LONG)).andReturn(new DetailAST());
		PowerMock.replayAll();
		assertTrue(testCheck.getDataTypeStrings(mockAST6).contains("long"));
		
		final DetailAST mockAST7 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(mockAST7.findFirstToken(TokenTypes.LITERAL_SHORT)).andReturn(new DetailAST());
		PowerMock.replayAll();
		assertTrue(testCheck.getDataTypeStrings(mockAST7).contains("short"));
		
		final DetailAST mockAST8 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(mockAST8.findFirstToken(TokenTypes.IDENT)).andReturn(new DetailAST());
		PowerMock.replayAll();
		assertTrue(testCheck.getDataTypeStrings(mockAST8).contains("str"));
	}
	
	/*
	 * integration test for getDataType and getDataTypeStrings
	 * tests for proper integration - correct strings returned for DetailAST of a specific data type
	 */
	@Test 
	public void ITgetDataTypeGetDataTypeStrings() {
		final DetailAST mockAST0 = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST typeAST0 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(mockAST0.findFirstToken(TokenTypes.TYPE)).andReturn(typeAST0);
		EasyMock.expect(typeAST0.findFirstToken(TokenTypes.LITERAL_BOOLEAN)).andReturn(new DetailAST());
		PowerMock.replayAll();
		assertEquals(testCheck.getDataTypeStrings(testCheck.getDataType(mockAST0)), "bool");
		
		final DetailAST mockAST1 = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST typeAST1 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(mockAST1.findFirstToken(TokenTypes.TYPE)).andReturn(typeAST1);
		EasyMock.expect(typeAST1.findFirstToken(TokenTypes.LITERAL_BYTE)).andReturn(new DetailAST());
		PowerMock.replayAll();
		assertEquals(testCheck.getDataTypeStrings(testCheck.getDataType(mockAST1)), "byte");
				
		final DetailAST mockAST2 = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST typeAST2 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(mockAST2.findFirstToken(TokenTypes.TYPE)).andReturn(typeAST2);
		EasyMock.expect(typeAST2.findFirstToken(TokenTypes.LITERAL_CHAR)).andReturn(new DetailAST());
		PowerMock.replayAll();
		assertEquals(testCheck.getDataTypeStrings(testCheck.getDataType(mockAST2)), "char");
		
		final DetailAST mockAST3 = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST typeAST3 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(mockAST3.findFirstToken(TokenTypes.TYPE)).andReturn(typeAST3);
		EasyMock.expect(typeAST3.findFirstToken(TokenTypes.LITERAL_DOUBLE)).andReturn(new DetailAST());
		PowerMock.replayAll();
		assertEquals(testCheck.getDataTypeStrings(testCheck.getDataType(mockAST3)), "double");
		
		final DetailAST mockAST4 = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST typeAST4 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(mockAST4.findFirstToken(TokenTypes.TYPE)).andReturn(typeAST4);
		EasyMock.expect(typeAST4.findFirstToken(TokenTypes.LITERAL_FLOAT)).andReturn(new DetailAST());
		PowerMock.replayAll();
		assertEquals(testCheck.getDataTypeStrings(testCheck.getDataType(mockAST4)), "float");
		
		final DetailAST mockAST5 = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST typeAST5 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(mockAST5.findFirstToken(TokenTypes.TYPE)).andReturn(typeAST5);
		EasyMock.expect(typeAST5.findFirstToken(TokenTypes.LITERAL_INT)).andReturn(new DetailAST());
		PowerMock.replayAll();
		assertEquals(testCheck.getDataTypeStrings(testCheck.getDataType(mockAST5)),"int");
		
		final DetailAST mockAST6 = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST typeAST6 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(mockAST6.findFirstToken(TokenTypes.TYPE)).andReturn(typeAST6);
		EasyMock.expect(typeAST6.findFirstToken(TokenTypes.LITERAL_LONG)).andReturn(new DetailAST());
		PowerMock.replayAll();
		assertEquals(testCheck.getDataTypeStrings(testCheck.getDataType(mockAST6)), "long");
		
		final DetailAST mockAST7 = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST typeAST7 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(mockAST7.findFirstToken(TokenTypes.TYPE)).andReturn(typeAST7);
		EasyMock.expect(typeAST7.findFirstToken(TokenTypes.LITERAL_SHORT)).andReturn(new DetailAST());
		PowerMock.replayAll();
		assertEquals(testCheck.getDataTypeStrings(testCheck.getDataType(mockAST7)), "short");
		
		final DetailAST mockAST8 = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST typeAST8 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(mockAST8.findFirstToken(TokenTypes.TYPE)).andReturn(typeAST8);
		EasyMock.expect(typeAST8.findFirstToken(TokenTypes.IDENT)).andReturn(new DetailAST());
		PowerMock.replayAll();
		assertEquals(testCheck.getDataTypeStrings(testCheck.getDataType(mockAST8)), "str");
	}
	
	/*
	 * integration test for all methods
	 * adds isUselessType integration to previous integration test
	 */
	@Test
	public void ITisUselessTypeIndication() {
		final DetailAST mockAST0 = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST typeAST0 = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST identAST0 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(mockAST0.findFirstToken(TokenTypes.TYPE)).andReturn(typeAST0);
		EasyMock.expect(typeAST0.findFirstToken(TokenTypes.LITERAL_BOOLEAN)).andReturn(new DetailAST());
		EasyMock.expect(mockAST0.findFirstToken(TokenTypes.IDENT)).andReturn(identAST0);
		EasyMock.expect(identAST0.getText()).andReturn("bool");
		PowerMock.replayAll();
		assertTrue(testCheck.isUselessType(testCheck.getIdent(mockAST0), testCheck.getDataTypeStrings(testCheck.getDataType(mockAST0))));
		
		final DetailAST mockAST1 = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST typeAST1 = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST identAST1 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(mockAST1.findFirstToken(TokenTypes.TYPE)).andReturn(typeAST1);
		EasyMock.expect(typeAST1.findFirstToken(TokenTypes.LITERAL_BYTE)).andReturn(new DetailAST());
		EasyMock.expect(mockAST1.findFirstToken(TokenTypes.IDENT)).andReturn(identAST1);
		EasyMock.expect(identAST1.getText()).andReturn("byte");
		PowerMock.replayAll();
		assertTrue(testCheck.isUselessType(testCheck.getIdent(mockAST1), testCheck.getDataTypeStrings(testCheck.getDataType(mockAST1))));
		
		final DetailAST mockAST2 = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST typeAST2 = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST identAST2 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(mockAST2.findFirstToken(TokenTypes.TYPE)).andReturn(typeAST2);
		EasyMock.expect(typeAST2.findFirstToken(TokenTypes.LITERAL_CHAR)).andReturn(new DetailAST());
		EasyMock.expect(mockAST2.findFirstToken(TokenTypes.IDENT)).andReturn(identAST2);
		EasyMock.expect(identAST2.getText()).andReturn("char");
		PowerMock.replayAll();
		assertTrue(testCheck.isUselessType(testCheck.getIdent(mockAST2), testCheck.getDataTypeStrings(testCheck.getDataType(mockAST2))));
	}

}
