package com.javarush.pavlichenko.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AbilityPriority {

    PASSIVE_ABILITY("Z"), LOW("D"), MEDIUM("C"), HIGH("B"), MAX("A");

    private final String value;
}
