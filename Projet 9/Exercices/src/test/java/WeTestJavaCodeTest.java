import UnitTesting.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WeTestJavaCodeTest {

    Person person = new Person();

    @BeforeEach
        void setupPerson() {
        person.setName("Fabien");
        person.setAge(37);
    }

    @Test
    void createNewUser(){

        Assertions.assertEquals("Fabien", person.getName());

    }

    @Test
    void testBob(){
        person.setName("Bob");

        Assertions.assertEquals("Bob", person.getName());
    }

    @Test
    void testIfAgeIsGreaterThan(){
        person.setAge(28);

        Assertions.assertTrue(person.getAge()<30);
    }
}


