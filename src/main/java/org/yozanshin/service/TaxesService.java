package org.yozanshin.service;

import org.yozanshin.exceptions.InvalidInputException;
import org.yozanshin.model.Output;

public interface TaxesService<T, S> {

    Output<S> applyTaxes(final T[] input) throws InvalidInputException;
}
