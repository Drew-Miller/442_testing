package net.sf.eclipsecs.sample.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class WholePartCheck extends AbstractCheck {
	
	@Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.METHOD_DEF, TokenTypes.VARIABLE_DEF }; // visit method and variable definitions
    }
	
	@Override
    public void visitToken(DetailAST ast) {
		String parentClassIdent = getParentClassIdent(ast);
		String ident = getIdent(ast);
		
		// log if whole part detected
		if(isWholePart(parentClassIdent, ident)) {
			log(ast.getLineNo(), "wholepart");
		}
    }
	
	// return true if parentClassIdent is contained in ident
	public boolean isWholePart(String parentClassIdent, String ident) {
		parentClassIdent = parentClassIdent.toLowerCase(); // ignore cases
		ident = ident.toLowerCase(); // ignore cases
		if(ident.contains(parentClassIdent)) {
			return true;
		}
		return false;
	}
	
	// return text for identifier
	public String getIdent(DetailAST ast) {
		return ast.findFirstToken(TokenTypes.IDENT).getText();
	}
	
	// return text for parent class identifier
	public String getParentClassIdent(DetailAST ast) {
		
		// traverse until parent is class definition
		// (we know all METHOD_DEF and VARIABLE_DEF will have some parent CLASS_DEF)
		DetailAST parent = ast.getParent();
		while(parent.getType() != TokenTypes.CLASS_DEF && parent != null) {
			parent = parent.getParent();
		}
		return parent.findFirstToken(TokenTypes.IDENT).getText();
	}
}