package org.mql.jcodeeditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.mql.jcodeeditor.menubar.menu.FileMenu;

public class JCodeEditor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private JTree explorerTree;
	private DefaultTreeModel treeModel;
	private JMenuBar menuBar;

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
				new Dimension(explorer.getPreferredSize().width - 5, explorer.getPreferredSize().height - 5));
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
		DefaultMutableTreeNode root = FilesUtiles.openFileInExplorer("D:\\Projects\\Detector\\js");
        treeModel = new DefaultTreeModel(root);
		explorerTree = new JTree(treeModel);
//		System.out.println(FilesUtiles.getFilesMap());
	}

	private void createMenuBar() {
		menuBar = new JMenuBar();
		JMenu fileMenu = new FileMenu("File",null, treeModel);

		

		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
}
