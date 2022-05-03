# Validator
Used to validate Objects at Runtime. 
Validators are immutable. If you build one, from a `ValidatorBuilder` there is no way to change the conditions in it.

## Validator Methods
Everything written here is better described in the javadoc

### validate
simply returns if the instance is valid or not

```boolean validate(T toValidate);```

### allValid

Checks if instances in the collection are valid

`boolean allValid(Collection<T> toValidate);`

### validOp

Returns an Optional of the object if its valid, otherwise an empty one

Optional<T> validOp(T toValidate);`

### validOr

Returns the given object if its valid, otherwise `other`

`T validOr(T toValidate,T other);`
### validOrThrow
Returns the given value if it is valid, otherwise it throws an exception

`T validOrThrow(T t) throws Exception;`

#### validOrThrow with supplied Exception
Returns the given value if it is valid, otherwise throws an exception from the throwableSupplier

`<X extends Throwable> T validOrThrow(T t, Supplier<? extends X> throwableSupplier) throws X;`

## Abstract Validator

Implement the `validate()` method from `AbstractValidator` to have a fully functioning Validator class

```java
public class SomeClassValidator extends AbstractValidator<SomeClass> {
    @Override
    public boolean validate(SomeClass toValidate) {
        /*
            some checks
         */
    }
} 
```

## ValidatorBuilder

This is the main way to create a Validator instance.

```java
public class Test {
    public static void main(String[] args) {
        Validator<SomeClass> someClassValidator = 
                ValidatorBuilder.getInstance(SomeClass::getInteger)
                .validating(i -> i >= 5)
                .key(SomeClass::getString)
                .validating(s -> s.length() > 10)
                .build();

        someClassValidator.validate(someClassInstance);
    }
}
```

Use `key()` to set a new keyExtractor and `validating()` to add a new condition to the validator.
