package util.validator.mappedValidator;

import util.validator.implementations.AbstractValidator;

import java.util.Collection;

public abstract class AbstractMappedValidator<T,K> extends AbstractValidator<T> implements MappedValidator<T,K> {
    @Override
    public abstract Collection<K> lastInvalidKeys();

    @Override
    public abstract Collection<K> lastValidKeys();

    @Override
    public abstract Collection<K> allKeys();

    /**
     * returns if the object matches all the conditions
     *
     * @param toValidate the object to validate
     * @return if the object is valid
     */
    @Override
    public boolean validate(T toValidate) {
        return false;
    }
}
