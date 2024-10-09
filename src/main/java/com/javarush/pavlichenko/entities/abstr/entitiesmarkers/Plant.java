package com.javarush.pavlichenko.entities.abstr.entitiesmarkers;

import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanAge;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanGrow;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanBeEaten;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.HasPlace;

public interface Plant extends CanGrow, CanBeEaten, CanAge, HasPlace {
}
