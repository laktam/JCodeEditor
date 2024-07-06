package org.mql.jcodeeditor.eventlisteners.explorerJTree;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class DoubleClickListener extends MouseAdapter{
	private JTree tree;
	public DoubleClickListener(JTree tree) {
		this.tree = tree;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		 if (e.getClickCount() == 2) {
             TreePath path = tree.getPathForLocation(e.getX(), e.getY());
             if (path != null) {
                 DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                 JOptionPane.showMessageDialog(null, "Double-clicked on: " + node.getUserObject());
             }
         }
	}
}
