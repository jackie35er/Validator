package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.validator.implementaions.DefaultValidator;
import util.validator.interfaces.Validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {
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
        Validator<Animal,?> viennaRabbitValidator = new DefaultValidator<>(Animal::age)
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




    public static record Animal(String name,int age,String type,String zoo){

    }
}