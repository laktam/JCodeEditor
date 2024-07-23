package org.mql.jcodeeditor.eventlisteners.tabbedPane;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class KeyboardSavingListener extends KeyAdapter {
	private JTextPane textPane;
	private File file;
	private boolean isCtrlPressed = false;

	public KeyboardSavingListener(JTextPane textArea, File file) {
		this.textPane = textArea;
		this.file = file;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		isCtrlPressed = e.isControlDown();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (isCtrlPressed && e.getKeyCode() == KeyEvent.VK_S) {
			System.out.println("Ctrl + S detected!");
			try {
				BufferedWriter f_writer = new BufferedWriter(new FileWriter(file));
				f_writer.write(textPane.getText());
				f_writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		isCtrlPressed = false;
	}

}
