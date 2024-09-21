package com.javarush.pavlichenko.entities.island;

import com.javarush.pavlichenko.exceptions.CellIsFilledException;
import com.javarush.pavlichenko.interfaces.IslandEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Getter
public class Cell {

    private final Integer capacity;
    private Set<IslandEntity> entities = ConcurrentHashMap.newKeySet();

    public boolean isFilled() {
        return capacity <= entities.size();
    }

    public void put(IslandEntity entity) throws CellIsFilledException {
        if (isFilled()) {
            log.info(String.format(TEMPLATE_CELL_IS_FILLED, entity, this));
            throw new CellIsFilledException();
        }
        entities.add(entity);


    }

    public void extract(IslandEntity entity) {
        entities.remove(entity);
    }

    public Integer getCreatureCount() {
        return entities.size();
    }

    @Override
    public String toString() {
        return "Cell{" +
                "entities=" + entities +
                '}';
    }

    private static final String TEMPLATE_CELL_IS_FILLED =
            "Cannot put entity in cell.\n" +
                    "\tCell is already filled. Entity: %s. Cell : %s.";

}
