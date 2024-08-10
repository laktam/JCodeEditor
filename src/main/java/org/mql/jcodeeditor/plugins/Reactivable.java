package org.mql.jcodeeditor.plugins;

public interface Reactivable {
	public void activate();
	public void deactivate();
	public boolean isActive();
}
