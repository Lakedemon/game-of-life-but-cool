package Game.ui.impl.image;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public interface ChangeableImageComponent {

    void maskBlackPixels(Color newColor);

    default Image getMaskedImage(Image originalImage, Color newColor) {
        int width = (int) originalImage.getWidth();
        int height = (int) originalImage.getHeight();
        WritableImage newImage = new WritableImage(width, height);

        PixelReader pixelReader = originalImage.getPixelReader();
        PixelWriter pixelWriter = newImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color pixelColor = pixelReader.getColor(x, y);

                if (pixelColor.equals(Color.BLACK)) {
                    pixelWriter.setColor(x, y, newColor);
                } else {
                    pixelWriter.setColor(x, y, pixelColor);
                }
            }
        }

        return newImage;
    }
}
