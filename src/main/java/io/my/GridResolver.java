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

    static Grid resolve(Grid challenge) {
        if (challenge == null) {
            return null;
        } else  if (challenge.isComplete()) {
            return challenge;
        } else {
            var emptyCell = challenge.findFistEmptyCell();
            Grid solution = null;
            int valueCandidate = 1;
            while (solution == null && valueCandidate < 10) {
                var cellCandidate = emptyCell.filledWith(valueCandidate);
                if (challenge.acceptCell(cellCandidate)) {
                    solution = resolve(challenge.filledWith(cellCandidate));
                }
                valueCandidate++;
            }
            return solution;
        }
    }

}
