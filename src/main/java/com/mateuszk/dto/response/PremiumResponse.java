package com.mateuszk.dto.response;

import java.math.BigDecimal;

public record PremiumResponse(String policyNumber, String requestStatus, BigDecimal premium) {}