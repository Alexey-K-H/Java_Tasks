import Context.Context;
import Exceptions.CalculatorException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FactoryTest extends Assert{
    Factory factory;
    Context context;

    @Before
    public void setFactory() {
        factory = Factory.getInstance();
        context = new Context();
    }

    @Test
    public void test_define() throws CalculatorException {
        factory.make_command("DEFINE a 4".split(" ")).do_command(context, "DEFINE a 4".split(" "));
        factory.make_command("PUSH a".split(" ")).do_command(context, "PUSH a".split(" "));
        assertEquals(4, context.pop(), 0.01);
    }

    @Test
    public void test_diff() throws CalculatorException{
        factory.make_command("PUSH 25".split(" ")).do_command(context, "PUSH 25".split(" "));
        factory.make_command("PUSH 105".split(" ")).do_command(context, "PUSH 105".split(" "));
        factory.make_command("DIFF".split(" ")).do_command(context, "DIFF".split(" "));
        assertEquals(80, context.pop(), 0.01);
    }

    @Test
    public void test_division() throws CalculatorException{
        factory.make_command("PUSH 25".split(" ")).do_command(context, "PUSH 25".split(" "));
        factory.make_command("PUSH 2540".split(" ")).do_command(context, "PUSH 2540".split(" "));
        factory.make_command("DIVISION".split(" ")).do_command(context, "DIVISION".split(" "));
        assertEquals(101.6, context.pop(), 0.01);
    }

    @Test
    public void test_multi() throws CalculatorException{
        factory.make_command("PUSH 23".split(" ")).do_command(context, "PUSH 23".split(" "));
        factory.make_command("PUSH 3".split(" ")).do_command(context, "PUSH 3".split(" "));
        factory.make_command("MULTI".split(" ")).do_command(context, "MULTI".split(" "));
        assertEquals(69, context.pop(), 0.01);
    }

    @Test
    public void test_sqrt() throws CalculatorException{
        factory.make_command("PUSH 144".split(" ")).do_command(context, "PUSH 144".split(" "));
        factory.make_command("SQRT".split(" ")).do_command(context, "SQRT".split(" "));
        assertEquals(12, context.pop(), 0.01);
    }

    @Test
    public void test_sum() throws CalculatorException{
        factory.make_command("PUSH 45859".split(" ")).do_command(context, "PUSH 45859".split(" "));
        factory.make_command("PUSH 1".split(" ")).do_command(context, "PUSH 1".split(" "));
        factory.make_command("SUM".split(" ")).do_command(context, "SUM".split(" "));
        assertEquals(45860, context.pop(), 0.01);
    }

    @Test
    public void test_push() throws CalculatorException{
        factory.make_command("PUSH 500".split(" ")).do_command(context, "PUSH 500".split(" "));
        assertEquals(500, context.pop(), 0.01);
    }

    @Test
    public void test_pop() throws CalculatorException{
        factory.make_command("PUSH 46".split(" ")).do_command(context, "PUSH 46".split(" "));
        factory.make_command("PUSH 1".split(" ")).do_command(context, "PUSH 1".split(" "));
        factory.make_command("POP".split(" ")).do_command(context, "POP".split(" "));
        assertEquals(46, context.pop(), 0.01);
    }
}
