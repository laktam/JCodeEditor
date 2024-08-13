package org.mql.jcodeeditor.eventlisteners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.mql.jcodeeditor.plugins.Plugin;
import org.mql.jcodeeditor.properties.PropertiesManager;

public class PluginsCheckBoxListener implements ItemListener {
	private Plugin plugin;

	public PluginsCheckBoxListener(Plugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			plugin.activate();
			System.out.println("activate");
			PropertiesManager.writeProperty("plugins."+ plugin.getName() + ".status", "active");
		}else {
			System.out.println("deactivate");
			PropertiesManager.writeProperty("plugins."+ plugin.getName() + ".status", "disabled");
			plugin.deactivate();
		}
	}

}
