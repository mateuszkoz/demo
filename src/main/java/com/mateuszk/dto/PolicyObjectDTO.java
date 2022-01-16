package com.mateuszk.dto;

import com.mateuszk.model.PolicyObject;
import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@Introspected
public class PolicyObjectDTO implements PolicyObject {

    @NotBlank(message = "Policy Object Name is required")
    @Schema(example = "House")
    private String name;

    @Valid
    @NotEmpty(message = "At least one Policy Sub Object is required")
    private List<SubObjectDTO> subObjects;
}
