package org.mql.jcodeeditor;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultStyledDocument;

import org.mql.jcodeeditor.eventlisteners.tabbedPane.KeyboardSavingListener;
import org.mql.jcodeeditor.highlighting.Tokenizer;
import org.mql.jcodeeditor.utils.Styles;

public class JEditor extends JTabbedPane{
	private static File focusedFile;
	private static JTextArea focusedTextArea;
	private static List<JTextPane> textPanes;
	public JEditor() {
		textPanes = new Vector<JTextPane>();
	}

	public  void openFile( File file) {
		addClosableTab(file.getName());
		DefaultStyledDocument document = new DefaultStyledDocument();
		JTextPane textPane = new JTextPane(document);
		textPane.addKeyListener(new KeyboardSavingListener(textPane, file));
		textPanes.add(textPane);
		JScrollPane scrollPane = new JScrollPane(textPane);		
		// read file
		String content = "";
		try {
			content = readFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		textPane.setText(content);
		
		//here i should use the extensions if they offer a highlighter for this file type
		Styles.setDocument(document);
		Styles.setTokenizer(new Tokenizer());
		Styles.highlight();
		setComponentAt(this.getTabCount() - 1, scrollPane);
		setSelectedIndex(this.getTabCount() - 1);

	}
	
	private static String readFile(File file) throws IOException {
		StringBuilder content = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
		}
		return content.toString();
	}

	public void addClosableTab( String title) {
		JPanel panel = new JPanel();
		addTab(title, panel);
		setTabComponentAt(this.indexOfComponent(panel), createTabComponent( panel, title));
	}

	private JComponent createTabComponent(JPanel panel, String title) {
		JPanel tabComponent = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		tabComponent.setOpaque(false);
		JLabel label = new JLabel(title);
		tabComponent.add(label);
		JButton closeButton = new JButton("x");
		closeButton.setPreferredSize(new Dimension(13, 13));
		closeButton.setMargin(new Insets(0, 0, 0, 0));
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index =  JEditor.this.indexOfTabComponent(tabComponent);
				if (index != -1) {
					JEditor.this.remove(index);
					JExplorer.getOpenFiles().remove(index);
				}
			}
		});
		tabComponent.add(closeButton);
		return tabComponent;
	}
}
