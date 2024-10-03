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
                    Aiging.class, AbilityPriority.MAX,
                    Hunger.class, AbilityPriority.HIGH,
                    Growth.class, AbilityPriority.HIGH,
                    Hunt.class, AbilityPriority.MEDIUM,
                    EatingPlants.class, AbilityPriority.MEDIUM,
                    Multiplication.class, AbilityPriority.MEDIUM,
                    Movement.class, AbilityPriority.LOW,
                    Prey.class, AbilityPriority.PASSIVE_ABILITY,
                    Edible.class, AbilityPriority.PASSIVE_ABILITY
            );
}
