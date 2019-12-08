package org.yozanshin.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class Item {

    private static String STRING_FORMAT = "%s %s: %.2f";

    private final int amount;
    private final boolean imported;
    private final String concept;
    private float price;

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, amount, concept, price);
    }
}
