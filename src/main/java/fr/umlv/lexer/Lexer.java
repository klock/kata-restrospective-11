package fr.umlv.lexer;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Lexer<T> {

    private final static Lexer LEXER = new Lexer();

    private Pattern currentPattern;

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

        LEXER.currentPattern = pattern;
        return LEXER;
    }

    Optional<String> tryParse(String toBeParsed) {
        Objects.requireNonNull(toBeParsed);

        if (currentPattern == null) {
            return Optional.empty();
        }

        Matcher matcher = currentPattern.matcher(toBeParsed);

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
