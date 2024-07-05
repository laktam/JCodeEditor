package org.mql.jcodeeditor;

import java.io.File;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class FilesUtiles {
	
	// add files and folders to explorer JTree
	public static DefaultMutableTreeNode openFileInExplorer(String path) {
		File file = new File(path);
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(file.getName());
		if (file.isDirectory()) {
			File subFiles[] = file.listFiles();
			for (File f : subFiles) {
				add(f, root);
			}
		}
		return root;
	}

	private static void add(File file, DefaultMutableTreeNode parent) {
		if (file.isDirectory()) {
			DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(file.getName());
			parent.add(fileNode);
			File subFiles[] = file.listFiles();
			for (File f : subFiles) {
				add(f, fileNode);
			}
		} else {
			DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(file.getName());
			parent.add(fileNode);

		}
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
