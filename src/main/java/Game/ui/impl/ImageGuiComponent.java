package Game.ui.impl;

import Game.ui.clicking.ClickEvent;
import Game.ui.clicking.ClickableGuiComponent;
import javafx.scene.Node;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.*;
import javafx.scene.paint.Color;

import javax.swing.plaf.ColorUIResource;
import java.io.InputStream;

public class ImageGuiComponent extends ClickableGuiComponent {

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

    public void changeBlackPixels(Color color) {
        Image originalImage = drawableElement.getImage();
        int width = (int) originalImage.getWidth();
        int height = (int) originalImage.getHeight();
        WritableImage newImage = new WritableImage(width, height);

        PixelReader pixelReader = originalImage.getPixelReader();
        PixelWriter pixelWriter = newImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color pixelColor = pixelReader.getColor(x, y);

                if (pixelColor.equals(Color.BLACK)) {
                    pixelWriter.setColor(x, y, color);
                } else {
                    pixelWriter.setColor(x, y, pixelColor);
                }
            }
        }

        this.drawableElement.setImage(newImage);
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
