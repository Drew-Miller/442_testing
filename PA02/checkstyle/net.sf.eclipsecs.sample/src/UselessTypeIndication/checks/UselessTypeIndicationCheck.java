package UselessTypeIndication.checks;

import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class UselessTypeIndicationCheck extends AbstractCheck {

	private final String message ="Type information in attribute name";
	
    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF };
    }

    @Override
    public void visitToken(DetailAST ast) {  
    		// must loop through here and find all of the tokens in the tree    	
    		processAST(ast);
    }
    
    // recursive function to locate and check all variable definitions
    private void processAST(DetailAST ast) {
    		if(ast != null) {
    			if(isVariableToken(ast)) {
    				// get the TYPE folder (if any) from the variable def, and get the types from it
    				List<DetailAST> types = getAllChildren(getType(ast));
    				
    				// must find the ident, which is the name of the variable
    				DetailAST variableName = getVarName(ast);
    				
    				findUselessTypeIndication(variableName, types);
    			}
    			else if(hasVariableToken(ast)) {
    				checkChildren(ast);
    			}
    		}
    }
    
    public boolean hasVariableToken(DetailAST ast) {
		return ast.branchContains(TokenTypes.VARIABLE_DEF);
    }
    
    public boolean isVariableToken(DetailAST ast) {
    	return ast.getType() == TokenTypes.VARIABLE_DEF;
    }
    
    public boolean isIdentifierToken(DetailAST ast) {
  		return ast.getType() == TokenTypes.IDENT;
    }

    public boolean isTypeToken(DetailAST ast) {
    	return ast.getType() == TokenTypes.TYPE;
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
    
    // returns the type folder from a vardef
    public DetailAST getType(DetailAST ast){    	
    	if(ast != null) {
    		if(isVariableToken(ast)) {
    			DetailAST type = ast.findFirstToken(TokenTypes.TYPE);
    			
    			if(type != null) {
	    			if(isTypeToken(type))
	    				return type;
    			}
    		}
    	}	
    	return null;
    }
    
    public DetailAST getVarName(DetailAST ast){    	
    	if(ast != null) {
    		if(isVariableToken(ast)) {
    			// the first identifier in the children of a variable definition
    			// should be the name of the variable if the code is syntactically correct
    			DetailAST name = ast.findFirstToken(TokenTypes.IDENT);
    			
    			// if we have an identifier, which we should ALWAYS
    			// we can return the variable name
    			if(name != null) {
    				return name;
    			}
    		}
    	}
    	
    	return null;
    }
    
    private List<DetailAST> getAllChildren(DetailAST ast) {
    	List<DetailAST> children = new ArrayList<DetailAST>();

    	DetailAST child = ast.getFirstChild();
    		
		while(child != null) {	
			
			if(child.getChildCount() != 0) {
				children.addAll(getAllChildren(child));
			} else {
				children.add(child);
			}
			
			child = child.getNextSibling();
		}	
    
    	
    	return children;
    }
    
    // Determine if a name contains a type name from a set of types
    private void findUselessTypeIndication(DetailAST name, List<DetailAST> types) {
    	// loop through all of the types
    	for(DetailAST type : types) {
			// check if there is useless type information		
			if(HasTypeIndication(name.getText(), type.getText())) {
				// log a warning
				logUselessTypeWarning(name, type);
			}
    	}
    }
    
    // indicates if a name has a useless type in it
    public boolean HasTypeIndication(String name, String type) {
    		return name.toUpperCase().contains(type.toUpperCase());
    }
    
    // indicates useless type warning to the user through logging
    public void logUselessTypeWarning(DetailAST name, DetailAST type) {
    		log(name.getLineNo(), name.getColumnNo(), message, type.getText());
    	}
}
