package GUI;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONSettingsParser {

    public HashMap<String, String> ReadFile() throws IOException, ParseException {
        HashMap<String, String> settingsMap = new HashMap<>();
        JSONParser parser = new JSONParser();
        File file = new File("src/settings.json");
        FileReader fileReader = new FileReader(file);
        JSONObject jo = (JSONObject) parser.parse(fileReader);
        settingsMap.put("height",String.valueOf(jo.get("height")));
        settingsMap.put("width",String.valueOf(jo.get("width")));
        settingsMap.put("jungleRatio",String.valueOf(jo.get("jungleRatio")));
        settingsMap.put("startEnergy",String.valueOf(jo.get("startEnergy")));
        settingsMap.put("energyPerEpoch",String.valueOf(jo.get("energyPerEpoch")));
        settingsMap.put("energyToBred",String.valueOf(jo.get("energyToBred")));
        settingsMap.put("energyPerGrass",String.valueOf(jo.get("energyPerGrass")));
        settingsMap.put("animalsOnStart",String.valueOf(jo.get("animalsOnStart")));
        settingsMap.put("numberOfSimulations",String.valueOf(jo.get("numberOfSimulations")));
        return settingsMap;
    }
}
