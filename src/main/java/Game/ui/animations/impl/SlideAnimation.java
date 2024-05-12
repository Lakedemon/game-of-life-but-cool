package Game.ui.animations.impl;

import Game.ui.GuiComponent;
import Game.ui.animations.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import org.w3c.dom.css.Rect;

public class SlideAnimation extends Animation {

    private final Direction direction;
    private final int durationMs;

    public SlideAnimation(Direction outwardsDirection, int durationMs) {
        this.direction = outwardsDirection;
        this.durationMs = durationMs;
    }

    @Override
    public void perform(GuiComponent component, boolean in) {
        final int beginX = calculateBeginX(component);
        final int endX = calculateEndX(component);
        final int beginY = calculateBeginY(component);
        final int endY = calculateEndY(component);

        System.out.println("begin x: "+ beginX);
        System.out.println("end x: "+ endX);
        System.out.println("begin y: "+ beginY);
        System.out.println("end y: "+ endY);

        SimpleIntegerProperty toTravelXProperty = new SimpleIntegerProperty(in ? endX - beginX : beginX - endX);
        SimpleIntegerProperty toTravelYProperty = new SimpleIntegerProperty(in ? endY - beginY : beginY - endY);
        SimpleIntegerProperty currentFrameProperty = new SimpleIntegerProperty(0);

        KeyFrame onMsChange = new KeyFrame(Duration.millis(1), e -> {
            double newXCoordinate = beginX + ((double) toTravelXProperty.get() / durationMs) * currentFrameProperty.get();
            double newYCoordinate = beginY + ((double) toTravelYProperty.get() / durationMs) * currentFrameProperty.get();
            setElementCoordinates(component, newXCoordinate, newYCoordinate);
            currentFrameProperty.set(currentFrameProperty.getValue() + 1);
            System.out.println("Set to: (" +newXCoordinate +", "+newYCoordinate+")" );
        });

        Timeline timeline = new Timeline(onMsChange);
        timeline.setCycleCount(this.durationMs);
        //timeline.play();

        setElementCoordinates(component, endX, endY);
    }

    private void setElementCoordinates(GuiComponent component, double x, double y) {
        component.getDrawableElement().setLayoutX(x);
        component.getDrawableElement().setLayoutY(y);
    }

    private int calculateBeginX(GuiComponent component) {
        if (!direction.isHorizontal()) {
            return (int) component.getDrawableElement().getLayoutX();
        }

        Node node = component.getDrawableElement();
        if (this.direction == Direction.LEFT) {
            System.out.println("layout width: " + node.getLayoutBounds().getWidth());
            return (int) -node.getLayoutBounds().getWidth();
        } else if (this.direction == Direction.RIGHT) {
            return (int) node.getScene().getWidth();
        }

        return (int) component.getDrawableElement().getLayoutX();
    }

    private int calculateBeginY(GuiComponent component) {
        if (!direction.isVertical()) {
            return (int) component.getDrawableElement().getLayoutY();
        }

        Node node = component.getDrawableElement();
        if (this.direction == Direction.UP) {
            return (int) -node.getLayoutBounds().getHeight();
        } else if (this.direction == Direction.DOWN) {
            return (int) node.getScene().getHeight();
        }

        return (int) component.getDrawableElement().getLayoutY();
    }

    private int calculateEndX(GuiComponent component) {
        return (int) component.getDrawableElement().getLayoutX();
    }

    private int calculateEndY(GuiComponent component) {
        return (int) component.getDrawableElement().getLayoutY();
    }

    public enum Direction {
        LEFT(true, false),
        RIGHT(true, false),
        UP(false, true),
        DOWN(false, true);

        private final boolean horizontal;
        private final boolean vertical;

        Direction(boolean horizontal, boolean vertical) {
            this.horizontal = horizontal;
            this.vertical = vertical;
        }

        public boolean isHorizontal() {
            return horizontal;
        }

        public boolean isVertical() {
            return vertical;
        }
    }

}
