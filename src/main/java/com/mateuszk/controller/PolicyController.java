package com.mateuszk.controller;

import com.mateuszk.dto.PolicyDTO;
import com.mateuszk.dto.response.PremiumResponse;
import com.mateuszk.service.PolicyService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static io.micronaut.http.MediaType.APPLICATION_JSON;

@Validated
@Controller(value = "/policy", produces = MediaType.APPLICATION_JSON, consumes = APPLICATION_JSON)
@AllArgsConstructor(onConstructor_={@Inject})
public class PolicyController {

    private final PolicyService policyService;

    //@formatter:off
    @Post(value = "/calculate", produces = APPLICATION_JSON)
    @Operation(summary = "Calculate Policy Premium", description = "Allows to upload a policy and calculate policy premium")
    public HttpResponse<PremiumResponse> calculatePolicy(@Body @Valid @NotNull PolicyDTO policyDTO) {
        return HttpResponse
                .status(HttpStatus.OK)
                .body(new PremiumResponse(policyDTO.getPolicyNumber(), policyService.calculate(policyDTO).doubleValue()));
    }
    //@formatter:on
}
