package org.mql.jcodeeditor.utils;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

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

		// Compile the pattern
		Pattern pattern = Pattern.compile("\\b(public|class|static|void)\\b");

		// Create the matcher
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			doc.setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), keywordStyle, false);
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

		try {
			// Insert styled text
			doc.insertString(doc.getLength(), "Hello, ", null);
			doc.insertString(doc.getLength(), "world!", style);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
}
