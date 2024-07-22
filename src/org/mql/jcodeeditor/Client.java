package org.mql.jcodeeditor;

import java.awt.Color;

import javax.swing.UIManager;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.mql.jcodeeditor.grammars.java.Java20Lexer;
import org.mql.jcodeeditor.grammars.java.Java20Parser;

import com.formdev.flatlaf.FlatLightLaf;

public class Client {
	public static void main(String[] args) {
//		FlatLightLaf.setup();
//		//configuration
//		UIManager.put( "TabbedPane.selectedBackground", Color.white );
//		new JCodeEditor();
		
		// Create an input stream from the code to be parsed
        CharStream input = (CharStream) CharStreams.fromString("hello world");

        // Create a lexer that feeds off of input CharStream
        Java20Lexer lexer = new Java20Lexer(input);
        // Create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        // Create a parser that feeds off the tokens buffer
        Java20Parser parser = new Java20Parser(tokens);

        // Begin parsing 
        ParseTree tree = parser.compilationUnit();
        // Print the parse tree (for debugging)
        System.out.println(tree.toStringTree(parser));
	}
}
