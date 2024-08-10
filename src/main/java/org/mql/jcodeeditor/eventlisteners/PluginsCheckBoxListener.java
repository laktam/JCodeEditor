package org.mql.jcodeeditor.eventlisteners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.mql.jcodeeditor.plugins.Reactivable;

public class PluginsCheckBoxListener implements ItemListener {
	private Reactivable reactivable;

	public PluginsCheckBoxListener(Reactivable reactivable) {
		this.reactivable = reactivable;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			reactivable.activate();
		}else {
			reactivable.deactivate();
		}
	}

}
