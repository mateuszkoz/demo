package com.mateuszk.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response contains Policy Number and Premium value calculated for policy")
public record PremiumResponse(@Schema(example = "LV20-02-100000-5") String policyNumber, @Schema(example = "102.58") Double premium) {}
