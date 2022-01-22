package util.validator.builder.numbervalidatorbuilder.implementations;

import util.validator.Validator;
import util.validator.builder.PredicateHolder;
import util.validator.builder.SimpleValidatorBuilder;
import util.validator.builder.ValidatorBuilder;
import util.validator.builder.numbervalidatorbuilder.DoubleValidatorBuilder;
import util.validator.builder.numbervalidatorbuilder.NumberValidatorBuilder;
import util.validator.implementations.AbstractValidator;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToLongFunction;

public class DoubleValidatorBuilderImpl<T> implements DoubleValidatorBuilder<T> {
    private final PredicateHolder<T,Double> predicateHolder;


    public DoubleValidatorBuilderImpl(ToDoubleFunction<T> keyExtractor){
        this.predicateHolder = new PredicateHolder<>(keyExtractor::applyAsDouble);
    }

    public DoubleValidatorBuilderImpl(ValidatorBuilder<T,?> validatorBuilder, ToDoubleFunction<T> keyExtractor){
        this.predicateHolder = new PredicateHolder<>(keyExtractor::applyAsDouble);
        this.predicateHolder.addExtractedCondition(n -> validatorBuilder.build().validate(n));
    }

    /**
     * Return a new ValidatorBuilder with a new keyExtractor
     *
     * @param keyExtractor the new keyExtractor
     * @return the new ValidatorBuilder
     */
    @Override
    public <NEW_R> ValidatorBuilder<T, NEW_R> key(Function<T, NEW_R> keyExtractor) {
        predicateHolder.key(keyExtractor);
        return new SimpleValidatorBuilder<>(this,keyExtractor);
    }

    /**
     * Builds a Validator from this ValidatorBuilder objects
     *
     * @return a build Validator
     */
    @Override
    public Validator<T> build() {
        return new AbstractValidator<>() {
            @Override
            public boolean validate(T toValidate) {
                return predicateHolder.test(toValidate);
            }
        };
    }

    /**
     * adds a condition to the validator
     *
     * @param isValid the condition
     * @return a FunctionalValidator object with the added condition
     */
    @Override
    public NumberValidatorBuilder<T, Double> validating(Predicate<Double> isValid) {
        this.predicateHolder.addCondition(isValid);
        return this;
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
    public NumberValidatorBuilder<T, Double> inRange(Double min, Double max) {
        this.predicateHolder.addCondition(x -> min <= x && max < x);
        return this;
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
    public NumberValidatorBuilder<T, Double> inRangeExclusive(Double min, Double max) {
        this.predicateHolder.addCondition(x -> min < x && max < x);
        return this;
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
    public NumberValidatorBuilder<T, Double> inRangeInclusive(Double min, Double max) {
        this.predicateHolder.addCondition(x -> min <= x && max <= x);
        return this;
    }

    /**
     * checks if the extracted key is bigger than the value
     * this function is true , when x = the extracted key
     * <p>
     * value > x
     *
     * @param value the lower bound
     * @return an instance with this condition added
     */
    @Override
    public NumberValidatorBuilder<T, Double> greaterThan(Double value) {
        this.predicateHolder.addCondition(x -> value > x);
        return this;
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
    public NumberValidatorBuilder<T, Double> greaterOrEqualThan(Double value) {
        this.predicateHolder.addCondition(x -> value <= x);
        return this;
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
    public NumberValidatorBuilder<T, Double> lesserThan(Double value) {
        this.predicateHolder.addCondition(x -> value > x);
        return this;
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
    public NumberValidatorBuilder<T, Double> lesserOrEqualThan(Double value) {
        this.predicateHolder.addCondition(x -> value >= x);
        return this;
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
    public NumberValidatorBuilder<T, Double> positive() {
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
    public NumberValidatorBuilder<T, Double> negative() {
        return this.lesserOrEqualThan(0.0);
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
    public NumberValidatorBuilder<T, Double> equal(Double value) {
        this.predicateHolder.addCondition(x -> Objects.equals(value, x));
        return this;
    }

    /**
     * checks if the extracted key is 0
     * this function is true , when x = the extracted key
     * x == 0
     *
     * @return an instance with this condition added
     */
    @Override
    public NumberValidatorBuilder<T, Double> isZero() {
        return this.equal(0.0);
    }
}
