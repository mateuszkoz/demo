package com.mateuszk.model;

import java.util.List;

public interface Policy {
    String getPolicyNumber();
    List<? extends PolicyObject> getPolicyObjects();
}
