package org.mql.jcodeeditor;

import java.awt.Color;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.FileHandler;

import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

import org.mql.jcodeeditor.documentHandlers.TextPanesHandler;
import org.mql.jcodeeditor.highlighting.Highlighter;
import org.mql.jcodeeditor.highlighting.Tokenizer;
import org.mql.jcodeeditor.plugins.FilesHandler;
import org.mql.jcodeeditor.plugins.PluginLoader;
import org.mql.jcodeeditor.plugins.PropertiesHandler;
import org.mql.jcodeeditor.plugins.Plugin;
import org.mql.jcodeeditor.properties.PropertiesManager;

public class Context {
    String filePath = System.getProperty("user.home") + "/config.properties";

	private static String settingPropertiesPath = System.getProperty("user.home") +  "/JCodeEditorSettings/settings.properties";
	private static Color lineNumbersColor = new Color(240, 240, 240);
	private static List<JTextPane> textPanes = new Vector<JTextPane>();
	private static List<TextPanesHandler> textPaneHandlers = new Vector<TextPanesHandler>();
	private static List<FilesHandler> filesHandlers = new Vector<FilesHandler>();
	private static Map<String, Plugin> pluginsMap = new HashMap<>();
	private static Map<String, List<Highlighter>> highlightersMap = new HashMap<String, List<Highlighter>>();
	
	static {
		init();
	}

	public static void init() {
		List<Highlighter> hList = PluginLoader.loadPlugins(Highlighter.class);
		List<Tokenizer> tList = PluginLoader.loadPlugins(Tokenizer.class);
		for (Tokenizer t : tList) {
			for (Highlighter h : hList) {
				if (h.getTargetExtension().equals(t.getTargetExtension())) {
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

		textPaneHandlers = PluginLoader.loadPlugins(TextPanesHandler.class);
		for (TextPanesHandler tph : textPaneHandlers) {
			tph.setTextPanes(textPanes);
			tph.execute();
		}

		filesHandlers = PluginLoader.loadPlugins(FilesHandler.class);
		
		List<Plugin> plugins = PluginLoader.loadPlugins(Plugin.class);
		for(Plugin plugin : plugins) {
			pluginsMap.put(plugin.getClass().getSimpleName(), plugin);
		}
		
		for(Plugin plugin : plugins) {
			String status = PropertiesManager.readProperty("plugins."+ plugin.getName() + ".status");
			if("disabled".equals(status)) {
				plugin.deactivate();
			}
			// plugins are activated by default when loaded
		}
	}

	public static Highlighter getHighlighter(String extension) {
		// !!!!!!!!!!!!!!!!!!!!!!!!
		// TODO here if there is two we should add an alert for the user to choose wich one
		// to use
		if (highlightersMap.containsKey(extension))
			return highlightersMap.get(extension).get(0);
		else
			return null;
	}

	public static void addTextPane(JTextPane textPane) {
		textPanes.add(textPane);
		for (TextPanesHandler h : textPaneHandlers) {
			h.addTextPane(textPane);
		}
	}

	public static void addExplorerFile(File file) {
		for (FilesHandler filesHandler : filesHandlers) {
			filesHandler.addFile(file);
		}
	}

	public static Color getLineNumbersColor() {
		return lineNumbersColor;
	}

	public static String getSettingPropertiesPath() {
		return settingPropertiesPath;
	}
	
	public static Plugin getPlugin(String key) {
		return pluginsMap.get(key);
	}
	
	public static Set<String> getPluginNames(){
		return pluginsMap.keySet();
	}
}
