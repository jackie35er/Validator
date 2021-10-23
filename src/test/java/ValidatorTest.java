import org.junit.jupiter.api.Test;
import util.Validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTest {
    @Test
    public void basicValidator() {
        Person valid = new Person("joe", 20);
        Person invalid = new Person("pete", 30);
        Validator<Person> validator = Validator.validating(Person::name, (a) -> a.startsWith("j"));
        assertTrue(validator.validate(valid));
        assertFalse(validator.validate(invalid));
    }

    @Test
    public void thenValidating() {
        Person valid = new Person("joe", 20);
        Person invalid1 = new Person("james",43);
        Person invalid2 = new Person("pete", 30);
        Person invalid3 = new Person("adma",17);

        Validator<Person> validator = Validator.validating(Person::name, (a) -> a.startsWith("j"))
                .thenValidating(Person::age, (a) -> a <= 20);
        assertTrue(validator.validate(valid));
        assertFalse(validator.validate(invalid1));
        assertFalse(validator.validate(invalid2));
        assertFalse(validator.validate(invalid3));
    }



    public static record Person(String name, int age) {

    }
}
