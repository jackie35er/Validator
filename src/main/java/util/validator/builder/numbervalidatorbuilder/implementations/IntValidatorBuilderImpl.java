package util.validator.builder.numbervalidatorbuilder.implementations;

import util.validator.Validator;
import util.validator.builder.PredicateHolder;
import util.validator.builder.SimpleValidatorBuilder;
import util.validator.builder.ValidatorBuilder;
import util.validator.builder.numbervalidatorbuilder.DoubleValidatorBuilder;
import util.validator.builder.numbervalidatorbuilder.IntValidatorBuilder;

import util.validator.builder.numbervalidatorbuilder.LongValidatorBuilder;
import util.validator.builder.numbervalidatorbuilder.NumberValidatorBuilder;
import util.validator.implementations.AbstractValidator;

import java.util.Objects;
import java.util.function.*;


public class IntValidatorBuilderImpl<T> implements IntValidatorBuilder<T> {
    private final PredicateHolder<T,Integer> predicateHolder;


    public IntValidatorBuilderImpl(ToIntFunction<T> keyExtractor){
        this.predicateHolder = new PredicateHolder<>(keyExtractor::applyAsInt);
    }

    public IntValidatorBuilderImpl(ValidatorBuilder<T,?> validatorBuilder, ToIntFunction<T> keyExtractor){
        this.predicateHolder = new PredicateHolder<>(keyExtractor::applyAsInt);
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
    public NumberValidatorBuilder<T, Integer> validating(Predicate<Integer> isValid) {
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
    public NumberValidatorBuilder<T, Integer> inRange(Integer min, Integer max) {
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
    public NumberValidatorBuilder<T, Integer> inRangeExclusive(Integer min, Integer max) {
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
    public NumberValidatorBuilder<T, Integer> inRangeInclusive(Integer min, Integer max) {
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
    public NumberValidatorBuilder<T, Integer> greaterThan(Integer value) {
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
    public NumberValidatorBuilder<T, Integer> greaterOrEqualThan(Integer value) {
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
    public NumberValidatorBuilder<T, Integer> lesserThan(Integer value) {
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
    public NumberValidatorBuilder<T, Integer> lesserOrEqualThan(Integer value) {
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
    public NumberValidatorBuilder<T, Integer> positive() {
        return this.greaterThan(0);
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
    public NumberValidatorBuilder<T, Integer> negative() {
        return this.lesserOrEqualThan(0);
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
    public NumberValidatorBuilder<T, Integer> equal(Integer value) {
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
    public NumberValidatorBuilder<T, Integer> isZero() {
        return this.equal(0);
    }
}
