package com.javarush.pavlichenko.entities.waysOfEating;

import com.javarush.pavlichenko.entities.creatures.AbstractAnimal;
import com.javarush.pavlichenko.interfaces.abilities.eating.WayOfEating;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractWayOfEating implements WayOfEating {
    protected final AbstractAnimal creature;
}
