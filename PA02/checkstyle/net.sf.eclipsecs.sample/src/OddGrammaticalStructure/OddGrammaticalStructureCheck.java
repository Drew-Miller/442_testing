package OddGrammaticalStructure;

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
import antlr.collections.ASTEnumeration;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

public class OddGrammaticalStructureCheck extends AbstractCheck {
	
	WordNet wn = null;
	
	public OddGrammaticalStructureCheck() {
		wn = new WordNet();
	}
	
	@Override
	public int[] getDefaultTokens() {
		return new int[] { TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF, TokenTypes.METHOD_DEF, TokenTypes.VARIABLE_DEF };
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
		ArrayList<String> tokenizedIdent = TokenizeIdentifier(tokenText);
		
		if(ast.getType() == TokenTypes.CLASS_DEF || ast.getType() == TokenTypes.INTERFACE_DEF) {
			if (CountNouns(tokenizedIdent) < 1 || CountVerbs(tokenizedIdent) > 0) {
				log(ast.getLineNo(), "Odd Grammatical Structure in class def");
			}
		}
		if(ast.getType() == TokenTypes.METHOD_DEF) {
			if(!wn.isVerb(tokenizedIdent.get(0))) {
				log(ast.getLineNo(), "Odd Grammatical Structure in method def");
			}
		}
		if(ast.getType() == TokenTypes.VARIABLE_DEF) {
			for (String s : tokenizedIdent) {
				if(wn.isVerb(s)) {
					log(ast.getLineNo(), "Odd Grammatical Structure in variable def");
				}
			}
		}
		
	}
		
	public int CountNouns(ArrayList<String> StringList) {
		int count = 0;
		for (String s : StringList) {
			if (wn.isNoun(s)) {
				count++;
			}
		}
		
		return count;
	}
	
	public int CountVerbs(ArrayList<String> StringList) {
		int count = 0;
		for (String s : StringList) {
			if (wn.isVerb(s)) {
				count++;
			}
		}
		
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
