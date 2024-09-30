package com.javarush.pavlichenko.entities.abilities;

import com.javarush.pavlichenko.entities.abilities.hunt.PossiblePrey;
import com.javarush.pavlichenko.entities.island.Cell;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.abilities.parameters.AbilityParameter;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanHunt;
import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanBeCached;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Objects.isNull;

@Slf4j
@Getter
public class Hunt implements Ability {

    private final CanHunt predator;
    private final Hunger hunger;
    private final AbilityKey key;

    @AbilityParameter
    private List<PossiblePrey> possiblePreys;

    private Double currentCatchProbability;

    public Hunt(CanHunt predator) {
        this.predator = predator;
        this.key = AbilityKey.getKeyFor(this);
        this.hunger = (Hunger) predator.getAbility(AbilityKey.getKeyForClass(Hunger.class));

        // TODO move to another place;
        // Collections.sort(this.possiblePreys);
    }

    @Override
    public void apply() {
        if (!hunger.isHungry()) {
            log.debug("Predator {} is full.", predator);
            return;
        }

        log.debug("{} starts hunt.", predator);

        CanBeCached prey = findPrey();
        if (isNull(prey)) {
            log.debug("{} did not find a prey", predator);
            return;
        }

        synchronized (prey.getLock()) {
            log.debug("{} found a prey: {}.", predator, prey);


            if (isHuntFailed()) {
                log.debug("Hunt failed. {} has missed.", predator);
                return;
            }

            if (isPreyRunAway(prey)) {
                log.debug("Hunt failed. {} run away from {}.", prey, predator);
                return;
            }

            if (prey.isDead()) {
                log.debug("{}'s prey ({}) already dead.", predator, prey);
                return;
            }
            prey.die();

            Prey preyAbility = (Prey) (prey.getAbility(AbilityKey.getKeyForClass(Prey.class)));
            Double preyAmount = preyAbility.getFoodAmount();

            log.debug("{} killed {}.", predator, prey);
            hunger.addSatiety(preyAmount);
        }
    }


    private CanBeCached findPrey() {
        currentCatchProbability = 0.0;
        Coordinate coordinate = predator.getCoordinate();
        Cell cell = predator.getIsland().getCell(coordinate);
        CanBeCached prey = null;
        for (PossiblePrey possiblePrey : possiblePreys) {
            List<IslandEntity> listOfPreys = cell.getListOf(possiblePrey.getPreyClass());
            if (!listOfPreys.isEmpty()) {
                prey = (CanBeCached) listOfPreys.get(0);
                currentCatchProbability = possiblePrey.getCatchProbability();
                break;
            }
        }
        return prey;
    }

    private boolean isPreyRunAway(CanBeCached prey) {
        return !predator.getCoordinate().equals(prey.getCoordinate());
    }

    private boolean isHuntFailed() {
        return currentCatchProbability < ThreadLocalRandom.current().nextDouble();
    }


}

