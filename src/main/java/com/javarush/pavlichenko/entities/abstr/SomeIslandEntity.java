package com.javarush.pavlichenko.entities.abstr;

import com.javarush.pavlichenko.entities.abilities.Placement;
import com.javarush.pavlichenko.entities.abilities.sideclasses.AbilityKey;
import com.javarush.pavlichenko.entities.abilities.Aging;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanAge;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.HasPlace;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.entities.abilities.Ability;

import com.javarush.pavlichenko.enums.AbilityPriority;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;


@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public abstract class SomeIslandEntity implements IslandEntity, CanAge, HasPlace {

    private final UUID id;
    private final Object lock;

    private Boolean isDead = false;

    private final Island island;

    private final Map<AbilityKey, Ability> abilities = new TreeMap<>();

    // All entities must be created by IslandEntityCreator
    protected SomeIslandEntity(Island island) {

        this.id = UUID.randomUUID();
        this.lock = new Object();
        this.island = island;

        new Placement(this);
        new Aging(this);
    }

    @Override
    public IslandEntity call() {
        return performLifeCycle();
    }

    @Override
    public void die() {
        synchronized (this.lock) {
            if (this.isDead)
                return;

            this.isDead = true;
            log.debug("{} died.", this);
        }
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public Ability getAbility(AbilityKey key) {
        return abilities.get(key);
    }

    @Override
    public void addAbility(Ability ability) {
        abilities.put(AbilityKey.getKeyFor(ability), ability);
    }

    @Override
    public Set<AbilityKey> getAllAbilityKeys() {
        return abilities.keySet();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + id + "}";
    }

    private IslandEntity performLifeCycle() {

        for (Ability ability : abilities.values()) {
            if (this.isDead()) {
                break;
            }

            AbilityPriority priority = AbilityKey.getKeyFor(ability).getPriority();
            if (AbilityPriority.PASSIVE_ABILITY.equals(priority)) {
                continue;
            }

            synchronized (this.getLock()) {
                ability.apply();
            }
        }

        return this;
    }


}
