package com.javarush.pavlichenko.island.entities.abstr.entitiesmarkers;

import com.javarush.pavlichenko.island.entities.abstr.abilitymarkers.CanAge;
import com.javarush.pavlichenko.island.entities.abstr.abilitymarkers.CanGrow;
import com.javarush.pavlichenko.island.entities.abstr.abilitymarkers.CanBeEaten;
import com.javarush.pavlichenko.island.entities.abstr.abilitymarkers.HasPlace;

public interface Plant extends CanGrow, CanBeEaten, CanAge, HasPlace {
}
