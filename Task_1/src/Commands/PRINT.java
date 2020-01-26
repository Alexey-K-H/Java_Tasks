package Commands;

import contex_exe.Block;
import contex_exe.Context;

import java.util.Stack;

public class PRINT implements Command {
    @Block(arg = Context.STACK)
    private Stack<Double> stack;

    @Block(arg = Context.ARGUMENTS)
    private String[] arguments;

    @Override
    public void do_command() {
        if(arguments.length != 1){
            System.out.println("Not valid command");
        }
        else if(stack.size() > 0)
            System.out.println(stack.peek());
        else{
            System.out.println("Stack is empty");
        }
    }
}
