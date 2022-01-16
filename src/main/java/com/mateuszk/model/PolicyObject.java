package com.mateuszk.model;

import com.mateuszk.logic.RiskType;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

public interface PolicyObject {
    List<? extends SubObject> getSubObjects();

    default Map<RiskType, Double> insuredSumSplitByRisk() {
        return getSubObjects().stream()
                .collect(groupingBy(SubObject::getRiskType, summingDouble(SubObject::getSumInsured)));
    }
}
