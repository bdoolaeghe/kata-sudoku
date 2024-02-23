package io.my.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
abstract class Cell {

    protected int rowIndex;
    protected int columnIndex;

    Cell(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    abstract boolean isEmpty();

    static EmptyCell at(int row, int column) {
        return new EmptyCell(row, column);
    }

    @EqualsAndHashCode(callSuper = true)
    static class EmptyCell extends Cell {

        Integer lastTestedValue = 0;

        EmptyCell(int rowIndex, int columnIndex) {
            super(rowIndex, columnIndex);
        }

        ValuedCell popNextUntestedValue() {
            return this.filledWith(++lastTestedValue);
        }

        boolean allValuesTested() {
            return lastTestedValue >= 9;
        }

        @Override
        public String toString() {
            return "_";
        }

        Cell containing(String content) {
            if ("_".equals(content)) {
                return this;
            } else {
                return filledWith(content);
            }
        }

        ValuedCell filledWith(int value) {
            return filledWith(Integer.toString(value));
        }

        private ValuedCell filledWith(String value) {
            if ("_".equals(value))
                throw new IllegalArgumentException("Expecting value to fill, but got: " + value);
            return new ValuedCell(this.rowIndex, this.columnIndex, value);
        }

        @Override
        boolean isEmpty() {
            return true;
        }
    }

    @Getter
    @EqualsAndHashCode(callSuper = true)
    static class ValuedCell extends Cell {
        String value;
        ValuedCell(int rowIndex, int columnIndex, String value) {
            super(rowIndex, columnIndex);
            this.value = value;
        }
        @Override
        public String toString() {
            return value;
        }

        @Override
        boolean isEmpty() {
            return false;
        }
    }

}
