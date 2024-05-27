package Game.ui.impl.rule;

import Game.MyComboBox;
import Game.rules.Comparators.IntComparators;
import Game.rules.Rule;
import Game.ui.GuiComponent;
import Game.ui.GuiManager;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public class RuleHolderGuiComponent extends GuiComponent {

    private final FlowPane drawableElement;
    private final Rule associatedRule;

    public RuleHolderGuiComponent(Rule associatedRule) {
        this.drawableElement = new FlowPane();
        this.associatedRule = associatedRule;

        Label label1 = new Label("If cell of value:");
        Label label2 = new Label("has:");
        Label label3 = new Label("neighbours of value:");
        Label label4 = new Label("then set the cell value to:");

        label1.setTextFill(Color.WHITE);
        label2.setTextFill(Color.WHITE);
        label3.setTextFill(Color.WHITE);
        label4.setTextFill(Color.WHITE);

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

        this.drawableElement.getChildren().addAll(label1, affectedState, label2, comparator, accountedCount, label3, accountedNeighbours, label4, resultingState);
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
        this.drawableElement.setBackground(Background.EMPTY);
        this.drawableElement.setHgap(2);
    }

    public Rule getAssociatedRule() {
        return associatedRule;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public Node getDrawableElement() {
        return this.drawableElement;
    }
}
