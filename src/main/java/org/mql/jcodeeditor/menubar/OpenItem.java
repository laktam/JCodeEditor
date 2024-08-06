package org.mql.jcodeeditor.menubar;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import org.mql.jcodeeditor.JExplorer;
import org.mql.jcodeeditor.properties.PropertiesManager;

public class OpenItem extends JMenuItem {
	private static final long serialVersionUID = 1L;

	public OpenItem(JExplorer explorerTree) {
		super("Open");
		addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			int result = fileChooser.showOpenDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				explorerTree.openFileInExplorer(selectedFile.toPath());
				// store in lastOpenedFile property
				PropertiesManager.writeProperty("lastOpenedFile", selectedFile.getPath());
//				new DirectoryWatcher(selectedFile.toPath(), explorerTree).start();

			}
		}

		);
	}
}
