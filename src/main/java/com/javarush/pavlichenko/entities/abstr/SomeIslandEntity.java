package com.javarush.pavlichenko.entities.abstr;

import com.javarush.pavlichenko.entities.abilities.AbilityKey;
import com.javarush.pavlichenko.entities.abilities.Aiging;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanAge;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.entities.abilities.Ability;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


@NoArgsConstructor(force = true)
@Getter
@Slf4j
public abstract class SomeIslandEntity implements IslandEntity, CanAge {

    private final UUID id;
    private final Object lock;

    private Boolean isDead = false;

    @Setter
    private Island island;

    @Setter
    protected Coordinate coordinate;

    private final Map<AbilityKey, Ability> abilities = new TreeMap<>();

    // All entities must be created by IslandEntityCreator
    protected SomeIslandEntity(Island island, Coordinate coordinate) {

        this.id = UUID.randomUUID();
        this.lock = new Object();

        this.isDead = false;
        this.island = island;
        this.coordinate = coordinate;

        Aiging aiging = new Aiging(this);
        this.addAbility(aiging);
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
            island.getCell(coordinate).extract(this);
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
        return this.getClass().getSimpleName() + "{"+ id+"}";
    }

    private IslandEntity performLifeCycle() {

        for (Ability ability : abilities.values()) {
            if (this.isDead()) {
                break;
            }
            synchronized (this.getLock()) {
                ability.apply();
            }
        }

        return this;
    }


}
