package com.javarush.pavlichenko.island.entities.abilities;

import com.javarush.pavlichenko.island.entities.island.Cell;
import com.javarush.pavlichenko.island.service.IslandEntityCreator;
import com.javarush.pavlichenko.island.enums.Gender;
import com.javarush.pavlichenko.island.exceptions.CellIsFilledException;
import com.javarush.pavlichenko.island.entities.abilities.sideclasses.AbilityParameter;
import com.javarush.pavlichenko.island.entities.abstr.IslandEntity;
import com.javarush.pavlichenko.island.entities.abstr.abilitymarkers.CanMultiply;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Getter
@Slf4j
public class Multiplication extends SomeAbility {

    private final Aging aiging;
    private final Placement placement;

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
        super(animal, Multiplication.class);
        this.aiging = getAnotherAbility(Aging.class);
        this.placement = getAnotherAbility(Placement.class);
    }

    @Override
    public void apply() {
        if (aiging.getAge() < reproductiveAge) {
            return;
        } else if (aiging.getAge() == reproductiveAge) {
            isActive = true;
            log.info(marker, "{} became sexually mature.", owner);
        }

        switch (gender) {
            case MALE -> fertilize();
            case FEMALE -> gestation();
        }
    }

    private void fertilize() {
        log.info(marker, "{} went hunting for females.", owner);
        CanMultiply female = findPartner();
        if (isNull(female)) {
            log.info(marker, "But {} did not find partner.", owner);
            return;
        }
        log.info(marker, "{} found partner: {}.", owner, female);
        synchronized (female.getLock()) {
            Placement herPlacement = getAnotherAbilityFor(female, Placement.class);
            if (!placement.getCoordinate().equals(herPlacement.getCoordinate())) {
                log.info(marker, "But {} was left by she {}.", owner, female);
                return;
            }
            if (female.isDead()) {
                log.info(marker, "But {} left sad and unsatisfied. She {} died.", owner, female);
                return;
            }
            Multiplication herMultiplication = getAnotherAbilityFor(female, Multiplication.class);
            herMultiplication.makePregnant();
            log.info(marker, "{} fertilized {}.", owner, female);
        }

    }

    private void giveBirth() {
        isActive = true;
        pregnancyStage = 0;
        List<IslandEntity> offspring = new ArrayList<>();
        try {
            log.info(marker, "{} try to give birth.", owner);
            for (int i = 0; i < broodSize; i++) {
                IslandEntity child = IslandEntityCreator.getInstance()
                        .create(owner.getClass(), placement.getCoordinate());
                offspring.add(child);
            }
        } catch (CellIsFilledException e) {

        } finally {
            if (offspring.isEmpty()) {
                log.info(marker, "{} could not give birth. Cell was filled.", owner);
            } else {
                log.info(marker, "{} gave birth for {}.", owner, offspring);
            }
        }
    }

    private void makePregnant() {
        isActive = false;
        pregnancyStage = 0;
        log.info(marker, "{} became pregnant.", owner);
    }

    private void gestation() {
        if (isActive) {
            return;
        }
        pregnancyStage += 1;
        log.info(marker, "{} pregnancy stage increased ({} of {}).", owner, pregnancyStage, pregnancyDelay);
        if (pregnancyStage == pregnancyDelay) {
            giveBirth();
        }
    }

    private CanMultiply findPartner() {
        Cell cell = owner.getIsland().getCell(placement.getCoordinate());
        List<IslandEntity> possiblePartners = cell.getListOf(owner.getClass());
        if (possiblePartners.isEmpty())
            return null;

        Optional<IslandEntity> partner = possiblePartners.stream()
                .filter(otherAnimal -> {
                    Multiplication otherMultiplication = otherAnimal.getAbility(key);
                    boolean isActive = otherMultiplication.isActive;
                    return (otherMultiplication.gender == Gender.FEMALE) && isActive;
                })
                .findFirst();

        return (CanMultiply) partner.orElse(null);

    }
}
