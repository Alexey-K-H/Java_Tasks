package Commands;

import contex_exe.Block;
import contex_exe.Context;

import java.util.Stack;

public class SUM implements Command {
    @Block(arg = Context.STACK)
    private Stack<Double> stack;

    @Block(arg = Context.ARGUMENTS)
    private String[] arguments;

    @Override
    public void do_command() {
        if(arguments.length != 1){
            System.out.println("Not valid command");
        }else if(stack.size() >= 2){
            stack.push(stack.pop() + stack.pop());
        }else{
            System.out.println("Not enough values in stack");
        }
    }
}
