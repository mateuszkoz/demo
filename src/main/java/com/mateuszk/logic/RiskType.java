package com.mateuszk.logic;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public enum RiskType {
    //@formatter:off
    FIRE(new CoefficientConfig(
            List.of(
                    new CoefficientLevel(0., 0.014, true),
                    new CoefficientLevel(100., 0.024, false)
            )
    )),
    //@formatter:on
    //@formatter:off
    THEFT(new CoefficientConfig(
            List.of(
                    new CoefficientLevel(0., 0.11, true),
                    new CoefficientLevel(15., 0.05, true)
            )
    ));
    //@formatter:on

    private final CoefficientConfig config;

    RiskType(CoefficientConfig config) {
        this.config = config;
    }

    /**
     * Returns coefficient value for {@code Double} amount.
     *
     * @throws NoSuchElementException for negative amount value
     * @return Coefficient value {@code Double}
     */
    public Double getCoefficient(Double amount) throws NoSuchElementException {
        return config.getValue(amount).orElseThrow().value();
    }
}

record CoefficientConfig(List<CoefficientLevel> levels) {
    Optional<CoefficientLevel> getValue(Double amount) {
        return levels.stream()
                .filter(level -> level.inclusive() ? amount >= level.level() : amount > level.level())
                .max(Comparator.comparing(CoefficientLevel::level));
    }
}

record CoefficientLevel(Double level, Double value, boolean inclusive) {}
