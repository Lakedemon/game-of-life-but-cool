package Game.rules;

import Game.Cell;
import Game.rules.Comparators.IntComparator;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Rule {
    private final IntegerProperty affectedState = new SimpleIntegerProperty();
    private final IntegerProperty accountedNeighbours = new SimpleIntegerProperty();
    private final IntegerProperty accountedCount = new SimpleIntegerProperty();
    private final IntegerProperty resultingState = new SimpleIntegerProperty();
    private final ObjectProperty<IntComparator> comparator = new SimpleObjectProperty<>();

    public Rule(int affectedState, int accountedNeighbours, int accountedCount, int resultingState, IntComparator comparator) {
        this.affectedState.set(affectedState);
        this.accountedNeighbours.set(accountedNeighbours);
        this.accountedCount.set(accountedCount);
        this.resultingState.set(resultingState);
        this.comparator.set(comparator);
    }

    public void applyRule(Cell cell){
        if (getAffectedState() == cell.getValue() && getComparator().compare(cell.filteredCount(getAccountedNeighbours()), getAccountedCount())) {
            cell.setNextValue(getResultingState());
        }
    }

    public int getAffectedState() {
        return affectedState.get();
    }

    public IntegerProperty affectedStateProperty() {
        return affectedState;
    }

    public int getAccountedNeighbours() {
        return accountedNeighbours.get();
    }

    public IntegerProperty accountedNeighboursProperty() {
        return accountedNeighbours;
    }

    public int getAccountedCount() {
        return accountedCount.get();
    }

    public IntegerProperty accountedCountProperty() {
        return accountedCount;
    }

    public int getResultingState() {
        return resultingState.get();
    }

    public IntegerProperty resultingStateProperty() {
        return resultingState;
    }

    public IntComparator getComparator() {
        return comparator.get();
    }

    public ObjectProperty<IntComparator> comparatorProperty() {
        return comparator;
    }
}