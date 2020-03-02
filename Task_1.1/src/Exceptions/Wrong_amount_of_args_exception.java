package Exceptions;

public class Wrong_amount_of_args_exception extends CalculatorException{
    public Wrong_amount_of_args_exception(String str){
        super(str);
    }

    public Wrong_amount_of_args_exception(){}

    public Wrong_amount_of_args_exception(String str, Throwable cause){
        super(str, cause);
    }

    public Wrong_amount_of_args_exception(Throwable cause){
        super(cause);
    }
}
