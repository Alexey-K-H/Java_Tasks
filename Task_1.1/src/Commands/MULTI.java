package Commands;

import Context.Context;
import Exceptions.CalculatorException;
import Exceptions.Stack_size_exception;
import Exceptions.Wrong_amount_of_args_exception;

public class MULTI implements Command {

    @Override
    public void do_command(Context context, String[] arguments) throws CalculatorException {
        if(arguments.length < 1){
            throw new Wrong_amount_of_args_exception("Wrong count of args for MULTI command");
        }else if(context.stack_size() >= 2){
            context.push(context.pop() * context.pop());
        }else{
            throw new Stack_size_exception("Not enough values in stack");
        }
    }
}
