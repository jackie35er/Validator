package util.validator;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

public interface Validator<T> {

    /**
     * returns if the object matches all the conditions
     * @param toValidate the object to validate
     * @return if the object is valid
     */
    boolean validate(T toValidate);

    /**
     * returns if all the objects match all the conditions
     * @param toValidate the objects to validate
     * @return if the objects are valid
     */
    boolean allValid(Collection<T> toValidate);

    /**
     * Returns an Optional of the object if its valid, otherwise an empty one
     * @param toValidate the object to validate
     * @return empty Optional if invalid, otherwise an Optional of the object;
     */
    Optional<T> validOp(T toValidate);


    /**
     * Returns the given object if its valid, otherwise other
     * @param toValidate the object to validate
     * @param other the object to return if toValidate is invalid
     * @return either toValidate if its valid, otherwise other
     */
    T validOr(T toValidate,T other);

    /**
     * Returns the given value if it is valid, otherwise it throws an exception
     * @param t the value to validate
     * @return the value
     * @throws Exception if the value is invalid
     */
    T validOrThrow(T t) throws Exception;

    /**
     * Returns the given value if it is valid, otherwise throws an exception from the throwableSupplier
     * @param t the value to validate
     * @param throwableSupplier the supplier of the Exception
     * @param <X> the type of exception
     * @return the value
     * @throws X if the value is invalid
     */
    <X extends Throwable> T validOrThrow(T t, Supplier<? extends X> throwableSupplier) throws X;

}
