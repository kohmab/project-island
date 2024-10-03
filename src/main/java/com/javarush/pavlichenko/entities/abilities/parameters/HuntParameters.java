package com.javarush.pavlichenko.entities.abilities.parameters;

import com.javarush.pavlichenko.entities.abilities.sideclasses.PossiblePrey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class HuntParameters implements AbilityParameters {
    private List<PossiblePrey> possiblePreys;

}
