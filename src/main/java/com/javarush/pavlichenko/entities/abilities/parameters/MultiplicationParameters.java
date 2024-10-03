package com.javarush.pavlichenko.entities.abilities.parameters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class MultiplicationParameters implements AbilityParameters {
    private Integer pregnancyDelay;
    private Integer broodSize;
    private Integer reproductiveAge;

}
