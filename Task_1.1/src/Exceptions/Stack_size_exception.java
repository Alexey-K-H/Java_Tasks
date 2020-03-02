package Exceptions;

public class Stack_size_exception extends CalculatorException{

    public Stack_size_exception(String str){
        super(str);
    }

    public Stack_size_exception(){}

    public Stack_size_exception(String str, Throwable cause){
        super(str, cause);
    }

    public Stack_size_exception(Throwable cause){
        super(cause);
    }
}
