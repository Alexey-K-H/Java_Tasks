package Exceptions;

public class CalculatorException extends Exception{

    public CalculatorException(String str){
        super(str);
    }

    public CalculatorException(){}

    public CalculatorException(String str, Throwable cause){
        super(str, cause);
    }

    public CalculatorException(Throwable cause){
        super(cause);
    }
}
