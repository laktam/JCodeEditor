package org.mql.jcodeeditor;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

public class OrderedTreeModel extends DefaultTreeModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderedTreeModel(TreeNode root) {
		super(root);
	}

	@Override
	public void insertNodeInto(MutableTreeNode newChild, MutableTreeNode parent, int index) {
		super.insertNodeInto(newChild, parent, index);
		sortChildren(parent);
	}

	@Override
	public void nodeStructureChanged(TreeNode node) {
		super.nodeStructureChanged(node);
		sortChildren((MutableTreeNode) node);
	}

	private void sortChildren(MutableTreeNode parent) {
		int childCount = parent.getChildCount();
		if (childCount <= 1) {
			return;
		}

		List<MutableTreeNode> children = new Vector<>();
		for (int i = 0; i < childCount; i++) {
			children.add((MutableTreeNode) parent.getChildAt(i));
		}

		Collections.sort(children, new Comparator<MutableTreeNode>() {
			@Override
			public int compare(MutableTreeNode o1, MutableTreeNode o2) {
				// - first is less
				File f1 = (File) ((DefaultMutableTreeNode) o1).getUserObject();
				File f2 = (File) ((DefaultMutableTreeNode) o2).getUserObject();
				if (f1.isDirectory() && f2.isFile()) {
					return -1;
				} else if (f2.isDirectory() && f1.isFile()) {
					return 1;
				} else {
					return f1.getName().compareTo(f2.getName());
				}
//            	if()
//                return o1.toString().compareTo(o2.toString()); // Customize this comparator as needed
			}
		});
		System.out.println(children);
		((DefaultMutableTreeNode) parent).removeAllChildren();
		for (MutableTreeNode child : children) {
			parent.insert(child, parent.getChildCount());
		}
		super.nodeStructureChanged(parent);
	}
}
