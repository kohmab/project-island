package com.javarush.pavlichenko.entities.abilities.parameters;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class GrowthParameters implements AbilityParameters {
    private Double maxWeight;

    private Double weight;

    private Double growthRate;
}
