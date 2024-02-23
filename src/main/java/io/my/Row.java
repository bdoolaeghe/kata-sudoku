package io.my;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Row {

    int rowIndex;
    String[] values;

    @Override
    public String toString() {
        return toCells().stream()
                .map(Cell::toString)
                .collect(Collectors.joining( " " ));
    }

    private List<Cell> toCells() {
        List<Cell> cells = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            var value = values[i];
            cells.add(new Cell(rowIndex, i, value));
        }
        return cells;
    }

    boolean contains(String value) {
        return Arrays.asList(values).contains(value);
    }

    Cell anEmptyCell() {
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals("_")) {
                return Cell.at(rowIndex, i).withValue(values[i]);
            }
        }
        return null;
    }
}
