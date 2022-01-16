package com.mateuszk.controller;

import com.mateuszk.dto.PolicyDTO;
import com.mateuszk.dto.PolicyObjectDTO;
import com.mateuszk.dto.SubObjectDTO;
import com.mateuszk.dto.common.PolicyStatus;
import com.mateuszk.dto.response.PremiumResponse;
import com.mateuszk.logic.RiskType;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
class PolicyControllerTest {
    @Inject PolicyController policyController;
    private final String policyNumber;

    {
        policyNumber = "LV20-02-100000-5";
    }

    @Test
    void loadValidPolicy() {
        final HttpResponse<PremiumResponse> response = policyController.calculatePolicy(createValidPolicyFirstAcceptanceCase());
        assertEquals(HttpStatus.OK, response.status());
        assertEquals(policyNumber, Objects.requireNonNull(response.body()).policyNumber());
        assertEquals(200, response.code());
        assertEquals(BigDecimal.valueOf(2.28), Objects.requireNonNull(response.body()).premium());
    }

    @Test
    void loadNullPolicyObjects() {
        basicRequestBodyValidationTest("calculatePolicy.policyDTO.policyObjects: At least one Policy Object is required", createNullPolicyObjects());
    }

    @Test
    void loadPolicyWithNegativeSumInsuredValue() {
        basicRequestBodyValidationTest("calculatePolicy.policyDTO.policyObjects[0].subObjects[0].sumInsured: Sum Insured must be grater than 0", createPolicyWithNegativeInsureValue());

    }

    private PolicyDTO createValidPolicyFirstAcceptanceCase() {
        return PolicyDTO.builder()
                .policyNumber(policyNumber)
                .policyStatus(PolicyStatus.APPROVED)
                .policyObjects(List.of(PolicyObjectDTO.builder()
                        .name("HOME")
                        .subObjects(List.of(
                                SubObjectDTO.builder()
                                    .riskType(RiskType.FIRE)
                                    .sumInsured(100.)
                                    .name("TV")
                                    .build(),
                                SubObjectDTO.builder()
                                    .riskType(RiskType.THEFT)
                                    .sumInsured(8.)
                                    .name("TV")
                                    .build()))
                        .build()))
                .build();
    }

    private void basicRequestBodyValidationTest(String expected, PolicyDTO policyWithNegativeInsureValue) {
        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> policyController.calculatePolicy(policyWithNegativeInsureValue)
        );
        assertEquals(expected, exception.getMessage());
    }

    private PolicyDTO createPolicyWithNegativeInsureValue() {
        return PolicyDTO.builder()
                .policyNumber(policyNumber)
                .policyStatus(PolicyStatus.APPROVED)
                .policyObjects(List.of(PolicyObjectDTO.builder()
                        .name("HOME")
                        .subObjects(List.of(SubObjectDTO.builder()
                                .riskType(RiskType.FIRE)
                                .sumInsured(-100.)
                                .name("TV")
                                .build()))
                        .build()))
                .build();
    }

    private PolicyDTO createNullPolicyObjects() {
        return PolicyDTO.builder()
                .policyNumber(policyNumber)
                .policyStatus(PolicyStatus.APPROVED)
                .build();
    }
}
