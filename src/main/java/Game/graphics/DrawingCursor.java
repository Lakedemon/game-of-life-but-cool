package Game.graphics;

import Game.paint.BrushShape;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import static Game.Main.CELL_SIZE;

public class DrawingCursor {

    private SimpleIntegerProperty hotSpotX = new SimpleIntegerProperty();
    private SimpleIntegerProperty hotSpotY = new SimpleIntegerProperty();

    private int relativeWidth;
    private Color color;
    private BrushShape cursorShape;

    private Scene scene;
    private Pane sceneRoot;
    private BorderPane content;
    private EventHandler<MouseEvent> eventHandler1;
    private EventHandler<MouseEvent> eventHandler2;
    private EventHandler<MouseEvent> eventHandler3;

    public DrawingCursor(Scene scene, Pane sceneRoot, int hotspotX, int hotspotY) {
        this.scene = scene;
        this.sceneRoot = sceneRoot;
        this.hotSpotX.set(hotspotX);
        this.hotSpotY.set(hotspotY);

        this.content = new BorderPane();
    }

    public void activate() {

        // Keep them in case of unRegister-reRegister
        // unRegister(); // has to be called before the below happens

        // cursor container
        content.setManaged(false);
        content.setMouseTransparent(true);

        // Keep the Content on the top of Scene
        ObservableList<Node> observable = sceneRoot.getChildren();
        observable.addListener((Observable osb) -> {
            if (content.getParent() != null && observable.get(observable.size() - 1) != content) {
                // move the cursor on the top
                Platform.runLater(content::toFront);
            }
        });

        if (!observable.contains(content))
            observable.add(content);

        // Add the event handlers (VERY UGLY)
        eventHandler1 = evt -> {
            if (!sceneRoot.getChildren().contains(content))
                observable.add(content);
        };
        eventHandler2 = evt -> observable.remove(content);

        eventHandler3 = evt -> {
            content.setLayoutX(evt.getX() - hotSpotX.get());
            content.setLayoutY(evt.getY() - hotSpotY.get());
        };

        // Ugly - refactor this later
        scene.addEventFilter(MouseEvent.MOUSE_ENTERED, eventHandler1);
        scene.addEventFilter(MouseEvent.MOUSE_EXITED, eventHandler2);
        scene.addEventFilter(MouseEvent.MOUSE_MOVED, eventHandler3);
        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, eventHandler3);

    }

    public void reloadPosition(ScrollEvent evt) {
        content.setLayoutX(evt.getX() - hotSpotX.get());
        content.setLayoutY(evt.getY() - hotSpotY.get());
    }

    public void setHotSpotX(int hotSpotX) {
        this.hotSpotX.set(hotSpotX);
    }

    public void setHotSpotY(int hotSpotY) {
        this.hotSpotY.set(hotSpotY);
    }

    public void makeCircle() {
        this.cursorShape = BrushShape.CIRCLE;

        Circle circle = new Circle();
        circle.setFill(Color.TRANSPARENT);
        this.content.setCenter(circle);
    }

    public void makeSquare() {
        this.cursorShape = BrushShape.SQUARE;

        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.TRANSPARENT);
        this.content.setCenter(rectangle);
    }

    public void setWidth(int width) {
        this.relativeWidth = width;
        updateWidthGraphics();
    }

    private void updateWidthGraphics() {
        if (this.cursorShape == BrushShape.CIRCLE) {
            Circle circle = (Circle) this.content.getCenter();
            circle.setRadius((double) (this.relativeWidth / 2) * CELL_SIZE);
        } else if (this.cursorShape == BrushShape.SQUARE) {
            Rectangle rectangle = (Rectangle) this.content.getCenter();
            rectangle.setWidth(this.relativeWidth * CELL_SIZE);
            rectangle.setHeight(this.relativeWidth * CELL_SIZE);
        }
    }

    public int getWidth() {
        return this.relativeWidth;
    }

    public void setColor(Color color) {
        this.color = color;

        if (this.content.getCenter() instanceof Shape shape) {
            shape.setStroke(color);
        }
    }

    public Color getColor() {
        return this.color;
    }

    public void unRegister() {
        if (scene != null) {
            sceneRoot.getChildren().remove(content);
            scene.removeEventFilter(MouseEvent.MOUSE_ENTERED, eventHandler1);
            scene.removeEventFilter(MouseEvent.MOUSE_EXITED, eventHandler2);
            scene.removeEventFilter(MouseEvent.MOUSE_MOVED, eventHandler3);
        }
    }

    public void reRegister() {
        if (scene != null)
            activate();
    }

    public SimpleIntegerProperty hotSpotXProperty() {
        return hotSpotX;
    }

    public SimpleIntegerProperty hotSpotYProperty() {
        return hotSpotY;
    }

}
