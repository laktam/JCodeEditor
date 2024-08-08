package org.mql.jcodeeditor.menubar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.mql.jcodeeditor.JExplorer;

public class ExitItem extends JMenuItem {
	private static final long serialVersionUID = 1L;

	public ExitItem(JFrame jFrame) {
		super("Exit");
		addActionListener(e -> {
			jFrame.dispose();
		});
	}
}
