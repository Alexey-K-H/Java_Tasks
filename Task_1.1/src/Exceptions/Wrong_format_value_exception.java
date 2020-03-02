package Exceptions;

public class Wrong_format_value_exception extends CalculatorException {
    public Wrong_format_value_exception(String str){
        super(str);
    }

    public Wrong_format_value_exception(){}

    public Wrong_format_value_exception(String str, Throwable cause){
        super(str, cause);
    }

    public Wrong_format_value_exception(Throwable cause){
        super(cause);
    }
}
