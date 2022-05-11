package validatorTest;

import org.junit.jupiter.api.*;
import util.validator.implementations.AbstractValidator;

import java.util.Collection;
import java.util.List;

public class ValidatorTest {

    private AnimalValidator animalValidator = new AnimalValidator();




    @Test
    void validObjectTest() {
        Animal validAnimal = new Animal("Joe",5);
        Assertions.assertTrue(animalValidator.validate(validAnimal));
    }

    @Test
    void invalidObjectTest() {
        Animal invalidAnimal1 = new Animal(null,34);
        Animal invalidAnimal2 = new Animal("asd",-5);

        Assertions.assertFalse(animalValidator.validate(invalidAnimal1));
        Assertions.assertFalse(animalValidator.validate(invalidAnimal2));
    }



    @Nested
    class AllValidTests{
        @Test
        void allValidTest(){
            Animal validAnimal1 = new Animal("Joe",20);
            Animal validAnimal2 = new Animal("ad",4);
            Animal validAnimal3 = new Animal("wer",3);
            Animal validAnimal4 = new Animal("ve",1);
            Animal validAnimal5 = new Animal("123",12);

            Collection<Animal> animals = List.of(validAnimal1,validAnimal2,validAnimal3,validAnimal4,validAnimal5);
            Assertions.assertTrue(animalValidator.allValid(animals));
        }

        @Test
        void oneInvalid() {
            Animal validAnimal1 = new Animal("Joe",20);
            Animal validAnimal2 = new Animal("ad",4);
            Animal validAnimal3 = new Animal("wer",3);
            Animal validAnimal4 = new Animal("ve",1);
            Animal invalid = new Animal(null,12);

            Collection<Animal> animals = List.of(validAnimal1,validAnimal2,validAnimal3,validAnimal4,invalid);
            Assertions.assertFalse(animalValidator.allValid(animals));
        }
    }

    @Nested
    class OptionalValidTest{
        @Test
        void validTest() {
            Animal validAnimal1 = new Animal("Joe",20);
            Assertions.assertEquals(animalValidator.validOp(validAnimal1).get(),validAnimal1);
        }

        @Test
        void invalidTest(){
            Animal invalid = new Animal(null,12);
            Assertions.assertTrue(animalValidator.validOp(invalid).isEmpty());
        }
    }

    @Nested
    class ValidOr{

        @Test
        void valid(){
            Animal valid = new Animal("Joe",12);
            Assertions.assertEquals(valid,animalValidator.validOr(valid,null));
        }

        @Test
        void invalid(){
            Animal other = new Animal("hey",10);
            Animal invalid = new Animal(null,12);
            Assertions.assertEquals(other,animalValidator.validOr(invalid,other));
        }
    }

    @Nested
    class ValidOrThrow{

        @Test
        void throwInvalid(){
            Animal invalid = new Animal(null,12);
            Assertions.assertThrows(Exception.class,() -> animalValidator.validOrThrow(invalid));
        }
        @Test
        void customThrowInvalid(){
            Animal invalid = new Animal(null,12);
            Assertions.assertThrows(IllegalArgumentException.class,() -> animalValidator.validOrThrow(invalid,() -> new IllegalArgumentException()));
        }
    }





    private class AnimalValidator extends AbstractValidator<Animal>{

        @Override
        public boolean validate(Animal toValidate) {
            return toValidate.age() >= 0 && toValidate.name() != null && !toValidate.name().isBlank();
        }
    }
}
