package org.mql.jcodeeditor;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Element;

import org.mql.jcodeeditor.eventlisteners.tabbedPane.KeyboardSavingListener;
import org.mql.jcodeeditor.highlighting.Highlighter;

public class JEditor extends JTabbedPane {
	private static final long serialVersionUID = 1L;
	private static List<JTextPane> textPanes;

	public JEditor() {
		textPanes = new Vector<JTextPane>();
	}

	public void openFile(File file) {
		addClosableTab(file.getName());
		DefaultStyledDocument document = new DefaultStyledDocument();

		JTextPane textPane = new JTextPane(document);
		textPane.addKeyListener(new KeyboardSavingListener(textPane, file));
		textPanes.add(textPane);
		// read file
		String content = "";
		try {
			content = readFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		textPane.setText(content);
		Context.addTextPane(textPane);
		String extension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
		System.out.println(extension);
		Highlighter h = Context.getHighlighter(extension);
		if (h != null) {
			h.setDocument(document);
			h.highlight();
		}

		JTextArea lineNumbersArea = new JTextArea();
		Element root = document.getDefaultRootElement();
		int c = root.getElementCount();
		System.out.println("line numbers : " + c);
		String lineNumbers = "";
		for (int i = 1; i <= c; i++) {
			lineNumbers += (i + "\n");
		}
		lineNumbersArea.setText(lineNumbers);
		lineNumbersArea.setPreferredSize(new Dimension(30, lineNumbersArea.getHeight()));
		lineNumbersArea.setEditable(false);
		lineNumbersArea.setBackground(Context.getLineNumbersColor());

		JPanel p = new JPanel(new BorderLayout());
		p.add(lineNumbersArea, BorderLayout.WEST);
		p.add(textPane, BorderLayout.CENTER);
		JScrollPane scrollPane = new JScrollPane(p);
		document.addDocumentListener(new NewLineDocumentListener(document, lineNumbersArea));

		setComponentAt(this.getTabCount() - 1, scrollPane);
		setSelectedIndex(this.getTabCount() - 1);

	}

	private static String readFile(File file) throws IOException {
        StringBuilder text = new StringBuilder();
		try (FileInputStream fis = new FileInputStream(file)) {
            int ch;
            while ((ch = fis.read()) != -1) {
            	text.append((char) ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		return text.toString();
	}

	public void addClosableTab(String title) {
		JPanel panel = new JPanel();
		addTab(title, panel);
		setTabComponentAt(this.indexOfComponent(panel), createTabComponent(panel, title));
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
				int index = JEditor.this.indexOfTabComponent(tabComponent);
				if (index != -1) {
					JEditor.this.remove(index);
					JExplorer.getOpenFiles().remove(index);
				}
			}
		});
		tabComponent.add(closeButton);
		return tabComponent;
	}

	private static class NewLineDocumentListener implements DocumentListener {
		private Document document;
		private JTextArea lineNumbersArea;
		
		public NewLineDocumentListener(Document document, JTextArea lineNumbersArea) {
			this.document = document;
			this.lineNumbersArea = lineNumbersArea;
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			Element root = document.getDefaultRootElement();
			int c = root.getElementCount();
			System.out.println("line numbers : " + c);
			String lineNumbers = "";
			for (int i = 1; i <= c; i++) {
				lineNumbers += (i + "\n");
			}
			lineNumbersArea.setText(lineNumbers);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			insertUpdate(e);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
		}

	}
}
