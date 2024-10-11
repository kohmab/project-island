package com.javarush.pavlichenko.island.entities.abilities;

import com.javarush.pavlichenko.island.entities.abilities.sideclasses.AbilityKey;
import com.javarush.pavlichenko.island.entities.island.Cell;
import com.javarush.pavlichenko.island.entities.abstr.abilitymarkers.CanBeEaten;
import com.javarush.pavlichenko.island.entities.abstr.entitiesmarkers.Herbivore;
import com.javarush.pavlichenko.island.entities.abstr.IslandEntity;
import com.javarush.pavlichenko.island.entities.island.parameters.GameSettings;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
@Getter
public class EatingPlants extends SomeAbility {

    private final Hunger hunger;
    private final Placement placement;

    private final List<Class<? extends IslandEntity>> knownPlants;

    public EatingPlants(Herbivore owner) {
        super(owner, EatingPlants.class);
        this.hunger = getAnotherAbility(Hunger.class);
        this.placement = getAnotherAbility(Placement.class);

        knownPlants = GameSettings.get().getInitialCreaturesCount().keySet().stream()
                .filter(CanBeEaten.class::isAssignableFrom)
                .toList();
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
            Edible edible = getAnotherAbilityFor(plant, Edible.class);
            Double realBiteSize = edible.getBite(desirableBiteSize);
            log.info(marker, "{} bit off amount of {} from the {}.", owner, realBiteSize, plant);
            hunger.addSatiety(realBiteSize);

        }

    }

    private CanBeEaten findPlant() {
        Cell cell = owner.getIsland().getCell(placement.getCoordinate());
        for (Class<? extends IslandEntity> plantClass : knownPlants) {
            List<IslandEntity> listOfPlants = cell.getListOf(plantClass);
            for (IslandEntity plant : listOfPlants) {
                if (!plant.isDead()){
                    return (CanBeEaten) plant;
                }
            }
        }
        return null;
    }
}
