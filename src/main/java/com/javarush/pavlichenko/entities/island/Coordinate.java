package com.javarush.pavlichenko.entities.island;

import lombok.Data;

@Data
public class Coordinate {
    private final Integer x;
    private final Integer y;

    public Coordinate add(Coordinate other) {

        Integer newX = this.x + other.x;
        Integer newY = this.y + other.y;

        return new Coordinate(newX, newY);
    }
}
