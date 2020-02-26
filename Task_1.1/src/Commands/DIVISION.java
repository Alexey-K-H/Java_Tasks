package Commands;

import Context.Context;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DIVISION implements Command {
    private final static Logger logger = Logger.getLogger(DIVISION.class.getName());

    @Override
    public void do_command(Context context, String[] arguments) {
        try{
            if(arguments.length != 1){
                logger.log(Level.WARNING, "Not valid command");
            }else if(context.stack_size() >= 2){
                context.push(context.pop() / context.pop());
            }else {
                logger.log(Level.WARNING, "Not enough values in stack");
            }
        }catch (ArithmeticException ex){
            logger.log(Level.WARNING, "Error! Division by 0");
        }
    }
}
