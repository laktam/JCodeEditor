package org.mql.jcodeeditor;

import java.awt.Color;

import javax.swing.UIManager;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.formdev.flatlaf.FlatLightLaf;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		System.out.println("Hello, World!");
//		FlatLightLaf.setup();
//		// configuration
//		UIManager.put("TabbedPane.selectedBackground", Color.white);
//		new JCodeEditor();

	}

	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("Goodbye, World!");
	}

}