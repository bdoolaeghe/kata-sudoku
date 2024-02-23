package io.my;


import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;


@AllArgsConstructor
public class Grid {

    String[][] content;

    int getSize() {
        return content.length;
    }

    boolean isComplete() {
        return Arrays.stream(content)
                .flatMap(Arrays::stream)
                .filter("_"::equals)
                .findFirst()
                .isEmpty();
    }

    boolean acceptCell(Cell proposal) {
        return hasNoDuplicateValueOnRow(proposal) &&
                hasNoDuplicateValueOnColumn(proposal) &&
                hasNoDuplicateValueOnBlock(proposal);
    }

    private boolean hasNoDuplicateValueOnRow(Cell proposal) {
        return !this.getRow(proposal.rowIndex)
                .contains(proposal.value);
    }

    private boolean hasNoDuplicateValueOnColumn(Cell proposal) {
        return !this.getColumn(proposal.columnIndex)
                .contains(proposal.value);
    }

    private boolean hasNoDuplicateValueOnBlock(Cell proposal) {
        return !this.getBlockContaining(proposal)
                .contains(proposal.value);
    }

    Block getBlockContaining(Cell cell) {
        int rowIndexStart = Block.SIZE * (cell.rowIndex / Block.SIZE);
        int rowIndexEnd = rowIndexStart + Block.SIZE;
        var subRows = Arrays.copyOfRange(this.content, rowIndexStart, rowIndexEnd);
        int columnIndexStart = Block.SIZE * (cell.columnIndex / Block.SIZE);
        int columnIndexEnd = columnIndexStart + Block.SIZE;
        var subSquare = new String[3][3];
        for (int i = 0; i < subRows.length; i++) {
            subSquare[i] = Arrays.copyOfRange(subRows[i], columnIndexStart, columnIndexEnd);
        }
        return new Block(subSquare);
    }

    Grid filledWith(Cell value) {
        var copy = copy(content);
        copy[value.rowIndex][value.columnIndex] = value.value;
        return new Grid(copy);
    }

    private static String[][] copy(String[][] content) {
        var copy = new String[content.length][content.length];
        for (int i = 0; i < content.length; i++) {
            copy[i] = content[i].clone();
        }
        return copy;
    }

    Cell findEmptyCell() {
        for (int i = 0; i < getSize(); i++) {
            var cell = getRow(i).anEmptyCell();
            if (cell != null)
                return cell;
        }
        return null;
    }

    static Grid parse(String smallGrid) {
        var rows = smallGrid.split("\\n");
        int size = rows.length;
        String[][] content = new String[size][size];
        for (int i = 0; i < size; i++) {
            String row = rows[i];
            var cells = row.trim().split(" ");
            content[i] = trim(cells);
        }
        return new Grid(content);
    }

    private static String[] trim(String[] cells) {
        return Stream.of(cells)
                .map(String::trim)
                .filter(not(String::isBlank))
                .toArray(String[]::new);
    }

    Row getRow(int index) {
        return new Row(index, content[index]);
    }

    Column getColumn(int index) {
        List<String> values = Stream.of(content)
                .map(row -> row[index])
                .toList();
        return new Column(index, values);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getSize(); i++) {
            sb.append(getRow(i));
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Grid grid = (Grid) o;
        return grid.toString().equals(this.toString());
    }

}
