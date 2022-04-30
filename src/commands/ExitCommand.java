package commands;

import exceptions.WrongAmountOfElementsException;
import utility.Console;

public class ExitCommand extends AbstractCommand{
    public ExitCommand() {
        super("exit", "завершить программу (без сохранения в файл)");
    }


    public boolean execute(String argument) {
        try {
            if (!argument.isEmpty()) throw new WrongAmountOfElementsException();
            return true;
        } catch (WrongAmountOfElementsException exception) {
            Console.println("Использование: '" + getName() + "'");
        }
        return false;
    }
}
