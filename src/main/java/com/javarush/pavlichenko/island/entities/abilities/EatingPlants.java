package com.javarush.pavlichenko.island.entities.abilities;

import com.javarush.pavlichenko.island.entities.abilities.sideclasses.AbilityKey;
import com.javarush.pavlichenko.island.entities.island.Cell;
import com.javarush.pavlichenko.island.entities.abstr.abilitymarkers.CanBeEaten;
import com.javarush.pavlichenko.island.entities.abstr.entitiesmarkers.Herbivore;
import com.javarush.pavlichenko.island.entities.abstr.IslandEntity;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static java.util.Objects.isNull;

@Slf4j
@Getter
public class EatingPlants extends SomeAbility {

    private final Hunger hunger;
    private final Placement placement;

    public EatingPlants(Herbivore owner) {
        super(owner, EatingPlants.class);
        this.hunger = getAnotherAbility(Hunger.class);
        this.placement = getAnotherAbility(Placement.class);
    }


    @Override
    public void apply() {
        log.info(marker, "{} started eating plants.", owner);
        if (hunger.isNotHungry()) {
            log.info(marker, "But {} was not hungry.", owner);
            return;
        }
        CanBeEaten plant = findPlant();
        if (isNull(plant)) {
            log.info(marker, "But {} did not find any plants.", owner);
            return;
        }
        synchronized (plant.getLock()) {
            Double desirableBiteSize = hunger.getMaxSatiety() - hunger.getSatiety();
            Edible edible = plant.getAbility(AbilityKey.getKeyForClass(Edible.class));
            Double realBiteSize = edible.getBite(desirableBiteSize);
            log.info(marker, "{} bit off amount of {} from the {}.", owner, realBiteSize, plant);
            hunger.addSatiety(realBiteSize);

        }

    }

    private CanBeEaten findPlant() {
        Cell cell = owner.getIsland().getCell(placement.getCoordinate());
        Optional<IslandEntity> somePlant = cell.getAllEntities().stream().filter(e -> (e instanceof CanBeEaten) && !e.isDead()).findFirst();
        return (CanBeEaten) somePlant.orElse(null);
    }
}
