package Game;

import de.ralleytn.simple.json.JSONArray;
import de.ralleytn.simple.json.JSONObject;
import de.ralleytn.simple.json.JSONParser;
import de.ralleytn.simple.json.JSONParseException;
// import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class SaveHandler {

    DbHandler db;

    SaveHandler() {
        db = new DbHandler();
    }
    
    public int[][] loadGrid(String identifier) {
        // The intention is to (for now) load a JSON file
        // Later load save from database
        int[][] grid = null;
        String jsonGrid = db.getEntry("Game", identifier);
        jsonGrid = jsonGrid.trim();
        if (jsonGrid != null && !jsonGrid.isEmpty()) {
            JSONObject jsonObject = null;
            try {
                System.out.println(jsonGrid);
                JSONParser parser = new JSONParser();
                jsonObject = (JSONObject) parser.parse(jsonGrid);
            } catch (JSONParseException e) {
                System.out.println("Error parsing JSON");
                System.out.println(e.getMessage());
            }
            grid = deserializeGrid(jsonObject);
        }
        // Old system where it loads from json file, kept for testing
        // try (FileReader file = new FileReader(identifier + ".json")) {
        //     JSONObject jsonObject = (JSONObject) new JSONParser().parse(file);
        //     grid = deserializeGrid(jsonObject);
        // } catch (IOException | JSONParseException e) {
        //     e.printStackTrace();
        // }
        return grid;
    }

    public static int[][] deserializeGrid(JSONObject jsonObject) {
        JSONArray jsonArray = (JSONArray) jsonObject.get("grid");
        int[][] grid = new int[jsonArray.size()][];
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONArray innerArray = (JSONArray) jsonArray.get(i);
            grid[i] = new int[innerArray.size()];
            for (int j = 0; j < innerArray.size(); j++) {
                grid[i][j] = ((Long) innerArray.get(j)).intValue();
            }
        }
        return grid;
    }

    public void saveGrid(GameOfLife game, String identifier) {
        int [][] grid = game.getGrid();
        // Define saveFile and put grid object in it under key 'grid'
        JSONObject saveFile = new JSONObject();
        saveFile.put("grid", new JSONArray(grid));
        // Save GameOfLife grid to a database entry with name identifier
        db.addGridEntry("Game", identifier, saveFile.toString());
    }

    /* public void saveStructure() {
     * 
     * Save a structure to JSON
     * 
    } */ 

    /* public void loadStructure() {
     * 
     * Load a structure from JSON 
     * 
    } */

}
