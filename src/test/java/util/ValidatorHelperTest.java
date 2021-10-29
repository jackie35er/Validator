package util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorHelperTest {
    Animal animal1;
    Animal animal2;
    Animal animal3;



    @BeforeEach
    public void setup(){
        animal1 = new Animal("John",4,"Rabbit","New York");
        animal2 = new Animal("Shayne",2,"Parrot","New York");
        animal3 = new Animal("Vici",5,"Rabbit","Vienna");
    }

    @Test
    public void basicTest(){
        Validator<Animal,?> viennaRabbitValidator = Validator.withKey(Animal::age)
                .thenVaildating(n -> n > 3)
                .thenVaildating(n -> String.valueOf(n).length() == 1)
                .key(Animal::type)
                .thenVaildating(n -> n.equals("Rabbit"))
                .key(Animal::zoo)
                .thenVaildating(n -> n.equals("Vienna"));

        assertTrue(viennaRabbitValidator.validate(animal3));
        assertFalse(viennaRabbitValidator.validate(animal1));
        assertFalse(viennaRabbitValidator.validate(animal2));
    }




    public static record Animal(String name,int age,String type,String zoo){

    }
}

