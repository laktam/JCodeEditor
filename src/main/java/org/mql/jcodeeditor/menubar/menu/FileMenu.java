package org.mql.jcodeeditor.menubar.menu;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.tree.DefaultTreeModel;

import org.mql.jcodeeditor.JEditor;
import org.mql.jcodeeditor.JExplorer;
import org.mql.jcodeeditor.menubar.items.ExitItem;
import org.mql.jcodeeditor.menubar.items.OpenItem;
import org.mql.jcodeeditor.menubar.items.SaveItem;
import org.mql.jcodeeditor.utils.DirectoryWatcher;

public class FileMenu extends JMenu {

	public FileMenu(String title, JEditor editor, DefaultTreeModel treeModel,
			JExplorer explorerTree, JFrame jFrame) {
		super(title);
		JMenuItem openItem = new OpenItem(explorerTree);
		JMenuItem saveItem = new SaveItem(editor);
		JMenuItem exitItem = new ExitItem(jFrame);

		add(openItem);
		add(saveItem);
		add(exitItem);
	}
}
