package com.javarush.pavlichenko.entities.abstr.entitiesmarkers;

import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanBeHungry;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanMove;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanMultiply;

public interface Animal extends CanBeHungry, CanMove, CanMultiply {
}
