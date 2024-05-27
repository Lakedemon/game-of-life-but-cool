package Game.ui.animations.impl;

import Game.ui.GuiComponent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Bounds;
import javafx.util.Duration;

public class SlideOnScreenAnimation extends SlideAnimation {

    private final int offsetX;
    private final int offsetY;

    public SlideOnScreenAnimation(int offsetX, int offsetY, GuiComponent component, int durationMs, Easing easing) {
        super(null, component, durationMs, easing);

        this.offsetX = offsetX;
        this.offsetY = offsetY;

        this.endX = this.calculateEndX(component);
        this.endY = this.calculateEndY(component);
        this.beginX = this.calculateBeginX(component);
        this.beginY = this.calculateBeginY(component);
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


    @Override
    public void perform(boolean in, GuiComponent root) {
        SimpleIntegerProperty toTravelXProperty = new SimpleIntegerProperty(in ? endX - beginX : beginX - endX);
        SimpleIntegerProperty toTravelYProperty = new SimpleIntegerProperty(in ? endY - beginY : beginY - endY);

        if (this instanceof SlideOnScreenAnimation) {
            System.out.println("end x: " + endX + "; begin x: " + beginX + "; end y: " + endY + "; begin y: " + beginY);
            System.out.println("to travel x: "+ toTravelXProperty.get());
            System.out.println("to travel y: "+ toTravelYProperty.get());
        }

        Timeline timeline = getTimeline(in, toTravelXProperty, toTravelYProperty);
        timeline.setCycleCount(this.durationMs);
        timeline.play();

        KeyFrame endKeyframe = new KeyFrame(Duration.millis(durationMs), e -> {
            if (in)
                setElementCoordinates(component, endX, endY);
            else {
                root.removeChild(component);
            }
            this.isActiveProperty.set(false);
        });
        Timeline timeline2 = new Timeline(endKeyframe);
        timeline2.setCycleCount(1);

        this.isActiveProperty.set(true);

        timeline2.play();

    }

    @Override
    protected int calculateEndX(GuiComponent component) {
        return this.beginX + this.offsetX;
    }

    @Override
    protected int calculateEndY(GuiComponent component) {
        return this.beginY + this.offsetY;
    }

    @Override
    protected int calculateBeginX(GuiComponent component) {
        Bounds boundsInScene = component.getDrawableElement().localToScene(component.getDrawableElement().getBoundsInLocal());
        return (int) boundsInScene.getMinX();
    }

    @Override
    protected int calculateBeginY(GuiComponent component) {
        Bounds boundsInScene = component.getDrawableElement().localToScene(component.getDrawableElement().getBoundsInLocal());
        return (int) boundsInScene.getMinY();
    }
}
