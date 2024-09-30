package com.javarush.pavlichenko.entities.abilities;

import com.javarush.pavlichenko.entities.island.Cell;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.exceptions.CellIsFilledException;
import com.javarush.pavlichenko.entities.abilities.parameters.AbilityParameter;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanMove;
import com.javarush.pavlichenko.enums.Direction;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.nonNull;

@Slf4j
@Getter
public class Movement implements Ability {

    private final CanMove creature;
    private final AbilityKey key;

    @AbilityParameter
    private Integer speed; //TODO speed feat

    public Movement(CanMove creature) {
        this.creature = creature;
        this.key = AbilityKey.getKeyFor(this);
    }

    @Override
    public void apply() {
        Coordinate currentCoordinate = creature.getCoordinate();
        Coordinate newCoordinate = getNewCoordinate(currentCoordinate);
        Cell nextCell = creature.getIsland().getCell(newCoordinate);
        try {
            log.debug("Trying to move {} to {}", creature, newCoordinate);
            nextCell.put(creature);
        } catch (CellIsFilledException e) {
            log.debug("Can not move {} to {}. Cell is filled", creature, newCoordinate);
            return;
        }

        Cell currentCell = creature.getIsland().getCell(currentCoordinate);
        if (nonNull(currentCell))
            currentCell.extract(creature);
        creature.setCoordinate(newCoordinate);
        log.debug("{} moved to {}", creature, newCoordinate);

    }

    private Coordinate getNewCoordinate(Coordinate current) {
        Coordinate newCoordinate = current;
        for (int i = 0; i < speed; i++) {
            do {
                newCoordinate = newCoordinate.add(Direction.getRandom().getDirection());
            } while (creature.getIsland().isForbiddenCoordinate(newCoordinate));
        }
        return newCoordinate;
    }


}
