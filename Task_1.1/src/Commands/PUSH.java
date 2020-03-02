package Commands;

import Context.Context;
import Exceptions.CalculatorException;
import Exceptions.Wrong_amount_of_args_exception;
import Exceptions.Wrong_format_value_exception;


public class PUSH implements Command {

    @Override
    public void do_command(Context context, String[] arguments) throws CalculatorException {
        if(arguments.length != 2){
            throw new Wrong_amount_of_args_exception("Not enough arguments for PUSH");
        }

        try{
            context.push(Double.parseDouble(arguments[1]));
        }
        catch (NumberFormatException ex){
            if(context.isVariable_in_stack(arguments[1]))
                context.push(context.get_variable_val(arguments[1]));
            else {
                throw new Wrong_format_value_exception("Wrong value for PUSH");
            }
        }
    }
}
