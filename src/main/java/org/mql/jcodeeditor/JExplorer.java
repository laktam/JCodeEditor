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

import org.mql.jcodeeditor.properties.PropertiesManager;

public class JExplorer extends JTree {private static final long serialVersionUID = 1L;
	private static List<File> openFiles = new Vector<File>();
	private DefaultTreeModel treeModel;

	public JExplorer() {
		treeModel = new OrderedTreeModel(null);
		String lastOpenedFile = PropertiesManager.readProperty("lastOpenedFile");
		System.out.println(lastOpenedFile);
		openFileInExplorer(Path.of(lastOpenedFile));
		setModel(treeModel);
		setDragEnabled(true);
		setDropMode(DropMode.ON_OR_INSERT);
		setTransferHandler(new JExplorerTransferHandler());
		expandTree();
		
	}

	public void openFileInExplorer(Path path) {
		File file = path.toFile();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(file);
		if (file.isDirectory()) {
			File subFiles[] = file.listFiles();
			for (File f : subFiles) {
				add(f, root);
				Context.addExplorerFile(f);
			}
		}
		treeModel.setRoot(root);
		treeModel.reload();
	}

	private void add(File file, DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(file);
		parent.add(fileNode);
		if (file.isDirectory()) {
			File subFiles[] = file.listFiles();
			for (File f : subFiles) {
				add(f, fileNode);
				Context.addExplorerFile(f);
			}
		}

	}
	
	public void reloadRoot() {
		File rootFile = (File) ((DefaultMutableTreeNode) treeModel.getRoot()).getUserObject();
		openFileInExplorer(rootFile.toPath());
	}

	public static List<File> getOpenFiles() {
		return openFiles;
	}

	public DefaultTreeModel getTreeModel() {
		return treeModel;
	}

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
