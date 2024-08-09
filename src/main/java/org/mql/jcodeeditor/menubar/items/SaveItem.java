package org.mql.jcodeeditor.menubar.items;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.mql.jcodeeditor.JEditor;
import org.mql.jcodeeditor.JExplorer;

public class SaveItem extends JMenuItem {


	public SaveItem(JEditor editor) {
		super("Save");
		addActionListener(e -> {
			File focusedFile = JExplorer.getOpenFiles().get(editor.getSelectedIndex());
			JScrollPane scrollPane = (JScrollPane) editor.getSelectedComponent();
			if(scrollPane != null) {
				JTextArea textArea = (JTextArea) scrollPane.getViewport().getView();
				try {
					BufferedWriter f_writer = new BufferedWriter(new FileWriter(focusedFile));
					f_writer.write(textArea.getText());
					f_writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}	
			}
			
		});
	}
}
