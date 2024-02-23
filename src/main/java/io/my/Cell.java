package io.my;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Cell {

    int rowIndex;
    int columnIndex;
    String value;

    static CellBuilder at(int row, int column) {
        return new CellBuilder(row, column);
    }

    Cell filledWith(int value) {
        return filledWith(Integer.toString(value));
    }

    Cell filledWith(String value) {
        return Cell.at(rowIndex, columnIndex).withValue(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @AllArgsConstructor
    static class CellBuilder {
        int row, column;
        Cell withValue(String value) {
            return new Cell(row, column, value);
        }
    }
}
