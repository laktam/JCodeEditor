package org.mql.jcodeeditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.mql.jcodeeditor.eventlisteners.explorerJTree.DoubleClickListener;
import org.mql.jcodeeditor.eventlisteners.tabbedPane.KeyboardSavingListener;
import org.mql.jcodeeditor.menubar.menu.FileMenu;

public class JCodeEditor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JEditor editor;
	private JExplorer explorerTree;
	private DefaultTreeModel treeModel;
	private JMenuBar menuBar;

	public JCodeEditor() {
		setTitle("JCodeEditor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		editor = new JEditor();

		createExplorer();
		createEditor();
		createMenuBar();
		pack();
		setVisible(true);

	}

	private void createExplorer() {
		JPanel explorer = new JPanel(new BorderLayout());
		explorer.setPreferredSize(new Dimension(800, 600));
//		explorer.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel label = new JLabel("Explorer");
		label.setBorder(new EmptyBorder(8, 10, 8, 10)); // Top, left, bottom, right padding
		explorer.add(label,  BorderLayout.PAGE_START);
		
		createExplorerJTree();
		JScrollPane scrollPane = new JScrollPane(explorerTree);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		explorer.add(scrollPane,  BorderLayout.CENTER);
//		explorerTree.setPreferredSize(
//				new Dimension(explorer.getPreferredSize().width - 5, explorer.getPreferredSize().height - 5));
		add(explorer, BorderLayout.LINE_START);
	}
	
	public void createExplorerJTree() {
//		DefaultMutableTreeNode root = FilesUtiles.openFileInExplorer("D:\\Projects\\Detector\\js");
//		treeModel = new DefaultTreeModel(root);
//		explorerTree = new JTree(treeModel);
		explorerTree = new JExplorer();
//		
//		explorerTree.openFileInExplorer();
		explorerTree.setCellRenderer(new CustomTreeCellRenderer());
		explorerTree.addMouseListener(new DoubleClickListener(explorerTree, editor));
		
	}

	private void createEditor() {
		JPanel editorPanel = new JPanel(new BorderLayout());
		editorPanel.setPreferredSize(new Dimension(100, 600));
//		editor.openFile(new File("C:\\Users\\laktam\\Desktop\\ideas.txt"));
		editorPanel.add(editor,  BorderLayout.CENTER);
		add(editorPanel, BorderLayout.CENTER);
	}

	

	private void createMenuBar() {
		menuBar = new JMenuBar();
		JMenu fileMenu = new FileMenu("File",null, treeModel,explorerTree);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
	}

	public JEditor getTabbedPane() {
		return editor;
	}
}
