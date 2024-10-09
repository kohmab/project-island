package com.javarush.pavlichenko.entities.island;

import com.javarush.pavlichenko.entities.island.parameters.CellCapacity;
import com.javarush.pavlichenko.exceptions.CellIsFilledException;
import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
@RequiredArgsConstructor
@Getter
public class Cell {

    private final Map<Class<? extends IslandEntity>, List<IslandEntity>> entitiesMap = new ConcurrentHashMap<>();

    private final Map<Class<? extends IslandEntity>, Integer> capacityMap = CellCapacity.getCapacityMap();

    public boolean isFilledFor(IslandEntity entity) {
        return isFilledFor(entity.getClass());
    }

    public boolean isFilledFor(Class<? extends IslandEntity> aClass) {
        List<IslandEntity> entitiesList = getListOf(aClass);
        return entitiesList.size() >= capacityMap.get(aClass);
    }

    public List<IslandEntity> getListFor(IslandEntity entity) {
        Class<? extends IslandEntity> entityKey = entity.getClass();
        return getListOf(entityKey);
    }

    public List<IslandEntity> getListOf(Class<? extends IslandEntity> entityClass) {
        List<IslandEntity> entitiesList = entitiesMap.get(entityClass);
        if (isNull(entitiesList)) {
            entitiesList = createNewListOf(entityClass);
            entitiesMap.put(entityClass, entitiesList);
        }
        return entitiesList;
    }

    private synchronized List<IslandEntity> createNewListOf(Class<? extends IslandEntity> entityClass) {
        List<IslandEntity> entityList = entitiesMap.get(entityClass);
        if (nonNull(entityList))
            return entityList;
        entityList = new CopyOnWriteArrayList<>();
        entitiesMap.put(entityClass, entityList);
        return entityList;
    }

    public void put(IslandEntity entity) throws CellIsFilledException {
        if (isFilledFor(entity)) {
            log.debug(String.format(TEMPLATE_CELL_IS_FILLED, entity, this));
            throw new CellIsFilledException();
        }
        List<IslandEntity> entityList = getListFor(entity);
        entityList.add(entity);
    }

    public void extract(IslandEntity entity) {
        getListFor(entity).remove(entity);
        log.debug("Entity {} removed from cell", entity);
    }


    public List<IslandEntity> getAllEntities() {
        List<IslandEntity> all = new ArrayList<>();
        entitiesMap.values().forEach(all::addAll);
        return all;
    }

    public Integer getCreatureCount() {
        AtomicInteger count = new AtomicInteger();
        entitiesMap.values().forEach((list) -> count.addAndGet(list.size()));
        return count.get();
    }

    public synchronized List<IslandEntity> collectAndRemoveDead() {
        List<IslandEntity> result = new ArrayList<>();
        for (List<IslandEntity> islandEntityList : entitiesMap.values()) {
            islandEntityList.stream()
                    .filter(IslandEntity::isDead)
                    .forEach(entity -> {
                        result.add(entity);
                        islandEntityList.remove(entity);
                    });
        }
        return result;
    }

    private static final String TEMPLATE_CELL_IS_FILLED =
            """
                    Cannot put entity in cell. Cell is already filled.
                    \t Entity: %s.
                    \t Cell : %s.""";

}
