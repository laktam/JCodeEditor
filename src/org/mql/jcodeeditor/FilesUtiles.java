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

public class FilesUtiles {
	private static Map<DefaultMutableTreeNode, File> filesMap;
	private static List<File> openFiles = new Vector<File>();

	// add files and folders to explorer JTree
	public static DefaultMutableTreeNode openFileInExplorer(String path) {
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
		return root;
	}

	private static void add(File file, DefaultMutableTreeNode parent) {
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


	 

}
