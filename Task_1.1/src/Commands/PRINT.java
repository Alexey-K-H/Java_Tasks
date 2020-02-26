package Commands;

import Context.Context;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PRINT implements Command {
    private final static Logger logger = Logger.getLogger(PRINT.class.getName());

    @Override
    public void do_command(Context context, String[] arguments) {
        if(arguments.length != 1){
            logger.log(Level.WARNING, "Not valid command");
        }
        else if(context.stack_size() > 0)
            System.out.println(context.peek_stack());
        else{
            logger.log(Level.WARNING, "Stack is empty");
        }
    }
}
