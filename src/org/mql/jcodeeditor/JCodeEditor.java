package org.mql.jcodeeditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
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
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
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

		JPanel explorer =  createExplorer();
		JPanel editor = createEditor();
		JSplitPane splitPane = new JSplitPane(SwingConstants.VERTICAL, explorer, editor);
//		splitPane.add(explorer);
//		splitPane.add(editor);
		add(splitPane);
		createMenuBar();
		pack();
		setVisible(true);

	}

	private JPanel createExplorer() {
		JPanel explorer = new JPanel(new BorderLayout());
		explorer.setPreferredSize(new Dimension(200, 600));
//		explorer.setBorder(BorderFactory.createLineBorder(Color.black));
		JPanel explorerHeader = new JPanel();
		JLabel label = new JLabel("Explorer");
		label.setBorder(new EmptyBorder(8, 10, 8, 10)); // Top, left, bottom, right padding
		explorerHeader.add(label);
//		// refresh button
//		JButton refresh = new JButton();
//		try {
//		    Image img = ImageIO.read(getClass().getResource("resources/refresh.png"));
//		    refresh.setIcon(new ImageIcon(img));
//		    refresh.addActionListener(e->{
//		    	explorerTree.reloadRoot();
//		    });
//		  } catch (Exception ex) {
//		    System.out.println(ex);
//		  }
//		explorerHeader.add(refresh);
		
		explorer.add(explorerHeader,  BorderLayout.PAGE_START);
		
		createExplorerJTree();
		JScrollPane scrollPane = new JScrollPane(explorerTree);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		explorer.add(scrollPane,  BorderLayout.CENTER);
//		explorerTree.setPreferredSize(
//				new Dimension(explorer.getPreferredSize().width - 5, explorer.getPreferredSize().height - 5));
		return explorer;
		//		add(explorer, BorderLayout.LINE_START);
	}
	
	public void createExplorerJTree() {
//		treeModel = new DefaultTreeModel(root);
//		explorerTree = new JTree(treeModel);
		explorerTree = new JExplorer();
//		
//		explorerTree.openFileInExplorer();
		explorerTree.setCellRenderer(new CustomTreeCellRenderer());
		explorerTree.addMouseListener(new DoubleClickListener(explorerTree, editor));
		
	}

	private JPanel createEditor() {
		JPanel editorPanel = new JPanel(new BorderLayout());
		editorPanel.setPreferredSize(new Dimension(600, 600));
//		editor.openFile(new File("C:\\Users\\laktam\\Desktop\\ideas.txt"));
		editorPanel.add(editor,  BorderLayout.CENTER);
		return editorPanel;
//		add(editorPanel, BorderLayout.CENTER);
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
