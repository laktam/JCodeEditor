package org.mql.jcodeeditor.eventlisteners.tabbedPane;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTextArea;

public class KeyboardSavingListener extends KeyAdapter {
	private JTextArea textArea;
	private File file;
	private boolean isCtrlPressed = false;

	public KeyboardSavingListener(JTextArea textArea, File file) {
		this.textArea = textArea;
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
				f_writer.write(textArea.getText());
				f_writer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		isCtrlPressed = false;
	}

}
