package com.javarush.pavlichenko.entities.abilities;

import com.javarush.pavlichenko.entities.island.Cell;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanBeEaten;
import com.javarush.pavlichenko.entities.abstr.entitiesmarkers.Herbivore;
import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import static java.util.Objects.isNull;

@Slf4j
@Getter
public class EatingPlants implements Ability {
    private final Herbivore herbivore;
    private final AbilityKey key;
    private final Hunger hunger;

    public EatingPlants(Herbivore herbivore) {
        this.herbivore = herbivore;
        this.key = AbilityKey.getKeyFor(this);
        this.hunger = (Hunger) herbivore.getAbility(AbilityKey.getKeyForClass(Hunger.class));
    }


    @Override
    public void apply() {
        if (!hunger.isHungry()) {
            log.debug("{} is not hungry", herbivore);
            return;
        }
        log.debug("{} starts eating plants.", herbivore);
        CanBeEaten plant = findPlant();
        if (isNull(plant)) {
            log.debug("{} did not find any plants", herbivore);
            return;
        }
        synchronized (plant.getLock()) {
            Double desirableBiteSize = hunger.getMaxSatiety() - hunger.getSatiety();
            Edible edible = (Edible) plant.getAbility(AbilityKey.getKeyForClass(Edible.class));
            Double realBiteSize = edible.getBite(desirableBiteSize);
            log.debug("{} bit off amount of {} from the {}", herbivore, realBiteSize, plant);
            hunger.addSatiety(realBiteSize);

        }


    }

    private CanBeEaten findPlant() {
        Cell cell = herbivore.getIsland().getCell(herbivore.getCoordinate());
        Optional<IslandEntity> somePlant = cell.getAllEntities().stream().filter(e -> (e instanceof CanBeEaten) && !e.isDead()).findFirst();
        return (CanBeEaten) somePlant.orElse(null);
    }
}
