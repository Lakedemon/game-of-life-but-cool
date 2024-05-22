package Game;

import java.util.Arrays;
import java.util.function.Predicate;

public class Cell {
    private final Cell[] neighbours;
    private int registeredNeighbours = 0;
    private int value;
    private int nextValue;

    public Cell(int value) {
        this.value = value;
        this.neighbours = new Cell[8];
    }

    public Cell[] getNeighbours() {
        return neighbours;
    }

    public Integer filteredCount(Predicate<Cell> predicate){
        return Math.toIntExact(Arrays.stream(neighbours).filter(predicate).count());
    }

    public Integer filteredCount(int requiredValue){
        int count = 0;
        for (Cell neighbour : neighbours) {
            if(neighbour.value == requiredValue){
                count ++;
            }
        }
        return count;
    }

    private void linkNeighbour(Cell neighbour, boolean firstRequest) {
        if(registeredNeighbours <= 8) {
            neighbours[registeredNeighbours] = neighbour;
            registeredNeighbours++;

            if(firstRequest) {
                neighbour.linkNeighbour(this, false);
            }
        }
    }

    public void linkNeighbour(Cell neighbour) {
        linkNeighbour(neighbour, true);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        setNextValue();
    }

    public void setNextValue() {
        setNextValue(this.value);
    }

    public void setNextValue(int nextValue) {
        this.nextValue = nextValue;
    }

    public void stepValue(){
        this.value = nextValue;
    }
}
