package com.javarush.pavlichenko.entities.abilities.parameters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class HungerParameters implements AbilityParameters{

    private Double maxSatiety; // TODO set according game rules
    private Double hungryRate;
    private Double satiety;
}
