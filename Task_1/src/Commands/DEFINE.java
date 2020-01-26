package Commands;

import contex_exe.Block;
import contex_exe.Context;

import java.util.Map;

public class DEFINE implements Command {

    @Block(arg = Context.VARIABLES)
    private Map<String, Double> map;

    @Block(arg = Context.ARGUMENTS)
    private String[] arguments;

    @Override
    public void do_command() {
        try{
            if(arguments.length != 3){
                System.out.println("Not valid command");
                return;
            }
            map.put(arguments[1], Double.parseDouble(arguments[2]));
        }
        catch (NumberFormatException ex){
            System.out.println("Invalid value to variable");
        }
    }
}
