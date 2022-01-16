package com.mateuszk.logic;

public enum RiskType {
    FIRE{
        @Override
        public Double getCoefficient(Double amount) {
            return amount > 100 ? 0.024 : 0.014;
        }
    },
    THEFT{
        @Override
        public Double getCoefficient(Double amount) {
            return amount >= 15 ? 0.05 : 0.11;
        }
    };

    public abstract Double getCoefficient(Double amount);
}
