package Game.rules;

import Game.Cell;

import java.util.Arrays;
import java.util.function.Predicate;

public class Rule {
    private final Predicate<Cell> accountedNeighbours;
    private final Predicate<Integer> condition;
    private final int resultingState;
    private final int[] affectedState;

    public Rule(Predicate<Cell> accountedNeighbours, Predicate<Integer> condition, int resultingState, int[] affectedState) {
        this.accountedNeighbours = accountedNeighbours;
        this.condition = condition;
        this.resultingState = resultingState;
        this.affectedState = affectedState;
    }

    public void applyRule(Cell cell){
        if (Arrays.stream(affectedState).anyMatch(x -> x == cell.getValue()) && condition.test(cell.filteredCount(accountedNeighbours))) {
            cell.setNextValue(resultingState);
        }
    }
}
