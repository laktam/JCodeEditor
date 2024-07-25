package org.mql.jcodeeditor.highlighting;

import java.util.List;

public interface Tokenizer {
	public List<Token> tokenize(String code);
	public String getTargetExtension();
}
