package org.yozanshin.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.yozanshin.mappers.ItemMapper;
import org.yozanshin.mappers.SalesItemMapper;
import org.yozanshin.model.Item;
import org.yozanshin.model.Output;

public class SalesTaxesServiceTest {

    private final ItemMapper<String> itemMapper = new SalesItemMapper();
    private final TaxesCalculator<Item> taxesCalculator = new SalesTaxesCalculator();
    private SalesTaxesService taxesService;

    @Before
    public void setUp() {
        taxesService = new SalesTaxesService(itemMapper, taxesCalculator);
    }

    @Test
    public void testCase_1() {
        //GIVEN
        String[] input = new String[]{
                "1 book at 12.49",
                "1 music CD at 14.99",
                "1 chocolate bar at 0.85"};
        String expectedOutputString =
                "1 book: 12.49\n" +
                        "1 music CD: 16.49\n" +
                        "1 chocolate bar: 0.85\n" +
                        "Sales Taxes: 1.50\n" +
                        "Total: 29.83";

        //WHEN
        Output<Item> output = taxesService.applyTaxes(input);
        Item item0 = output.getItems().get(0);
        Item item1 = output.getItems().get(1);
        Item item2 = output.getItems().get(2);

        //THEN
        Assert.assertEquals(1, item0.getAmount());
        Assert.assertEquals("book", item0.getConcept());
        Assert.assertEquals(12.49F, item0.getPrice(), 0.0);

        Assert.assertEquals(1, item1.getAmount());
        Assert.assertEquals("music CD", item1.getConcept());
        Assert.assertEquals(16.49F, item1.getPrice(), 0.0);

        Assert.assertEquals(1, item2.getAmount());
        Assert.assertEquals("chocolate bar", item2.getConcept());
        Assert.assertEquals(0.85F, item2.getPrice(), 0.0);

        Assert.assertEquals(1.5F, output.getTaxes(), 0.0);
        Assert.assertEquals(29.83F, output.getTotal(), 0.0);

        Assert.assertEquals(expectedOutputString, output.toString());
    }

    @Test
    public void testCase_2() {
        //GIVEN
        String[] input = new String[]{
                "1 imported box of chocolates at 10.00",
                "1 imported bottle of perfume at 47.50"
        };
        String expectedOutputString =
                "1 imported box of chocolates: 10.50\n" +
                        "1 imported bottle of perfume: 54.65\n" +
                        "Sales Taxes: 7.65\n" +
                        "Total: 65.15";

        //WHEN
        Output<Item> output = taxesService.applyTaxes(input);
        Item item0 = output.getItems().get(0);
        Item item1 = output.getItems().get(1);

        //THEN
        Assert.assertEquals(1, item0.getAmount());
        Assert.assertEquals("imported box of chocolates", item0.getConcept());
        Assert.assertEquals(10.50F, item0.getPrice(), 0.0);

        Assert.assertEquals(1, item1.getAmount());
        Assert.assertEquals("imported bottle of perfume", item1.getConcept());
        Assert.assertEquals(54.65F, item1.getPrice(), 0.0);

        Assert.assertEquals(7.65F, output.getTaxes(), 0.0);
        Assert.assertEquals(65.15F, output.getTotal(), 0.0);

        Assert.assertEquals(expectedOutputString, output.toString());
    }

    @Test
    public void testCase_3() {

        //GIVEN
        String[] input = new String[]{
                "1 imported bottle of perfume at 27.99",
                "1 bottle of perfume at 18.99",
                "1 packet of headache pills at 9.75",
                "1 imported box of chocolates at 11.25"
        };
        String expectedOutputString =
                "1 imported bottle of perfume: 32.19\n" +
                        "1 bottle of perfume: 20.89\n" +
                        "1 packet of headache pills: 9.75\n" +
                        "1 imported box of chocolates: 11.85\n" +
                        "Sales Taxes: 6.70\n" +
                        "Total: 74.68";

        //WHEN
        Output<Item> output = taxesService.applyTaxes(input);
        Item item0 = output.getItems().get(0);
        Item item1 = output.getItems().get(1);
        Item item2 = output.getItems().get(2);
        Item item3 = output.getItems().get(3);

        //THEN
        Assert.assertEquals(1, item0.getAmount());
        Assert.assertEquals("imported bottle of perfume", item0.getConcept());
        Assert.assertEquals(32.19F, item0.getPrice(), 0.0);

        Assert.assertEquals(1, item1.getAmount());
        Assert.assertEquals("bottle of perfume", item1.getConcept());
        Assert.assertEquals(20.89F, item1.getPrice(), 0.0);

        Assert.assertEquals(1, item2.getAmount());
        Assert.assertEquals("packet of headache pills", item2.getConcept());
        Assert.assertEquals(9.75F, item2.getPrice(), 0.0);

        Assert.assertEquals(1, item3.getAmount());
        Assert.assertEquals("imported box of chocolates", item3.getConcept());
        Assert.assertEquals(11.85F, item3.getPrice(), 0.0);

        Assert.assertEquals(6.70F, output.getTaxes(), 0.0);
        Assert.assertEquals(74.68F, output.getTotal(), 0.0);
    }
}