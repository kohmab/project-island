package com.javarush.pavlichenko.entities.island.parameters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class CellCapacity {

    private final static String CAPACITY_CONF_FILE = "src/main/resources/conf/cell/capacity.yaml";

    @Getter
    private final static Map<Class<? extends IslandEntity>, Integer> capacityMap;

    static {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            capacityMap = Collections.unmodifiableMap(
                    mapper.readValue(
                            new File(CAPACITY_CONF_FILE),
                            new TypeReference<Map<Class<? extends IslandEntity>, Integer>>() {
                            })
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
