package org.mql.jcodeeditor.eventlisteners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.mql.jcodeeditor.JExplorer;
import org.mql.jcodeeditor.JEditor;

public class DoubleClickListener extends MouseAdapter {
	private JTree tree;
	private JEditor editor;

	public DoubleClickListener(JTree tree, JEditor editor) {
		this.tree = tree;
		this.editor = editor;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			TreePath path = tree.getPathForLocation(e.getX(), e.getY());
			if (path != null) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
//                 JOptionPane.showMessageDialog(null, "Double-clicked on: " + node.getUserObject());
				// check if it is a file
				if (node.getUserObject() instanceof File) {
					File clickedFile = (File) node.getUserObject();
					if (clickedFile.isFile() && !JExplorer.getOpenFiles().contains(clickedFile)) {
						editor.openFile(clickedFile);
						JExplorer.getOpenFiles().add(clickedFile);
					}
				}
			}
		}
	}
}
