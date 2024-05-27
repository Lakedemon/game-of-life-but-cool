package Game.ui.impl.slider;

import Game.ui.GuiComponent;
import javafx.scene.Node;
import javafx.scene.control.Slider;

public class SliderGuiComponent extends GuiComponent {

    private final Slider drawableElement;
    private final int width, height;

    public SliderGuiComponent(double minValue, double maxValue, double defaultValue, int width, SliderHandler handler) {
        this.drawableElement = new Slider(minValue, maxValue, defaultValue);
        this.width = width;
        this.height = (int) drawableElement.getHeight();
        this.drawableElement.setStyle(
                "-fx-focus-color: transparent; " + "-fx-faint-focus-color: transparent;"
        );

        this.drawableElement.setMinWidth(width);
        this.drawableElement.setMaxWidth(width);
        this.drawableElement.valueProperty().addListener((observable, oldValue, newValue) -> handler.slide((Double) oldValue, (Double) newValue));
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public Node getDrawableElement() {
        return drawableElement;
    }
}
