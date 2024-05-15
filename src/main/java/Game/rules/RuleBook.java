package Game.rules;

import Game.Cell;

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

    public Rule getRule(int position) {
        return rules.get(position);
    }

    public void applyRules(Cell cell){
        for (Rule rule : rules) {
            rule.applyRule(cell);
        }
    }
}
