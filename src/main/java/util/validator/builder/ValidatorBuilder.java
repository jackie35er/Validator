package util.validator.builder;

import util.validator.Validator;

import java.util.function.Function;
import java.util.function.Predicate;

public interface ValidatorBuilder<T,R> {



    /**
     * Return a new ValidatorBuilder with a new keyExtractor
      * @param keyExtractor the new keyExtractor
     * @return the new ValidatorBuilder
     */
    <NEW_R> ValidatorBuilder<T,NEW_R> key(Function<T,NEW_R> keyExtractor);

    /**
     * adds a new Condition for the given object to be valid
     * @param isValid the new Condition
     * @return the current VaildatorBuilder
     */
    ValidatorBuilder<T,R> validating(Predicate<R> isValid);

    /**
     * Builds a Validator from this ValidatorBuilder objects
     * @return a build Validator
     */
    Validator<T> build();
}
