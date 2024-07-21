package org.mql.jcodeeditor.highlighting;

import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentEvent.EventType;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import org.mql.jcodeeditor.utils.Styles;

public class DocumentChangeListener implements DocumentListener {

	@Override
	public void insertUpdate(DocumentEvent e) {
		// defer the style application until after the current event dispatch is
		// complete.
		SwingUtilities.invokeLater(() -> {
			Styles.applyStyles();
		});
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// defer the style application until after the current event dispatch is
		// complete.
		SwingUtilities.invokeLater(() -> {
			Styles.applyStyles();
		});
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}

}
