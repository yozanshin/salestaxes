package org.yozanshin.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
public class Output<T> {

    private static String STRING_PATTERN = "%s\nSales Taxes: %.2f\nTotal: %.2f";

    @Setter
    protected List<T> items;
    protected float taxes;
    protected float total;

    public final void addTax(final float tax) {
        taxes += tax;
    }

    public final void addPrice(final float price) {
        total += price;
    }

    @Override
    public String toString() {
        return String.format(
                STRING_PATTERN,
                String.join("\n", items.stream().map(Object::toString).toArray(String[]::new)),
                taxes,
                total);
    }
}
