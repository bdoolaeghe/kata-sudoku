package io.my;

public class GridResolver {

    static Grid resolve(Grid challenge) {
        if (challenge == null) {
            return null;
        } else  if (challenge.isComplete()) {
            return challenge;
        } else {
            Grid solution = null;
            var nextEmptyCell = challenge.findFistEmptyCell();
            while (solution == null && !nextEmptyCell.allValuesTested()) {
                var cellCandidate = nextEmptyCell.popNextUntestedValue();
                if (challenge.acceptCell(cellCandidate)) {
                    solution = resolve(challenge.filledWith(cellCandidate));
                }
            }
            return solution;
        }
    }

}
