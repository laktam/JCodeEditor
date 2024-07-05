package org.mql.jcodeeditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.DefaultMutableTreeNode;

public class JCodeEditor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private JTree explorerTree;

	public JCodeEditor() {
		setTitle("JCodeEditor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createExplorer();
		createEditor();
		createMenuBar();
		pack();
		setVisible(true);

	}

	private void createExplorer() {
		JPanel explorer = new JPanel();
		explorer.setPreferredSize(new Dimension(200, 600));
		explorer.setBorder(BorderFactory.createLineBorder(Color.black));
		createExplorerJTree();
		explorer.add(explorerTree);
		explorerTree.setPreferredSize(
				new Dimension(explorer.getPreferredSize().width - 5, explorer.getPreferredSize().height - 5)
				);
		add(explorer, BorderLayout.LINE_START);
	}

	private void createEditor() {
		JPanel editor = new JPanel();
		editor.setPreferredSize(new Dimension(800, 600));
		tabbedPane = new JTabbedPane();

		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		tabbedPane.setPreferredSize(editor.getPreferredSize());// new Dimension(400,400)
		tabbedPane.add("main", p1);
		tabbedPane.add("visit", p2);
		tabbedPane.add("help", p3);

		editor.add(tabbedPane);
		add(editor, BorderLayout.CENTER);
	}

	public void createExplorerJTree() {
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
		
		explorerTree = new JTree(FilesUtiles.openFileInExplorer("D:\\Projects\\Detector\\js\\.gitignore"));
	}

	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");

		JMenuItem openItem = new JMenuItem("Open");
		JMenuItem saveItem = new JMenuItem("Save");
		JMenuItem exitItem = new JMenuItem("Exit");

//        openItem.addActionListener(e -> openFile());
//        saveItem.addActionListener(e -> saveFile());
//        exitItem.addActionListener(e -> System.exit(0));

		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);

		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
}
