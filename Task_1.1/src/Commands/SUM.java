package Commands;

import Context.Context;
import Exceptions.CalculatorException;
import Exceptions.Stack_size_exception;
import Exceptions.Wrong_amount_of_args_exception;

public class SUM implements Command {

    @Override
    public void do_command(Context context, String[] arguments) throws CalculatorException {
        if(arguments.length != 1){
            throw new Wrong_amount_of_args_exception("Not enough args for SUM");
        }else if(context.stack_size() >= 2){
            context.push(context.pop() + context.pop());
        }else{
            throw new Stack_size_exception("Not enough values in stack");
        }
    }
}
