package util.validator.builder;

import util.validator.Validator;
import util.validator.implementations.AbstractValidator;

import java.util.function.Function;
import java.util.function.Predicate;

public class SimpleValidatorBuilder<T,R> implements ValidatorBuilder<T,R>{

    private PredicateHolder<T,R> predicateHolder;

    public SimpleValidatorBuilder(Function<T,R> keyExtractor){
        this.predicateHolder = new PredicateHolder<>(keyExtractor);
    }

    public SimpleValidatorBuilder(ValidatorBuilder<T, ?> validatorBuilder, Function<T, R> keyExtractor){
        this.predicateHolder = new PredicateHolder<>(keyExtractor);
        this.predicateHolder.addExtractedCondition(n -> validatorBuilder.build().validate(n));
    }

    @Override
    public <NEW_R> ValidatorBuilder<T,NEW_R> key(Function<T, NEW_R> keyExtractor) {
        return new SimpleValidatorBuilder<>(this,keyExtractor);
    }


    @Override
    public ValidatorBuilder<T,R> validating(Predicate<R> isValid) {
        predicateHolder.addCondition(isValid);
        return this;
    }


    @Override
    public Validator<T> build() {
        return new AbstractValidator<>() {
            @Override
            public boolean validate(T toValidate) {
                return predicateHolder.test(toValidate);
            }
        };
    }
}
