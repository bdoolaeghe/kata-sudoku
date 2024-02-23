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
            for (int i = 1; i < 10; i++) {
                var candidateValue = emptyCell.filledWith(Integer.toString(i));
                if (grid.acceptCell(candidateValue)) {
                    var subSolution = resolve(grid.filledWith(candidateValue));
                    if (subSolution != null) {
                        return subSolution;
                    }
                }
            }
            // no solution
            return null;
        }
    }

}
