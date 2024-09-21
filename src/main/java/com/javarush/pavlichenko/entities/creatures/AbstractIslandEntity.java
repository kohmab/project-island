package com.javarush.pavlichenko.entities.creatures;

import com.javarush.pavlichenko.entities.island.Coordinate;
import com.javarush.pavlichenko.entities.island.Island;
import com.javarush.pavlichenko.enums.Gender;
import com.javarush.pavlichenko.interfaces.abilities.Ability;

import com.javarush.pavlichenko.interfaces.IslandEntity;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor(force = true)
@Getter
public abstract class AbstractIslandEntity implements IslandEntity {
    private final UUID id;
    private final Gender sex;

    private Boolean isDead = false;
    private Island island;
    protected Coordinate coordinate;

    public AbstractIslandEntity(Gender sex, Island island, Coordinate coordinate) {
        this.isDead = false;
        this.id = UUID.randomUUID();

        this.sex = sex;
        this.island = island;
        this.coordinate = coordinate;
    }

    @Getter(AccessLevel.NONE)
    protected final List<Ability> abilities = new ArrayList<>();

    @Override
    public void die() {
        this.isDead = true;
        Thread.currentThread().interrupt();
    }

    private boolean performLifeCycle() {
        if (this.isDead || this.checkDeath()) {
            this.die();
    } //@TODO implement with abilities


        for (Ability ability : abilities)
            ability.use();

        return true;

    }

    @Override
    public Boolean call(){
        return performLifeCycle();
    }
}
