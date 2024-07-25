package org.mql.jcodeeditor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mql.jcodeeditor.highlighting.Tokenizer;

public class Context {
	// string : file extensions, we might have more that one highlighter for same extension
	private static Map<String, List<Tokenizer>> highlightersMap = new HashMap<String, List<Tokenizer>>();
	
	static {
		
		init();
	}
	// this should be called to search for plugins related stuff
	public static void init() {
		//load classes

	}
	
	
	
	
	
}
