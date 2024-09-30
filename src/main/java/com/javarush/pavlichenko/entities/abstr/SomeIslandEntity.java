package com.javarush.pavlichenko.entities.abstr;

import com.javarush.pavlichenko.entities.abilities.AbilityKey;
import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.entities.abilities.Ability;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;


@NoArgsConstructor(force = true)
@Getter
@Slf4j
public abstract class SomeIslandEntity implements IslandEntity {
    private final UUID id;
    private final Object lock;

    private Boolean isDead = false;

    @Setter
    private Island island;

    @Setter
    protected Coordinate coordinate;

    private final Map<AbilityKey, Ability> abilities = new TreeMap<>();

    public SomeIslandEntity(Island island, Coordinate coordinate) {

        this.id = UUID.randomUUID();
        this.lock = new Object();

        this.isDead = false;
        this.island = island;
        this.coordinate = coordinate;
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
