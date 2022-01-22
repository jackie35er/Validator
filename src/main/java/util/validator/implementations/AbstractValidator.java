package util.validator.implementations;

import util.validator.Validator;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class AbstractValidator<T> implements Validator<T> {
    /**
     * returns if the object matches all the conditions
     *
     * @param toValidate the object to validate
     * @return if the object is valid
     */
    @Override
    public abstract boolean validate(T toValidate);


    /**
     * returns if all the objects match all the conditions
     *
     * @param toValidate the objects to validate
     * @return if the objects are valid
     */
    @Override
    public boolean allValid(Collection<T> toValidate) {
        return toValidate.stream().allMatch(this::validate);
    }

    /**
     * Returns an Optional of the object if its valid, otherwise an empty one
     *
     * @param toValidate the object to validate
     * @return empty Optional if invalid, otherwise an Optional of the object;
     */
    @Override
    public Optional<T> validOp(T toValidate) {
        if(this.validate(toValidate))
            return Optional.of(toValidate);
        return Optional.empty();
    }

    /**
     * Returns the given object if its valid, otherwise other
     *
     * @param toValidate the object to validate
     * @param other      the object to return if toValidate is invalid
     * @return either toValidate if its valid, otherwise other
     */
    @Override
    public T validOr(T toValidate, T other) {
        return this.validate(toValidate) ? toValidate : other;
    }

    /**
     * Returns the given value if it is valid, otherwise it throws an exception
     *
     * @param t the value to validate
     * @return the value
     * @throws IllegalArgumentException if the value is invalid
     */
    @Override
    public T validOrThrow(T t) throws IllegalArgumentException {
        if(this.validate(t))
            return t;
        throw new IllegalArgumentException(t + "is invalid");
    }

    /**
     * Returns the given value if it is valid, otherwise throws an exception from the throwableSupplier
     *
     * @param t                 the value to validate
     * @param throwableSupplier the supplier of the Exception
     * @return the value
     * @throws X if the value is invalid
     */
    @Override
    public <X extends Throwable> T validOrThrow(T t, Supplier<? extends X> throwableSupplier) throws X {
        if(this.validate(t))
            return t;
        throw throwableSupplier.get();
    }
}
