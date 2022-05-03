package util.validator.mappedValidator.mappedValidatorBuilder;

import util.validator.Validator;
import util.validator.mappedValidator.AbstractMappedValidator;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SimpleMappedValidtatorBuilder<T,K,R> extends AbstractMappedValidatorBuilder<T,K,R> {

    private final Map<K,Predicate<R>> predicateHolder = new LinkedHashMap<>();
    private final List<AbstractMappedValidatorBuilder<T,K,?>> previousBuilders = new ArrayList<>();
    private final Function<T,R> keyExtractor;

    private SimpleMappedValidtatorBuilder(AbstractMappedValidatorBuilder<T,K,?> previousBuilder,Function<T,R> keyExtractor){
        this.previousBuilders.add(previousBuilder);
        this.keyExtractor = keyExtractor;
    }

    /**
     * adds a new Condition mapped to the given mapKey
     *
     * @param mapKey
     * @param isValid
     * @return
     */
    @Override
    public MappedValidatorBuilder<T, K, R> validating(K mapKey, Predicate<R> isValid) {
        predicateHolder.put(mapKey,isValid);
        return this;
    }

    /**
     * Return a new ValidatorBuilder with a new keyExtractor
     *
     * @param keyExtractor the new keyExtractor
     * @return the new ValidatorBuilder
     */
    @Override
    public <NEW_R> MappedValidatorBuilder<T, K, NEW_R> key(Function<T, NEW_R> keyExtractor) {
        return new SimpleMappedValidtatorBuilder<>(this,keyExtractor);
    }

    @Override
    protected boolean testKey(K key,T toValidate) {
        if(key == null)
            throw new IllegalArgumentException("key can't be null");
        var predicate = predicateHolder.get(key);
        if(predicate == null)
            throw new IllegalStateException("key doesn't exist");

        return predicate.test(keyExtractor.apply(toValidate));
    }

    @Override
    public Collection<K> getKeys() {
        return predicateHolder.keySet();
    }

    /**
     * Builds a Validator from this MappedValidatorBuilder objects
     *
     * @return a build Validator
     */
    @Override
    public Validator<T> build() {
        return new AbstractMappedValidator<T,K>(){
            private Map<K,Boolean> keys = new LinkedHashMap<>();

            @Override
            public Collection<K> lastInvalidKeys() {
                return keys.entrySet().stream()
                        .filter(e -> !e.getValue())
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
            }

            @Override
            public Collection<K> lastValidKeys() {
                return keys.entrySet().stream()
                        .filter(Map.Entry::getValue)
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());
            }

            @Override
            public Collection<K> allKeys() {
                return keys.keySet();
            }

            @Override
            public boolean validate(T toValidate) {
                keys.clear();
                for(var builder : previousBuilders){
                   for(var key : builder.getKeys()){
                       keys.put(key,builder.testKey(key,toValidate));
                   }
                }
                for(var entry : predicateHolder.entrySet()){
                    keys.put(entry.getKey(),entry.getValue().test(keyExtractor.apply(toValidate)));
                }
                return keys.values().stream().allMatch(p -> p);
            }
        };
    }


}
