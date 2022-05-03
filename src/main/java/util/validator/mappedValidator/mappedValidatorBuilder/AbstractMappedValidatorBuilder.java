package util.validator.mappedValidator.mappedValidatorBuilder;

import java.util.Collection;

public abstract class AbstractMappedValidatorBuilder<T,K,R> implements MappedValidatorBuilder<T,K,R>{

    abstract protected boolean testKey(K key,T toValidate);

    abstract public Collection<K> getKeys();

}
