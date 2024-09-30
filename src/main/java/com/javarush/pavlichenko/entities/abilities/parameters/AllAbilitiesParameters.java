package com.javarush.pavlichenko.entities.abilities.parameters;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class AllAbilitiesParameters {
    private GrowthParameters growth;
    private HungerParameters hunger;
    private HuntParameters hunt;
    private MovementParameters movement;
    private MultiplicationParameters multiplication;
    private PreyParameters prey;
}
