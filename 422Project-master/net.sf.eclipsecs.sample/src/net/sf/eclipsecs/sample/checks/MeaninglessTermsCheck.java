/*
 * This checks for variable, interface, class, parameter, or method names with meaningless terms defined by user (default "foo, bar, var, par" etc...)
 * Author: Michael Duncan
 */

package net.sf.eclipsecs.sample.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class MeaninglessTermsCheck extends AbstractCheck {

    private String terms = "foo,bar,var,par,param";

    // Description: Sets the tokens I want to check
    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.VARIABLE_DEF, TokenTypes.INTERFACE_DEF, TokenTypes.CLASS_DEF, TokenTypes.PARAMETER_DEF, TokenTypes.METHOD_DEF };
    }

    // Sets the list of terms considered meaningless. Terms should be comma separated with no spaces
    public void setTerms(String termList) {
        terms = termList;
    }

    // Description: Tests if the String name is contained int the meaningless terms list
    public boolean isMeaninglessTerm(String name)
    {
    	String[] separatedTerms = terms.split(",");
        for(int i = 0; i < separatedTerms.length; i++)
        {
        	if (name.toLowerCase().equals(separatedTerms[i].toLowerCase())) {
                return true; 
            }
        }
        return false;
    }
    
    // Description: Called each time we hit a node in the tree with token we are looking for
    @Override
    public void visitToken(DetailAST ast) {
        // find the identifier (name)
        DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);

        String name = ident.getText();
        if(isMeaninglessTerm(name))
        	log(ast.getLineNo(), "meaninglessterm");
    }
}
