package commands;

import exceptions.WrongAmountOfElementsException;
import utility.Console;

public class HelpCommand extends AbstractCommand{
    public HelpCommand() {
        super("help", "вывести справку по доступным командам");
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
