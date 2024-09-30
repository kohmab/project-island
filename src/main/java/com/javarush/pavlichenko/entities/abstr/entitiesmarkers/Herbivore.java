package com.javarush.pavlichenko.entities.abstr.entitiesmarkers;

import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanBeCached;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanEatPlants;

public interface Herbivore extends Animal, CanEatPlants, CanBeCached {
}
