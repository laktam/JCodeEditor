package org.mql.jcodeeditor;

import java.awt.Color;

import javax.swing.UIManager;

import org.mql.jcodeeditor.JCodeEditor;

import com.formdev.flatlaf.FlatLightLaf;

public class Client {
	public static void main(String[] args) {
		FlatLightLaf.setup();
		//configuration
		UIManager.put( "TabbedPane.selectedBackground", Color.white );
		new JCodeEditor();

	}
}
