package org.mql.jcodeeditor.utils;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.mql.jcodeeditor.highlighting.DocumentChangeListener;
import org.mql.jcodeeditor.highlighting.Token;
import org.mql.jcodeeditor.highlighting.TokenType;
import org.mql.jcodeeditor.highlighting.Tokenizer;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Styles {
	private static StyledDocument doc;
	private static Tokenizer tokenizer;
	private static Style keywordStyle;
	private static Style numbersStyle;
	private static Style identifierStyle;
	private static Style defaultStyle;
	public static void setDocument(StyledDocument document) {
		doc = document;
	}
	
	public static void setTokenizer(Tokenizer t) {
		tokenizer = t;
	}
	
	public static void highlight() {
		
		 defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		
//		blackStyle = doc.addStyle("blackStyle", defaultStyle);
//		StyleConstants.setForeground(blackStyle, Color.BLACK);

		keywordStyle = doc.addStyle("KeywordStyle", defaultStyle);
		StyleConstants.setForeground(keywordStyle, Color.BLUE);
		StyleConstants.setBold(keywordStyle, true);
		
		
		numbersStyle = doc.addStyle("NumbersStyle", defaultStyle);
		StyleConstants.setForeground(numbersStyle, Color.RED);
		StyleConstants.setBold(numbersStyle, true);
		
		identifierStyle = doc.addStyle("Identifier", defaultStyle);
		StyleConstants.setForeground(identifierStyle, Color.ORANGE);
		StyleConstants.setBold(identifierStyle, true);
		applyStyles();
		doc.addDocumentListener(new DocumentChangeListener());
	}
	
	public static void applyStyles() {
		String code = "";
		try {
			code = doc.getText(0,doc.getLength());
//			System.out.println(code);

		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		// set to default
		doc.setCharacterAttributes(0, doc.getLength(), defaultStyle, true);
		List<Token> tokens = tokenizer.tokenize(code);
		System.out.println(tokens);
		for (Token token : tokens) {
			if(token.getType().equals(TokenType.KEYWORD)) {
				doc.setCharacterAttributes(token.getStart(), token.getSize(), keywordStyle, true);
			}
			if(token.getType().equals(TokenType.NUMBER)) {
				doc.setCharacterAttributes(token.getStart(), token.getSize(), numbersStyle, true);
			}
			if(token.getType().equals(TokenType.IDENTIFIER)) {
				doc.setCharacterAttributes(token.getStart(), token.getSize(), identifierStyle, true);
			}	
		}
	}
}
