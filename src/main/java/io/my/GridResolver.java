package io.my;

import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GridResolver {

    public static void main(String[] args) throws IOException {
        var grid = Grid.parse(readFile("grid.txt"));
        var resolved = resolve(grid);
        if (resolved == null) {
            System.out.println("pas de solution !");
        } else {
            System.out.println(resolved);
        }
    }

    private static String readFile(String path) throws IOException {
        return Resources.toString(Resources.getResource(path), StandardCharsets.UTF_8);
    }

    static Grid resolve(Grid grid) {
        if (grid == null) {
            return null;
        } else  if (grid.isComplete()) {
            return grid;
        } else {
            var emptyCell = grid.findFistEmptyCell();
            for (int value = 1; value < 10; value++) {
                var candidate = emptyCell.filledWith(value);
                if (grid.acceptCell(candidate)) {
                    var solution = resolve(grid.filledWith(candidate));
                    if (solution != null) {
                        return solution;
                    }
                }
            }
            // no solution
            return null;
        }
    }

}
