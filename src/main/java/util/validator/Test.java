package util.validator;

import util.validator.builder.SimpleValidatorBuilder;
import util.validator.builder.ValidatorBuilder;

public class Test {
    public static void main(String[] args) {
        Validator<SomeClass>  someClassValidator = ValidatorBuilder.getInstance(SomeClass::getInteger)
                .validating(i -> i >= 5)
                .key(SomeClass::getString)
                .validating(s -> s.length() > 10)
                .build();

        someClassValidator.validate(new SomeClass());
    }
    public static class SomeClass{

        public int getInteger(){
            return 5;
        }

        public String getString(){
            return "asdf";
        }
    }
}
