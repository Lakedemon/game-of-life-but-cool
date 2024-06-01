package Game.ui.impl.rule;

import Game.rules.Comparators.IntComparators;
import Game.rules.Rule;
import Game.rules.RuleBook;
import Game.ui.GuiManager;
import Game.ui.impl.button.LabeledButtonGuiComponent;
import Game.ui.impl.shape.RectangleGuiComponent;
import Game.ui.impl.stack.HStackGuiComponent;
import Game.ui.impl.stack.VStackGuiComponent;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class RulesGuiComponent extends VStackGuiComponent {

    private final RuleBook associatedRuleBook;

    public RulesGuiComponent(RuleBook associatedRuleBook, int width, int height, int spacing) {
        super(spacing, Color.TRANSPARENT, width, height);
        this.setAlignment(Pos.TOP_CENTER);
        this.addChild(new RectangleGuiComponent(2, 40, Color.TRANSPARENT));
        this.associatedRuleBook = associatedRuleBook;

        associatedRuleBook.getRules().forEach(this::addRuleHolder);
        initAddButton();
    }

    private void addRuleHolder(Rule rule){
        HStackGuiComponent ruleBox = new HStackGuiComponent(10, GuiManager.SETTINGS_BG, 0);
        ruleBox.setAlignment(Pos.CENTER);
        RuleHolderGuiComponent ruleHolder = new RuleHolderGuiComponent(rule);
        LabeledButtonGuiComponent removeButton = new LabeledButtonGuiComponent("-", new Font(30), Color.WHITE, 40, 40, GuiManager.SETTINGS_BG, Color.INDIANRED, 5, 2, (e) -> {
            removeRuleHolder(ruleBox, ruleHolder);
        });

        ruleBox.addChild(ruleHolder);
        ruleBox.addChild(removeButton);

        this.addChild(ruleBox);
    }

    public void newRuleHolder(Rule rule) {
        associatedRuleBook.addRule(rule);
        addRuleHolder(rule);
    }

    private void removeRuleHolder(HStackGuiComponent ruleBox, RuleHolderGuiComponent ruleHolder) {
        this.removeChild(ruleBox);
        associatedRuleBook.getRules().remove(ruleHolder.getAssociatedRule());
    }

    private void initAddButton() {
        LabeledButtonGuiComponent addButton = new LabeledButtonGuiComponent("+", new Font(30), Color.WHITE, 60, 30, Color.CORNFLOWERBLUE.darker(), GuiManager.ACCENT.darker().darker().darker(), 5, 2, (e) -> {
            if (associatedRuleBook.getRules().size() >= 10) return;
            newRuleHolder(new Rule(10,10,10,10, IntComparators.EQUAL_TO));
        });

        this.addChild(addButton);
        this.addChild(new RectangleGuiComponent(4, 20, Color.TRANSPARENT));
    }

    public RuleBook getAssociatedRuleBook(){
        return this.associatedRuleBook;
    }
}
