package util.validator.interfaces.numberValidator;

import util.validator.implementaions.IntValidatorImpl;

import java.util.function.Predicate;
import java.util.function.ToIntFunction;

public interface IntValidator<T> extends NumberValidator<T, Integer> {

    /**
     * Returns a default instance of IntValidator
     *
     * @param keyExtractor the keyExtractor to get the key
     * @param <T>          The type of object passed to the extractor
     * @return a default instance of IntValidator
     */
    static <T> IntValidator<T> getInstance(ToIntFunction<T> keyExtractor) {
        return new IntValidatorImpl<>(keyExtractor);
    }

    /**
     * adds a condition to the validator
     *
     * @param isValid the condition
     * @return a FunctionalValidator object with the added condition
     */
    @Override
    IntValidator<T> validating(Predicate<Integer> isValid);

    /**
     * inverses the next condition set with validating <br>
     * {@code not().validating((o) -> false)} would be the same as {@code validating((o) -> true)}
     *
     * @return a FunctionalValidator where the next Condition gets inverted
     * @see util.validator.interfaces.Validator#validating(Predicate)
     */
    @Override
    IntValidator<T> not();

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
    IntValidator<T> inRange(Integer min, Integer max);

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
    IntValidator<T> inRangeExclusive(Integer min, Integer max);

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
    IntValidator<T> inRangeInclusive(Integer min, Integer max);

    /**
     * checks if the extracted key is bigger than the value
     * this function is true , when x = the extracted key
     * <p>
     * value < x
     *
     * @param value the lower bound
     * @return an instance with this condition added
     */
    IntValidator<T> greaterThan(Integer value);

    /**
     * checks if the extracted key is bigger or equal than the value
     * this function is true , when x = the extracted key
     * <p>
     * value <= x
     *
     * @param value the lower bound
     * @return an instance with this condition added
     */
    IntValidator<T> greaterOrEqualThan(Integer value);

    /**
     * checks if the extracted key is smaller than the value
     * this function is true , when x = the extracted key
     * <p>
     * value > x
     *
     * @param value the upper bound
     * @return an instance with this condition added
     */
    IntValidator<T> lesserThan(Integer value);

    /**
     * checks if the extracted key is smaller or equal than the value
     * this function is true , when x = the extracted key
     * <p>
     * value >= x
     *
     * @param value the upper bound
     * @return an instance with this condition added
     */
    IntValidator<T> lesserOrEqualThan(Integer value);

    /**
     * checks if the extracted key is positive. 0 is not counted as positive
     * this function is true , when x = the extracted key
     * <p>
     * x > 0
     *
     * @return an instance with this condition added
     */
    IntValidator<T> positive();

    /**
     * checks if the extracted key is negative. 0 is not counted as negative
     * this function is true , when x = the extracted key
     * <p>
     * x > 0
     *
     * @return an instance with this condition added
     */
    IntValidator<T> negative();

    /**
     * checks if the extracted key is equal to the value
     * this function is true , when x = the extracted key
     * <p>
     * x == value
     *
     * @param value the value to be compared to the extracted key
     * @return an instance with this condition added
     */
    IntValidator<T> equal(Integer value);

    /**
     * checks if the extracted key is 0
     * this function is true , when x = the extracted key
     * x == 0
     *
     * @return an instance with this condition added
     */
    IntValidator<T> isZero();
}
