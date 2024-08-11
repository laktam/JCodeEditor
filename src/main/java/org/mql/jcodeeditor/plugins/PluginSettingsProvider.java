package org.mql.jcodeeditor.plugins;

import java.util.List;

import javax.swing.JComponent;

public interface PluginSettingsProvider {
	public List<JComponent> getSettings();
}
