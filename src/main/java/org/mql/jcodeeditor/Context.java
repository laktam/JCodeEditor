package org.mql.jcodeeditor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

import org.mql.jcodeeditor.documentHandlers.TextPanesHandler;
import org.mql.jcodeeditor.highlighting.Highlighter;
import org.mql.jcodeeditor.highlighting.Tokenizer;
import org.mql.jcodeeditor.plugins.PluginLoader;

public class Context {
	private static List<JTextPane> textPanes = new Vector<JTextPane>();
	private static List<TextPanesHandler> textPaneHandlers = new Vector<TextPanesHandler>();
	// string : file extensions, we might have more that one highlighter for same
	// extension
	private static Map<String, List<Highlighter>> highlightersMap = new HashMap<String, List<Highlighter>>();
	static {

		init();
	}

	// this should be called to search for plugins related stuff
	public static void init() {
		// load classes
		List<Highlighter> hList = PluginLoader.loadPlugins(Highlighter.class);
		List<Tokenizer >tList = PluginLoader.loadPlugins(Tokenizer.class);
		for(Tokenizer t : tList) {
			for(Highlighter h : hList) {
				if(h.getTargetExtension().equals(t.getTargetExtension())){
					h.setTokenizer(t);
					String targetExtension = h.getTargetExtension();
					if (!highlightersMap.containsKey(targetExtension)) {
						List<Highlighter> highlighterList = new Vector<Highlighter>();
						highlighterList.add(h);
						highlightersMap.put(targetExtension, highlighterList);
					} else {
						highlightersMap.get(targetExtension).add(h);
					}
				}
			}
		}
		
		// docs handlers
		 List<TextPanesHandler> tpHandlers =  PluginLoader.loadPlugins(TextPanesHandler.class);
		 for(TextPanesHandler tph : tpHandlers ) {
			 textPaneHandlers.add(tph);
			 tph.setTextPanes(textPanes);
			 tph.execute();
		 }
	}

	public static Highlighter getHighlighter(String extension) {
		// !!!!!!!!!!!!!!!!!!!!!!!!
		// here if there is two we should add an alert for the user to choose wich one to
		// use
		if (highlightersMap.containsKey(extension))
			return highlightersMap.get(extension).get(0);
		else
			return null;
	}
	
	public static void addTextPane(JTextPane textPane) {
		textPanes.add(textPane);
		for(TextPanesHandler h : textPaneHandlers) {
			h.addTextPane(textPane);
		}
	}

}
