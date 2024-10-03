package com.javarush.pavlichenko.entities.island.parameters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Getter
public class GameSettings {

    private final static String GAME_SETTINGS_FILE = "src/main/resources/conf/game.yaml";

    private Integer islandHeight;
    private Integer islandWidth;

    private Integer threadsCount;

    private Map<Class<? extends IslandEntity>, Integer> initialCreaturesCount;

    public static GameSettings get() {
        return instance;
    }

    private static final GameSettings instance;

    static {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            instance = mapper.readValue(new File(GAME_SETTINGS_FILE), GameSettings.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        instance.initialCreaturesCount = Collections.unmodifiableMap(instance.initialCreaturesCount);
    }


}
