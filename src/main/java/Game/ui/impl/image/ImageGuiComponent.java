package Game.ui.impl.image;

import Game.ui.clicking.ClickEvent;
import Game.ui.clicking.ClickableGuiComponent;
import javafx.scene.Node;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

import java.io.InputStream;

public class ImageGuiComponent extends ClickableGuiComponent implements ChangeableImageComponent {

    private final ImageView drawableElement;

    public ImageGuiComponent(Image image) {
        super(e -> {});
        this.drawableElement = new ImageView(image);

        super.registerClicks();
    }

    public ImageGuiComponent(Image image, ClickEvent eventHandler) {
        super(eventHandler);
        this.drawableElement = new ImageView(image);
        super.registerClicks();
    }

    @Override
    public void maskBlackPixels(Color color) {
        this.drawableElement.setImage(this.getMaskedImage(this.drawableElement.getImage(), color));
    }

    @Override
    public int getWidth() {
        return (int) drawableElement.getFitWidth();
    }

    @Override
    public int getHeight() {
        return (int) drawableElement.getFitHeight();
    }

    @Override
    public Node getDrawableElement() {
        return this.drawableElement;
    }

    public static ImageGuiComponent fromUrl(String url) {
        return new ImageGuiComponent(new Image(url));
    }
    public static ImageGuiComponent fromUrl(String url, ClickEvent eventHandler) {
        return new ImageGuiComponent(new Image(url), eventHandler);
    }
    public static ImageGuiComponent fromUrl(String url, int width, int height, boolean preserveRatio) {
        return new ImageGuiComponent(new Image(url, width, height, preserveRatio, true));
    }
    public static ImageGuiComponent fromUrl(String url, int width, int height, boolean preserveRatio, ClickEvent eventHandler) {
        return new ImageGuiComponent(new Image(url, width, height, preserveRatio, true), eventHandler);
    }

    public static ImageGuiComponent fromInputStream(InputStream inputStream) {
        return new ImageGuiComponent(new Image(inputStream));
    }
    public static ImageGuiComponent fromInputStream(InputStream inputStream, ClickEvent eventHandler) {
        return new ImageGuiComponent(new Image(inputStream), eventHandler);
    }
    public static ImageGuiComponent fromInputStream(InputStream inputStream, int width, int height, boolean preserveRatio) {
        return new ImageGuiComponent(new Image(inputStream, width, height, preserveRatio, true));
    }
    public static ImageGuiComponent fromInputStream(InputStream inputStream, int width, int height, boolean preserveRatio, ClickEvent eventHandler) {
        return new ImageGuiComponent(new Image(inputStream, width, height, preserveRatio, true), eventHandler);
    }
}
