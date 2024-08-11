package org.mql.jcodeeditor.plugins;

public interface Plugin {
	public String getName();
	public String getDescription();
	public void activate();
	public void deactivate();
	public boolean isActive();
}
