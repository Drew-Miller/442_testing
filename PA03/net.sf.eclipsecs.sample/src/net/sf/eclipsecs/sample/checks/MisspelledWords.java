package net.sf.eclipsecs.sample.checks;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class MisspelledWords extends AbstractCheck {


	
    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.METHOD_DEF};
    }
    
    private String[] getSubStrings(String arg) //Make substring
    {
    	
    	String[] r = arg.split("(?=\\p{Upper})");
    	for(int i = 0; i < r.length; i++)
    	{
    		r[i] = r[i].toLowerCase();
    	}
    	return r;
    }
    
    public static boolean isNotSpelledRight(String ast) //Is the word given in our dictionary?
    {    	
        	if(engDict.DoContain((ast.replaceAll("[_]*", ""))))
        	{
        		return false;
        	}
        	return true;
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
        	if(isNotSpelledRight(mySplit[i])) //Check Spelling
        	{
        		log(ast.getLineNo(), "misspelledwords", mySplit[i]); //Incorrect Spelling, thus misspelled word.
        	}
        }
    }
}
