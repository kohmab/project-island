package com.javarush.pavlichenko.entities.abilities;

import com.javarush.pavlichenko.entities.island.Cell;
import com.javarush.pavlichenko.service.IslandEntityCreator;
import com.javarush.pavlichenko.enums.Gender;
import com.javarush.pavlichenko.exceptions.CellIsFilledException;
import com.javarush.pavlichenko.entities.abilities.parameters.AbilityParameter;
import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanMultiply;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Getter
@Slf4j
public class Multiplication implements Ability {

    private final CanMultiply animal;
    private final AbilityKey key = AbilityKey.getKeyForClass(Multiplication.class);
    private final Aiging aiging;

    @AbilityParameter
    private Integer reproductiveAge;

    @AbilityParameter
    private Integer pregnancyDelay;

    @AbilityParameter
    private Integer broodSize;

    private final Gender gender = Gender.getRandom();

    private Boolean isActive = false;
    private Integer pregnancyStage = 0;

    public Multiplication(CanMultiply animal) {
        this.animal = animal;
        this.aiging = (Aiging) animal.getAbility(AbilityKey.getKeyForClass(Aiging.class));
    }

    @Override
    public void apply() {
        if (aiging.getAge() < reproductiveAge) {
            return;
        } else if (aiging.getAge() == reproductiveAge) {
            isActive = true;
            log.info("{} became sexually mature.", animal);
        }

        switch (gender) {
            case MALE -> fertilize();
            case FEMALE -> gestation();
        }
    }

    private void fertilize() {
        log.info("{} went hunting for females.", animal);
        CanMultiply female = findPartner();
        if (isNull(female)) {
            log.info("But {} did not find partner.", animal);
            return;
        }
        log.info("{} found partner: {}.", animal, female);
        synchronized (female.getLock()) {
            if (!animal.getCoordinate().equals(female.getCoordinate())) {
                log.info("But {} was left by she {}.", animal, female);
                return;
            }
            if (female.isDead()) {
                log.info("But {} left sad and unsatisfied. She {} died.", animal, female);
                return;
            }
            Multiplication herMultiplication = (Multiplication) female.getAbility(key);
            herMultiplication.makePregnant();
            log.info("{} fertilized {}.", animal, female);
        }

    }

    private void giveBirth() {
        isActive = true;
        pregnancyStage = 0;
        List<IslandEntity> offspring = new ArrayList<>();
        try {
            log.info("{} try to give birth.", animal);
            for (int i = 0; i < broodSize; i++) {
                IslandEntity child = IslandEntityCreator.getInstance()
                        .create(animal.getClass(), animal.getCoordinate());
                offspring.add(child);
            }
        } catch (CellIsFilledException e) {

        } finally {
            if (offspring.isEmpty()) {
                log.info("{} could not give birth. Cell was filled.", animal);
            } else {
                log.info("{} gave birth for {}.", animal, offspring);
            }
        }
    }

    private void makePregnant() {
        isActive = false;
        pregnancyStage = 0;
        log.info("{} became pregnant.", animal);
    }

    private void gestation() {
        if (isActive) {
            return;
        }
        pregnancyStage += 1;
        log.info("{} pregnancy stage increased ({} of {}).", animal, pregnancyStage, pregnancyDelay);
        if (pregnancyStage == pregnancyDelay) {
            giveBirth();
        }
    }

    private CanMultiply findPartner() {
        Cell cell = animal.getIsland().getCell(animal.getCoordinate());
        List<IslandEntity> possiblePartners = cell.getListOf(animal.getClass());
        if (possiblePartners.isEmpty())
            return null;

        Optional<IslandEntity> partner = possiblePartners.stream()
                .filter(otherAnimal -> {
                    Multiplication otherMultiplication = (Multiplication) otherAnimal.getAbility(key);
                    boolean isActive = otherMultiplication.isActive;
                    return (otherMultiplication.gender == Gender.FEMALE) && isActive;
                })
                .findFirst();

        return (CanMultiply) partner.orElse(null);

    }
}
