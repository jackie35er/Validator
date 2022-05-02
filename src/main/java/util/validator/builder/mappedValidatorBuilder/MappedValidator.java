package util.validator.builder.mappedValidatorBuilder;

import util.validator.Validator;

import java.util.Collection;

public interface MappedValidator<T,K> extends Validator<T> {

    Collection<K> lastInvalidKeys();

    Collection<K> lastValidKeys();

    Collection<K> allKeys();
}
