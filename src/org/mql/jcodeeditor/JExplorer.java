package org.mql.jcodeeditor;

import java.awt.Component;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

public class JExplorer extends JTree {
	private static Map<DefaultMutableTreeNode, File> filesMap;
	private static List<File> openFiles = new Vector<File>();
	private DefaultTreeModel treeModel;

	
	public JExplorer() {
		treeModel = new DefaultTreeModel(null);
		openFileInExplorer("");
		setModel(treeModel);
	}

	// add files and folders to explorer JTree
	public void openFileInExplorer(String path) {//  return DefaultMutableTreeNode
		filesMap = new HashMap<DefaultMutableTreeNode, File>();
		File file = new File(path);
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(file.getName());
		filesMap.put(root, file);

		if (file.isDirectory()) {
			File subFiles[] = file.listFiles();
			for (File f : subFiles) {
				add(f, root);
			}
		}
		treeModel.setRoot(root);
		treeModel.reload();
//		return root;
	}

	private void add(File file, DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(file.getName());
		parent.add(fileNode);
		filesMap.put(fileNode, file);

		if (file.isDirectory()) {
			File subFiles[] = file.listFiles();
			for (File f : subFiles) {
				add(f, fileNode);
			}
		}

	}

	public static Map<DefaultMutableTreeNode, File> getFilesMap() {
		return filesMap;
	}

	public static List<File> getOpenFiles() {
		return openFiles;
	}
	
	public DefaultTreeModel getTreeModel() {
		return treeModel;
	}

}
