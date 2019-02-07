package fr.umlv.lexer;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Lexer<T> {

    private final static Lexer LEXER = new Lexer();

    private Pattern currentPattern;
    private Function<?, T> mapper;

    static Lexer create() {
        return LEXER;
    }

    static <U> Lexer<U> from(String patternString) {
        Objects.requireNonNull(patternString);
        return Lexer.from(Pattern.compile(patternString));
    }

    static <U> Lexer<U> from(Pattern pattern) {
        Objects.requireNonNull(pattern);

        Matcher matcher = pattern.matcher("");
        if (matcher.groupCount() != 1) {
            throw new IllegalArgumentException();
        }

        LEXER.currentPattern = pattern;
        return LEXER;
    }

    Optional<T> tryParse(String toBeParsed) {
        Objects.requireNonNull(toBeParsed);

        if (currentPattern == null) {
            return Optional.empty();
        }

        return applyMatcher.apply(toBeParsed);
    }

    private Function<String, Optional<T>> applyMatcher = (toBeParsed) -> {
        Matcher matcher = currentPattern.matcher(toBeParsed);

        if (matcher.matches()) {
            if (matcher.groupCount() > 1) {
                throw new IllegalStateException();
            }
            String group = matcher.group(1);
            return Optional.of((T) group);
        }
        return Optional.empty();
    };
//
//    public <U> Lexer<U> map(final Function<T, U> mapper) {
//        this.mapper = mapper;
//        return ;
//    }

//    public Lexer<T> map(final Function<T, T> function) {
//        this.mapper = function;
//        return this;
//    }
}
