package org.yozanshin.service;

import org.yozanshin.model.Item;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.stream.Stream;

public class SalesTaxesCalculator implements TaxesCalculator<Item> {

    //This information is hardcoded for testing purposes, it should be loaded from application parameters or
    // external source
    private static final String[] BOOKS_CONCEPTS = new String[]{"book"};
    private static final String[] FOOD_CONCEPTS = new String[]{"chocolate"};
    private static final String[] MEDICAL_CONCEPTS = new String[]{"pill"};

    @Override
    public float calculateTaxes(final Item item) {
        float taxes = 0F;
        if (!isExempt(item.getConcept())) {
            taxes += 0.1F;
        }
        if (item.isImported()) {
            taxes += 0.05F;
        }
        return taxes;
    }

    @Override
    public float calculateAppliedTaxes(final float originalPrice, final float taxes) {
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.UP);
        return roundPrice(Float.parseFloat(df.format(originalPrice * taxes)));
    }

    private boolean isExempt(final String concept) {
        return Stream.of(BOOKS_CONCEPTS, FOOD_CONCEPTS, MEDICAL_CONCEPTS)
                .flatMap(Arrays::stream)
                .anyMatch(concept::contains);
    }

    //According to defined rules for this task
    private float roundPrice(final float price) {
        int n = (int) (price * 100F);
        int n0 = n % 10;
        if (n0 > 0 && n0 < 5) {
            n += (5 - n0);
        } else if (n0 > 5) {
            n += (10 - n0);
        }
        return (float) n / 100;
    }
}
