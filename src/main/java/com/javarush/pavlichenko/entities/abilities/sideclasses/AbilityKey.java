package com.javarush.pavlichenko.entities.abilities.sideclasses;

import com.javarush.pavlichenko.entities.abilities.Ability;
import com.javarush.pavlichenko.enums.AbilityPriority;
import lombok.Getter;

import static java.util.Objects.isNull;
@Getter
public class AbilityKey implements Comparable<AbilityKey> {

    private final Class<? extends Ability> type;
    private final AbilityPriority priority;

    public static AbilityKey getKeyFor(Ability ability) {
        return getKeyForClass(ability.getClass());
    }

    public static AbilityKey getKeyForClass(Class<? extends Ability> clazz) {
        return new AbilityKey(clazz, PriorityConfiguration.getPriorities().get(clazz));
    }


    @Override
    public int compareTo(AbilityKey o) {
        String thisRepresentation = this.priority.getValue() + this.type;
        String otherRepresentation = o.priority.getValue() + o.type;
        return thisRepresentation.compareTo(otherRepresentation);
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (isNull(obj) || !(obj instanceof AbilityKey other)) {
            return false;
        }
        return other.type.equals(this.type);
    }

    private AbilityKey(Class<? extends Ability> clazz, AbilityPriority priority) {
        this.type = clazz;
        this.priority = priority;
    }
}
