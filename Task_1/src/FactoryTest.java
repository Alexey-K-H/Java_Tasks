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
    public void test_1(){
        factory.makeCommand("DEFINE a 4".split(" ")).do_command();
        factory.makeCommand("PUSH a".split(" ")).do_command();
        factory.makeCommand("SQRT".split(" ")).do_command();

        //В стеке должно остаться значение 2
        assertEquals(2, factory.getStack().peek(), 0.01);//так как у нас имеются вещ. числа, то сравнение выполняется с погрешностью
    }

    @Test
    public void test_2(){
        factory.makeCommand("DEFINE a 5".split(" ")).do_command();
        factory.makeCommand("DEFINE b 2".split(" ")).do_command();
        factory.makeCommand("PUSH b".split(" ")).do_command();
        factory.makeCommand("PUSH a".split(" ")).do_command();
        factory.makeCommand("DIVISION".split(" ")).do_command();

        assertEquals(2.5, factory.getStack().peek(), 0.01);
    }

    @Test
    public void test_3(){
        factory.makeCommand("PUSH 500".split(" ")).do_command();
        factory.makeCommand("PUSH 25".split(" ")).do_command();
        factory.makeCommand("SQRT".split(" ")).do_command();
        factory.makeCommand("MULTI".split(" ")).do_command();

        assertEquals(2500, factory.getStack().peek(), 0.01);
    }

    @Test
    public void test_4(){
        factory.makeCommand("DEFINE a 56".split(" ")).do_command();
        factory.makeCommand("DEFINE c 89".split( " ")).do_command();
        factory.makeCommand("DEFINE e 2.71".split(" ")).do_command();

        factory.makeCommand("PUSH a".split(" ")).do_command();
        factory.makeCommand("PUSH c".split(" ")).do_command();
        factory.makeCommand("PUSH e".split(" ")).do_command();

        assertEquals(2.71, factory.getStack().pop(), 0.01);
        assertEquals(89, factory.getStack().pop(), 0.01);
        assertEquals(56, factory.getStack().pop(), 0.01);
    }

    @Test
    public void test_5(){
        factory.makeCommand("POP".split(" ")).do_command();
        factory.makeCommand("POP".split(" ")).do_command();
        factory.makeCommand("POP".split(" ")).do_command();

        boolean actual = factory.getStack().isEmpty();
        assertTrue(actual);
    }
}