package org.mql.jcodeeditor.eventlisteners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.mql.jcodeeditor.plugins.Plugin;
import org.mql.jcodeeditor.properties.PropertiesManager;

public class PluginsCheckBoxListener implements ItemListener {
	private Plugin reactivable;

	public PluginsCheckBoxListener(Plugin reactivable) {
		this.reactivable = reactivable;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			reactivable.activate();
			System.out.println("activate");
			PropertiesManager.writeProperty("plugins.status." + reactivable.getClass().getSimpleName(), "active");
		}else {
			System.out.println("deactivate");
			PropertiesManager.writeProperty("plugins.status." + reactivable.getClass().getSimpleName(), "disabled");
			reactivable.deactivate();
		}
	}

}
