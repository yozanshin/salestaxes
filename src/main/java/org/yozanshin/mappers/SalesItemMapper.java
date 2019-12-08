package org.yozanshin.mappers;

import org.yozanshin.exceptions.InvalidInputException;
import org.yozanshin.model.Item;

import java.util.Arrays;

public class SalesItemMapper implements ItemMapper<String> {

    private static final String PRICE_DELIMITER = " at ";
    private static final String TOKEN_SEPARATOR = " ";
    private static final String IMPORTED_IDENTIFIER = "imported";
    private static final String CONCEPT_TOKEN_SEPARATOR = " ";

    private static final String INVALID_INPUT_PATTERN = "Invalid input '%s': %s";

    @Override
    public Item map(final String input) throws InvalidInputException {
        final String[] tokens = removeUnrequiredInfo(input).split(TOKEN_SEPARATOR);
        if (tokens.length < 3) {
            throw new InvalidInputException("Not enough info provided in input: " + input);
        }
        try {
            return Item.builder()
                    .amount(getAmountFromTokens(tokens))
                    .imported(getImportedFromTokens(tokens))
                    .concept(getConceptFromTokens(tokens))
                    .price(getPriceFromTokens(tokens))
                    .build();
        } catch (IllegalArgumentException ex) {
            throw new InvalidInputException(String.format(INVALID_INPUT_PATTERN, input, ex.getMessage()));
        }
    }

    private String removeUnrequiredInfo(final String input) throws IllegalArgumentException {
        if (!input.contains(PRICE_DELIMITER)) {
            throw new IllegalArgumentException("No price delimiter found");
        }
        return input.replaceAll(PRICE_DELIMITER, TOKEN_SEPARATOR);
    }

    private int getAmountFromTokens(final String[] tokens) throws IllegalArgumentException {
        try {
            return Integer.parseInt(tokens[0]);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid amount: " + tokens[0]);
        }
    }

    private boolean getImportedFromTokens(final String[] tokens) {
        return IMPORTED_IDENTIFIER.equals(tokens[1]);
    }

    private String getConceptFromTokens(final String[] tokens) throws IllegalArgumentException {
        final String[] conceptTokens = Arrays.copyOfRange(tokens, 1, tokens.length - 1);
        if (conceptTokens.length < 1) {
            throw new IllegalArgumentException("Concept not defined");
        }
        return String.join(CONCEPT_TOKEN_SEPARATOR, conceptTokens);
    }

    private float getPriceFromTokens(final String[] tokens) throws IllegalArgumentException {
        try {
            return Float.parseFloat(tokens[tokens.length - 1]);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid price format: " + tokens[tokens.length - 1]);
        }
    }
}
