package org.mql.jcodeeditor;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

public class Client {
	public static void main(String[] args) {
		FlatLightLaf.setup();
		new JCodeEditor();
	}
}
