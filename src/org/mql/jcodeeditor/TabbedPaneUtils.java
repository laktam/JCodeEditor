package org.mql.jcodeeditor;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabbedPaneUtils {
	public static void addClosableTab(JTabbedPane tabbedPane, String title) {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Content of " + title));
		tabbedPane.addTab(title, panel);
		tabbedPane.setTabComponentAt(tabbedPane.indexOfComponent(panel), createTabComponent(tabbedPane, panel, title));
	}

	private static JComponent createTabComponent(JTabbedPane tabbedPane, JPanel panel, String title) {
		JPanel tabComponent = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		tabComponent.setOpaque(false);

		JLabel label = new JLabel(title);
		tabComponent.add(label);

		JButton closeButton = new JButton("x");
		closeButton.setPreferredSize(new Dimension(17, 17));
		closeButton.setMargin(new Insets(0, 0, 0, 0));
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tabbedPane.remove(panel);
			}
		});
		tabComponent.add(closeButton);

		return tabComponent;
	}
}
