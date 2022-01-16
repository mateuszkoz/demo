package com.mateuszk.logic;

import com.mateuszk.dto.PolicyDTO;
import com.mateuszk.dto.PolicyObjectDTO;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@MicronautTest
class PremiumCalculatorTest {

    @Inject PremiumCalculator calculator;

    @Test
    void calculate() {

        PolicyDTO mockedPolicy = Mockito.mock(PolicyDTO.class);

        //@formatter:off
        final List<PolicyObjectDTO> policyObjects = List.of(
                getPolicyObject(RiskType.FIRE, 100),
                getPolicyObject(RiskType.FIRE, 101),
                getPolicyObject(RiskType.THEFT, 14),
                getPolicyObject(RiskType.THEFT, 15)
        );
        //@formatter:on
        when(mockedPolicy.getPolicyObjects()).thenReturn(policyObjects);

        BigDecimal premium = calculator.calculate(mockedPolicy);

        assertEquals(premium, BigDecimal.valueOf(6.11));

    }

    private PolicyObjectDTO getPolicyObject(RiskType type, double amount) {
        PolicyObjectDTO mock = Mockito.mock(PolicyObjectDTO.class);
        when(mock.insuredSumSplitByRisk()).thenReturn(Map.of(type, amount));

        return mock;
    }
}
