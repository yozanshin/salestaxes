package org.yozanshin.mappers;

import org.junit.Before;
import org.junit.Test;
import org.yozanshin.exceptions.InvalidInputException;
import org.yozanshin.model.Item;

import static org.junit.Assert.assertEquals;


public class SalesItemMapperTest {

    private static final String INPUT_1 = "1 imported book at 15.50";
    private static final int INPUT_1_AMOUNT = 1;
    private static final boolean INPUT_1_IMPORTED = true;
    private static final String INPUT_1_CONCEPT = "imported book";
    private static final float INPUT_1_PRICE = 15.50F;

    private static final String INPUT_2 = "5 CD at 50.00";
    private static final int INPUT_2_AMOUNT = 5;
    private static final boolean INPUT_2_IMPORTED = false;
    private static final String INPUT_2_CONCEPT = "CD";
    private static final float INPUT_2_PRICE = 50.00F;

    private static final String INPUT_INVALID = "book at 10.00";
    private static final String INPUT_INVALID_AMOUNT = "book at 10.00";
    private static final String INPUT_INVALID_PRICE = "1 book at ten";


    private SalesItemMapper mapper;

    @Before
    public void setUp() {
        mapper = new SalesItemMapper();
    }

    @Test
    public void testMapStringImportedInout() {
        //GIVEN
        final Item expected = Item.builder()
                .amount(INPUT_1_AMOUNT)
                .imported(INPUT_1_IMPORTED)
                .concept(INPUT_1_CONCEPT)
                .price(INPUT_1_PRICE)
                .build();

        //WHEN
        final Item result = mapper.map(INPUT_1);

        //THEN
        assertEquals(expected, result);
    }

    @Test
    public void testMapStringNonImportedInput() {
        //GIVEN
        final Item expected = Item.builder()
                .amount(INPUT_2_AMOUNT)
                .imported(INPUT_2_IMPORTED)
                .concept(INPUT_2_CONCEPT)
                .price(INPUT_2_PRICE)
                .build();

        //WHEN
        final Item result = mapper.map(INPUT_2);

        //THEN
        assertEquals(expected, result);
    }

    @Test(expected = InvalidInputException.class)
    public void testMapStringInvalidInput() {
        //WHEN
        mapper.map(INPUT_INVALID);
    }

    @Test(expected = InvalidInputException.class)
    public void testMapStringInvalidAmountInput() {
        //WHEN
        mapper.map(INPUT_INVALID_AMOUNT);
    }

    @Test(expected = InvalidInputException.class)
    public void testMapStringPriceInput() {
        //WHEN
        mapper.map(INPUT_INVALID_PRICE);
    }
}