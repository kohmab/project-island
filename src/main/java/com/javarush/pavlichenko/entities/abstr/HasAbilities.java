package com.javarush.pavlichenko.entities.abstr;

import com.javarush.pavlichenko.entities.abilities.sideclasses.AbilityKey;
import com.javarush.pavlichenko.entities.abilities.Ability;

import java.util.Set;

public interface HasAbilities {

    <T extends Ability > T getAbility(AbilityKey key);

    void addAbility(Ability ability);

    Set<AbilityKey> getAllAbilityKeys();

}
