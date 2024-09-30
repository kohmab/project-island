package com.javarush.pavlichenko.entities.islandentitycreator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.javarush.pavlichenko.entities.abilities.parameters.AllAbilitiesParameters;
import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class IslandEntityParametersProvider {

    private final static String CONF_FILE_NAME_TEMPLATE = "src/main/resources/entitiesconf/%s.yaml";

    private final Map<Class<? extends IslandEntity>, AllAbilitiesParameters> entitiesParameters = new HashMap<>();

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    public AllAbilitiesParameters getParametersFor(Class<? extends IslandEntity> entityClass){
        if (!entitiesParameters.containsKey(entityClass)){
            loadParametersFor(entityClass);
        }
        return entitiesParameters.get(entityClass);
    }

    private void loadParametersFor(Class<? extends IslandEntity> entityClass){
        AllAbilitiesParameters parameters;
        try {
            parameters = mapper.readValue(
                    new File(String.format(CONF_FILE_NAME_TEMPLATE, entityClass.getSimpleName())),
                    AllAbilitiesParameters.class);
        } catch (IOException e) {
            log.error("Can not load parametes for {}.", entityClass);
            throw new RuntimeException(e);
        }
        entitiesParameters.put(entityClass,parameters);
    }

}
