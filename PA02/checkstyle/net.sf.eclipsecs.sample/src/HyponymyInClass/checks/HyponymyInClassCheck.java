package HyponymyInClass.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;

import Wordnet.Wordnet;

public class HyponymyInClassCheck extends AbstractCheck {

	private final String message ="Hyponmy/hypernymy missing in class hierarchy";
	
    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF };
    }

    @Override
    public void visitToken(DetailAST ast) {  
    		// must loop through here and find all of the tokens in the tree    	
    		processAST(ast);
    }
    
    // #HERE - --- - - - - - -  
    // recursive function to locate and check all class definitions
    private void processAST(DetailAST ast) {
    	if(ast != null) {
    		if(isClassToken(ast)) {
    			// find the class name and the super name from the tree
				DetailAST extension = getExtension(ast);
				DetailAST identifier = getIdentifier(ast);

				// check if hyponym
				if(identifier != null && extension != null) {
					checkIfHypernym(identifier, extension);
				}
   			}
    		else if(hasClassToken(ast)) {
    			checkChildren(ast);
    		}
    	}
    }
    
    private void checkChildren(DetailAST ast) {
		DetailAST child = ast.getFirstChild();
			
		// go to the next child to process
		while(child != null) {
			processAST(child);
					
			// go to the next child to process
			child = child.getNextSibling();
		}
    }
    
    public boolean hasClassToken(DetailAST ast) {
    	return ast.branchContains(TokenTypes.CLASS_DEF);
    }
    
    public boolean isClassToken(DetailAST ast) {
    	return ast.getType() == TokenTypes.CLASS_DEF;
    }
    
    //finds and returns an extension from a class_definition token
    public DetailAST getExtension(DetailAST ast) {
    		// make sure the tree isn't null and is class_def
    		// s.t. we can find the extension token
    		if(ast != null) {
    			if(ast.getType() == TokenTypes.CLASS_DEF) {
		    		if(ast.branchContains(TokenTypes.EXTENDS_CLAUSE)) {
		    			// gets the super name from the extend
		    			return ast.findFirstToken(TokenTypes.EXTENDS_CLAUSE).findFirstToken(TokenTypes.IDENT);		
		    		}
    			} 	
    		}
    		
    		// return null if we cannot find the super name
    		return null;
    }
    
    // gets the identifier from a class_def tree
    public DetailAST getIdentifier(DetailAST ast) {
		// make sure the tree isn't null and is class_def
		// s.t. we can find the identifier token
    		if(ast != null) {
    			if(ast.getType() == TokenTypes.CLASS_DEF) {
    				// get the class name from the class definition
    				return ast.findFirstToken(TokenTypes.IDENT);
    			}
    		}
    		
    		return null;
    }
    
    // determines if there is a hyponym or not
    public void checkIfHypernym(DetailAST identifier, DetailAST extension) {
    	String c = identifier.getText();
    	String s = extension.getText();
    		
		Wordnet wn = null;
    	try {
    		wn = new Wordnet();
    	} catch(Exception e) {
    		System.out.print(e.getStackTrace());
    	} 	
    		
    	if(wn != null) {
			if(wn.hypernymMissing(c, s)) {
				logHypernym(identifier);
			}
    	}
    }
    
    // indicates useless type warning to the user through logging
    public void logHypernym(DetailAST identifier) {
		log(identifier.getLineNo(), identifier.getColumnNo(), message, identifier.getText());
    }
}
