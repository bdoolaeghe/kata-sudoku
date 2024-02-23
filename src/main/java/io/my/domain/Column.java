package io.my.domain;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
class Column {

    int columnIndex;
    List<String> values;

    boolean contains(String value) {
        return values.contains(value);
    }

    @Override
    public String toString() {
        return String.join(" ", values) + "\n";
    }
}
