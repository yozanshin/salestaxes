package org.yozanshin.mappers;

import org.yozanshin.exceptions.InvalidInputException;
import org.yozanshin.model.Item;

public interface ItemMapper<T> {

    Item map(final T input) throws InvalidInputException;
}
