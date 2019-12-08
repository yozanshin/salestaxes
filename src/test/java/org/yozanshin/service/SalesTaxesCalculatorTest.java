package org.yozanshin.service;

import org.junit.Before;
import org.junit.Test;
import org.yozanshin.model.Item;

import static org.junit.Assert.assertEquals;

public class SalesTaxesCalculatorTest {

    private SalesTaxesCalculator taxesCalculator;

    @Before
    public void setUp() {
        taxesCalculator = new SalesTaxesCalculator();
    }

    @Test
    public void testCalculateTaxes() {
        //Given
        Item bookItem = Item.builder()
                .price(0F)
                .concept("book")
                .imported(false)
                .amount(1)
                .build();
        Item foodItem = Item.builder()
                .price(0F)
                .concept("box of chocolates")
                .imported(false)
                .amount(1)
                .build();
        Item medicalItem = Item.builder()
                .price(0F)
                .concept("packet of headache pills")
                .imported(false)
                .amount(1)
                .build();
        Item nonExemptItem = Item.builder()
                .price(0F)
                .concept("bottle of perfume")
                .imported(false)
                .amount(1)
                .build();

        //When
        float bookItemTaxes = taxesCalculator.calculateTaxes(bookItem);
        float foodItemTaxes = taxesCalculator.calculateTaxes(foodItem);
        float medicalItemTaxes = taxesCalculator.calculateTaxes(medicalItem);
        float nonExemptItemTaxes = taxesCalculator.calculateTaxes(nonExemptItem);

        //Then
        assertEquals(0F, bookItemTaxes, 0.0);
        assertEquals(0F, foodItemTaxes, 0.0);
        assertEquals(0F, medicalItemTaxes, 0.0);
        assertEquals(0.1F, nonExemptItemTaxes, 0.0);
    }

    @Test
    public void testCalculateAppliedTaxes() {

        //When
        float appliedTaxes0 = taxesCalculator.calculateAppliedTaxes(10F, 0F);
        float appliedTaxes1 = taxesCalculator.calculateAppliedTaxes(10F, 0.1F);
        float appliedTaxes2 = taxesCalculator.calculateAppliedTaxes(10F, 0.15F);
        float appliedTaxes3 = taxesCalculator.calculateAppliedTaxes(10F, 0.05F);

        float appliedTaxes_4750 = taxesCalculator.calculateAppliedTaxes(47.50F, 0.15F);
        float appliedTaxes_1125 = taxesCalculator.calculateAppliedTaxes(11.25F, 0.05F);

        //Then
        assertEquals(0F, appliedTaxes0, 0.0);
        assertEquals(1F, appliedTaxes1, 0.0);
        assertEquals(1.5F, appliedTaxes2, 0.0);
        assertEquals(0.5F, appliedTaxes3, 0.0);

        assertEquals(7.15F, appliedTaxes_4750, 0.0);
        assertEquals(0.60F, appliedTaxes_1125, 0.0);
    }

}