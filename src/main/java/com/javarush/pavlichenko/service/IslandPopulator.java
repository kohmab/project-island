package com.javarush.pavlichenko.service;

import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.entities.island.parameters.CellCapacity;
import com.javarush.pavlichenko.entities.island.parameters.GameSettings;
import com.javarush.pavlichenko.exceptions.CellIsFilledException;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.javarush.pavlichenko.helpers.Helpers.getRandom;

@Slf4j

public class IslandPopulator {

    private final Island island;

    private final IslandEntityCreator entityCreator = IslandEntityCreator.getInstance();

    public IslandPopulator(Island island) {
        this.island = island;
    }

    public synchronized void populate() {
        island.clear();

        Map<Class<? extends IslandEntity>, Integer> targetCountMap = GameSettings.get().getInitialCreaturesCount();
        Map<Class<? extends IslandEntity>, Integer> cellCapacityMap = CellCapacity.getCapacityMap();

        for (Map.Entry<Class<? extends IslandEntity>, Integer> entry : targetCountMap.entrySet()) {
            Class<? extends IslandEntity> entityClass = entry.getKey();
            Integer targetCount = entry.getValue();

            Set<Coordinate> avaliableCoordinate = new HashSet<>(island.getMap().keySet());

            try {
                for (int i = 0; i < targetCount; i++) {
                    Coordinate coordinate = getRandom(avaliableCoordinate);
                    entityCreator.create(entityClass, coordinate);

                    if (island.getMap().get(coordinate).isFilledFor(entityClass)){
                        avaliableCoordinate.remove(coordinate);
                    }

                }
            } catch (CellIsFilledException e) {
                throw new RuntimeException("Impossible to create new " + entityClass, e);
            }
        }
        log.debug("Island was populated");
    }


}



