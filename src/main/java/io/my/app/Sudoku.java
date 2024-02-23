package io.my.app;

import io.my.domain.Grid;

import java.io.IOException;

import static io.my.domain.GridResolver.resolve;
import static io.my.infra.GridReader.readFile;

public class Sudoku {

    public static void main(String[] args) throws IOException {
        var grid = Grid.parse(readFile("grid.txt"));
        var resolved = resolve(grid);
        if (resolved == null) {
            System.out.println("pas de solution !");
        } else {
            System.out.println(resolved);
        }
    }

}
