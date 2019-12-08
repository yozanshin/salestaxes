package org.yozanshin.service;

public interface TaxesCalculator<T> {

    float calculateTaxes(final T item);
    float calculateAppliedTaxes(final float originalPrice, final float taxes);
}
