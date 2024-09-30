package com.javarush.pavlichenko.entities.islandentitycreator;

import com.javarush.pavlichenko.entities.abilities.parameters.AbilityParameters;
import com.javarush.pavlichenko.entities.abilities.parameters.AllAbilitiesParameters;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.exceptions.CellIsFilledException;
import com.javarush.pavlichenko.entities.abilities.Ability;
import com.javarush.pavlichenko.entities.abilities.parameters.AbilityParameter;
import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import com.javarush.pavlichenko.exceptions.ConfigurationException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static java.util.Objects.isNull;

@Slf4j
public class IslandEntityCreator {

    private final Island island;

    private final IslandEntityParametersProvider parametersProvider = new IslandEntityParametersProvider();

    public IslandEntityCreator(Island island) {
        this.island = island;
    }

    public void create(Class<? extends IslandEntity> entityClass, Coordinate coordinate) throws CellIsFilledException {
        IslandEntity entity = createBlank(entityClass);
        configureEntity(entity);

        entity.setCoordinate(coordinate);
        entity.getIsland().getCell(coordinate).put(entity);
        log.debug("Created new entity: {}.",entity);
    }

    private IslandEntity createBlank(Class<? extends IslandEntity> entityClass) {
        IslandEntity entity = null;
        try {
            entity = entityClass
                    .getDeclaredConstructor(Island.class, Coordinate.class)
                    .newInstance(island, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    private void configureEntity(IslandEntity entity) {
        Class<? extends IslandEntity> clazz = entity.getClass();
        AllAbilitiesParameters allParameters = parametersProvider.getParametersFor(clazz);
        configureAbilities(entity,allParameters);
    }

    private List<Ability> getAbilitiesOf(IslandEntity entity) {
        ArrayList<Ability> result = new ArrayList<>();
        entity.getAllAbilityKeys()
                .forEach(abilityKey -> result.add(entity.getAbility(abilityKey)));
        return result;
    }

    private void configureAbilities(IslandEntity entity, AllAbilitiesParameters allParameters) {
        Class<? extends IslandEntity> entityClass = entity.getClass();
        List<Ability> abilities = getAbilitiesOf(entity);

        for (Ability ability : abilities) {
            if (hasNoParameters(ability))
                continue;

            String name = ability.getClass().getSimpleName();
            try {
                String getterName = deriveGetterName(name);
                Method method = allParameters.getClass().getMethod(getterName);
                AbilityParameters parameters = (AbilityParameters) method.invoke(allParameters);

                if (isNull(parameters)) {
                    throw new ConfigurationException(String.format("No parameters specified for ability %s", name));
                }

                configureAbility(ability, parameters);

            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException  e) {
                throw new RuntimeException(e);
            } catch (ConfigurationException e){
                throw new RuntimeException(e.getMessage() + "\nError while loading configuration for " + entityClass);
            }
        }
    }

    private void configureAbility(Ability ability, AbilityParameters abilityParameters) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, ConfigurationException {
        List<Field> abilityParametersFields = Arrays.stream(ability.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(AbilityParameter.class))
                .toList();
        for (Field field : abilityParametersFields) {
            String fieldName = field.getName();
            String getterName = deriveGetterName(fieldName);
            Method getter = abilityParameters.getClass().getMethod(getterName);
            Object value = getter.invoke(abilityParameters);
            if (isNull(value)) {
                throw new ConfigurationException(String.format("No value specified parameter %s of ability %s", fieldName, ability.getClass()));
            }
            field.setAccessible(true);
            field.set(ability, value);
            field.setAccessible(false);
        }
    }

    private boolean hasNoParameters(Ability ability) {
        return Arrays.stream(ability.getClass().getDeclaredFields())
                .noneMatch(field -> field.isAnnotationPresent(AbilityParameter.class));
    }

    private String deriveGetterName(String fieldName){
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

    }

}
