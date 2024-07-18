package org.mql.jcodeeditor.highlighting;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
	private static final String KEYWORDS = "\\b(abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|default|do|double|else|enum|extends|final|finally|float|for|goto|if|implements|import|instanceof|int|interface|long|native|new|null|package|private|protected|public|return|short|static|strictfp|super|switch|synchronized|this|throw|throws|transient|try|void|volatile|while)\\b";
	private static final Pattern TOKEN_PATTERN = Pattern.compile(
			"\\b(abstract|assert|boolean|break|byte|case|catch|char|class|const|continue|default|do|double|else|enum|extends|final|finally|float|for|goto|if|implements|import|instanceof|int|interface|long|native|new|null|package|private|protected|public|return|short|static|strictfp|super|switch|synchronized|this|throw|throws|transient|try|void|volatile|while)\\b|"
					+ // keywords
					"[a-zA-Z_]\\w*|" + // identifiers
					"\"[^\"]*\"|" + // strings
					"\\d+|" + // numbers
					"//.*|" + // comments
					"[+\\-*/=<>]|" + // operators
					"\\s+" // whitespace
	);

	public static List<Token> tokenize(String code) {
		List<Token> tokens = new ArrayList<>();
		Matcher matcher = TOKEN_PATTERN.matcher(code);

		while (matcher.find()) {
			String value = matcher.group();
			TokenType type = determineTokenType(value);
			tokens.add(new Token(type, value, matcher.start(), value.length()));
		}

		return tokens;
	}

	private static TokenType determineTokenType(String value) {
		// order is important !!!
		if (isKeyword(value))
			return TokenType.KEYWORD;
		if (value.matches("\".*\""))
			return TokenType.STRING;
		if (value.matches("\\d+"))
			return TokenType.NUMBER;
		if (value.startsWith("//"))
			return TokenType.COMMENT;
		if (value.matches("[+\\-*/=<>]"))
			return TokenType.OPERATOR;
		if (value.trim().isEmpty())
			return TokenType.WHITESPACE;
		return TokenType.IDENTIFIER;
	}

	private static boolean isKeyword(String value) {
//		Pattern keywordsPattern = Pattern.compile(KEYWORDS);
//		Matcher matcher = keywordsPattern.matcher(value);
		return value.matches(KEYWORDS);
	}
}