package Commands;

import contex_exe.Block;
import contex_exe.Context;

import java.util.Map;
import java.util.Stack;

public class PUSH implements Command {
    @Block(arg = Context.STACK)
    private Stack<Double> stack;

    @Block(arg = Context.VARIABLES)
    private Map<String, Double> map;

    @Block(arg = Context.ARGUMENTS)
    private String[] arguments;

    @Override
    public void do_command() {
        try{
            if(arguments.length != 2){
                System.out.println("Not valid command");
            }
            stack.push(Double.parseDouble(arguments[1]));
        }
        catch (NumberFormatException ex){
            if(map.containsKey(arguments[1]))
                stack.push(map.get(arguments[1]));
            else System.out.println("Invalid value to PUSH");
        }
    }
}
