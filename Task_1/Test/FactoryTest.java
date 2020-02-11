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
    public void test_define(){
        factory.makeCommand("DEFINE a 4".split(" ")).do_command();
        factory.makeCommand("PUSH a".split(" ")).do_command();
        assertEquals(4, factory.getStack().pop(), 0.01);
    }

    @Test
    public void test_diff(){
        factory.makeCommand("PUSH 25".split(" ")).do_command();
        factory.makeCommand("PUSH 105".split(" ")).do_command();
        factory.makeCommand("DIFF".split(" ")).do_command();
        assertEquals(80, factory.getStack().pop(), 0.01);
    }

    @Test
    public void test_division(){
        factory.makeCommand("PUSH 25".split(" ")).do_command();
        factory.makeCommand("PUSH 2540".split(" ")).do_command();
        factory.makeCommand("DIVISION".split(" ")).do_command();
        assertEquals(101.6, factory.getStack().pop(), 0.01);
    }

    @Test
    public void test_multi(){
        factory.makeCommand("PUSH 23".split(" ")).do_command();
        factory.makeCommand("PUSH 3".split(" ")).do_command();
        factory.makeCommand("MULTI".split(" ")).do_command();
        assertEquals(69, factory.getStack().pop(), 0.01);
    }

    @Test
    public void test_sqrt(){
        factory.makeCommand("PUSH 144".split(" ")).do_command();
        factory.makeCommand("SQRT".split(" ")).do_command();
        assertEquals(12, factory.getStack().pop(), 0.01);
    }

    @Test
    public void test_sum(){
        factory.makeCommand("PUSH 45859".split(" ")).do_command();
        factory.makeCommand("PUSH 1".split(" ")).do_command();
        factory.makeCommand("SUM".split(" ")).do_command();
        assertEquals(45860, factory.getStack().pop(), 0.01);
    }

    @Test
    public void test_push(){
        factory.makeCommand("PUSH 500".split(" ")).do_command();
        assertEquals(500, factory.getStack().pop(), 0.01);
    }

    @Test
    public void test_pop(){
        factory.makeCommand("PUSH 46".split(" ")).do_command();
        factory.makeCommand("PUSH 1".split(" ")).do_command();
        factory.makeCommand("POP".split(" ")).do_command();
        assertEquals(46, factory.getStack().pop(), 0.01);
    }
}