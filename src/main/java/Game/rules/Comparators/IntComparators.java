package Game.rules.Comparators;

import java.util.*;

public class IntComparators {
    public final static IntComparator GREATER_THAN = new IntComparator(">") {
        @Override
        public boolean compare(int left, int right) {
            return left > right;
        }
    };

    public final static IntComparator GREATER_THAN_OR_EQUAL_TO = new IntComparator(">=") {
        @Override
        public boolean compare(int left, int right) {
            return left >= right;
        }
    };

    public final static IntComparator LESS_THAN = new IntComparator("<") {
        @Override
        public boolean compare(int left, int right) {
            return left < right;
        }
    };

    public final static IntComparator LESS_THAN_OR_EQUAL_TO = new IntComparator("<=") {
        @Override
        public boolean compare(int left, int right) {
            return left <= right;
        }
    };

    public final static IntComparator EQUAL_TO = new IntComparator("=") {
        @Override
        public boolean compare(int left, int right) {
            return left == right;
        }
    };

    public final static IntComparator NOT_EQUAL_TO = new IntComparator("!=") {
        @Override
        public boolean compare(int left, int right) {
            return left != right;
        }
    };

    public final static Map<String, IntComparator> comparatorMap = Map.ofEntries(
            Map.entry(">", GREATER_THAN),
            Map.entry(">=", GREATER_THAN_OR_EQUAL_TO),
            Map.entry("<", LESS_THAN),
            Map.entry("<=", LESS_THAN_OR_EQUAL_TO),
            Map.entry("=", EQUAL_TO),
            Map.entry("!=", NOT_EQUAL_TO)
    );

    public final static Set<String> availableComparators = comparatorMap.keySet();
}