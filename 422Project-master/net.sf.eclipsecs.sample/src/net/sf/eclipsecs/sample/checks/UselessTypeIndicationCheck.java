package net.sf.eclipsecs.sample.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.util.ArrayList;
import java.util.List;

public class UselessTypeIndicationCheck extends AbstractCheck {
	
	@Override
    public int[] getDefaultTokens() {
        return new int[] { TokenTypes.VARIABLE_DEF }; // only check variable definitions
    }
	
	@Override
    public void visitToken(DetailAST ast) {
		// get the identifier
		String ident = getIdent(ast);
		
		// get the data type
		DetailAST def = getDataType(ast);
		String type = getDataTypeStrings(def);
		
		// log if useless type detected
		if(isUselessType(ident, type)) {
			log(ast.getLineNo(), "uselesstypeindication");
		}
    }
	
	// determine if the string ident contains any string from the types list
	public boolean isUselessType(String ident, String type) {
		ident = ident.toLowerCase(); // ignore letter case
		if(ident.contains(type)) {
			return true;
		}
		return false;
	}
	
	// return the text from the token's identifier
	public String getIdent(DetailAST ast) {
		DetailAST ident = ast.findFirstToken(TokenTypes.IDENT);
		return ident.getText();
	}
	
	// return the DetailAST for the token's data type
	public DetailAST getDataType(DetailAST ast) {
		return ast.findFirstToken(TokenTypes.TYPE);
	}
	
	// return the string corresponding to the data type
	public String getDataTypeStrings(DetailAST def) {
		String type = "";
		// (tree traversal makes it so you have to check for each data type)
		if(def.findFirstToken(TokenTypes.LITERAL_BOOLEAN) != null) {
			type = "bool"; // "bool" covers both "bool" and "boolean"
		}
		else if(def.findFirstToken(TokenTypes.LITERAL_BYTE) != null) {
			type = "byte";
		}
		else if(def.findFirstToken(TokenTypes.LITERAL_CHAR) != null) {
			type = "char";
		}
		else if(def.findFirstToken(TokenTypes.LITERAL_DOUBLE) != null) {
			type = "double";
		}
		else if(def.findFirstToken(TokenTypes.LITERAL_FLOAT) != null) {
			type = "float";
		}
		else if(def.findFirstToken(TokenTypes.LITERAL_INT) != null) {
			type = "int";
		}
		else if(def.findFirstToken(TokenTypes.LITERAL_LONG) != null) {
			type = "long";
		}
		else if(def.findFirstToken(TokenTypes.LITERAL_SHORT) != null) {
			type = "short";
		}
		else if(def.findFirstToken(TokenTypes.IDENT) != null) {
			type = "str"; // "str" covers both "str" and "string"
		}
		return type;
	}
}