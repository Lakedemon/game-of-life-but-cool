package Game;

import java.util.ArrayList;
import java.util.List;

public class RuleBook {
    private final List<Rule> rules = new ArrayList<>();

    public void addRule(Rule rule) {
        rules.add(rule);
    }

    public void changeRuleOrder(Rule rule, int newPosition) {
        rules.remove(rule);
        rules.add(newPosition, rule);
    }

    public void applyRules(Cell cell){
        for (Rule rule : rules) {
            rule.ApplyRule(cell);
        }
    }
}
