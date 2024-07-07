package org.mql.jcodeeditor;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class TabbedPaneUtils {

	public static void openFile(JTabbedPane pane, File file) {
		addClosableTab(pane, file.getName());
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true); // Enable line wrapping
		textArea.setWrapStyleWord(true); // Wrap at word boundaries
		textArea.addKeyListener(new KeyboardSavingListener(textArea, file));
		
//		InputMap inputMap = textArea.getInputMap(JComponent.WHEN_FOCUSED);
//	    ActionMap actionMap = textArea.getActionMap();
//
//	    KeyStroke ctrlSKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK);
//	    AbstractAction saveAction = new AbstractAction() {
//	      @Override
//	      public void actionPerformed(ActionEvent e) {
//	        System.out.println("Ctrl + S detected!");
//	        // Perform your save action here.
//	      }
//	    };
//	    inputMap.put(ctrlSKeyStroke, "saveAction");
//	    actionMap.put("saveAction", saveAction);
		
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		
		// read file
		String content = "";
		try {
			content = readFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		textArea.setText(content);

		pane.setComponentAt(pane.getTabCount() - 1, scrollPane);
		pane.setSelectedIndex(pane.getTabCount() - 1);

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

	public static void addClosableTab(JTabbedPane tabbedPane, String title) {
		JPanel panel = new JPanel();
		tabbedPane.addTab(title, panel);
		tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(panel), createTabComponent(tabbedPane, panel, title));
	}

	private static JComponent createTabComponent(JTabbedPane tabbedPane, JPanel panel, String title) {
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
				int index = tabbedPane.indexOfTabComponent(tabComponent);
				if (index != -1) {
					tabbedPane.remove(index);
				}
			}
		});
		tabComponent.add(closeButton);

		return tabComponent;
	}
}
