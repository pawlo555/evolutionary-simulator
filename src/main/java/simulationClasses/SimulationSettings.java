package simulationClasses;

import utilities.JSONSettingsParser;

import java.util.HashMap;

public class SimulationSettings {

    private HashMap<String, String> settingsMap;

    static private final JSONSettingsParser parser = new JSONSettingsParser();

    public SimulationSettings() {
        try {
            settingsMap = parser.ReadFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getValue(String key) {
        return settingsMap.get(key);
    }

    public void setValue(String key,String value) {
        settingsMap.remove(key);
        settingsMap.put(key,value);
    }

    public void CheckParameters() {
        if (Integer.parseInt(settingsMap.get("width")) <= 0) {
            throw new IllegalArgumentException("Map width must be greater than 0");
        }
        if (Integer.parseInt(settingsMap.get("height")) <= 0) {
            throw new IllegalArgumentException("Map height must be greater than 0");
        }
        float jungleRatio = Float.parseFloat(settingsMap.get("jungleRatio"));
        if (jungleRatio <= 0 || jungleRatio >= 1) {
            throw new IllegalArgumentException("JungleRation must be greater 0 and lower than 0");
        }
        if (Integer.parseInt(settingsMap.get("startEnergy")) <= 0) {
            throw new IllegalArgumentException("Animal must start with positive energy");
        }
        if (Integer.parseInt(settingsMap.get("energyPerEpoch")) <= 0) {
            throw new IllegalArgumentException("Animals have to loose energy per epoch");
        }
        if (Integer.parseInt(settingsMap.get("energyToBreed")) <= 0) {
            throw new IllegalArgumentException("Animal need to have positive energy to breed");
        }
        if (Integer.parseInt(settingsMap.get("energyPerGrass")) <= 0) {
            throw new IllegalArgumentException("Grass have to feed animals");
        }
        if (Integer.parseInt(settingsMap.get("animalsOnStart")) <= 2) {
            throw new IllegalArgumentException("Must be at least two animals at start");
        }
        int simulations = Integer.parseInt(settingsMap.get("numberOfSimulations"));
        if (simulations != 1 && simulations != 2) {
            throw new IllegalArgumentException("Should be 1 or 2 simulations at one time");
        }
    }

    @Override
    public String toString() {
        return settingsMap.toString();
    }
}
