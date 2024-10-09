package com.javarush.pavlichenko.entities.island;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import static java.util.Objects.isNull;

@Getter
@RequiredArgsConstructor
@ToString
public class Coordinate {
    private final Integer x;
    private final Integer y;

    public Coordinate add(Coordinate other) {

        Integer newX = this.x + other.x;
        Integer newY = this.y + other.y;

        return new Coordinate(newX, newY);
    }

    @Override
    public int hashCode() {
        return x.hashCode() + 31 * y.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (isNull(obj))
            return false;
        if (obj instanceof Coordinate other)
            return other.x.equals(x) && other.y.equals(y);
        return false;
    }
}
