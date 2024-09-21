package com.javarush.pavlichenko.enums;

import com.javarush.pavlichenko.entities.island.Coordinate;
import lombok.Getter;

import java.util.concurrent.ThreadLocalRandom;

public enum Direction {
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, 1),
    DOWN(0, -1);

    @Getter
    private Coordinate direction;

    Direction(Integer xDir, Integer yDir) {
        this.direction = new Coordinate(xDir, yDir);
    }

    public static Direction getRandom() {
        {
            int choice = ThreadLocalRandom.current().nextInt(Direction.values().length);
            return Direction.values()[choice];
        }
    }
}
