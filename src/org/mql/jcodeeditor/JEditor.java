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
import javax.swing.KeyStroke;

import org.mql.jcodeeditor.eventlisteners.tabbedPane.KeyboardSavingListener;

public class JEditor extends JTabbedPane{
	private static File focusedFile;
	private static JTextArea focusedTextArea;
	public JEditor() {
		// TODO Auto-generated constructor stub
	}

	public  void openFile( File file) {
		addClosableTab(file.getName());
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true); // Enable line wrapping
		textArea.setWrapStyleWord(true); // Wrap at word boundaries
		textArea.addKeyListener(new KeyboardSavingListener(textArea, file));
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		
		// read file
		String content = "";
		try {
			content = readFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		textArea.setText(content);

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
