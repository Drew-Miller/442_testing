package net.sf.eclipsecs.sample.checks;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ExtremeContraction extends AbstractCheck {
	
    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.METHOD_DEF};
    }
    
    private String[] getSubStrings(String arg) //Make a substring out of a string
    {
    	
    	String[] r = arg.split("(?=\\p{Upper})");
    	for(int i = 0; i < r.length; i++)
    	{
    		r[i] = r[i].toLowerCase();
    	}
    	return r;
    }
    
    public static boolean isShortWord(String arg) //Checks if string given is below 3 characters.
    {
    	if(arg.length() <= 3)
    	{
    		return true;
    	}
    	return false;
    }
    
    @Override
    public void visitToken(DetailAST ast) {
    	engDict.initDict();
        // find the OBJBLOCK node below the CLASS_DEF/INTERFACE_DEF
        DetailAST objIdent = ast.findFirstToken(TokenTypes.IDENT);
        // count the number of direct children of the OBJBLOCK
        // that are METHOD_DEFS
        String methodDefs = objIdent.getText();
        // report error if limit is reached
        String mySplit[] = getSubStrings(methodDefs);
        for(int i = 0; i < mySplit.length; i++)
        {
        	if(isShortWord(mySplit[i])) //Is word a contraction?
        	{
            	if(!engDict.DoContain((mySplit[i].replaceAll("[_]*", "")))) //Does this word exist in our dictionary?
            	{
            		log(ast.getLineNo(), "extremecontraction", mySplit[i]); //It doesn't, also short. thus extreme contraction
            	}
        	}
        }
    }
}
