package com.javarush.pavlichenko.entities.abilities;

import com.javarush.pavlichenko.entities.island.Cell;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.exceptions.CellIsFilledException;
import com.javarush.pavlichenko.entities.abilities.parameters.AbilityParameter;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanMove;
import com.javarush.pavlichenko.enums.Direction;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Objects.nonNull;

@Slf4j
@Getter
public class Movement implements Ability {

    private final CanMove creature;
    private final AbilityKey key;

    @AbilityParameter
    private Integer speed;

    public Movement(CanMove creature) {
        this.creature = creature;
        this.key = AbilityKey.getKeyFor(this);
    }

    @Override
    public void apply() {
        Coordinate currentCoordinate = creature.getCoordinate();
        log.info("{} started movement from {}.", creature, currentCoordinate);
        Coordinate newCoordinate = getNewCoordinate(currentCoordinate);

        if (newCoordinate.equals(currentCoordinate)) {
            log.info("But {} changed its mind about moving.", creature);
            return;
        }

        Cell nextCell = creature.getIsland().getCell(newCoordinate);
        try {
            log.info("{} tried to move to {}.", creature, newCoordinate);
            nextCell.put(creature);
        } catch (CellIsFilledException e) {
            log.info("But {} could not move to {}. Cell was filled.", creature, newCoordinate);
            return;
        }

        Cell currentCell = creature.getIsland().getCell(currentCoordinate);
        if (nonNull(currentCell))
            currentCell.extract(creature);
        creature.setCoordinate(newCoordinate);
        log.info("{} moved to {}.", creature, newCoordinate);

    }

    private Coordinate getNewCoordinate(Coordinate current) {
        Coordinate newCoordinate = current;
        for (int i = 0; i < speed; i++) {
            newCoordinate = randomOneStep(newCoordinate);
        }
        return newCoordinate;
    }

    private Coordinate randomOneStep(Coordinate coordinate) {
        List<Coordinate> possibleNewCoordinates = Arrays.stream(Direction.values())
                .map(direction -> direction.getDirection().add(coordinate))
                .filter(creature.getIsland()::isPossibleCoordinate)
                .toList();
        int randomIndex = ThreadLocalRandom.current().nextInt(possibleNewCoordinates.size());
        return possibleNewCoordinates.get(randomIndex);
    }


}
