package com.mateuszk.dto;

import com.mateuszk.dto.common.PolicyStatus;
import com.mateuszk.model.Policy;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@Builder
@Validated
@Introspected
public class PolicyDTO implements Policy {

    @NotBlank(message = "Policy Number is required")
    @Pattern(regexp = "^[a-zA-Z]{2}+[0-9]{2}+(-[0-9]{2})+(-[0-9]{6})+(-[0-9]{1})\\z", message = "Policy number should looks like: AA00-00-000000-0")
    @Schema(example = "LV20-02-100000-5")
    private String policyNumber;

    @Pattern(regexp = "(REGISTERED|APPROVED)", message = "Policy Status needs to be set to one of the following values: REGISTERED, APPROVED")
    @Schema(example = "\"REGISTERED\"")
    private PolicyStatus policyStatus;

    @Valid
    @NotNull(message = "At least one Policy Object is required")
    private List<PolicyObjectDTO> policyObjects;
}
