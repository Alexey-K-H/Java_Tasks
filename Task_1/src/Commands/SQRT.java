package Commands;

import contex_exe.Block;
import contex_exe.Context;

import java.util.Stack;

public class SQRT implements Command {
    @Block(arg = Context.STACK)
    private Stack<Double> stack;

    @Block(arg = Context.ARGUMENTS)
    private String[] arguments;

    @Override
    public void do_command() {
        double value;

        if(arguments.length != 1){
            System.out.println("Not valid command");
        }
        value = stack.pop();
        if(value >= 0){
            stack.push(Math.sqrt(value));
        }else{
            System.out.println("Sqrt for negative number is impossible");
        }
    }
}
