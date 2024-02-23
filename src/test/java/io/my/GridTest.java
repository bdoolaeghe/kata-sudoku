package io.my;


import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GridTest {

    @Nested
    class ParseGrid {

        @Test
        public void should_parse_grid() {
            var grid = Grid.parse("""
                1 2 _
                4 5 6
                7 _ 9
                """);
            assertThat(grid.toString()).isEqualTo(
                """
                1 2 _
                4 5 6
                7 _ 9
                """);
        }

        @Test
        public void should_be_tolerant_to_useless_whitespaces_in_grid() {
            var gridPollutedWithWhiteSpaces = Grid.parse("""
                1    2 _
                4 5 6 
                   7 _ 9
                """);
            assertThat(gridPollutedWithWhiteSpaces.toString()).isEqualTo(
                """
                1 2 _
                4 5 6
                7 _ 9
                """);
        }

    }

    @Nested
    class Column {

        @Test
        void should_get_column() {
            var grid = Grid.parse("""
                1 2 _
                4 5 6
                7 _ 9
                """);
            assertThat(grid.getColumn(1).toString()).isEqualTo(
                    """
                    2 5 _
                    """);
        }
    }

    @Nested
    class Filling {

        @Test
        void isFilled_should_detect_empty_cell() {
            assertThat(Grid.parse("""
                1 2 _
                4 5 6
                7 _ 9
                """).isComplete())
                    .isFalse();
            assertThat(Grid.parse("""
                1 2 3
                4 5 6
                7 8 9
                """).isComplete())
                    .isTrue();
        }

        @Test
        void should_fill_grid() {
            var grid = Grid.parse("""
                1 2 _
                4 5 6
                7 _ 9
                """);

            assertThat(grid.filledWith(Cell.at(2, 1).withValue("8"))).isEqualTo(
                    Grid.parse("""
                1 2 _
                4 5 6
                7 8 9
                """)
            );

            assertThat(grid).isEqualTo(
                    Grid.parse("""
                1 2 _
                4 5 6
                7 _ 9
                """)
            );
        }

    }

    @Nested
    class FindBlock {

        @Test
        void getBlock_should_find_block_in_the_middle() {
            // Given
            var grid = Grid.parse("""
                    1 2 _ 4 5 6 7 8 9
                    4 5 6 7 8 9 1 2 3
                    7 _ 9 _ _ _ _ _ _
                    4 5 6 7 8 9 1 2 3
                    1 2 _ 4 5 6 7 8 9
                    7 _ 9 _ _ _ _ _ _
                    7 _ 9 _ _ _ _ _ _
                    4 5 6 7 8 9 1 2 3
                    1 2 _ 4 5 6 7 8 9
                    """);

            // When
            var foundBlock = grid.getBlockContaining(Cell.at(5, 5).withValue("any"));

            // Then
            assertThat(foundBlock.toString()).isEqualTo(
                    """
                            7 8 9
                            4 5 6
                            _ _ _
                            """
            );
        }

        @Test
        void getBlock_should_find_first_block() {
            // Given
            var grid = Grid.parse("""
                    1 2 _ 4 5 6 7 8 9
                    4 5 6 7 8 9 1 2 3
                    7 _ 9 _ _ _ _ _ _
                    4 5 6 7 8 9 1 2 3
                    1 2 _ 4 5 6 7 8 9
                    7 _ 9 _ _ _ _ _ _
                    7 _ 9 _ _ _ _ _ _
                    4 5 6 7 8 9 1 2 3
                    1 2 _ 4 5 6 7 8 9
                    """);

            // When
            var foundBlock = grid.getBlockContaining(Cell.at(0, 0).withValue("any"));

            // Then
            assertThat(foundBlock.toString()).isEqualTo(
                    """
                            1 2 _
                            4 5 6
                            7 _ 9
                            """
            );
        }

        @Test
        void getBlock_should_find_last_block() {
            // Given
            var grid = Grid.parse("""
                    1 2 _ 4 5 6 7 8 9
                    4 5 6 7 8 9 1 2 3
                    7 _ 9 _ _ _ _ _ _
                    4 5 6 7 8 9 1 2 3
                    1 2 _ 4 5 6 7 8 9
                    7 _ 9 _ _ _ _ _ _
                    7 _ 9 _ _ _ _ _ _
                    4 5 6 7 8 9 1 2 3
                    1 2 _ 4 5 6 7 8 9
                    """);

            // When
            var foundBlock = grid.getBlockContaining(Cell.at(8, 8).withValue("any"));

            // Then
            assertThat(foundBlock.toString()).isEqualTo(
               """
               _ _ _
               1 2 3
               7 8 9
               """
            );
        }

    }

    @Nested
    class CheckSolution {

        @Test
        void should_accept_valid_solution() {
            // Given
            var grid = Grid.parse("""
                    1 2 _ 4 5 6 7 8 0
                    4 5 6 7 8 9 1 2 3
                    7 _ 9 _ _ _ _ _ _
                    4 5 6 7 8 9 1 2 3
                    1 2 _ 4 5 6 7 8 9
                    7 _ 9 _ _ _ _ _ _
                    7 _ 9 _ _ _ _ _ _
                    4 5 6 7 8 9 1 2 3
                    1 2 _ 4 5 6 7 8 9
                    """);

            // when/then
            assertThat(grid.acceptCell(Cell.at(0, 2).withValue("3"))).isTrue();
        }

        @Test
        void should_refuse_duplicate_on_row() {
            // Given
            var grid = Grid.parse("""
                    1 2 _ 4 5 6 7 8 0
                    4 5 6 7 8 9 1 2 3
                    7 _ 9 _ _ _ _ _ _
                    4 5 6 7 8 9 1 2 3
                    1 2 _ 4 5 6 7 8 9
                    7 _ 9 _ _ _ _ _ _
                    7 _ 9 _ _ _ _ _ _
                    4 5 6 7 8 9 1 2 3
                    1 2 _ 4 5 6 7 8 9
                    """);

            // when/then
            assertThat(grid.acceptCell(Cell.at(0, 2).withValue("1"))).isFalse();
        }

        @Test
        void should_refuse_duplicate_on_column() {
            // Given
            var grid = Grid.parse("""
                    1 2 _ 4 5 6 7 8 0
                    4 5 6 7 8 9 1 2 3
                    7 _ 9 _ _ _ _ _ _
                    4 5 6 7 8 9 1 2 3
                    1 2 _ 4 5 6 7 8 9
                    7 _ 9 _ _ _ _ _ _
                    7 _ 9 _ _ _ _ _ _
                    4 5 6 7 8 9 1 2 3
                    1 2 _ 4 5 6 7 8 9
                    """);

            // when/then
            assertThat(grid.acceptCell(Cell.at(0, 2).withValue("9"))).isFalse();
        }

        @Test
        void should_refuse_duplicate_in_block() {
            // Given
            var grid = Grid.parse("""
                    1 2 _ 4 5 6 7 8 0
                    4 5 6 7 8 9 1 2 3
                    7 _ 9 _ _ _ _ _ _
                    4 5 6 7 8 9 1 2 3
                    1 2 _ 4 5 6 7 8 9
                    7 _ 9 _ _ _ _ _ _
                    7 _ 9 _ _ _ _ _ _
                    4 5 6 7 8 9 1 2 3
                    1 2 _ 4 5 6 7 8 9
                    """);

            // when/then
            assertThat(grid.acceptCell(Cell.at(2, 1).withValue("4"))).isFalse();
        }

    }

    @Nested
    class AnEmptyCell {
        @Test
        void should_find_an_empty_cell() {
            assertThat(Grid.parse("""
                1 2 3
                4 5 6
                7 _ 9
                """).findEmptyCell())
                    .isEqualTo(Cell.at(2, 1).withValue("_"));
        }

        @Test
        void should_return_null_when_all_cells_are_filled() {
            assertThat(Grid.parse("""
                1 2 3
                4 5 6
                7 8 9
                """).findEmptyCell())
                    .isNull();
        }

    }

}