package util.validator;

public interface BasicValidator<T> {

    /**
     * returns if the object matches all the conditions
     * @param toValidate the object to validate
     * @return if the object is valid
     */
    boolean validate(T toValidate);
}
