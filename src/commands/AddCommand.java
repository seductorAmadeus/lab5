package commands;

import classesandenums.Person;
import exceptions.IncorrectInputInScriptException;
import exceptions.WrongAmountOfElementsException;
import utility.CollectionManager;
import utility.Console;
import utility.QuestionAboutPerson;

import java.time.LocalDateTime;

public class AddCommand extends AbstractCommand {
    private CollectionManager collectionManager;
    private QuestionAboutPerson questionAboutPerson;

    public AddCommand(CollectionManager collectionManager, QuestionAboutPerson questionAboutPerson) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
        this.questionAboutPerson = questionAboutPerson;
    }

    public boolean execute(String argument) { //throw – используется для возбуждения исключения
        try { //определяет блок кода, в котором может произойти исключение
            if (argument.isEmpty())
                throw new WrongAmountOfElementsException(); //если аргумент пустой, то выкинет исключение
            collectionManager.addToCollection(new Person(
                    collectionManager.generateNextId(),
                    questionAboutPerson.askName(),
                    questionAboutPerson.askCoordinates(),
                    LocalDateTime.now(),
                    questionAboutPerson.askHeight(),
                    questionAboutPerson.askEyeColour(),
                    questionAboutPerson.askHairColour(),
                    questionAboutPerson.askNationality(),
                    questionAboutPerson.askLocation()
            ));
            Console.println("Человек успешно добавлен!");
            return true;
        } //catch – определяет блок кода, в котором происходит обработка исключения
        catch (WrongAmountOfElementsException exception) { //неправильное количество элементов
            Console.println("Использование: '" + getName() + "'");
        } catch (IncorrectInputInScriptException exception) {
            // TODO: add smth
        }//неправильный ввод в скрипте
        return false;
    }
}
