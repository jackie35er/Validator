package util.validator.mappedValidator.mappedValidatorBuilder;


import util.validator.Validator;


import java.util.function.*;

public interface MappedValidatorBuilder<T,K,R>{



    /**
     * adds a new Condition mapped to the given mapKey
     *
     * @param mapKey
     * @param isValid
     * @return
     */
    MappedValidatorBuilder<T,K,R> validating(K mapKey, Predicate<R> isValid);

    /**
     * Return a new ValidatorBuilder with a new keyExtractor
     * @param keyExtractor the new keyExtractor
     * @return the new ValidatorBuilder
     */
    <NEW_R> MappedValidatorBuilder<T,K,NEW_R> key(Function<T,NEW_R> keyExtractor);

    /**
     * Builds a Validator from this MappedValidatorBuilder objects
     * @return a build Validator
     */
    Validator<T> build();

}
