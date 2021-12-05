package util.validator.implementaions;

import util.validator.interfaces.Validator;
import util.validator.interfaces.numberValidator.DoubleValidator;
import util.validator.interfaces.numberValidator.IntValidator;
import util.validator.interfaces.numberValidator.LongValidator;

import java.util.Collection;
import java.util.Objects;
import java.util.function.*;

public class ValidatorImpl<T,R> implements Validator<T,R> {
    private final Function<T,R> keyExtractor;
    private FunctionalValidator<T> isValid;

    private boolean inverseNextStatement;

    public ValidatorImpl(Function<T, R> keyExtractor){
        this.keyExtractor = keyExtractor;
        isValid = (o) -> true;
    }

    public ValidatorImpl(Function<T,R> keyExtractor, Validator<T,?> validator){
        this.keyExtractor = keyExtractor;
        isValid = validator::validate;
    }

    /**
     * adds a condition to the validator
     * @param isValid the condition
     * @return a Validator object with the added condition
     */
    @Override
    public Validator<T,R> validating(Predicate<R> isValid) {
        if(inverseNextStatement){
            inverseNextStatement = false;
            return validating((o) -> !isValid.test(o));
        }
        this.isValid = this.isValid.thenValidating(keyExtractor,isValid);
        return new ValidatorImpl<>(keyExtractor,this);
    }

    /**
     * inverses the next condition set with validating <br> <br>
     * {@code not().validating((o) -> false)} would be the same as    {@code validating((o) -> true)}<br> <br>
     * @see util.validator.interfaces.Validator#validating(Predicate)
     * @return a Validator where the next condition gets inverted
     */
    @Override
    public Validator<T, R> not() {
        this.inverseNextStatement = true;
        return this;
    }

    /**
     * After building the Validator this method returns if the given object matches the given conditions
     * @param t the given object
     * @return true if the object matches the conditions
     */
    @Override
    public boolean validate(T t) {
        return isValid.validate(t);
    }

    /**
     * After building the Validator this method returns, if the given objects, in the collection, match the given conditions
     * @see util.validator.interfaces.Validator#validate(Object)
     * @param tCollection a collection of T
     * @return true if all Objects match
     */
    @Override
    public boolean allValid(Collection<T> tCollection) {
        Objects.requireNonNull(tCollection);
        return tCollection.stream().allMatch(this::validate);
    }

    /**
     * Returns the <code>t</code> if <code>t</code> matches the validators conditions. Otherwise it returns <code>other</code>
     * @param t the value to validate
     * @param other the value to return if <code>t</code> is not valid
     * @return <code>t</code> if validator matches, otherwise <code>other</code>
     */
    @Override
    public T validOr(T t, T other) {
        return this.validate(t) ? t : other;
    }

    /**
     * Returns the given value if it is valid, otherwise it throws an exception
     * @param t the value to validate
     * @return the value
     * @throws InvalidValueException if the value is invalid
     */
    @Override
    public T validOrThrow(T t) throws InvalidValueException {
        if(this.validate(t))
            return t;
        throw new InvalidValueException("Value is invalid: " + t);
    }

    /**
     * Returns the given value if it is valid, otherwise throws an exception from the throwableSupplier
     * @param t the value to validate
     * @param throwableSupplier the supplier of the Exception
     * @param <X> the type of exception
     * @return the value
     * @throws X if the value is invalid
     */
    @Override
    public <X extends Throwable> T validOrThrow(T t, Supplier<? extends X> throwableSupplier) throws X {
        if(this.validate(t))
            return t;
        throw throwableSupplier.get();
    }

    /**
     * returns a new Validator object, with a new key, but still keeping the old conditions
     * @param keyExtractor the key extractor to get the key
     * @param <U> the new key Type
     * @return a Validator instance with the new key
     */
    @Override
    public <U> Validator<T,U> key(Function<T,U> keyExtractor) {
        return new ValidatorImpl<>(keyExtractor,this);
    }

    /**
     * returns a new IntValidator object, with a new key, but still keeping the old conditions
     *
     * @param keyExtractor the key extractor to get the key
     * @return a IntValidator instance with the new key
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


    static class InvalidValueException extends RuntimeException{
        public InvalidValueException(){
            super();
        }

        public InvalidValueException(String message) {
            super(message);
        }

        public InvalidValueException(String message, Throwable cause) {
            super(message, cause);
        }

        public InvalidValueException(Throwable cause) {
            super(cause);
        }

        protected InvalidValueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }
}
