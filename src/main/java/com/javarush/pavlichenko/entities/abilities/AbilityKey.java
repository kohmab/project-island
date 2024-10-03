package com.javarush.pavlichenko.entities.abilities;

import com.javarush.pavlichenko.entities.abilities.parameters.PriorityConfiguration;
import com.javarush.pavlichenko.enums.AbilityPriority;

import static java.util.Objects.isNull;

public class AbilityKey implements Comparable<AbilityKey> {

    private final Class<? extends Ability> clazz;
    private final AbilityPriority priority;

    public static AbilityKey getKeyFor(Ability ability) {
        return getKeyForClass(ability.getClass());
    }

    public static AbilityKey getKeyForClass(Class<? extends Ability> clazz) {
        return new AbilityKey(clazz, PriorityConfiguration.getPriorities().get(clazz));
    }


    @Override
    public int compareTo(AbilityKey o) {
        String thisRepresentation = this.priority.getValue() + this.clazz;
        String otherRepresentation = o.priority.getValue() + o.clazz;
        return thisRepresentation.compareTo(otherRepresentation);
    }

    @Override
    public int hashCode() {
        return clazz.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (isNull(obj) || !(obj instanceof AbilityKey other)) {
            return false;
        }
        return other.clazz.equals(this.clazz);
    }

    private AbilityKey(Class<? extends Ability> clazz, AbilityPriority priority) {
        this.clazz = clazz;
        this.priority = priority;
    }
}
