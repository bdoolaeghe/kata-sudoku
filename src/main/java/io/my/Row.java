package io.my;

import io.my.Cell.EmptyCell;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

@AllArgsConstructor
public class Row {

    int rowIndex;
    String[] content;

    @Override
    public String toString() {
        return toCells().stream()
                .map(Cell::toString)
                .collect(joining( " " ));
    }

    private List<Cell> toCells() {
        AtomicInteger index = new AtomicInteger(0);
        return Stream.of(content)
                .map(c -> Cell
                          .at(rowIndex, index.getAndIncrement())
                          .containing(c))
                .toList();
    }

    boolean contains(String value) {
        return Arrays.asList(content).contains(value);
    }

    EmptyCell findFirstEmptyCell() {
        for (int i = 0; i < content.length; i++) {
            var cell = Cell.at(rowIndex, i).containing(content[i]);
            if (cell.isEmpty())
                return (EmptyCell) cell;
        }
        return null;
    }
}
