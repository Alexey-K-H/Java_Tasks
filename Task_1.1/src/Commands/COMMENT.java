package Commands;

import Context.Context;

import java.util.logging.Level;
import java.util.logging.Logger;

public class COMMENT implements Command {

    private final static Logger logger = Logger.getLogger(COMMENT.class.getName());

    @Override
    public void do_command(Context context, String[] arguments) {
        if(arguments.length < 2){
            logger.log(Level.WARNING, "Not a valid command" + arguments[0]);
        }
        else{
            for(int i = 1; i < arguments.length; i++){
                System.out.print(arguments[i]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
