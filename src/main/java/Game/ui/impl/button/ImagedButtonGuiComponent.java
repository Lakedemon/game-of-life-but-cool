package Game.ui.impl.button;

import Game.ui.clicking.ClickEvent;
import Game.ui.clicking.ClickableGuiComponent;
import Game.ui.impl.image.ChangeableImageComponent;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.InputStream;

public class ImagedButtonGuiComponent extends ClickableGuiComponent implements ChangeableImageComponent {

    private final StackPane drawableElement;
    private final ImageView imageView;

    private final int width, height;

    public ImagedButtonGuiComponent(Image image, int width, int height, Color backgroundColor, Color strokeColor, int arc, int strokeWidth, ClickEvent eventHandler) {
        super(eventHandler);
        this.width = width;
        this.height = height;

        this.drawableElement = new StackPane();
        this.drawableElement.setMinWidth(width);
        this.drawableElement.setMinHeight(height);
        this.drawableElement.setMaxWidth(width);
        this.drawableElement.setMaxHeight(height);

        Rectangle background = new Rectangle();
        background.setFill(backgroundColor);
        background.setStrokeWidth(strokeWidth);
        background.setStroke(strokeColor);
        background.setArcHeight(arc);
        background.setArcWidth(arc);
        background.setWidth(width);
        background.setHeight(height);

        this.imageView = new ImageView(image);

        this.drawableElement.getChildren().add(background);
        this.drawableElement.getChildren().add(this.imageView);

        super.registerClicks();
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
        return this.drawableElement;
    }

    @Override
    public void maskBlackPixels(Color newColor) {
        this.imageView.setImage(getMaskedImage(this.imageView.getImage(), newColor));
    }

    public static ImagedButtonGuiComponent fromUrl(String url, int width, int height, boolean preserveRatio, Color backgroundColor, Color strokeColor, int strokeWidth, int arc) {
        return new ImagedButtonGuiComponent(new Image(url, width, height, preserveRatio, true), width, height, backgroundColor, strokeColor, arc, strokeWidth, e -> {});
    }
    public static ImagedButtonGuiComponent fromUrl(String url, int width, int height, boolean preserveRatio, Color backgroundColor, Color strokeColor, int strokeWidth, int arc, ClickEvent eventHandler) {
        return new ImagedButtonGuiComponent(new Image(url, width, height, preserveRatio, true), width, height, backgroundColor, strokeColor, arc, strokeWidth, eventHandler);
    }

    public static ImagedButtonGuiComponent fromInputStream(InputStream inputStream, int width, int height, boolean preserveRatio, Color backgroundColor, Color strokeColor, int strokeWidth, int arc) {
        return new ImagedButtonGuiComponent(new Image(inputStream, width, height, preserveRatio, true), width, height, backgroundColor, strokeColor, arc, strokeWidth, e -> {});
    }
    public static ImagedButtonGuiComponent fromInputStream(InputStream inputStream, int width, int height, boolean preserveRatio, Color backgroundColor, Color strokeColor, int strokeWidth, int arc, ClickEvent eventHandler) {
        return new ImagedButtonGuiComponent(new Image(inputStream, width, height, preserveRatio, true), width, height, backgroundColor, strokeColor, arc, strokeWidth, eventHandler);
    }
}
