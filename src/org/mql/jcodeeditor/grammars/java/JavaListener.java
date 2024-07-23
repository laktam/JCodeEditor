package org.mql.jcodeeditor.grammars.java;

import java.util.List;
import java.util.Vector;

import org.mql.jcodeeditor.grammars.java.Java20Parser.TypeIdentifierContext;
import org.mql.jcodeeditor.highlighting.Token;
import org.mql.jcodeeditor.highlighting.TokenType;

public class JavaListener extends Java20ParserBaseListener {
	private List<Token> tokens;
	
	public JavaListener() {
		tokens = new Vector<Token>();
	}
    
    @Override
    public void enterTypeIdentifier(TypeIdentifierContext ctx) {
    	tokens.add(new Token(TokenType.IDENTIFIER, ctx.getText(), ctx.getStart().getStartIndex(), ctx.getStart().getStopIndex() - ctx.getStart().getStartIndex()));
    	System.out.println(tokens);
    }
    
    public List<Token> getTokens() {
		return tokens;
	}
}