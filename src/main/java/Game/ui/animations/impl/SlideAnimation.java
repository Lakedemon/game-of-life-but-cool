package Game.ui.animations.impl;

import Game.ui.GuiComponent;
import Game.ui.GuiManager;
import Game.ui.animations.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.util.Duration;

public class SlideAnimation extends Animation {

    private final Direction direction;
    private final int durationMs;
    private final GuiComponent component;

    final int beginX, beginY, endX, endY;

    public SlideAnimation(Direction outwardsDirection, GuiComponent component, int durationMs, Easing easing) {
        super(easing);
        this.direction = outwardsDirection;
        this.durationMs = durationMs;
        this.component = component;

        this.beginX = calculateBeginX(component);
        this.beginY = calculateBeginY(component);
        this.endX = calculateEndX(component);
        this.endY = calculateEndY(component);
    }

    @Override
    public void perform(boolean in, GuiComponent root) {
        SimpleIntegerProperty toTravelXProperty = new SimpleIntegerProperty(in ? endX - beginX : beginX - endX);
        SimpleIntegerProperty toTravelYProperty = new SimpleIntegerProperty(in ? endY - beginY : beginY - endY);

        System.out.println("Animation: ");
        System.out.println("endX: " + endX + "; beginX: " + beginX);
        System.out.println("endY: " + endY + "; beginY: " + beginY);

        Timeline timeline = getTimeline(in, toTravelXProperty, toTravelYProperty);
        timeline.setCycleCount(this.durationMs);
        timeline.play();

        KeyFrame endKeyframe = new KeyFrame(Duration.millis(durationMs), e -> {
            if (in)
                setElementCoordinates(component, endX, endY);
            else
                if (root.removeChild(component)){
                    System.out.println("Removed child!");
                }
        });
        Timeline timeline2 = new Timeline(endKeyframe);
        timeline2.setCycleCount(1);

        timeline2.play();
    }

    private Timeline getTimeline(boolean in, SimpleIntegerProperty toTravelXProperty, SimpleIntegerProperty toTravelYProperty) {
        SimpleIntegerProperty currentFrameProperty = new SimpleIntegerProperty(0);
        KeyFrame onMsChange = new KeyFrame(Duration.millis(1), e -> {
            double t = this.easing.calculateEasedT(currentFrameProperty.get(), durationMs);

            double newXCoordinate = (in ? beginX : endX) + ((double) toTravelXProperty.get()) * t;
            double newYCoordinate = (in ? beginY : endY) + ((double) toTravelYProperty.get()) * t;
            setElementCoordinates(component, newXCoordinate, newYCoordinate);
            currentFrameProperty.set(currentFrameProperty.getValue() + 1);
        });

        return new Timeline(onMsChange);
    }

    private void setElementCoordinates(GuiComponent component, double x, double y) {
        component.getDrawableElement().setTranslateX(x);
        component.getDrawableElement().setTranslateY(y);
    }

    private int calculateBeginX(GuiComponent component) {
        Bounds boundsInScene = component.getDrawableElement().localToScene(component.getDrawableElement().getBoundsInLocal());
        if (!direction.isHorizontal()) {
            return (int) boundsInScene.getMinX();
        }

        if (this.direction == Direction.LEFT) {
            return (int) -boundsInScene.getWidth();
        } else if (this.direction == Direction.RIGHT) {
            return GuiManager.STAGE_WIDTH;
        }

        return (int) boundsInScene.getMinX();
    }

    private int calculateBeginY(GuiComponent component) {
        Bounds boundsInScene = component.getDrawableElement().localToScene(component.getDrawableElement().getBoundsInLocal());
        if (!direction.isVertical()) {
            return (int) boundsInScene.getMinY();
        }

        if (this.direction == Direction.UP) {
            return (int) -boundsInScene.getHeight();
        } else if (this.direction == Direction.DOWN) {
            return GuiManager.STAGE_HEIGHT;
        }

        return (int) boundsInScene.getMinY();
    }

    private int calculateEndX(GuiComponent component) {
        Bounds boundsInScene = component.getDrawableElement().localToScene(component.getDrawableElement().getBoundsInLocal());
        return (int) boundsInScene.getMinX();
    }

    private int calculateEndY(GuiComponent component) {
        Bounds boundsInScene = component.getDrawableElement().localToScene(component.getDrawableElement().getBoundsInLocal());
        return (int) boundsInScene.getMinY();
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
