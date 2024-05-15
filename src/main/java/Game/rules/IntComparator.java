package Game.rules;

public abstract class IntComparator {
    private final String symbol;

    public IntComparator(String symbol){
        this.symbol = symbol;
    }

    abstract boolean compare(int left, int right);

    public String getSymbol() {
        return symbol;
    }
}