package com.mateuszk.dto;

import com.mateuszk.dto.common.PolicyStatus;
import com.mateuszk.dto.common.RiskType;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Data
@Validated
@Introspected
public class PolicyDTO {

    @NotBlank(message = "Policy Number is required")
    @Pattern(regexp = "^[a-zA-Z]{2}+[0-9]{2}+(-[0-9]{2})+(-[0-9]{6})+(-[0-9]{1})\\z", message = "Policy number should looks like: AA00-00-000000-0")
    @Schema(example = "LV20-02-100000-5")
    private String policyNumber;

    @Pattern(regexp = "(REGISTERED|APPROVED)", message = "Policy Status needs to be set to one of the following values: REGISTERED, APPROVED")
    @Schema(example = "\"REGISTERED\"")
    private PolicyStatus policyStatus;

    @Valid
    @NotNull(message = "At least one Policy Object is required")
    private List<PolicyObject> policyObjects;
}

@Data
@Introspected
class PolicyObject {

    @NotBlank(message = "Policy Object Name is required")
    @Schema(example = "House")
    private String name;

    @Valid
    @NotNull(message = "At least one Policy Sub Object is required")
    private List<SubObject> subObjects;
}

@Data
@Introspected
class SubObject {

    @NotBlank(message = "Policy Sub Object Name is required")
    @Schema(example = "TV")
    private String name;

    @NotNull
    @DecimalMin(value = "0.01", message = "Sum Insured must be grater than 0")
    @Schema(example = "12.5")
    private Double sumInsured;

    @NotNull
    @Pattern(regexp = "(FIRE|THEFT)", message = "Risk Type needs to be set to one of the following values: FIRE, THEFT")
    @Schema(example = "FIRE")
    private RiskType riskType;
}
