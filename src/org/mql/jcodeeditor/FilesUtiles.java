package org.mql.jcodeeditor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class FilesUtiles {
	private static Map<DefaultMutableTreeNode, File> filesMap;

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
//		else {
//			DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(file.getName());
//			
//
//		}
	}

	public static Map<DefaultMutableTreeNode, File> getFilesMap() {
		return filesMap;
	}
//	public void createExplorerJTree() {
//		// nœud racine
//		DefaultMutableTreeNode framework = new DefaultMutableTreeNode("Framework");
//		// nœud interne 1
//		DefaultMutableTreeNode front = new DefaultMutableTreeNode("Front-End");
//		// nœud interne 2
//		DefaultMutableTreeNode back = new DefaultMutableTreeNode("Back-End");
//		// feuille
//		DefaultMutableTreeNode autres = new DefaultMutableTreeNode("Autres");
//
//		// Ajouter le nœud interne 1 au nœud racine
//		framework.add(front);
//		// Ajouter les feuille au nœud 1
//		DefaultMutableTreeNode angular = new DefaultMutableTreeNode("AngularJS");
//		DefaultMutableTreeNode react = new DefaultMutableTreeNode("React.js");
//		DefaultMutableTreeNode meteor = new DefaultMutableTreeNode("Meteor.js");
//		DefaultMutableTreeNode ember = new DefaultMutableTreeNode("Ember.js ");
//		front.add(angular);
//		front.add(react);
//		front.add(meteor);
//		front.add(ember);
//
//		// Ajouter le nœud interne 2 au nœud racine
//		framework.add(back);
//		// Ajouter les feuille au nœud 2
//		DefaultMutableTreeNode nodejs = new DefaultMutableTreeNode("NodeJS");
//		DefaultMutableTreeNode express = new DefaultMutableTreeNode("Express");
//		back.add(nodejs);
//		back.add(express);
//
//		// Ajouter la feuille au nœud racine
//		framework.add(autres);
//
//		explorerTree = new JTree(framework);
//	}
}
