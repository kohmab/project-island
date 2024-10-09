package com.javarush.pavlichenko.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.pavlichenko.entities.abilities.Placement;
import com.javarush.pavlichenko.entities.abilities.sideclasses.AbilityKey;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.exceptions.CellIsFilledException;
import com.javarush.pavlichenko.entities.abilities.Ability;
import com.javarush.pavlichenko.entities.abilities.sideclasses.AbilityParameter;
import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import com.javarush.pavlichenko.exceptions.EntityConfigurationException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public class IslandEntityCreator {

    private final Island island;

    private final AbilityParametersProvider parametersProvider = new AbilityParametersProvider();
    private final ObjectMapper mapper = new ObjectMapper();

    private static IslandEntityCreator instance;

    public static void init(Island island) {
        if (nonNull(instance)) {
            throw new IllegalStateException("Already initialized.");
        }
        instance = new IslandEntityCreator(island);
    }

    public static IslandEntityCreator getInstance() {
        if (isNull(instance)) {
            throw new IllegalStateException("IslandEntityCreator no initialized. Use init.");
        }
        return instance;
    }

    public IslandEntity create(Class<? extends IslandEntity> entityClass, Coordinate coordinate) throws CellIsFilledException {
        IslandEntity entity = createBlank(entityClass);
        configureAbilities(entity);

        island.getCell(coordinate).put(entity);

        Placement placing = entity.getAbility(AbilityKey.getKeyForClass(Placement.class));
        try {
            Field coordinateField = placing.getClass().getDeclaredField("coordinate");
            coordinateField.setAccessible(true);
            coordinateField.set(placing, coordinate);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        log.debug("Created new entity: {}.", entity);

        return entity;
    }


    private IslandEntityCreator(Island island) {
        this.island = island;
    }


    private IslandEntity createBlank(Class<? extends IslandEntity> entityClass) {
        IslandEntity entity;
        try {
            Constructor<? extends IslandEntity> constructor = entityClass
                    .getDeclaredConstructor(Island.class);
            constructor.setAccessible(true);
            entity = constructor.newInstance(island);
            constructor.setAccessible(false);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return entity;
    }


    private List<Ability> getAbilitiesOf(IslandEntity entity) {
        ArrayList<Ability> result = new ArrayList<>();
        entity.getAllAbilityKeys()
                .forEach(abilityKey -> result.add(entity.getAbility(abilityKey)));
        return result;
    }

    private void configureAbilities(IslandEntity entity) {
        Map<String, Map<String, Object>> allParameters = parametersProvider.getParametersFor(entity.getClass());

        List<Ability> abilities = getAbilitiesOf(entity);

        for (Ability ability : abilities) {
            if (hasNoParameters(ability)) {
                continue;
            } else {
                String name = ability.getClass().getSimpleName();
                Map<String, Object> parametersMap = allParameters.get(name);

                try {
                    mapper.updateValue(ability, parametersMap);
                } catch (JsonMappingException e) {
                    throw new EntityConfigurationException(String.format("Can not set parameters of ability %s for %s", name, entity), e);
                }
            }

        }
    }

    private boolean hasNoParameters(Ability ability) {
        return Arrays.stream(ability.getClass().getDeclaredFields())
                .noneMatch(field -> field.isAnnotationPresent(AbilityParameter.class));
    }

}
