package com.mateuszk.logic;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
class RiskTypeTest {

    @Test
    void getCoefficientForFireRisk() {
        assertEquals(0.014, RiskType.FIRE.getCoefficient(98.));
        assertEquals(0.014, RiskType.FIRE.getCoefficient(100.));
        assertEquals(0.024, RiskType.FIRE.getCoefficient(100.01));
        assertEquals(0.024, RiskType.FIRE.getCoefficient(120.01));
    }

    @Test
    void getCoefficientForTheftRisk() {
        assertEquals(0.11, RiskType.THEFT.getCoefficient(10.));
        assertEquals(0.05, RiskType.THEFT.getCoefficient(15.));
        assertEquals(0.05, RiskType.THEFT.getCoefficient(100.01));
    }

    @Test
    void getCoefficientForNegativeAmount() {
        assertThrows(
                NoSuchElementException.class,
                () -> RiskType.THEFT.getCoefficient(-100.)
        );
    }
}
