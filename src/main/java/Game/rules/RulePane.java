package Game.rules;

import Game.rules.Comparators.IntComparator;
import Game.rules.Comparators.IntComparators;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class RulePane extends VBox {
    private final RuleBook associatedRuleBook;

    public RulePane(RuleBook associatedRuleBook) {
        super();
        this.associatedRuleBook = associatedRuleBook;

        for (Rule rule : associatedRuleBook.getRules()){
            addRuleHolder(rule);
        }

        initAddButton();
    }

    public RulePane() {
        super();
        this.associatedRuleBook = new RuleBook();
        initAddButton();
    }

    public void newRuleHolder(Rule rule) {
        associatedRuleBook.addRule(rule);
        addRuleHolder(rule);
    }

    private void removeRuleHolder(HBox ruleBox, RuleHolder ruleHolder) {
        this.getChildren().remove(ruleBox);
        associatedRuleBook.getRules().remove(ruleHolder.getAssociatedRule());
    }

    private void addRuleHolder(Rule rule){
        RuleHolder ruleHolder = new RuleHolder(rule);
        Button removeButton = new Button("-");
        HBox ruleBox = new HBox(ruleHolder, removeButton);
        removeButton.setOnAction(e -> removeRuleHolder(ruleBox, ruleHolder));

        this.getChildren().add(ruleBox);
    }

    private void initAddButton(){
        Button addButton = new Button("+");
        addButton.setOnAction(e -> {
            newRuleHolder(new Rule(10,10,10,10, IntComparators.EQUAL_TO));
            addButton.toFront();
        });

        this.getChildren().add(addButton);
        VBox.setVgrow(addButton, Priority.ALWAYS);
    }

    public RuleBook getAssociatedRuleBook(){
        return this.associatedRuleBook;
    }
}
