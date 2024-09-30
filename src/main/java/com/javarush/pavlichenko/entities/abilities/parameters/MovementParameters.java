package com.javarush.pavlichenko.entities.abilities.parameters;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class MovementParameters implements AbilityParameters {
    private Integer speed;
}
