package Commands;

import Context.Context;
import Exceptions.CalculatorException;
import Exceptions.Stack_size_exception;
import Exceptions.Wrong_amount_of_args_exception;

public class DIVISION implements Command {

    @Override
    public void do_command(Context context, String[] arguments) throws CalculatorException {
        if(arguments.length != 1){
            throw new Wrong_amount_of_args_exception("Wrong count of args for DIVISION command");
        }

        if(context.stack_size() < 2){
            throw new Stack_size_exception("Not enough values in stack for DIVISION");
        }

        try{
            if(context.stack_size() >= 2){
                context.push(context.pop() / context.pop());
            }
        }catch (ArithmeticException ex){
            throw new ArithmeticException("Division by ZERO!");
        }
    }
}
