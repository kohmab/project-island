package com.javarush.pavlichenko.entities.creatures;

import com.javarush.pavlichenko.entities.abilities.Movement;
import com.javarush.pavlichenko.entities.island.Cell;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.enums.Gender;
import com.javarush.pavlichenko.interfaces.Animal;
import com.javarush.pavlichenko.exceptions.CellIsFilledException;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.nonNull;

@Slf4j
public abstract class AbstractAnimal extends AbstractIslandEntity implements Animal {

    public AbstractAnimal(Gender sex, Island island, Coordinate coordinate) {
        super(sex, island, coordinate);

        Movement movement = new Movement(this);
        abilities.add(movement);
    }

    @Override
    public void moveTo(@NonNull Coordinate newCoordinate) {

        Cell nextCell = getIsland().getCell(newCoordinate);
        try {
            nextCell.put(this);
        } catch (CellIsFilledException e) {
            log.info("Can not move {} to {}. Cell is filled", this, newCoordinate);
            return;
        }

        Cell currentCell = getIsland().getCell(getCoordinate());
        if (nonNull(currentCell))
            currentCell.extract(this);
        log.debug("{} moved to {}", this, newCoordinate);

        this.coordinate = newCoordinate;

    }
}
