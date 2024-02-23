package io.my;

import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static io.my.GridResolver.resolve;

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

    private static String readFile(String path) throws IOException {
        return Resources.toString(Resources.getResource(path), StandardCharsets.UTF_8);
    }
}
