package util.validator.implementaions;

import util.validator.interfaces.Validator;
import util.validator.interfaces.numberValidator.DoubleValidator;
import util.validator.interfaces.numberValidator.IntValidator;
import util.validator.interfaces.numberValidator.LongValidator;

import java.util.Collection;
import java.util.Objects;
import java.util.function.*;

public class DoubleValidatorImpl<T> implements DoubleValidator<T> {
    private final ToDoubleFunction<T> keyExtractor;
    private FunctionalValidator<T> isValid;

    private boolean inverseNextStatement;

    public DoubleValidatorImpl(ToDoubleFunction<T> keyExtractor) {
        this.keyExtractor = keyExtractor;
        this.isValid = (o) -> true;
    }

    public DoubleValidatorImpl(ToDoubleFunction<T> keyExtractor, Validator<T, ?> validator) {
        this.keyExtractor = keyExtractor;
        this.isValid = validator::validate;
    }

    /**
     * adds a condition to the validator
     *
     * @param isValid the condition
     * @return a Validator object with the added condition
     */
    @Override
    public DoubleValidator<T> validating(Predicate<Double> isValid) {
        if (inverseNextStatement) {
            inverseNextStatement = false;
            return validating((o) -> !isValid.test(o));
        }
        this.isValid = this.isValid.thenValidating(keyExtractor::applyAsDouble, isValid);
        return new DoubleValidatorImpl<>(keyExtractor, this);
    }

    /**
     * inverses the next condition set with validating <br>
     * {@code not().validating((o) -> false)} would be the same as {@code validating((o) -> true)}
     *
     * @return a FunctionalValidator where the next Condition gets inverted
     * @see Validator#validating(Predicate)
     */
    @Override
    public DoubleValidator<T> not() {
        this.inverseNextStatement = false;
        return this;
    }

    /**
     * After building the Validator this method returns if the given object matches the given conditions
     *
     * @param t the given object
     * @return true if the object matches the conditions
     */
    @Override
    public boolean validate(T t) {
        return isValid.validate(t);
    }

    /**
     * After building the Validator this method returns, if the given objects, in the collection, match the given conditions
     *
     * @param tCollection a collection of T
     * @return true if all Objects match
     * @see util.validator.interfaces.Validator#validate(Object)
     */
    @Override
    public boolean allValid(Collection<T> tCollection) {
        Objects.requireNonNull(tCollection);
        return tCollection.stream().allMatch(this::validate);
    }

    /**
     * Returns the <code>t</code> if <code>t</code> matches the validators conditions. Otherwise it returns <code>other</code>
     *
     * @param t     the value to validate
     * @param other the value to return if <code>t</code> is not valid
     * @return <code>t</code> if validator matches, otherwise <code>other</code>
     */
    @Override
    public T validOr(T t, T other) {
        return this.validate(t) ? t : other;
    }

    /**
     * Returns the given value if it is valid, otherwise it throws an exception
     *
     * @param t the value to validate
     * @return the value
     * @throws InvalidDoubleValueException if the value is invalid
     */
    @Override
    public T validOrThrow(T t) throws InvalidDoubleValueException {
        if (this.validate(t))
            return t;
        throw new InvalidDoubleValueException("Double key value is invalid: " + t);
    }

    /**
     * Returns the given value if it is valid, otherwise throws an exception from the throwableSupplier
     *
     * @param t                 the value to validate
     * @param throwableSupplier the supplier of the Exception
     * @param <X>               the type of exception
     * @return the value
     * @throws X if the value is invalid
     */
    @Override
    public <X extends Throwable> T validOrThrow(T t, Supplier<? extends X> throwableSupplier) throws X {
        if (this.validate(t))
            return t;
        throw throwableSupplier.get();
    }

    @Override
    public <U> Validator<T, U> key(Function<T, U> keyExtractor) {
        return new ValidatorImpl<>(keyExtractor, this);
    }

    /**
     * returns a new IntValidatorImpl object, with a new key, but still keeping the old conditions
     *
     * @param keyExtractor the key extractor to get the key
     * @return a Validator instance with the new key
     */
    @Override
    public IntValidator<T> intKey(ToIntFunction<T> keyExtractor) {
        return IntValidator.getInstance(keyExtractor);
    }

    /**
     * returns a new LongValidator object, with a new key, but still keeping the old conditions
     *
     * @param keyExtractor the key extractor to get the key
     * @return a LongValidator instance with the new key
     */
    @Override
    public LongValidator<T> longKey(ToLongFunction<T> keyExtractor) {
        return LongValidator.getInstance(keyExtractor);
    }

    /**
     * returns a new DoubleValidator object, with a new key, but still keeping the old conditions
     *
     * @param keyExtractor the key extractor to get the key
     * @return a Validator instance with the new key
     */
    @Override
    public DoubleValidator<T> doubleKey(ToDoubleFunction<T> keyExtractor) {
        return DoubleValidator.getInstance(keyExtractor);
    }

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
    public DoubleValidator<T> inRange(Double min, Double max) {
        return this.validating((x) -> min <= x && x < max);
    }

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
    public DoubleValidator<T> inRangeExclusive(Double min, Double max) {
        return this.validating((x) -> min < x && x < max);
    }

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
    public DoubleValidator<T> inRangeInclusive(Double min, Double max) {
        return this.validating((x) -> min <= x && x <= max);
    }

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
    public DoubleValidator<T> greaterThan(Double value) {
        return this.validating((x) -> value < x);
    }

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
    public DoubleValidator<T> greaterOrEqualThan(Double value) {
        return this.validating((x) -> value <= x);
    }

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
    public DoubleValidator<T> lesserThan(Double value) {
        return this.validating((x) -> value > x);
    }

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
    public DoubleValidator<T> lesserOrEqualThan(Double value) {
        return this.validating((x) -> value >= x);
    }

    /**
     * checks if the extracted key is positive. 0 is not counted as positive
     * this function is true , when x = the extracted key
     * <p>
     * x > 0
     *
     * @return an instance with this condition added
     */
    @Override
    public DoubleValidator<T> positive() {
        return this.greaterThan(0.0);
    }

    /**
     * checks if the extracted key is negative. 0 is not counted as negative
     * this function is true , when x = the extracted key
     * <p>
     * x > 0
     *
     * @return an instance with this condition added
     */
    @Override
    public DoubleValidator<T> negative() {
        return this.lesserThan(0.0);
    }

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
    public DoubleValidator<T> equal(Double value) {
        return this.validating((x) -> x.equals(value));
    }

    /**
     * checks if the extracted key is 0
     * this function is true , when x = the extracted key
     * x == 0
     *
     * @return an instance with this condition added
     */
    @Override
    public DoubleValidator<T> isZero() {
        return this.equal(0.0);
    }

    static class InvalidDoubleValueException extends RuntimeException {
        public InvalidDoubleValueException() {
            super();
        }

        public InvalidDoubleValueException(String message) {
            super(message);
        }

        public InvalidDoubleValueException(String message, Throwable cause) {
            super(message, cause);
        }

        public InvalidDoubleValueException(Throwable cause) {
            super(cause);
        }

        protected InvalidDoubleValueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
