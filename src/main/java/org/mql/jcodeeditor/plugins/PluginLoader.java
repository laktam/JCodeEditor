package org.mql.jcodeeditor.plugins;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Vector;

import org.mql.jcodeeditor.highlighting.Highlighter;
import org.mql.jcodeeditor.highlighting.Tokenizer;

public class PluginLoader {
    private static final String PLUGINS_DIR = "./plugins";

    public static <T> List<T> loadPlugins(Class<T> pluginType) {
//    	List<Tokenizer> tokenizers = new Vector<Tokenizer>();
//    	List<Highlighter> highlighter= new Vector<Highlighter>();
    	List<T> implementations = new Vector<>();
        File pluginsDir = new File(PLUGINS_DIR);
        if (!pluginsDir.exists() || !pluginsDir.isDirectory()) {
            System.out.println("Plugins directory not found or is not a directory.");
            return null;
        }

        File[] jarFiles = pluginsDir.listFiles((dir, name) -> name.endsWith(".jar"));

        if (jarFiles != null) {
            for (File jar : jarFiles) {
                try {
                    URL jarUrl = jar.toURI().toURL();
                    URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl}, PluginLoader.class.getClassLoader());
                    ServiceLoader<T> loader = ServiceLoader.load(pluginType, classLoader);                    
                    for (T plugin : loader) {
                        System.out.println("Loaded plugin: " + plugin.getClass().getName());
                        implementations.add(plugin);
                    }
                } catch (Exception e) {
                    System.err.println("Error loading plugin from " + jar.getName() + ": " + e.getMessage());
                }
            }
        } else {
            System.out.println("No plugin JARs found in the plugins directory.");
        }
        return implementations;
    }
}