package com.javarush.pavlichenko.entities.abilities;

import com.javarush.pavlichenko.entities.concrete.TestChild;
import com.javarush.pavlichenko.entities.island.Cell;
import com.javarush.pavlichenko.entities.islandentitycreator.IslandEntityCreator;
import com.javarush.pavlichenko.enums.Gender;
import com.javarush.pavlichenko.exceptions.CellIsFilledException;
import com.javarush.pavlichenko.entities.abilities.parameters.AbilityParameter;
import com.javarush.pavlichenko.entities.abstr.IslandEntity;
import com.javarush.pavlichenko.entities.abstr.abilitymarkers.CanMultiply;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Getter
@Slf4j
public class Multiplication implements Ability {

    private final CanMultiply animal;
    private final AbilityKey key = AbilityKey.getKeyForClass(Multiplication.class);

    @AbilityParameter
    private Integer pregnancyDelay;

    private final Gender gender = Gender.getRandom();

    private Boolean isPregnant = false;
    private Integer pregnancyStage = 0;

    public Multiplication(CanMultiply animal) {
        this.animal = animal;
    }

    @Override
    public void apply() {
        switch (gender) {
            case MALE -> fertilize();
            case FEMALE -> gestation();
        }
    }

    private void fertilize() {
        CanMultiply female = findPartner();
        if (isNull(female)) {
            log.debug("{} did not find partner.", animal);
            return;
        }
        log.debug("{} found partner: {}", animal, female);
        synchronized (female.getLock()) {
            if (!animal.getCoordinate().equals(female.getCoordinate())) {
                log.debug("Female {} left from {}.", female, animal);
                return;
            }
            if (female.isDead()) {
                log.debug("Female {} die.", female);
                return;
            }
            Multiplication herMultiplication = (Multiplication) female.getAbility(key);
            herMultiplication.makePregnant();
            log.debug("{} fertilized {}", animal, female);
        }

    }

    private void giveBirth() {
        isPregnant = false;
        pregnancyStage = 0;
        try {
            log.debug("{} trying to give birth", animal);
            IslandEntity child = IslandEntityCreator.getInstance()
                    .create(animal.getClass(), animal.getCoordinate());
            log.debug("{} give birth for {}", animal, child);
        } catch (CellIsFilledException e) {
            log.debug("{} could not give birth. Cell is filled", animal);
        }
    }

    private void makePregnant() {
        isPregnant = true;
        pregnancyStage = 0;
    }

    private void gestation() {
        if (!isPregnant) {
            return;
        }
        pregnancyStage += 1;
        log.debug("Female's {} pregnancy stage increased ({} of {})", animal, pregnancyStage, pregnancyDelay);
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
                    boolean notPregnant = !otherMultiplication.isPregnant;
                    return (otherMultiplication.gender == Gender.FEMALE) && notPregnant;
                })
                .findFirst();

        return (CanMultiply) partner.orElse(null);

    }
}
