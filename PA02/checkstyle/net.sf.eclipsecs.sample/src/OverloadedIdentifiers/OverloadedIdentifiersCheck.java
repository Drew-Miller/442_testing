package OverloadedIdentifiers;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import Utilities.WordNet;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

public class OverloadedIdentifiersCheck extends AbstractCheck {
	
	WordNet wn = null;
	
	public OverloadedIdentifiersCheck() {
		wn = new WordNet();
	}
	
	@Override
	public int[] getDefaultTokens() {
		return new int[] { TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF, TokenTypes.METHOD_DEF, TokenTypes.PARAMETERS, TokenTypes.VARIABLE_DEF };
	}
	
	@Override
    public void visitToken(DetailAST ast) {
		
		
		
		DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
		String tokenText = null;
		if(ident != null) {
			tokenText = ident.getText();
		} else {
			return;
		}
		ArrayList<String> sList = TokenizeIdentifier(tokenText);
		
		//two verbs in a method / function
		if(ast.getType() == TokenTypes.METHOD_DEF) {
			//Check Verbs
			int verbs = numVerbs(sList);
			if (verbs > 1) {
				String message = "OverloadedIdentifier, too many verbs (" + verbs + ") in method / function: " + tokenText;
			    log(ast.getLineNo(), message);
			}
		}
		//two nouns in a class / attribute / variable / parameter, none of which used as a specifier
		else {
			int nouns = numNouns(sList);
			if (nouns > 1) {
				String message = "OverloadedIdentifier, too many nouns (" + nouns + ") in class / attribute / variable / parameter: " + tokenText;
			    log(ast.getLineNo(), message);
			}
		}
	}

	public int numVerbs(ArrayList<String> sList) {	
		

		int count= 0;
		for(String s : sList) {
			if (wn.isVerb(s)) {
				count++;
			}
		}
		// TODO: figure out how to handle verb or noun
		return count;
	}
	
	public int numNouns (ArrayList<String> sList) {	


		int count = 0;
		for(String s : sList) {
			if (wn.isNoun(s)) {
				count++;
			}
		}
		// TODO: figure out how to handle verb or noun
		return count;
	}
	
	public ArrayList<String> TokenizeIdentifier(String s){
		//Split Identifier into its individual words
		String[] sArray = s.split("((?<=.)(?=(\\p{Upper})))|_");
		ArrayList<String> sList = new ArrayList<String>();
		for (String str : sArray) {
			if((str.length() > 0) && (str != null)) {
				sList.add(str);
			}
		}
		return sList;
	}
}
