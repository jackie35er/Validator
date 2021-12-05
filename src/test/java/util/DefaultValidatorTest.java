package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.validator.implementaions.ValidatorImpl;
import util.validator.interfaces.Validator;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DefaultValidatorTest {
    Animal animal1;
    Animal animal2;
    Animal animal3;

    @BeforeEach
    public void setup(){
        animal1 = new Animal("John",4,"Rabbit","New York");
        animal2 = new Animal("Shayne",2,"Parrot","New York");
        animal3 = new Animal("Lilly",5,"Rabbit","Vienna");
    }

    @Test
    public void basicTest(){
        Validator<Animal,?> viennaRabbitValidator = Validator.getInstance(Animal::age)
                .validating(n -> n > 3)
                .validating(n -> String.valueOf(n).length() == 1)
                .key(Animal::type)
                .validating(n -> n.equals("Rabbit"))
                .key(Animal::zoo)
                .validating(n -> n.equals("Vienna"));

        assertTrue(viennaRabbitValidator.validate(animal3));
        assertFalse(viennaRabbitValidator.validate(animal1));
        assertFalse(viennaRabbitValidator.validate(animal2));
    }

    @Test
    public void notTest(){
        Validator<Animal,?> notRabbitValidator = Validator.getInstance(Animal::type)
                .not().validating(n -> n.equals("Rabbit"));

        assertTrue(notRabbitValidator.validate(animal2));
        assertFalse(notRabbitValidator.validate(animal1));
    }

    @Test
    public void allValidTest(){
        Validator<Animal,?> ageValidator = Validator.getInstance(Animal::age)
                .validating(n -> n > 0);

        assertTrue(ageValidator.allValid(List.of(animal1,animal2,animal3)));
    }

    @Test
    public void validOrTest(){
        Validator<Animal,?> validator = Validator.getInstance(Animal::name)
                .validating(n -> n.equals("Shayne"));

        assertNull(validator.validOr(animal1,null));
        assertEquals(animal2,validator.validOr(animal2,null));
    }
    @Test
    public void validOrThrow() throws Exception {
        Validator<Animal,?> validator = Validator.getInstance(Animal::name)
                .validating(n -> n.equals("Shayne"));
        assertThrows(Exception.class,() -> validator.validOrThrow(animal1));
        assertEquals(animal2,validator.validOrThrow(animal2));
    }
    @Test
    public void validOrThrowWithSupplier() throws Exception {
        Validator<Animal,?> validator = Validator.getInstance(Animal::name)
                .validating(n -> n.equals("Shayne"));
        assertThrows(Exception.class,() -> validator.validOrThrow(animal1, Exception::new));
        assertEquals(animal2,validator.validOrThrow(animal2, Exception::new));
    }



    public static record Animal(String name,int age,String type,String zoo){

    }
}