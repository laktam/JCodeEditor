package org.mql.jcodeeditor;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mql.jcodeeditor.highlighting.Tokenizer;

public class Context {
	// string : file extensions
	private static Map<String, Tokenizer> tokenizersMap = new HashMap<String, Tokenizer>();
	
	static {
		
		init();
	}
	// this should be called to search for plugins related stuff
	public static void init() {
		//load classes
//		findAllImplementations()
	}
	
	
	
	
	
}
