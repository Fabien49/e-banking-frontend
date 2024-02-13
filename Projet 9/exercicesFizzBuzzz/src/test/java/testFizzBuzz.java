import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class testFizzBuzz {
    Fizzbuzz fizzbuzz = new Fizzbuzz();

    @Test
    void testWithNumberOne(){
        Assertions.assertEquals("1", fizzbuzz.check(1));
    }

    @Test
    void testWithNumberTwo(){
        Assertions.assertEquals("2", fizzbuzz.check(2));
    }

    @Test
    void testWithNumberThree(){
        Assertions.assertEquals("Fizz", fizzbuzz.check(3));
    }

    @Test
    void testWithNumberFive(){
        Assertions.assertEquals("Buzz", fizzbuzz.check(5));
    }

    @Test
    void testWithNumberFiveteen(){
        Assertions.assertEquals("FizzBuzz", fizzbuzz.check(15));
    }

    @Test
    void testAll(){
        for (int i = 1; i<=100; i++){
            System.out.println(fizzbuzz.check(i));
        }
    }
}
