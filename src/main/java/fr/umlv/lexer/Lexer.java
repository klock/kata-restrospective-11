package fr.umlv.lexer;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Lexer<T> {

    private final static Lexer LEXER = new Lexer();

    private static Pattern CURRENT_PATTERN;

    static Lexer create() {
        return LEXER;
    }

    static Lexer<String> from(String patternString) {
        Objects.requireNonNull(patternString);
        return Lexer.from(Pattern.compile(patternString));
    }

    static Lexer<String> from(Pattern pattern) {
        Objects.requireNonNull(pattern);

        Matcher matcher = pattern.matcher("");
        if (matcher.groupCount() != 1) {
            throw new IllegalArgumentException();
        }

        CURRENT_PATTERN = pattern;
        return LEXER;
    }

    Optional<String> tryParse(String toBeParsed) {
        Objects.requireNonNull(toBeParsed);

        if (CURRENT_PATTERN == null) {
            return Optional.empty();
        }

        Matcher matcher = CURRENT_PATTERN.matcher(toBeParsed);

        if (matcher.matches()) {
            if (matcher.groupCount() > 1) {
                throw new IllegalStateException();
            }
            String group = matcher.group(1);
            return Optional.of(group);
        }

        return Optional.empty();
    }
}
