package utility;

import exceptions.ScriptRecursionException;
import main.Proga;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;


    public class Console {
        private CommandManager commandManager;
        private Scanner userScanner;
        private QuestionAboutPerson questionAboutPerson;
        private List<String> scriptStack = new ArrayList<>();

        public Console(CommandManager commandManager, Scanner userScanner, QuestionAboutPerson questionAboutPerson) {
            this.commandManager = commandManager;
            this.userScanner = userScanner;
            this.questionAboutPerson = questionAboutPerson;
        }

        /**
         * Mode for catching commands from user input.
         */
        public void interactiveMode() {
            String[] userCommand = {"", ""};
            int commandStatus;
            try {
                do {
                    Console.print(Proga.PS1);
                    userCommand = (userScanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                    commandStatus = launchCommand(userCommand);
                } while (commandStatus != 2);
            } catch (NoSuchElementException exception) {
                Console.printerror("Пользовательский ввод не обнаружен!");
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
            }
        }

        /**
         * Mode for catching commands from a script.
         * @param argument Its argument.
         * @return Exit code.
         */
        public int scriptMode(String argument) {
            String[] userCommand = {"", ""};
            int commandStatus;
            scriptStack.add(argument);
            try (Scanner scriptScanner = new Scanner(new File(argument))) {
                if (!scriptScanner.hasNext()) throw new NoSuchElementException();
                Scanner tmpScanner = questionAboutPerson.getUserScanner();
                questionAboutPerson.setUserScanner(scriptScanner);
                questionAboutPerson.setFileMode();
                do {
                    userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                    while (scriptScanner.hasNextLine() && userCommand[0].isEmpty()) {
                        userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                        userCommand[1] = userCommand[1].trim();
                    }
                    Console.println(Proga.PS1 + String.join(" ", userCommand));
                    if (userCommand[0].equals("execute_script")) {
                        for (String script : scriptStack) {
                            if (userCommand[1].equals(script)) throw new ScriptRecursionException();
                        }
                    }
                    commandStatus = launchCommand(userCommand);
                } while (commandStatus == 0 && scriptScanner.hasNextLine());
                questionAboutPerson.setUserScanner(tmpScanner);
                questionAboutPerson.setUserMode();
                if (commandStatus == 1 && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty()))
                    Console.println("Проверьте скрипт на корректность введенных данных!");
                return commandStatus;
            } catch (FileNotFoundException exception) {
                Console.printerror("Файл со скриптом не найден!");
            } catch (NoSuchElementException exception) {
                Console.printerror("Файл со скриптом пуст!");
            } catch (ScriptRecursionException exception) {
                Console.printerror("Скрипты не могут вызываться рекурсивно!");
            } catch (IllegalStateException exception) {
                Console.printerror("Непредвиденная ошибка!");
                System.exit(0);
            } finally {
                scriptStack.remove(scriptStack.size()-1);
            }
            return 1;
        }

        /**
         * Launchs the command.
         * @param userCommand Command to launch.
         * @return Exit code.
         */
        private int launchCommand(String[] userCommand) {
            switch (userCommand[0]) {
                case "":
                    break;
                case "help":
                    if (!commandManager.help(userCommand[1])) return 1;
                    break;
                case "info":
                    if (!commandManager.info(userCommand[1])) return 1;
                    break;
                case "show":
                    if (!commandManager.show(userCommand[1])) return 1;
                    break;
                case "add":
                    if (!commandManager.add(userCommand[1])) return 1;
                    break;
                case "update":
                    if (!commandManager.update(userCommand[1])) return 1;
                    break;
                case "remove_by_id":
                    if (!commandManager.removeById(userCommand[1])) return 1;
                    break;
                case "clear":
                    if (!commandManager.clear(userCommand[1])) return 1;
                    break;
                case "save":
                    if (!commandManager.save(userCommand[1])) return 1;
                    break;
                case "execute_script":
                    if (!commandManager.executeScript(userCommand[1])) return 1;
                    else return scriptMode(userCommand[1]);
                case "add_if_min":
                    if (!commandManager.addIfMin(userCommand[1])) return 1;
                    break;
                case "remove_greater":
                    if (!commandManager.removeGreater(userCommand[1])) return 1;
                    break;
                case "remove_lower":
                    if (!commandManager.removeLower(userCommand[1])) return 1;
                    break;
                case "group_counting_by_id":
                    if (!commandManager.groupCountingById(userCommand[1])) return 1;
                    break;
                case "filter_starts_with_name":
                    if (!commandManager.filterStartsWithName(userCommand[1])) return 1;
                    break;
                case "print_unique_location":
                    if (!commandManager.printUniqueLocation(userCommand[1])) return 1;
                    break;
                case "exit":
                    if (!commandManager.exit(userCommand[1])) return 1;
                    else return 2;
                default:
                    if (!commandManager.noSuchCommand(userCommand[0])) return 1;
            }
            return 0;
        }

        /**
         * Prints toOut.toString() to Console
         * @param toOut Object to print
         */
        public static void print(Object toOut) {
            System.out.print(toOut);
        }

        /**
         * Prints toOut.toString() + \n to Console
         * @param toOut Object to print
         */
        public static void println(Object toOut) {
            System.out.println(toOut);
        }

        /**
         * Prints error: toOut.toString() to Console
         * @param toOut Error to print
         */
        public static void printerror(Object toOut) {
            System.out.println("error: " + toOut);
        }

        /**
         * Prints formatted 2-element table to Console
         * @param element1 Left element of the row.
         * @param element2 Right element of the row.
         */
        public static void printtable(Object element1, Object element2) {
            System.out.printf("%-37s%-1s%n", element1, element2);
        }

        @Override
        public String toString() {
            return "Console (класс для обработки ввода команд)";
        }
    }
