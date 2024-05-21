package Game.file;

import java.io.*;
import java.util.Optional;

public class StaticFileHandler {

    public static String SETTINGS_MENU_IMG_RESOURCE_PATH = "./src/main/resources/open_settings_menu_button.png";
    public static String LEFT_ARROW_IMG_RESOURCE_PATH = "./src/main/resources/left_arrow.png";
    public static String RIGHT_ARROW_IMG_RESOURCE_PATH = "./src/main/resources/right_arrow.png";

    public static Optional<InputStream> getImageInputStream(String filePath) {
        try {
            InputStream imageStream = new FileInputStream(filePath);
            return Optional.of(imageStream);
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: Could not load image. No file found at path '" + filePath + "'");
            return Optional.empty();
        } catch (SecurityException e) {
            System.out.println("ERROR: Security exception while loading image from path '" + filePath + "'. Check if the program has access to the file.");
            return Optional.empty();
        }
    }

}
