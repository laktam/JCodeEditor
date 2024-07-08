package org.mql.jcodeeditor;

import java.awt.Component;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
	private final Icon leafIcon;
	private final Icon openIcon;
	private final Icon closedIcon;

	public CustomTreeCellRenderer() {
		// Load custom icons
		leafIcon = UIManager.getIcon("Tree.leafIcon");
		openIcon = UIManager.getIcon("Tree.openIcon");
		closedIcon = UIManager.getIcon("Tree.closedIcon");
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		File file = JExplorer.getFilesMap().get(node);
		
		if (expanded) {
			setIcon(openIcon);
		} else if (file.isDirectory()) {
			setIcon(closedIcon);
		} else {
			setIcon(leafIcon);
		}


		return this;
	}
}