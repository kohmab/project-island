package com.javarush.pavlichenko.entities.abilities.parameters;

import com.javarush.pavlichenko.entities.abilities.*;
import com.javarush.pavlichenko.enums.AbilityPriority;
import com.javarush.pavlichenko.entities.abilities.Ability;
import lombok.Getter;

import java.util.Map;


public class PriorityConfiguration {
    @Getter
    private final static Map<Class<? extends Ability>, AbilityPriority> priorities =
            Map.of(
                    Hunger.class, AbilityPriority.MAX,
                    Growth.class, AbilityPriority.MAX,
                    Hunt.class, AbilityPriority.HIGH,
                    EatingPlants.class, AbilityPriority.HIGH,
                    Multiplication.class, AbilityPriority.HIGH,
                    Movement.class, AbilityPriority.LOW,
                    Prey.class, AbilityPriority.PASSIVE_ABILITY,
                    Edible.class, AbilityPriority.PASSIVE_ABILITY
            );
}
