package com.javarush.pavlichenko.island.entities.abstr.entitiesmarkers;

import com.javarush.pavlichenko.island.entities.abstr.abilitymarkers.CanBeCached;
import com.javarush.pavlichenko.island.entities.abstr.abilitymarkers.CanEatPlants;

public interface Herbivore extends Animal, CanEatPlants, CanBeCached {
}
