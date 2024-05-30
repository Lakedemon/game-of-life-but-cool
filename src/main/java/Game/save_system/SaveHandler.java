package Game.save_system;

import java.util.List;
import java.util.HashMap;
import Game.GameOfLife;
import Game.Structure;
import Game.rules.RuleBook;
import Game.rules.Comparators.IntComparator;
import Game.rules.Comparators.IntComparators;
import Game.rules.Rule;
import de.ralleytn.simple.json.JSONArray;
import de.ralleytn.simple.json.JSONObject;
import de.ralleytn.simple.json.JSONParser;
import de.ralleytn.simple.json.JSONParseException;
// import java.io.FileWriter;


public class SaveHandler {

    DbHandler db;
    private final GameOfLife gameOfLife;

    public SaveHandler(GameOfLife gameOfLife) {
        db = new DbHandler();
        this.gameOfLife = gameOfLife;
    }
    
    public void loadGrid(String identifier) {
        int[][] grid = null;
        String jsonGrid = db.getEntry("Game", identifier, "Grid");
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
        gameOfLife.setGridValues(grid);
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

    public void saveGrid(String identifier) {
        int[][] grid = gameOfLife.getGridValues();
        // Define saveFile and put grid object in it under key 'grid'
        JSONObject saveFile = new JSONObject();
        saveFile.put("grid", new JSONArray(grid));
        // Save GameOfLife grid to a database entry with name identifier
        db.addGridEntry("Game", identifier, saveFile.toString());
    }

    public void saveStructure(Structure structure, String identifier) { 
        int[][] grid = structure.getGridValues();
        // Define structure and put grid object in it under key 'grid'
        JSONObject structureJson = new JSONObject();
        structureJson.put("grid", new JSONArray(grid));
        // Save structure grid grid to a database entry with name identifier
        db.addGridEntry("Structures", identifier, structureJson.toString());
    }

    public Structure loadStructure(String identifier) { 
        int[][] grid = null;
        String jsonGrid = db.getEntry("Structures", identifier, "Grid");
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
        return new Structure(grid);
    }

    public void saveRulebook(String identifier) {
        RuleBook ruleBook = gameOfLife.getRuleBook();
        List<Rule> rules = ruleBook.getRules();
        Integer i = Integer.valueOf(1);
        HashMap<Integer, HashMap<String, String>> bookAsMap = new HashMap<Integer, HashMap<String, String>>();  
        for (Rule rule : rules) {
            HashMap<String, String> ruleAsMap = new HashMap<String, String>();
            ruleAsMap.put("affectedState", String.valueOf(rule.getAffectedState()));
            ruleAsMap.put("accountedNeighbours", String.valueOf(rule.getAccountedNeighbours()));
            ruleAsMap.put("accountedCount", String.valueOf(rule.getAccountedCount()));
            ruleAsMap.put("resultingState", String.valueOf(rule.getResultingState()));
            ruleAsMap.put("comparator", rule.getComparator().getSymbol());

            bookAsMap.put(i, ruleAsMap);
            i++;
        }
        JSONObject bookAsJson = new JSONObject(bookAsMap);
        // System.out.println(bookAsJson);
        db.addRulebookEntry(identifier, bookAsJson.toString());
    }

    public RuleBook loadRulebook(String identifier) {
        String jsonString = db.getEntry("Rulebooks", identifier, "Content");
        JSONObject jsonObject = null;
        RuleBook ruleBook = new RuleBook();
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONParseException e) {
            System.out.println("Error parsing JSON"); 
            System.out.println(e.getMessage());
        }
        for (Object ruleObj : jsonObject.values()) {
            JSONObject ruleJson = (JSONObject) ruleObj;
            IntComparator comparator = IntComparators.comparatorMap.get(ruleJson.get("comparator"));
            int accountedCount = Integer.valueOf((String) ruleJson.get("accountedCount"));
            int accountedNeighbours = Integer.valueOf((String) ruleJson.get("accountedNeighbours"));
            int resultingState = Integer.valueOf((String) ruleJson.get("resultingState"));
            int affectedState = Integer.valueOf((String) ruleJson.get("affectedState"));
            Rule rule = new Rule(affectedState, accountedNeighbours, accountedCount, resultingState, comparator);
            ruleBook.addRule(rule);
        }
        gameOfLife.setRuleBook(ruleBook);
        return ruleBook;
    }

}
