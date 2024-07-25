package org.mql.jcodeeditor.utils;

import java.awt.Color;

import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.mql.jcodeeditor.highlighting.DocumentChangeListener;
import org.mql.jcodeeditor.highlighting.Highlighter;
import org.mql.jcodeeditor.highlighting.Token;
import org.mql.jcodeeditor.highlighting.TokenType;
import org.mql.jcodeeditor.highlighting.Tokenizer;
import org.mql.jcodeeditor.highlighting.JavaTokenizer;

import java.util.List;

public class Styles {
	private static Highlighter highlighter; 
	

	
	public static void setHighlighter(Highlighter h) {
		highlighter = h;
	}
	
	public static void highlight() {
		highlighter.highlight() ;
	}

}
