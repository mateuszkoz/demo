package com.mateuszk.model;

import com.mateuszk.dto.PolicyObjectDTO;
import com.mateuszk.dto.SubObjectDTO;
import com.mateuszk.logic.RiskType;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@MicronautTest
class PolicyObjectTest {

    @Test
    void insuredSumSplitByRisk() {
        PolicyObjectDTO mockedPolicyObject = Mockito.mock(PolicyObjectDTO.class);

        List<SubObjectDTO> value = List.of(
                    getMockedSubObject(RiskType.FIRE, 20.),
                    getMockedSubObject(RiskType.FIRE, 30.),
                    getMockedSubObject(RiskType.FIRE, 40.),
                    getMockedSubObject(RiskType.THEFT, 10.),
                    getMockedSubObject(RiskType.THEFT, 20.),
                    getMockedSubObject(RiskType.THEFT, 30.)
                );

        when(mockedPolicyObject.getSubObjects()).thenReturn(value);
        when(mockedPolicyObject.insuredSumSplitByRisk()).thenCallRealMethod();

        Map<RiskType, Double> expected = new HashMap<>();
        expected.put(RiskType.FIRE, 90.);
        expected.put(RiskType.THEFT, 60.);

        assertEquals(expected, mockedPolicyObject.insuredSumSplitByRisk());
    }

    private SubObjectDTO getMockedSubObject(RiskType riskType, Double sumInsured) {
        SubObjectDTO subObject = Mockito.mock(SubObjectDTO.class);
        when(subObject.getRiskType()).thenReturn(riskType);
        when(subObject.getSumInsured()).thenReturn(sumInsured);
        return subObject;
    }
}
