package com.mateuszk.dto;

import com.mateuszk.logic.RiskType;
import com.mateuszk.model.SubObject;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@Introspected
public class SubObjectDTO implements SubObject {

    @NotBlank(message = "Policy Sub Object Name is required")
    @Schema(example = "TV")
    private String name;

    @NotNull
    @DecimalMin(value = "0.01", message = "Sum Insured must be grater than 0")
    @Schema(example = "12.5")
    private Double sumInsured;

    @NotNull(message = "Risk Type needs to be set to one of the following values: FIRE, THEFT")
    @Pattern(regexp = "(FIRE|THEFT)")
    @Schema(example = "FIRE")
    private RiskType riskType;
}
