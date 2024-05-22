package Game.rules;

import Game.MyComboBox;
import Game.rules.Comparators.IntComparators;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.FlowPane;

public class RuleHolder extends FlowPane {
    private final Rule associatedRule;

    public RuleHolder(Rule associatedRule) {
        super();
        this.associatedRule = associatedRule;

        // Creating UI controls
        Label label1 = new Label("If cell of value:");
        Label label2 = new Label("has:");
        Label label3 = new Label("neighbours of value:");
        Label label4 = new Label("then set the cell value to:");

        Spinner<Integer> affectedState = createSpinner(0, 100, associatedRule.getAffectedState());
        Spinner<Integer> accountedCount = createSpinner(0, 8, associatedRule.getAccountedCount());
        Spinner<Integer> accountedNeighbours = createSpinner(0, 100, associatedRule.getAccountedNeighbours());
        Spinner<Integer> resultingState = createSpinner(0, 100, associatedRule.getResultingState());
        MyComboBox<String> comparator = createComboBox();
        comparator.setValue(associatedRule.getComparator().getSymbol());

        associatedRule.affectedStateProperty().bind(affectedState.valueProperty());
        associatedRule.accountedCountProperty().bind(accountedCount.valueProperty());
        associatedRule.accountedNeighboursProperty().bind(accountedNeighbours.valueProperty());
        associatedRule.resultingStateProperty().bind(resultingState.valueProperty());
        associatedRule.comparatorProperty().bind(comparator.valueProperty().map(IntComparators.comparatorMap::get));

        // Layout setup
        getChildren().addAll(label1, affectedState, label2, comparator, accountedCount, label3, accountedNeighbours, label4, resultingState);
        setStyle();
    }

    private Spinner<Integer> createSpinner(int min, int max, int initialValue) {
        Spinner<Integer> spinner = new Spinner<>(min, max, initialValue);
        int maxWidth = 60;
        spinner.setMaxWidth(maxWidth);
        return spinner;
    }

    private MyComboBox<String> createComboBox() {
        MyComboBox<String> comboBox = new MyComboBox<>();
        comboBox.getItems().addAll(IntComparators.availableComparators);
        double width = comboBox.getItems().stream()
                .mapToDouble(item -> comboBox.getLayoutBounds().getWidth())
                .max().orElse(0);
        comboBox.setMaxWidth(width);

        return comboBox;
    }

    private void setStyle(){
        setStyle("-fx-background-color: #f0f0f0; -fx-border-color: black; -fx-border-width: 2; -fx-border-style: solid;");
        setHgap(2);
    }

    public Rule getAssociatedRule() {
        return associatedRule;
    }
}
