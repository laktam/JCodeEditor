package org.mql.jcodeeditor.plugins;

public interface Plugin {
	public void getName();
	public void getDescription();
	public void activate();
	public void deactivate();
	public boolean isActive();
}
