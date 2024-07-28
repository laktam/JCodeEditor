package org.mql.jcodeeditor.documentHandlers;

import java.io.File;
import java.util.List;

import javax.swing.text.StyledDocument;

/*
 * inmplement this interface if the plugin need to access documents 
 * i need to have a listener to recall the setter when files change ??????????????????????
 */
public interface OpenDocumentsHandler {
	public void setDocuments(List<StyledDocument> openDocuments);
	public void execute();
}
