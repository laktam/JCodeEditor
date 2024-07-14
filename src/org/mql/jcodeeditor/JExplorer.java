package org.mql.jcodeeditor;

import java.awt.Component;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.io.File;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.DropMode;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class JExplorer extends JTree {
	private static Map<DefaultMutableTreeNode, File> filesMap;
	private static List<File> openFiles = new Vector<File>();
	private DefaultTreeModel treeModel;

	public JExplorer() {
//		treeModel = new DefaultTreeModel(null);
		treeModel = new OrderedTreeModel(null);
		openFileInExplorer(Path.of(""));
		setModel(treeModel);

		// Enable drag-and-drop
		setDragEnabled(true);
		setDropMode(DropMode.ON_OR_INSERT);
		setTransferHandler(new JExplorerTransferHandler());
		// Expand the tree
		expandTree();
		addHierarchyListener(new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent e) {
            	 
            }
        });
	}

	// add files and folders to explorer JTree
	public void openFileInExplorer(Path path) {
		File file = path.toFile();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(file);

		if (file.isDirectory()) {
			File subFiles[] = file.listFiles();
			for (File f : subFiles) {
				add(f, root);
			}
		}
		treeModel.setRoot(root);
		treeModel.reload();
	}

	private void add(File file, DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(file);
		parent.add(fileNode);
//		filesMap.put(fileNode, file);

		if (file.isDirectory()) {
			File subFiles[] = file.listFiles();
			for (File f : subFiles) {
				add(f, fileNode);
			}
		}

	}
	
	public void reloadRoot() {
		File rootFile = (File) ((DefaultMutableTreeNode) treeModel.getRoot()).getUserObject();
		openFileInExplorer(rootFile.toPath());
	}

	// remove a file or directory with children
//	public static void removeFromFileMap(DefaultMutableTreeNode node) {
//		filesMap.remove(node);
//		int c = node.getChildCount();
//		for (int i = 0; i < c; i++) {
//			removeFromFileMap((DefaultMutableTreeNode) node.getChildAt(i));
//		}
//
//	}

	public static Map<DefaultMutableTreeNode, File> getFilesMap() {
		return filesMap;
	}

	public static List<File> getOpenFiles() {
		return openFiles;
	}

	public DefaultTreeModel getTreeModel() {
		return treeModel;
	}

	// drag and drop
	private void expandTree() {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
		expandAll(this, new TreePath(root.getPath()), true);
	}

	private void expandAll(JTree tree, TreePath parent, boolean expand) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) parent.getLastPathComponent();
		if (node.getChildCount() > 0) {
			for (Enumeration<?> e = node.children(); e.hasMoreElements();) {
				DefaultMutableTreeNode n = (DefaultMutableTreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path, expand);
			}
		}

		if (expand) {
			tree.expandPath(parent);
		} else {
			tree.collapsePath(parent);
		}
	}
}
