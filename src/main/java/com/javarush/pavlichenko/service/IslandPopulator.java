package com.javarush.pavlichenko.service;

import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.entities.island.parameters.CellCapacity;
import com.javarush.pavlichenko.entities.island.parameters.GameSettings;
import com.javarush.pavlichenko.exceptions.CellIsFilledException;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j

public class IslandPopulator {

    private final Island island;

    private final IslandEntityCreator entityCreator = IslandEntityCreator.getInstance();

    private final Map<Class<? extends IslandEntity>, Integer> capacityMap =
            CellCapacity.getCapacityMap();

    private Map<Class<? extends IslandEntity>, Set<Coordinate>> availableCoordinates;

    private final Map<Class<? extends IslandEntity>, Integer> entitiesToBeCreatedCount =
            GameSettings.get().getInitialCreaturesCount();

    public IslandPopulator(Island island) {
        this.island = island;
    }

    public void populate() {
        island.clear();
        reset();

        for (Map.Entry<Class<? extends IslandEntity>, Integer> entry : entitiesToBeCreatedCount.entrySet()) {
            Class<? extends IslandEntity> entityClass = entry.getKey();
            Integer count = entry.getValue();
            try {
                for (int i = 0; i < count; i++) {
                    Coordinate coordinate = getRandomCoordinateFor(entityClass);

                    entityCreator.create(entityClass, coordinate);

                }
            } catch (CellIsFilledException e) {
                throw new RuntimeException("Impossible to create new " + entityClass, e);
            }
        }
    }

    private void reset() {
        availableCoordinates = new HashMap<>();
        entitiesToBeCreatedCount.keySet()
                .forEach(clazz -> availableCoordinates.put(clazz, island.getMap().keySet()));
    }

    private Coordinate getRandomCoordinateFor(Class<? extends IslandEntity> entityClass) {
        Set<Coordinate> coordinates = availableCoordinates.get(entityClass);
        Coordinate coordinate = coordinates.stream()
                .skip(new Random().nextInt(coordinates.size()))
                .findFirst()
                .orElseThrow();

        int currCount = island.getCell(coordinate).getListOf(entityClass).size();
        int availCount = capacityMap.get(entityClass);

        if (currCount == availCount) {
            availableCoordinates.get(entityClass).remove(coordinate);
        }
        return coordinate;
    }


}



