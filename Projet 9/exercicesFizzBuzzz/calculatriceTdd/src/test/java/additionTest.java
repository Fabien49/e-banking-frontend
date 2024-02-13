import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class additionTest {

    @Test
    void  addOneAndZero(){
        Calcul calcul = new Calcul();
        Assertions.assertEquals("1", calcul.add(1, 0));
    }
}
