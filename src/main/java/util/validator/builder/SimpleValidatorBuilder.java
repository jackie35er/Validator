package util.validator.builder;

import util.validator.Validator;
import util.validator.builder.numbervalidatorbuilder.DoubleValidatorBuilder;
import util.validator.builder.numbervalidatorbuilder.IntValidatorBuilder;
import util.validator.builder.numbervalidatorbuilder.LongValidatorBuilder;
import util.validator.builder.numbervalidatorbuilder.implementations.DoubleValidatorBuilderImpl;
import util.validator.builder.numbervalidatorbuilder.implementations.IntValidatorBuilderImpl;
import util.validator.builder.numbervalidatorbuilder.implementations.LongValidatorBuilderImpl;
import util.validator.implementations.AbstractValidator;

import java.util.function.*;

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
    public DoubleValidatorBuilder<T> doubleKey(ToDoubleFunction<T> keyExtractor) {
        return new DoubleValidatorBuilderImpl<>(this,keyExtractor);
    }

    @Override
    public IntValidatorBuilder<T> intKey(ToIntFunction<T> keyExtractor) {
        return new IntValidatorBuilderImpl<>(this,keyExtractor);
    }

    @Override
    public LongValidatorBuilder<T> longKey(ToLongFunction<T> keyExtractor) {
        return new LongValidatorBuilderImpl<>(this,keyExtractor);
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
