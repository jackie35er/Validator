package util.validator.builder;

import util.validator.Validator;
import util.validator.builder.numbervalidatorbuilder.DoubleValidatorBuilder;
import util.validator.builder.numbervalidatorbuilder.IntValidatorBuilder;
import util.validator.builder.numbervalidatorbuilder.LongValidatorBuilder;

import java.util.function.*;

public interface ValidatorBuilder<T,R> {



    /**
     * Return a new ValidatorBuilder with a new keyExtractor
      * @param keyExtractor the new keyExtractor
     * @return the new ValidatorBuilder
     */
    <NEW_R> ValidatorBuilder<T,NEW_R> key(Function<T,NEW_R> keyExtractor);

    /**
     * Return a new DoubleValidatorBuilder with a new keyExtractor
     * @param keyExtractor the new keyExtractor
     * @return the new ValidatorBuilder
     */
    DoubleValidatorBuilder<T> doubleKey(ToDoubleFunction<T> keyExtractor);

    /**
     * Return a new IntValidatorBuilder with a new keyExtractor
     * @param keyExtractor the new keyExtractor
     * @return the new ValidatorBuilder
     */
    IntValidatorBuilder<T> intKey(ToIntFunction<T> keyExtractor);

    /**
     * Return a new LongValidatorBuilder with a new keyExtractor
     * @param keyExtractor the new keyExtractor
     * @return the new ValidatorBuilder
     */
    LongValidatorBuilder<T> longKey(ToLongFunction<T> keyExtractor);

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
