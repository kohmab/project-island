package com.javarush.pavlichenko.service;

import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.entities.island.parameters.GameSettings;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IslandStatsCollector {
    private final Island island;

    private final Map<Class<? extends IslandEntity>, Map<Coordinate, Integer>> creaturesCount;
    private final Set<Class<? extends IslandEntity>> knownEntities;
    private final Set<Coordinate> coordinates;

    public IslandStatsCollector(Island island) {
        this.island = island;
        creaturesCount = new HashMap<>();
        coordinates = island.getMap().keySet();
        knownEntities = GameSettings.get().getInitialCreaturesCount().keySet();
        for (Class<? extends IslandEntity> entityClass : knownEntities) {
            Map<Coordinate, Integer> emptyMap = new HashMap<>();
            coordinates.forEach(coordinate -> emptyMap.put(coordinate, 0));
            creaturesCount.put(entityClass, emptyMap);
        }
    }

    public void collect() {
        collectCount();
    }

    public Map<Coordinate, Integer> getCountOf(Class<? extends IslandEntity> entityClass) {
        return creaturesCount.get(entityClass);
    }

    public Map<Class<? extends IslandEntity>, Integer> getTotalCount() {
        Map<Class<? extends IslandEntity>, Integer> result = new HashMap<>();
        for (Class<? extends IslandEntity> entity : knownEntities) {
            int count = getCountOf(entity).values().stream()
                    .mapToInt(Integer::intValue)
                    .sum();
            result.put(entity, count);
        }
        return result;
    }

    private void collectCount() {
        for (Class<? extends IslandEntity> aClass : knownEntities) {
            for (Coordinate coordinate : coordinates) {
                int size = island.getCell(coordinate).getListOf(aClass).size();
                creaturesCount.get(aClass).put(coordinate, size);
            }
        }
    }
}
