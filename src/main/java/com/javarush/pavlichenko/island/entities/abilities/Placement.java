package com.javarush.pavlichenko.island.entities.abilities;

import com.javarush.pavlichenko.island.entities.abstr.abilitymarkers.HasPlace;
import com.javarush.pavlichenko.island.entities.island.Cell;
import com.javarush.pavlichenko.island.entities.island.Coordinate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class Placement extends SomeAbility {

    @Getter
    @Setter(value = AccessLevel.PROTECTED)
    protected Coordinate coordinate;

    public Cell getCell() {
        return owner.getIsland().getCell(coordinate);
    }

    public Placement(HasPlace owner) {
        super(owner, Placement.class);
    }

    @Override
    public void apply() {

    }
}
