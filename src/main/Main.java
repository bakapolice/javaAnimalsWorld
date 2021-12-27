package main;

import controller.GeneralController;
import model.*;
import resources.Resources;
import storage.Storage;
import view.ClientForm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) {
        int selection = 0;


        if(!GeneralController.startApp()){
            System.out.println(Resources.rb.getString("MESSAGE_SETUP_ERROR"));
            return;
        }

        ClientForm form = new ClientForm();

        System.out.println(Resources.rb.getString("MESSAGE_HELLO"));
        Storage storage = Storage.getInstance();

        while (true) {
            System.out.println(
                            "1. " + Resources.rb.getString("MESSAGE_MAIN_MENU_ITEM_CREATE") + '\n' +
                            "2. " + Resources.rb.getString("MESSAGE_MAIN_MENU_ITEM_KILL") + "\n" +
                            "3. " + Resources.rb.getString("MESSAGE_MAIN_MENU_ITEM_FEED") + "\n" +
                            "4. " + Resources.rb.getString("MESSAGE_MAIN_MENU_ITEM_PRINT") + "\n" +
                            "0. " + Resources.rb.getString("MESSAGE_MAIN_MENU_ITEM_EXIT") + "\n");

            selection = scanner.nextInt();

            switch (selection) {
                //Создать
                case 1 -> {
                    System.out.println(
                            "1. " + Resources.rb.getString("MESSAGE_HERBIVORE") + "\n" +
                            "2. " + Resources.rb.getString("MESSAGE_PREDATOR") + "\n" +
                            "3. " + Resources.rb.getString("MESSAGE_GRASS"));
                    selection = scanner.nextInt();
                    switch (selection) {
                        case 1 -> createHerbivore(storage);
                        case 2 -> createPredator(storage);
                        case 3 -> createGrass(storage);
                        default -> throw new IllegalArgumentException("Неверный пункт меню!");
                    }
                }
                //Убить
                case 2 -> {
                    System.out.println(
                            "1. " + Resources.rb.getString("MESSAGE_HERBIVORE") + "\n" +
                            "2. " + Resources.rb.getString("MESSAGE_PREDATOR"));
                    selection = scanner.nextInt();
                    switch (selection) {
                        case 1 -> {
                            //Список всех живых травоядных
                            System.out.println(storage.printAllAliveHerbivores());
                            //Выбрать травоядное
                            System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHO_TO_KILL"));
                            selection = scanner.nextInt();
                            Herbivore herbivore = storage.findHerbivoreById(selection);
                            //Убить травоядное
                            herbivore.die();
                            //Обновить статус в хранилище на "Убит"
                            storage.update(herbivore);
                        }
                        case 2 -> {
                            //Список всех живых хищников
                            System.out.println(storage.printAllAlivePredators());
                            //Выбрать хищника
                            System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHO_TO_KILL"));
                            selection = scanner.nextInt();
                            Predator predator = storage.findPredatorById(selection);
                            //Убить хищника
                            predator.die();
                            //Обновить статус в хранилище на "Убит"
                            storage.update(predator);
                        }
                        default -> throw new IllegalArgumentException("Неверный пункт меню!");
                    }
                }
                //Покормить
                case 3 -> {
                    //Выбрать кого кормить
                    System.out.println(
                            "1." + Resources.rb.getString("MESSAGE_HERBIVORE") + "\n" +
                            "2." + Resources.rb.getString("MESSAGE_PREDATOR"));
                    selection = scanner.nextInt();
                    switch (selection) {
                        case 1 -> {
                            //Список всех живых травоядных
                            System.out.println(storage.printAllAliveHerbivores());
                            //Выбрать травоядное
                            System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHO_TO_FEED"));
                            selection = scanner.nextInt();
                            Herbivore herbivore = storage.findHerbivoreById(selection);
                            //Выбрать чем кормить
                            System.out.println(storage.printAllGrasses());
                            System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHAT_TO_FEED"));
                            selection = scanner.nextInt();
                            Grass grass = storage.findGrassById(selection);
                            //Покормить
                            herbivore.eat(grass);
                            //Обновить данные
                            storage.update(herbivore);
                            storage.update(grass);
                        }
                        case 2 -> {
                            //Список всех живых хищников
                            System.out.println(storage.printAllAlivePredators());
                            //Выбрать хищника
                            System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHO_TO_FEED"));
                            selection = scanner.nextInt();
                            Predator predator = storage.findPredatorById(selection);
                            //Выбрать чем кормить
                            System.out.println(storage.printAllAliveHerbivores());
                            System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHAT_TO_FEED"));
                            selection = scanner.nextInt();
                            Herbivore herbivore = storage.findHerbivoreById(selection);
                            //Покормить
                            predator.eat(herbivore);
                            //Обновить данные
                            storage.update(predator);
                            storage.update(herbivore);
                        }
                        default -> throw new IllegalArgumentException("Неверный пункт меню!");
                    }
                }
                //Вывести
                case 4 -> {
                    System.out.println(
                                    "1. " + Resources.rb.getString("MESSAGE_PRINT_ALL_ANIMALS") + '\n' +
                                    "2. " + Resources.rb.getString("MESSAGE_PRINT_ALL_ALIVE_ANIMALS") + '\n' +
                                    "3. " + Resources.rb.getString("MESSAGE_PRINT_ALL_HERBIVORES") + '\n' +
                                    "4. " + Resources.rb.getString("MESSAGE_PRINT_ALL_PREDATORS") + '\n' +
                                    "5. " + Resources.rb.getString("MESSAGE_PRINT_ALL_ALIVE_HERBIVORES") + '\n' +
                                    "6. " + Resources.rb.getString("MESSAGE_PRINT_ALL_ALIVE_PREDATORS") + '\n' +
                                    "7. " + Resources.rb.getString("MESSAGE_PRINT_ALL_FOOD") + '\n');
                    selection = scanner.nextInt();
                    switch (selection) {
                        case 1 -> System.out.println(storage.printAllAnimals());
                        case 2 -> System.out.println(storage.printAllAliveAnimals());
                        case 3 -> System.out.println(storage.printAllHerbivores());
                        case 4 -> System.out.println(storage.printAllPredators());
                        case 5 -> System.out.println(storage.printAllAliveHerbivores());
                        case 6 -> System.out.println(storage.printAllAlivePredators());
                        case 7 -> System.out.println(storage.printAllGrasses());
                        default -> throw new IllegalArgumentException("Неверный пункт меню!");
                    }
                }
                case 0 -> {
                    System.out.println(Resources.rb.getString("MESSAGE_EXIT"));
                    selection = scanner.nextInt();
                    switch (selection) {
                        case 1 -> {
                            storage.save();
                            return;
                        }
                        case 2 -> {
                            return;
                        }
                        default -> throw new IllegalArgumentException("Неверный пункт меню!");
                    }
                }
                default -> throw new IllegalArgumentException("Неверный пункт меню!");
            }
        }
    }

    private static void createPredator(Storage storage) {
        try {
            storage.create(new Predator(inputName("predator"), inputWeight("predator")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void createHerbivore(Storage storage) {
        try {
            storage.create(new Herbivore(inputName("herbivore"), inputWeight("herbivore")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void createGrass(Storage storage) {
        try {
            storage.create(new Grass(inputName("grass"), inputWeight("grass")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static String inputName(String type) throws IOException {
        if (type.equals("predator")) System.out.println(Resources.rb.getString("MESSAGE_ENTER_NAME_PREDATOR"));
        else if (type.equals("herbivore")) System.out.println(Resources.rb.getString("MESSAGE_ENTER_NAME_HERBIVORE"));
        else if (type.equals("grass")) System.out.println(Resources.rb.getString("MESSAGE_ENTER_NAME_GRASS"));
        else System.out.println(Resources.rb.getString("MESSAGE_ENTER_NAME"));
        return reader.readLine();
    }

    private static float inputWeight(String type) {
        if (type.equals("predator")) System.out.println(Resources.rb.getString("MESSAGE_ENTER_WEIGHT_PREDATOR"));
        else if (type.equals("herbivore")) System.out.println(Resources.rb.getString("MESSAGE_ENTER_WEIGHT_HERBIVORE"));
        else if (type.equals("grass")) System.out.println(Resources.rb.getString("MESSAGE_ENTER_WEIGHT_GRASS"));
        else System.out.println(Resources.rb.getString("MESSAGE_ENTER_WEIGHT"));
        return scanner.nextFloat();
    }
}


