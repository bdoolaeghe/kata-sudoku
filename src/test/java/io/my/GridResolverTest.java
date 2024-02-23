package io.my;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GridResolverTest {

    @Test
    void should_resolve_an_already_resolved_grid() {
        var fullGrid = """
                5 3 9 8 7 6 4 1 2
                7 2 8 3 1 4 9 6 5
                6 4 1 2 9 5 7 3 8
                4 6 2 5 3 9 8 7 1
                3 8 5 7 2 1 6 4 9
                1 9 7 4 6 8 2 5 3
                2 5 6 1 8 7 3 9 4
                9 1 3 6 4 2 5 8 7
                8 7 4 9 5 3 1 2 6
                """;

        var solution = GridResolver.resolve(Grid.parse(fullGrid));

        assertThat(solution.toString()).isEqualTo(
                fullGrid
        );
    }

    @Test
    void should_resolve_simple_grid() {
        var simpleGrid = """
                5 3 9 8 7 6 4 1 2
                7 2 8 3 1 4 9 6 5
                6 4 1 2 9 5 7 3 8
                4 6 2 5 3 9 _ 7 1
                3 8 5 7 2 1 6 4 9
                1 9 7 4 6 8 2 5 3
                2 5 6 1 8 7 3 9 4
                9 1 3 6 4 2 5 8 7
                8 7 4 9 5 3 1 2 6
                """;
        var solution = GridResolver.resolve(Grid.parse(simpleGrid));

        assertThat(solution.toString()).isEqualTo(
                """
                5 3 9 8 7 6 4 1 2
                7 2 8 3 1 4 9 6 5
                6 4 1 2 9 5 7 3 8
                4 6 2 5 3 9 8 7 1
                3 8 5 7 2 1 6 4 9
                1 9 7 4 6 8 2 5 3
                2 5 6 1 8 7 3 9 4
                9 1 3 6 4 2 5 8 7
                8 7 4 9 5 3 1 2 6
                """
        );
    }

    @Test
    void should_resolve_hard_grid() {
        var hardGrid = """
            _ 3 _ 8 7 6 _ 1 2
            _ 2 _ 3 _ _ _ _ 5
            6 _ _ 2 9 _ 7 _ 8
            _ 6 2 _ 3 _ 8 7 _
            _ _ 5 7 _ 1 6 _ 9
            _ _ 7 _ 6 _ 2 _ 3
            2 _ 6 _ 8 _ _ 9 4
            _ 1 _ 6 _ 2 5 _ 7
            8 7 _ _ 5 _ _ 2 6
            """;

        var solution = GridResolver.resolve(Grid.parse(hardGrid));

        assertThat(solution.toString()).isEqualTo(
                """
                5 3 9 8 7 6 4 1 2
                7 2 8 3 1 4 9 6 5
                6 4 1 2 9 5 7 3 8
                4 6 2 5 3 9 8 7 1
                3 8 5 7 2 1 6 4 9
                1 9 7 4 6 8 2 5 3
                2 5 6 1 8 7 3 9 4
                9 1 3 6 4 2 5 8 7
                8 7 4 9 5 3 1 2 6
                """
        );
    }

    @Test
    void should_resolve_empty_grid() {
        var hardGrid = """
            _ _ _ _ _ _ _ _ _
            _ _ _ _ _ _ _ _ _
            _ _ _ _ _ _ _ _ _
            _ _ _ _ _ _ _ _ _
            _ _ _ _ _ _ _ _ _
            _ _ _ _ _ _ _ _ _
            _ _ _ _ _ _ _ _ _
            _ _ _ _ _ _ _ _ _
            _ _ _ _ _ _ _ _ _
            """;

        var solution = GridResolver.resolve(Grid.parse(hardGrid));

        assertThat(solution.toString()).isEqualTo(
                """
                1 2 3 4 5 6 7 8 9
                4 5 6 7 8 9 1 2 3
                7 8 9 1 2 3 4 5 6
                2 1 4 3 6 5 8 9 7
                3 6 5 8 9 7 2 1 4
                8 9 7 2 1 4 3 6 5
                5 3 1 6 4 2 9 7 8
                6 4 2 9 7 8 5 3 1
                9 7 8 5 3 1 6 4 2
                """
        );
    }

    @Test
    void should_support_grid_without_solution() {
        var impossibleGrid = """
            1 2 9 4 5 6 7 8 _
            4 5 6 7 8 9 1 2 3
            7 8 9 1 2 3 4 5 6
            2 1 4 3 6 5 8 9 7
            3 6 5 8 9 7 2 1 4
            8 9 7 2 1 4 3 6 5
            5 3 1 6 4 2 9 7 8
            6 4 2 9 7 8 5 3 1
            9 7 8 5 3 1 6 4 2
            """;

        var solution = GridResolver.resolve(Grid.parse(impossibleGrid));

        assertThat(solution).isNull();
    }
}