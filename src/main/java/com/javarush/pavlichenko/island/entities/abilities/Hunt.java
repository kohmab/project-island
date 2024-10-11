package com.javarush.pavlichenko.island.entities.abilities;

import com.javarush.pavlichenko.island.entities.abilities.sideclasses.PossiblePrey;
import com.javarush.pavlichenko.island.entities.island.Cell;
import com.javarush.pavlichenko.island.entities.island.Coordinate;
import com.javarush.pavlichenko.island.entities.abilities.sideclasses.AbilityParameter;
import com.javarush.pavlichenko.island.entities.abstr.abilitymarkers.CanHunt;
import com.javarush.pavlichenko.island.entities.abstr.IslandEntity;
import com.javarush.pavlichenko.island.entities.abstr.abilitymarkers.CanBeCached;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static com.javarush.pavlichenko.island.helpers.Helpers.getRandom;
import static java.util.Objects.isNull;

@Slf4j
@Getter
public class Hunt extends SomeAbility{

    private final Hunger hunger;
    private final Placement placement;

    @AbilityParameter
    private List<PossiblePrey> possiblePreys;

    private Double currentCatchProbability;

    public Hunt(CanHunt predator) {
        super(predator, Hunt.class);
        this.hunger = getAnotherAbility(Hunger.class);
        this.placement = getAnotherAbility(Placement.class);
    }

    @Override
    public void apply() {
        log.info(marker, "{} started hunt.", owner);

        if (hunger.isNotHungry()) {
            log.info(marker, "But {} is full.", owner);
            return;
        }


        CanBeCached prey = findPrey();
        if (isNull(prey)) {
            log.info(marker, "But {} did not find a prey.", owner);
            return;
        }

        synchronized (prey.getLock()) {
            log.info(marker, "{} found a prey: {}.", owner, prey);


            if (isHuntFailed()) {
                log.info(marker, "But {} failed (miss). ", owner);
                return;
            }

            if (isPreyRunAway(prey)) {
                log.info(marker, "But {} missed the prey {}.", owner, prey);
                return;
            }

            if (prey.isDead()) {
                log.info(marker, "But {}'s prey ({}) was already dead.", owner, prey);
                return;
            }
            prey.die();

            Prey preyAbility = getAnotherAbilityFor(prey, Prey.class);
            Double preyAmount = preyAbility.getFoodAmount();

            log.info(marker, "{} killed {}.", owner, prey);
            log.info(marker, "{} was killed by {}.", prey, owner);
            hunger.addSatiety(preyAmount);
        }
    }


    private CanBeCached findPrey() {
        currentCatchProbability = 0.0;
        Coordinate coordinate = placement.getCoordinate();
        Cell cell = owner.getIsland().getCell(coordinate);
        CanBeCached prey = null;
        for (PossiblePrey possiblePrey : possiblePreys) {
            List<IslandEntity> listOfPreys = cell.getListOf(possiblePrey.getPreyClass());
            if (!listOfPreys.isEmpty()) {
                prey = (CanBeCached) getRandom(listOfPreys);
                currentCatchProbability = possiblePrey.getCatchProbability();
                break;
            }
        }
        return prey;
    }

    private boolean isPreyRunAway(CanBeCached prey) {
        Coordinate predatorCoordinate = placement.getCoordinate();
        Coordinate preyCoordinate = getAnotherAbilityFor(prey, Placement.class).getCoordinate();
        return !predatorCoordinate.equals(preyCoordinate);
    }

    private boolean isHuntFailed() {
        return currentCatchProbability < ThreadLocalRandom.current().nextDouble();
    }


}

