package com.javarush.pavlichenko.island.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.javarush.pavlichenko.island.entities.abilities.Ability;
import com.javarush.pavlichenko.island.entities.abilities.sideclasses.AbilityParameter;
import com.javarush.pavlichenko.island.entities.abilities.sideclasses.AbilityKey;
import com.javarush.pavlichenko.island.entities.abstr.IslandEntity;
import com.javarush.pavlichenko.island.exceptions.EntityConfigurationException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public class AbilityParametersProvider {

    private final static String CONF_FILE_NAME_TEMPLATE = "src/main/resources/conf/entities/%s.yaml";

    private final Map<Class<? extends IslandEntity>, Map<String, Map<String, Object>>> entitiesParameters = new ConcurrentHashMap<>();

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    public Map<String, Map<String, Object>> getParametersFor(Class<? extends IslandEntity> entityClass) {
        if (!entitiesParameters.containsKey(entityClass)) {
            loadParametersFor(entityClass);
        }
        return entitiesParameters.get(entityClass);
    }

    private void loadParametersFor(Class<? extends IslandEntity> entityClass) {
        Map<String, Map<String, Object>> parameters = null;
        try {
            parameters = mapper.readValue(
                    new File(String.format(CONF_FILE_NAME_TEMPLATE, entityClass.getSimpleName())),
                    new TypeReference<>() {
                    });

            Constructor<? extends IslandEntity> constructor = entityClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            IslandEntity entity = constructor.newInstance();
            constructor.setAccessible(false);
            checkAllParametersPresent(entity, parameters);

        } catch (Exception e) {
            log.error("Can not load parametes for {}.", entityClass);
        }
        parameters = Collections.unmodifiableMap(parameters);
        entitiesParameters.put(entityClass, parameters);
    }

    private void checkAllParametersPresent(IslandEntity entity, Map<String, @NonNull Map<String, Object>> parameters) {
        Set<Class<? extends Ability>> requiredAbilities = entity.getAllAbilityKeys().stream()
                .map(AbilityKey::getType)
                .collect(Collectors.toSet());

        Set<String> actualAbilities = parameters.keySet();

        List<String> absentAbilitiesList = requiredAbilities.stream()
                .map(Class::getSimpleName)
                .filter(actualAbilities::contains)
                .toList();

        if (absentAbilitiesList.size() > 0) {
            throw new EntityConfigurationException(String.format("Entity %s is missing parameters for abilities: %s",
                    entity, absentAbilitiesList));
        }

        for (Class<? extends Ability> clazz : requiredAbilities) {
            String abilityName = clazz.getSimpleName();
            Set<String> abilityParameters = parameters.get(abilityName).keySet();
            Set<String> requiredAbilityParameters = Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(AbilityParameter.class))
                    .map(Field::getName)
                    .collect(Collectors.toSet());

            List<String> absentParemetersList = requiredAbilityParameters.stream()
                    .filter(abilityParameters::contains)
                    .toList();

            if (absentParemetersList.size() > 0) {
                throw new EntityConfigurationException(String.format("Entity %s is missing parameters for ability %s: %s",
                        entity, abilityName, absentParemetersList));
            }

        }
    }


}
