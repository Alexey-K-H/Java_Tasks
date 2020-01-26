package Commands;

import contex_exe.Block;
import contex_exe.Context;

public class COMMENT implements Command {
    @Block(arg = Context.ARGUMENTS)
    private String[] arguments;

    @Override
    public void do_command() {
        if(arguments.length < 2){
            System.out.println("Not valid command");
        }
        else{
            for(int i = 1; i < arguments.length; i++){
                System.out.println(arguments[i]);
            }
        }
    }
}
