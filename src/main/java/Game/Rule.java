package Game;

import java.util.Arrays;
import java.util.function.Predicate;

public class Rule {
    Predicate<Cell> accountedNeighbours;
    Predicate<Integer> condition;
    int ResultingState;
    int[] AffectedState;

    public Rule(Predicate<Cell> accountedNeighbours, Predicate<Integer> condition, int ResultingState, int[] AffectedState) {
        this.accountedNeighbours = accountedNeighbours;
        this.condition = condition;
        this.ResultingState = ResultingState;
        this.AffectedState = AffectedState;
    }

    public void ApplyRule(Cell cell){
        if (Arrays.stream(AffectedState).anyMatch(x -> x == cell.getValue()) && condition.test(cell.filteredCount(accountedNeighbours))) {
            cell.setNextValue(ResultingState);
        }
    }
}
