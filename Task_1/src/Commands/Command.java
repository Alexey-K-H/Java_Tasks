package Commands;

//Интерфейс команд, классы определенных команд имплицируют данный интерфейс
//Метод Do_command перегружен в реализации каждого класса отдельной команды

public interface Command {
    void do_command();
}
