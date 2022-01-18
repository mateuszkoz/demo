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
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
class PolicyControllerTest {
    @Inject PolicyController policyController;
    private final String policyNumber = "LV20-02-100000-5";

    //@formatter:off
    @Test
    void loadValidPolicyFirstAcceptanceCase() {
        final HttpResponse<PremiumResponse> response = policyController.calculatePolicy(createValidPolicyFirstAcceptanceCase());
        assertEquals(HttpStatus.OK, response.status());
        assertEquals(policyNumber, Objects.requireNonNull(response.body()).policyNumber());
        assertEquals(200, response.code());
        assertEquals(2.28, Objects.requireNonNull(response.body()).premium());
    }
    //@formatter:on

    //@formatter:off
    @Test
    void loadValidPolicySecondAcceptanceCase() {
        final HttpResponse<PremiumResponse> response = policyController.calculatePolicy(createValidPolicySecondAcceptanceCase());
        assertEquals(HttpStatus.OK, response.status());
        assertEquals(policyNumber, Objects.requireNonNull(response.body()).policyNumber());
        assertEquals(200, response.code());
        assertEquals(17.13, Objects.requireNonNull(response.body()).premium());
    }
    //@formatter:on

    //@formatter:off
    @Test
    void loadValidComplexPolicy() {
        final HttpResponse<PremiumResponse> response = policyController.calculatePolicy(createValidComplexPolicy());
        assertEquals(HttpStatus.OK, response.status());
        assertEquals(policyNumber, Objects.requireNonNull(response.body()).policyNumber());
        assertEquals(200, response.code());
        assertEquals(4.56, Objects.requireNonNull(response.body()).premium());
    }
    //@formatter:on

    @Test
    void loadNullPolicyObjects() {
        basicRequestBodyValidationTest("calculatePolicy.policyDTO.policyObjects: At least one Policy Object is required", createNullPolicyObjects());
    }

    @Test
    void loadPolicyWithNegativeSumInsuredValue() {
        basicRequestBodyValidationTest("calculatePolicy.policyDTO.policyObjects[0].subObjects[0].sumInsured: Sum Insured must be grater than 0", createPolicyWithNegativeInsureValue());

    }

    private void basicRequestBodyValidationTest(String expected, PolicyDTO policyWithNegativeInsureValue) {
        ConstraintViolationException exception = assertThrows(
                ConstraintViolationException.class,
                () -> policyController.calculatePolicy(policyWithNegativeInsureValue)
        );
        assertEquals(expected, exception.getMessage());
    }

    //@formatter:off
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
    //@formatter:on

    //@formatter:off
    private PolicyDTO createValidPolicySecondAcceptanceCase() {
        return PolicyDTO.builder()
                .policyNumber(policyNumber)
                .policyStatus(PolicyStatus.APPROVED)
                .policyObjects(List.of(PolicyObjectDTO.builder()
                        .name("HOME")
                        .subObjects(List.of(
                                SubObjectDTO.builder()
                                        .riskType(RiskType.FIRE)
                                        .sumInsured(500.)
                                        .name("TV")
                                        .build(),
                                SubObjectDTO.builder()
                                        .riskType(RiskType.THEFT)
                                        .sumInsured(102.51)
                                        .name("TV")
                                        .build()))
                        .build()))
                .build();
    }
    //@formatter:on

    //@formatter:off
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
    //@formatter:on

    //@formatter:off
    private PolicyDTO createValidComplexPolicy() {
        final PolicyObjectDTO object = PolicyObjectDTO.builder()
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
                        .build();

        return PolicyDTO.builder()
                .policyNumber(policyNumber)
                .policyStatus(PolicyStatus.APPROVED)
                .policyObjects(List.of(object, object))
                .build();
    }
    //@formatter:on

    private PolicyDTO createNullPolicyObjects() {
        return PolicyDTO.builder()
                .policyNumber(policyNumber)
                .policyStatus(PolicyStatus.APPROVED)
                .build();
    }
}
