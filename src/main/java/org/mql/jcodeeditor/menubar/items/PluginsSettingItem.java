package org.mql.jcodeeditor.menubar.items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import org.mql.jcodeeditor.JEditor;

public class PluginsSettingItem extends JMenuItem{
	private static final long serialVersionUID = 1L;

	public PluginsSettingItem(JEditor editor) {
		super("Plugins");
		addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				editor.openPluginSetting();
			}
		});
	}

}
