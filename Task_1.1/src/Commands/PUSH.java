package Commands;

import Context.Context;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PUSH implements Command {
    private final static Logger logger = Logger.getLogger(PUSH.class.getName());

    @Override
    public void do_command(Context context, String[] arguments) {
        try{
            if(arguments.length != 2){
                logger.log(Level.WARNING, "Not valid command");
            }
            context.push(Double.parseDouble(arguments[1]));
        }
        catch (NumberFormatException ex){
            if(context.isVariable_in_stack(arguments[1]))
                context.push(context.get_variable_val(arguments[1]));
            else logger.log(Level.WARNING, "Invalid value to PUSH");
        }
    }
}
