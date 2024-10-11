package com.javarush.pavlichenko.island.entities.abilities;

import com.javarush.pavlichenko.island.entities.abilities.sideclasses.AbilityKey;
import com.javarush.pavlichenko.island.entities.abstr.IslandEntity;
import lombok.Getter;
import lombok.NonNull;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

@Getter
public abstract class SomeAbility implements Ability {

    protected final Marker marker = MarkerFactory.getMarker("BIOGRAPHY");

    protected final IslandEntity owner;
    protected final AbilityKey key;

    protected <T extends Ability> T getAnotherAbility(Class<T> abilityType) {
        return owner.getAbility(AbilityKey.getKeyForClass(abilityType));
    }

    protected <T extends Ability> T getAnotherAbilityFor(IslandEntity entity, Class<T> abilityType) {
        return entity.getAbility(AbilityKey.getKeyForClass(abilityType));
    }

    protected SomeAbility(@NonNull IslandEntity owner, Class<? extends Ability> abilityType) {
        this.owner = owner;
        this.key = AbilityKey.getKeyForClass(abilityType);
        owner.addAbility(this);
    }

}
