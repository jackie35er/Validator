package util.validator.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class PredicateHolder<T,R> {
    private List<Predicate<T>> predicates;
    private Function<T,R> keyExtractor;


    public PredicateHolder(Function<T,R> keyExtractor){
        this.keyExtractor = keyExtractor;
        predicates = new ArrayList<>();
    }

    private PredicateHolder(Function<T,R> keyExtractor,List<Predicate<T>> predicates){
        this.keyExtractor = keyExtractor;
        this.predicates = new ArrayList<>(predicates);
    }

    public void addCondition(Predicate<R> predicate){
        Predicate<T> test = (n) -> predicate.test(keyExtractor.apply(n));
        predicates.add(test);
    }

    public void addExtractedCondition(Predicate<T> predicate){
        predicates.add(predicate);
    }

    public <NEW_R> PredicateHolder<T,NEW_R> key(Function<T,NEW_R> keyExtractor){
        return new PredicateHolder<>(keyExtractor,predicates);
    }

    public boolean test(T object){
        return predicates.stream().allMatch(p -> p.test(object));
    }

}
