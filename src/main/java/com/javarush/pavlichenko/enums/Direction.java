package com.javarush.pavlichenko.enums;

import com.javarush.pavlichenko.entities.island.Coordinate;
import lombok.Getter;

@Getter
public enum Direction {
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, 1),
    DOWN(0, -1);

    private final Coordinate direction;

    Direction(Integer xDir, Integer yDir) {
        this.direction = new Coordinate(xDir, yDir);
    }

}
