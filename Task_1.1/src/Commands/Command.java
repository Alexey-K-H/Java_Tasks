package Commands;

//Интерфейс команд, классы определенных команд имплицируют данный интерфейс
//Метод Do_command перегружен в реализации каждого класса отдельной команды

import Context.Context;
import Exceptions.CalculatorException;

public interface Command {
    void do_command(Context context, String[] arguments) throws CalculatorException;
}