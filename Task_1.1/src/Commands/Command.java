package Commands;

//Интерфейс команд, классы определенных команд имплицируют данный интерфейс
//Метод Do_command перегружен в реализации каждого класса отдельной команды

import Context.Context;

public interface Command {
    void do_command(Context context, String[] arguments);
}