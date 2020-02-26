package Commands;

import Context.Context;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SQRT implements Command {
    private final static Logger logger = Logger.getLogger(SQRT.class.getName());

    @Override
    public void do_command(Context context, String[] arguments) {
        double value;

        if(arguments.length != 1){
            logger.log(Level.WARNING, "Not valid command");
        }
        value = context.pop();
        if(value >= 0){
            context.push(Math.sqrt(value));
        }else{
            logger.log(Level.WARNING, "Sqrt for negative number is impossible");
        }
    }
}
