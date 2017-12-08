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
@PrepareForTest(DetailAST.class)
public class WholePartCheckTest {
	WholePartCheck testCheck = new WholePartCheck();

	/*
	 * Unit test for isWholePart
	 * true if string parentClassIdent is contained in string ident
	 */
	@Test
	public void testIsWholePart() {
		assertTrue(testCheck.isWholePart("Test", "doTest"));
		assertTrue(testCheck.isWholePart("word", "hasWord"));
		assertFalse(testCheck.isWholePart("test", "foo"));
		assertFalse(testCheck.isWholePart("Sarah", "test"));
	}

	/*
	 * unit test for getIdent
	 * should return ident text for input DetailAST
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
	 * unit test for getParentClassIdent
	 * should return text for parent class's identifier based on input DetailAST
	 */
	@Test
	public void testGetParentClassIdent() {
		final DetailAST parentTestAST = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST parentAST = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST parentIdentAST = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(parentTestAST.getParent()).andReturn(parentAST);
		EasyMock.expect(parentAST.getType()).andReturn(TokenTypes.CLASS_DEF);
		EasyMock.expect(parentAST.findFirstToken(TokenTypes.IDENT)).andReturn(parentIdentAST);
		EasyMock.expect(parentIdentAST.getText()).andReturn("parentText");
		PowerMock.replayAll();
		assertEquals(testCheck.getParentClassIdent(parentTestAST), "parentText");
	}
	
	/*
	 * integration test for isWholePart
	 * include getParentClassIdent method and getIdent method as input strings
	 * should return true if parentClassIdent is contained in input DetailAST ident
	 */
	@Test
	public void ITisWholePart() {
		final DetailAST parentTestAST = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST parentAST = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST parentIdentAST = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(parentTestAST.getParent()).andReturn(parentAST);
		EasyMock.expect(parentAST.getType()).andReturn(TokenTypes.CLASS_DEF);
		EasyMock.expect(parentAST.findFirstToken(TokenTypes.IDENT)).andReturn(parentIdentAST);
		EasyMock.expect(parentIdentAST.getText()).andReturn("parentText");
		
		final DetailAST testAST = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST identAST = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(testAST.findFirstToken(TokenTypes.IDENT)).andReturn(identAST);
		EasyMock.expect(identAST.getText()).andReturn("test");
		PowerMock.replayAll();
		
		assertFalse(testCheck.isWholePart(testCheck.getParentClassIdent(parentTestAST), testCheck.getIdent(testAST)));
		
		final DetailAST parentTestAST1 = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST parentAST1 = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST parentIdentAST1 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(parentTestAST1.getParent()).andReturn(parentAST1);
		EasyMock.expect(parentAST1.getType()).andReturn(TokenTypes.CLASS_DEF);
		EasyMock.expect(parentAST1.findFirstToken(TokenTypes.IDENT)).andReturn(parentIdentAST1);
		EasyMock.expect(parentIdentAST1.getText()).andReturn("test");
		
		final DetailAST testAST1 = PowerMock.createNiceMock(DetailAST.class);
		final DetailAST identAST1 = PowerMock.createNiceMock(DetailAST.class);
		EasyMock.expect(testAST1.findFirstToken(TokenTypes.IDENT)).andReturn(identAST1);
		EasyMock.expect(identAST1.getText()).andReturn("do_test");
		PowerMock.replayAll();
		
		assertTrue(testCheck.isWholePart(testCheck.getParentClassIdent(parentTestAST1), testCheck.getIdent(testAST1)));
	}

}
