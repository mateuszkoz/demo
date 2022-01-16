package com.mateuszk.logic;

import com.mateuszk.model.Policy;
import com.mateuszk.model.PolicyObject;
import jakarta.inject.Singleton;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Singleton
public class PremiumCalculator {
    //@formatter:off
    public BigDecimal calculate(Policy policy) {
        return policy.getPolicyObjects()
                .parallelStream()
                .map(this::calculatePremiumForObject)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    private BigDecimal calculatePremiumForObject(PolicyObject policyObject) {
        return BigDecimal.valueOf(policyObject.insuredSumSplitByRisk().entrySet().parallelStream()
                .mapToDouble(this::multiplyByCoefficient)
                .sum()).setScale(2, RoundingMode.HALF_UP);
    }

    private double multiplyByCoefficient(Map.Entry<RiskType, Double> entry) {
        final Double amount = entry.getValue();
        return amount * entry.getKey().getCoefficient(amount);
    }
    //@formatter:on
}
