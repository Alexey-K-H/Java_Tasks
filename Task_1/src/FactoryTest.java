import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FactoryTest extends Assert{
    Factory factory;

    //Перед тестами создаем саму фабрику
    @Before
    public void setFactory() {
        factory = Factory.getInstance();
    }

    @Test
    public void test(){
        factory.makeCommand("DEFINE a 4".split(" ")).do_command();
        factory.makeCommand("PUSH a".split(" ")).do_command();
        factory.makeCommand("SQRT".split(" ")).do_command();
        factory.makeCommand("PRINT".split(" ")).do_command();

        //В стеке должно остаться значение 2
        assertEquals(2, factory.getStack().peek(), 0.01);//так как у нас имеются вещ. числа, то сравнение выполняется с погрешностью
    }
}