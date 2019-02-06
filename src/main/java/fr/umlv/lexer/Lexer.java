package fr.umlv.lexer;

import java.util.Objects;
import java.util.Optional;

class Lexer<T> {

    private final static Lexer LEXER = new Lexer();

    static Lexer create() {
        return LEXER;
    }

    Optional<T> tryParse(String toBeParsed) {
        Objects.requireNonNull(toBeParsed);

        return Optional.empty();
    }
}
