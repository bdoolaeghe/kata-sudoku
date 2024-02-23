package io.my.domain;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

@AllArgsConstructor
class Block {

    final static int SIZE = 3;

    String[][] values;

    boolean contains(String value) {
        return Stream.of(values)
                .flatMap(Stream::of)
                .anyMatch(value::equals);
    }

    @Override
    public String toString() {
        return Stream.of(values)
                .map(row -> String.join(" ", row))
                .collect(joining( "\n" )) + "\n";
    }
}
