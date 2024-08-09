package org.mql.jcodeeditor.menubar.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.mql.jcodeeditor.JEditor;
import org.mql.jcodeeditor.menubar.items.PluginsSettingItem;

public class SettingMenu extends JMenu{
	
	public SettingMenu(JEditor editor) {
		super("Setting");
		JMenuItem pluginsItem = new PluginsSettingItem(editor);
		add(pluginsItem);
	}

}
