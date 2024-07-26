package org.mql.jcodeeditor.highlighting;

import javax.swing.text.StyledDocument;

public interface Highlighter {
	public void highlight();
	public void setTokenizer(Tokenizer tokenizer);
	public void setDocument(StyledDocument doc);
	public String getTargetExtension();

}
