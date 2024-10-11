package com.javarush.pavlichenko.island.entities.abilities;

import com.javarush.pavlichenko.island.entities.island.Cell;
import com.javarush.pavlichenko.island.entities.island.Coordinate;
import com.javarush.pavlichenko.island.exceptions.CellIsFilledException;
import com.javarush.pavlichenko.island.entities.abilities.sideclasses.AbilityParameter;
import com.javarush.pavlichenko.island.entities.abstr.abilitymarkers.CanMove;
import com.javarush.pavlichenko.island.enums.Direction;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Objects.nonNull;

@Slf4j
@Getter
public class Movement extends SomeAbility {


    @AbilityParameter
    private Integer speed;

    private final Placement placing;

    public Movement(CanMove creature)
    {
        super(creature, Movement.class);
        placing = getAnotherAbility(Placement.class);
    }

    @Override
    public void apply() {
        Coordinate currentCoordinate = placing.getCoordinate();
        log.info(marker, "{} started movement from {}.", owner, currentCoordinate);
        Coordinate newCoordinate = getNewCoordinate(currentCoordinate);

        if (newCoordinate.equals(currentCoordinate)) {
            log.info(marker, "But {} changed its mind about moving.", owner);
            return;
        }

        Cell nextCell = owner.getIsland().getCell(newCoordinate);
        try {
            log.info(marker, "{} tried to move to {}.", owner, newCoordinate);
            nextCell.put(owner);
        } catch (CellIsFilledException e) {
            log.info(marker, "But {} could not move to {}. Cell was filled.", owner, newCoordinate);
            return;
        }

        Cell currentCell = owner.getIsland().getCell(currentCoordinate);
        if (nonNull(currentCell))
            currentCell.extract(owner);
        placing.setCoordinate(newCoordinate);
        log.info(marker, "{} moved to {}.", owner, newCoordinate);

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
                .filter(owner.getIsland()::isPossibleCoordinate)
                .toList();
        int randomIndex = ThreadLocalRandom.current().nextInt(possibleNewCoordinates.size());
        return possibleNewCoordinates.get(randomIndex);
    }


}
