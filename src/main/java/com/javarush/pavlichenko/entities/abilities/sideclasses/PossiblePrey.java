package com.javarush.pavlichenko.entities.abilities.sideclasses;

import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanBeCached;
import lombok.*;

@Getter
@Data
public class PossiblePrey implements Comparable<PossiblePrey> {
    private Class<? extends CanBeCached> preyClass;
    private Double catchProbability;

    @Override
    public int compareTo(PossiblePrey o) {
        return (int) (o.catchProbability - this.catchProbability) * 100;
    }


}
