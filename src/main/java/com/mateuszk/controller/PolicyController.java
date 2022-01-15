package com.mateuszk.controller;

import com.mateuszk.dto.PolicyDTO;
import com.mateuszk.dto.response.PremiumResponse;
import com.mateuszk.service.PolicyService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

import javax.inject.Inject;
import javax.validation.Valid;

import static io.micronaut.http.MediaType.APPLICATION_JSON;

@Validated
@Controller(value = "/policy", produces = MediaType.APPLICATION_JSON, consumes = APPLICATION_JSON)
@AllArgsConstructor(onConstructor_={@Inject})
public class PolicyController {

    private PolicyService policyService;

    @Post(value = "/calculate")
    @Operation(summary = "Calculate Policy Premium", description = "Allows to upload a policy and calculate policy premium")
    public HttpResponse<PremiumResponse> calculatePolicy(@Body @Valid PolicyDTO dto) {
        policyService.calculate(dto);
        return HttpResponse.accepted();
    }
}
