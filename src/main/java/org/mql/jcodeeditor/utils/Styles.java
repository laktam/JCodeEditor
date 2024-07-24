package org.mql.jcodeeditor.utils;

import java.awt.Color;

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

public class Styles {
	private static StyledDocument doc;
	private static Tokenizer tokenizer;
	private static Style keywordStyle;
	private static Style numberStyle;
	private static Style identifierStyle;
	private static Style defaultStyle;
	private static Style commentStyle;
	private static Style stringStyle;

	private static Color commentColor = new Color(63, 127, 95);
	private static Color identifierColor = new Color(143, 86, 4) ;
	private static Color stringColor = new Color(42, 0, 255);
	private static Color keywordColor = new Color(127, 0, 85);
	private static Color numberColor = new Color(127, 0, 85);


	private static void setColors(Color comment, Color identifier, Color string, Color keyword) {
		commentColor = comment;
		identifierColor = identifier;
		stringColor = string;
		keywordColor = keyword;
	}

	// setter for styles

	public static void setDocument(StyledDocument document) {
		doc = document;
		defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

		keywordStyle = doc.addStyle("KeywordStyle", defaultStyle);
		StyleConstants.setForeground(keywordStyle, keywordColor);
		StyleConstants.setBold(keywordStyle, true);

		numberStyle = doc.addStyle("NumbersStyle", defaultStyle);
		StyleConstants.setForeground(numberStyle, numberColor);

		identifierStyle = doc.addStyle("Identifier", defaultStyle);
		StyleConstants.setForeground(identifierStyle, identifierColor);

		commentStyle = doc.addStyle("CommentsStyle", defaultStyle);
		StyleConstants.setForeground(commentStyle, commentColor);

		stringStyle = doc.addStyle("stringStyle", defaultStyle);
		StyleConstants.setForeground(stringStyle, stringColor);
	}

	public static void setTokenizer(Tokenizer t) {
		tokenizer = t;
	}

	public static void highlight() {
		applyStyles();
		doc.addDocumentListener(new DocumentChangeListener());
	}

	public static void applyStyles() {
		String code = "";
		try {
			code = doc.getText(0, doc.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		doc.setCharacterAttributes(0, doc.getLength(), defaultStyle, true);
		List<Token> tokens = tokenizer.tokenize(code);
		System.out.println(tokens);
		for (Token token : tokens) {
			if (token.getType().equals(TokenType.KEYWORD)) {
				doc.setCharacterAttributes(token.getStart(), token.getSize(), keywordStyle, true);
			}
			if (token.getType().equals(TokenType.IDENTIFIER)) {
				doc.setCharacterAttributes(token.getStart(), token.getSize(), identifierStyle, true);
			}
			if (token.getType().equals(TokenType.COMMENT)) {
				doc.setCharacterAttributes(token.getStart(), token.getSize(), commentStyle, true);
			}
			if (token.getType().equals(TokenType.STRING)) {
				doc.setCharacterAttributes(token.getStart(), token.getSize(), stringStyle, true);
			}
		}
	}
}
