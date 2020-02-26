package Commands;

import Context.Context;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DEFINE implements Command {
    private final static Logger logger = Logger.getLogger(DEFINE.class.getName());

    @Override
    public void do_command(Context context, String[] arguments) {
        try{
            if(arguments.length != 3){
                logger.log(Level.WARNING,"Not valid command");
                return;
            }
            context.put_variable(arguments[1], Double.parseDouble(arguments[2]));
        }
        catch (NumberFormatException ex){
            logger.log(Level.WARNING,"Invalid value to variable");
        }
    }
}
