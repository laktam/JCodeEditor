package org.mql.jcodeeditor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.mql.jcodeeditor.highlighting.Tokenizer;

public class Context {
	// string : file extensions
	public static Map<String, Tokenizer> tokenizersMap = new HashMap<String, Tokenizer>();
	
	// this should be called to search for plugins related stuff
	public void init() {
		
	}
}
