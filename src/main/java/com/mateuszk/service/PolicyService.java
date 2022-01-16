package com.mateuszk.service;

import com.mateuszk.dto.PolicyDTO;
import com.mateuszk.logic.PremiumCalculator;
import lombok.AllArgsConstructor;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.math.BigDecimal;

@Singleton
@AllArgsConstructor(onConstructor_={@Inject})
public class PolicyService {

    private final PremiumCalculator premiumCalculator;

    public BigDecimal calculate(PolicyDTO policy) {
        return premiumCalculator.calculate(policy);
    }
}
