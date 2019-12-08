package org.yozanshin.service;

import lombok.RequiredArgsConstructor;
import org.yozanshin.exceptions.InvalidInputException;
import org.yozanshin.mappers.ItemMapper;
import org.yozanshin.model.Item;
import org.yozanshin.model.Output;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class SalesTaxesService implements TaxesService<String, Item> {

    private final ItemMapper<String> itemMapper;
    private final TaxesCalculator<Item> taxexCalculator;

    @Override
    public Output<Item> applyTaxes(final String[] input) throws InvalidInputException {

        Output<Item> output = new Output<>();

        output.setItems(buildItems(input).map(item -> this.applyTaxesToItem(item, output)).collect(Collectors.toList()));

        return output;
    }

    private Stream<Item> buildItems(final String[] inputArray) throws InvalidInputException {
        return Arrays.stream(inputArray).map(itemMapper::map);
    }

    private Item applyTaxesToItem(final Item item, final Output output) {

        float appliedTaxes = getAppliedTaxes(item);
        float updatedPrice = item.getPrice() + appliedTaxes;

        Item newItem = Item.builder()
                .amount(item.getAmount())
                .imported(item.isImported())
                .concept(item.getConcept())
                .price(updatedPrice)
                .build();

        //Updates Output with taxes and price
        output.addPrice(newItem.getPrice());
        output.addTax(appliedTaxes);

        return newItem;
    }

    private float getAppliedTaxes(final Item item) {
        float taxes = taxexCalculator.calculateTaxes(item);
        if (taxes != 0F) {
            return taxexCalculator.calculateAppliedTaxes(item.getPrice(), taxes);
        } else {
            return 0F;
        }
    }
}
