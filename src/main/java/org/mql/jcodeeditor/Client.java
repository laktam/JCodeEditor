package org.mql.jcodeeditor;

import java.awt.Color;

import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLightLaf;

public class Client {
	public static void main(String[] args) {
		FlatLightLaf.setup();
		UIManager.put( "TabbedPane.selectedBackground", Color.white );
		new JCodeEditor();

	}
}
