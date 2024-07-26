package org.mql.jcodeeditor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mql.jcodeeditor.highlighting.Highlighter;
import org.mql.jcodeeditor.highlighting.Tokenizer;
import org.mql.jcodeeditor.plugins.PluginLoader;

public class Context {
	// string : file extensions, we might have more that one highlighter for same extension
	private static Map<String, List<Tokenizer>> highlightersMap = new HashMap<String, List<Tokenizer>>();
	private static Highlighter highlighter;
	static {
		
		init();
	}
	// this should be called to search for plugins related stuff
	public static void init() {
		//load classes
		Highlighter h = PluginLoader.loadPlugins(Highlighter.class).get(0);
		Tokenizer t = PluginLoader.loadPlugins(Tokenizer.class).get(0);

		h.setTokenizer(t);
		highlighter = h;
	}
	
	public static Highlighter getHighlighter() {
		return highlighter;
	}
	
	
	
	
	
}
