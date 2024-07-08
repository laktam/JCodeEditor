package org.mql.jcodeeditor.menubar.menu;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.tree.DefaultTreeModel;

import org.mql.jcodeeditor.JExplorer;

public class FileMenu extends JMenu {
	
	
	public FileMenu(String title, JFrame jcodeeditor, DefaultTreeModel treeModel, JExplorer explorerTree) {
		super(title);
		JMenuItem openItem = new JMenuItem("Open");
		JMenuItem saveItem = new JMenuItem("Save");
		JMenuItem exitItem = new JMenuItem("Exit");
		
		openItem.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // Allow both files and directories

			int result = fileChooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
//				JOptionPane.showMessageDialog(this, "Selected: " + selectedFile.getAbsolutePath());
				explorerTree.openFileInExplorer(selectedFile.getPath());
				
			}
		}

		);

		add(openItem);
		add(saveItem);
		add(exitItem);
	}
}
