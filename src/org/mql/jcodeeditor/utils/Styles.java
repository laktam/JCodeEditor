package org.mql.jcodeeditor.utils;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.mql.jcodeeditor.highlighting.Token;
import org.mql.jcodeeditor.highlighting.TokenType;
import org.mql.jcodeeditor.highlighting.Tokenizer;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Styles {
	public static void setHTMLStyle(JTextPane textPane) {
		Style style = textPane.addStyle("BoldRed", null);

		StyleConstants.setBold(style, true);
		StyleConstants.setForeground(style, Color.RED);
		StyledDocument doc = textPane.getStyledDocument();
		String content = textPane.getText();

		Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
		Style keywordStyle = doc.addStyle("KeywordStyle", defaultStyle);
		StyleConstants.setForeground(keywordStyle, Color.BLUE);
		StyleConstants.setBold(keywordStyle, true);
		
		Style boldRed = doc.addStyle("BoldRed", defaultStyle);
		StyleConstants.setForeground(boldRed, Color.RED);
		StyleConstants.setBold(boldRed, true);
		
		Style identifierStyle = doc.addStyle("Identifier", defaultStyle);
		StyleConstants.setForeground(identifierStyle, Color.ORANGE);
		StyleConstants.setBold(identifierStyle, true);

		// Compile the pattern
//		Pattern pattern = Pattern.compile("\\b(public|class|static|void)\\b");
//		Pattern keywordPattern = Pattern.compile("\\b(abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|default|do|double|else|enum|extends|final|finally|float|for|goto|if|implements|import|instanceof|int|interface|long|native|new|null|package|private|protected|public|return|short|static|strictfp|super|switch|synchronized|this|throw|throws|transient|try|void|volatile|while)\\b") ;
//		Pattern keywordPattern = Pattern.compile("\\b(abstract|assert|break|case|catch|class|const|continue|default|do|else|enum|extends|final|finally|for|goto|if|implements|import|instanceof|interface|native|new|null|package|private|protected|public|return|static|strictfp|super|switch|synchronized|this|throw|throws|transient|try|volatile|while)\\b");

//		// Create the matcher
//		Matcher matcher = keywordPattern.matcher(content);
//		while (matcher.find()) {
//			doc.setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), keywordStyle, false);
//		}
		Tokenizer tokenizer = new Tokenizer();
		List<Token> tokens = tokenizer.tokenize(content);
		for (Token token : tokens) {
			if(token.getType().equals(TokenType.KEYWORD)) {
				doc.setCharacterAttributes(token.getStart(), token.getSize(), keywordStyle, false);
			}
			if(token.getType().equals(TokenType.NUMBER)) {
				doc.setCharacterAttributes(token.getStart(), token.getSize(), boldRed, false);
			}
			if(token.getType().equals(TokenType.IDENTIFIER)) {
				doc.setCharacterAttributes(token.getStart(), token.getSize(), identifierStyle, false);
			}
			
			
		}

//     Stream<MatchResult> results=   matcher.results();

//		String[] keywords = { "public", "class", "static", "void" };
//		for (String keyword : keywords) {
//			int pos = content.indexOf(keyword);
//			while (pos >= 0) {
//				doc.setCharacterAttributes(pos, keyword.length(), keywordStyle, false);
//				pos = content.indexOf(keyword, pos + keyword.length());
//			}
//		}

//		try {
//			// Insert styled text
//			doc.insertString(doc.getLength(), "Hello, ", null);
//			doc.insertString(doc.getLength(), "world!", style);
//		} catch (BadLocationException e) {
//			e.printStackTrace();
//		}
	}
}
