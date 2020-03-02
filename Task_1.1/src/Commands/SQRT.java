package Commands;

import Context.Context;
import Exceptions.CalculatorException;
import Exceptions.Wrong_amount_of_args_exception;
import Exceptions.Wrong_format_value_exception;


public class SQRT implements Command {

    @Override
    public void do_command(Context context, String[] arguments) throws CalculatorException {
        double value;

        if(arguments.length != 1){
            throw new Wrong_amount_of_args_exception("Not enough args for SQRT");
        }
        value = context.pop();
        if(value >= 0){
            context.push(Math.sqrt(value));
        }else{
            throw new Wrong_format_value_exception("Sqrt from negative number cannot be got!");
        }
    }
}
