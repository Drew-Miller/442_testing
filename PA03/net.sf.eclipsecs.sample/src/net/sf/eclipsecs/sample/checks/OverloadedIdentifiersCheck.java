/*
 * This checks for methods with more than 1 verb or class/variable/parameters with more than 1 noun
 * Author: Michael Duncan
 */

package net.sf.eclipsecs.sample.checks;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class OverloadedIdentifiersCheck extends AbstractCheck {

	private MaxentTagger tagger;
	public OverloadedIdentifiersCheck()
	{
		init();
	}
	
	// Description: Starts the POS tagger
	public void init() {
		try {
			tagger = new MaxentTagger("taggers/bidirectional-distsim-wsj-0-18.tagger");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Description: Describes the tokens that I want this to run for
    @Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.VARIABLE_DEF, TokenTypes.INTERFACE_DEF, TokenTypes.CLASS_DEF, TokenTypes.PARAMETER_DEF, TokenTypes.METHOD_DEF };
    }
    
    // isNoun, isVerb: Tests a string that has been tagged to see if it is a noun or a verb based on the tag.
    // Takes into account backslash for escape so no false positives
    
    private boolean isNoun(String s)
	{
		return s.matches(".*[^\\\\]/NN.*");
	}
	
	private boolean isVerb(String s)
	{
		return s.matches(".*[^\\\\]/VB.*");
	} 
	
	// Description: Takes in a camelCase or underscore separated string and returns separated with spaces
	private String nameToSentence(String s)
	{
		String[] splitWords;
		String sentence = "";
		if(s.matches(".*?([a-z]|[A-Z])_.*")) //if it contains an underscore after letters, split by underscore
			splitWords =  s.split("_");
		else //otherwise assume split by capital
			splitWords =  s.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])"); // taken from stack overflow
		
		for(String word : splitWords)
		{
			sentence += word.toLowerCase() + " ";
		}
		return sentence;
	}
	
	// Description: Returns true if the string follows the rules
	// Rules: If method, maximum 1 verb. Otherwise, maximum 1 noun.
	public boolean followsRules(String s, boolean isMethod)
	{
		int nouns = 0, verbs = 0;	
		String sentence = nameToSentence(s);
		// Tag the string then split
        String[] taggedWords = tagger.tagString(sentence).split(" ");
        //Count the nouns and verbs
        for(String taggedWord : taggedWords)
        {
        	if(isNoun(taggedWord))
        		nouns++;
        	else if(isVerb(taggedWord))
        		verbs++;
        }
        //Check that rules are followed
        if((!isMethod && nouns >= 2)  || (isMethod && verbs >= 2))
        	return false;
        return true;
}

	// Description: Called when a node in tree is visited with matching token
    @Override
    public void visitToken(DetailAST ast) {
        // find the identifier(name)
        DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);

        String name = ident.getText();
        if(ast.getType() == TokenTypes.METHOD_DEF && !followsRules(name, true)) //if it is a method, we are checking for verbs
        	log(ast.getLineNo(), "toomanyverbs");
        else if(!followsRules(name, false)) //otherwise we are checking for nouns
        	log(ast.getLineNo(), "toomanynouns");
    }
}
