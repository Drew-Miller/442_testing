package WholeParts;

import java.io.Console;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import Utilities.WordNet;
import edu.mit.jwi.morph.WordnetStemmer;

public class WholePartsCheck extends AbstractCheck {

	WordNet wn = null;
	String identText1 = null;
	
	public WholePartsCheck () {
		wn = new WordNet();
	}
	
	@Override
	public int[] getDefaultTokens() {
		return new int[] { TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF, TokenTypes.METHOD_DEF };
	}

	@Override
    public void visitToken(DetailAST ast) {
		DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
		if(ident != null) {
			identText1 = ident.getText();
		} else {
			return;
		}
        // get the object block of the class to check for all the IDENT types inside
        DetailAST child = ast.getFirstChild();
        checkChildren(child);
	}
	
	private void processAST(DetailAST ast) {
		if (ast != null) {
			if(ast.findFirstToken(TokenTypes.IDENT) != null) {
				if(wn.isSameWord(identText1, ast.findFirstToken(TokenTypes.IDENT).getText())) {
			        String message = "Whole Parts: Same term \"" + identText1 + "\" was used to to represent a concept and its properties or parts.";
			        log(ast.getLineNo(), message);
				} 
			} else { //TODO: fix.
				checkChildren(ast.getFirstChild());
			}
		}
	}
	
	private void checkChildren(DetailAST child) {
		//DetailAST child = ast.getFirstChild();
		while(child != null) {
			processAST(child);
			child = child.getNextSibling();
		}
	}
	

}
