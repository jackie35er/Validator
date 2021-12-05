package util.validator.interfaces.numberValidator;

import util.validator.implementaions.IntValidatorImpl;
import util.validator.implementaions.LongValidatorImpl;

import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public interface LongValidator<T> extends NumberValidator<T, Long> {

    /**
     * Returns a default instance of LongValidator
     *
     * @param keyExtractor the keyExtractor to get the key
     * @param <T>          The type of object passed to the extractor
     * @return a default instance of LongValidator
     */
    static <T> LongValidator<T> getInstance(ToLongFunction<T> keyExtractor) {
        return new LongValidatorImpl<>(keyExtractor);
    }

    /**
     * adds a condition to the validator
     *
     * @param isValid the condition
     * @return a FunctionalValidator object with the added condition
     */
    @Override
    LongValidator<T> validating(Predicate<Long> isValid);

    /**
     * inverses the next condition set with validating <br>
     * {@code not().validating((o) -> false)} would be the same as {@code validating((o) -> true)}
     *
     * @return a FunctionalValidator where the next Condition gets inversed
     * @see util.validator.interfaces.Validator#validating(Predicate)
     */
    @Override
    LongValidator<T> not();

    /**
     * checks if the Value is within normal range of the given values
     * this function is true , when x = the extracted key
     * <p>
     * min <= x < max
     *
     * @param min the lower bound
     * @param max the upper bound
     * @return an instance with this condition added
     */
    @Override
    LongValidator<T> inRange(Long min, Long max);

    /**
     * checks if the Value is within exclusive range of the given values
     * this function is true , when x = the extracted key
     * <p>
     * min < x < max
     *
     * @param min the lower bound
     * @param max the upper bound
     * @return an instance with this condition added
     */
    @Override
    LongValidator<T> inRangeExclusive(Long min, Long max);

    /**
     * checks if the Value is within inclusive range of the given values
     * this function is true , when x = the extracted key
     * <p>
     * min <= x <= max
     *
     * @param min the lower bound
     * @param max the upper bound
     * @return an instance with this condition added
     */
    @Override
    LongValidator<T> inRangeInclusive(Long min, Long max);

    /**
     * checks if the extracted key is bigger than the value
     * this function is true , when x = the extracted key
     * <p>
     * value < x
     *
     * @param value the lower bound
     * @return an instance with this condition added
     */
    @Override
    LongValidator<T> greaterThan(Long value);

    /**
     * checks if the extracted key is bigger or equal than the value
     * this function is true , when x = the extracted key
     * <p>
     * value <= x
     *
     * @param value the lower bound
     * @return an instance with this condition added
     */
    @Override
    LongValidator<T> greaterOrEqualThan(Long value);

    /**
     * checks if the extracted key is smaller than the value
     * this function is true , when x = the extracted key
     * <p>
     * value > x
     *
     * @param value the upper bound
     * @return an instance with this condition added
     */
    @Override
    LongValidator<T> lesserThan(Long value);

    /**
     * checks if the extracted key is smaller or equal than the value
     * this function is true , when x = the extracted key
     * <p>
     * value >= x
     *
     * @param value the upper bound
     * @return an instance with this condition added
     */
    @Override
    LongValidator<T> lesserOrEqualThan(Long value);

    /**
     * checks if the extracted key is positive. 0 is not counted as positive
     * this function is true , when x = the extracted key
     * <p>
     * x > 0
     *
     * @return an instance with this condition added
     */
    @Override
    LongValidator<T> positive();

    /**
     * checks if the extracted key is negative. 0 is not counted as negative
     * this function is true , when x = the extracted key
     * <p>
     * x > 0
     *
     * @return an instance with this condition added
     */
    @Override
    LongValidator<T> negative();

    /**
     * checks if the extracted key is equal to the value
     * this function is true , when x = the extracted key
     * <p>
     * x == value
     *
     * @param value the value to be compared to the extraceted key
     * @return an instance with this condition added
     */
    @Override
    LongValidator<T> equal(Long value);

    /**
     * checks if the extracted key is 0
     * this function is true , when x = the extracted key
     * x == 0
     *
     * @return an instance with this condition added
     */
    @Override
    LongValidator<T> isZero();
}
