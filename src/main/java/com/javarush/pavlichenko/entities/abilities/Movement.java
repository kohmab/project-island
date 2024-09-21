package com.javarush.pavlichenko.entities.abilities;

import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.interfaces.abilities.CanMove;
import com.javarush.pavlichenko.interfaces.abilities.Ability;
import com.javarush.pavlichenko.enums.Direction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Movement implements Ability {

    private final CanMove creature;

    public Movement(CanMove creature) {
        this.creature = creature;
    }

    @Override
    public void use() {
        move();
    }

    private void move() {
        Coordinate currentCoordinate = creature.getCoordinate();
        Coordinate newCoordinate = getNewCoordinate(currentCoordinate);
        log.debug("Trying to move {} to {}", creature, newCoordinate);
        creature.moveTo(newCoordinate);
    }

    private Coordinate getNewCoordinate(Coordinate current) {
        Coordinate newCoordinate;
        do {
            newCoordinate = current.add(Direction.getRandom().getDirection());
        } while (creature.getIsland().isForbiddenCoordinate(newCoordinate));
        return newCoordinate;
    }


}
