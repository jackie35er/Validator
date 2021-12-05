package util.validator.interfaces.numberValidator;

import util.validator.implementaions.DoubleValidatorImpl;

import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

public interface DoubleValidator<T> extends NumberValidator<T, Double> {

    /**
     * Returns a default instance of DoubleValidator
     *
     * @param keyExtractor the keyExtractor to get the key
     * @param <T>          The type of object passed to the extractor
     * @return a default instance of DoubleValidator
     */
    static <T> DoubleValidator<T> getInstance(ToDoubleFunction<T> keyExtractor) {
        return new DoubleValidatorImpl<>(keyExtractor);
    }

    /**
     * adds a condition to the validator
     *
     * @param isValid the condition
     * @return a FunctionalValidator object with the added condition
     */
    @Override
    DoubleValidator<T> validating(Predicate<Double> isValid);

    /**
     * inverses the next condition set with validating <br>
     * {@code not().validating((o) -> false)} would be the same as {@code validating((o) -> true)}
     *
     * @return a FunctionalValidator where the next Condition gets inverted
     * @see util.validator.interfaces.Validator#validating(Predicate)
     */
    @Override
    DoubleValidator<T> not();

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
    DoubleValidator<T> inRange(Double min, Double max);

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
    DoubleValidator<T> inRangeExclusive(Double min, Double max);

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
    DoubleValidator<T> inRangeInclusive(Double min, Double max);

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
    DoubleValidator<T> greaterThan(Double value);

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
    DoubleValidator<T> greaterOrEqualThan(Double value);

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
    DoubleValidator<T> lesserThan(Double value);

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
    DoubleValidator<T> lesserOrEqualThan(Double value);

    /**
     * checks if the extracted key is positive. 0 is not counted as positive
     * this function is true , when x = the extracted key
     * <p>
     * x > 0
     *
     * @return an instance with this condition added
     */
    @Override
    DoubleValidator<T> positive();

    /**
     * checks if the extracted key is negative. 0 is not counted as negative
     * this function is true , when x = the extracted key
     * <p>
     * x > 0
     *
     * @return an instance with this condition added
     */
    @Override
    DoubleValidator<T> negative();

    /**
     * checks if the extracted key is equal to the value
     * this function is true , when x = the extracted key
     * <p>
     * x == value
     *
     * @param value the value to be compared to the extracted key
     * @return an instance with this condition added
     */
    @Override
    DoubleValidator<T> equal(Double value);

    /**
     * checks if the extracted key is 0
     * this function is true , when x = the extracted key
     * x == 0
     *
     * @return an instance with this condition added
     */
    @Override
    DoubleValidator<T> isZero();
}
