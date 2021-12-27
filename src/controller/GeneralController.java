package controller;

import model.*;
import resources.Resources;
import storage.Storage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class GeneralController {
    public static Storage storage;
    public static final Scanner scanner = new Scanner(System.in);
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static boolean startApp(){
        boolean isOk = Resources.startApp();
        if(isOk) storage = Storage.getInstance();
        return isOk;
    }

    // Создание -----------------------------------------------------
    public static void createPredator() {
        try {
            storage.create(new Predator(inputName("predator"), inputWeight("predator")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void createHerbivore() {
        try {
            storage.create(new Herbivore(inputName("herbivore"), inputWeight("herbivore")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void createGrass() {
        try {
            storage.create(new Grass(inputName("grass"), inputWeight("grass")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public static void createPredator(String name, Float weigh) {
            storage.create(new Predator(name, weigh));
    }

    public static void createHerbivore(String name, Float weigh) {
            storage.create(new Herbivore(name, weigh));
    }

    public static void createGrass(String name, Float weigh) {
            storage.create(new Grass(name, weigh));
    }


    private static String inputName(String type) throws IOException {
        switch (type) {
            case "predator" -> System.out.println(Resources.rb.getString("MESSAGE_ENTER_NAME_PREDATOR"));
            case "herbivore" -> System.out.println(Resources.rb.getString("MESSAGE_ENTER_NAME_HERBIVORE"));
            case "grass" -> System.out.println(Resources.rb.getString("MESSAGE_ENTER_NAME_GRASS"));
            default -> System.out.println(Resources.rb.getString("MESSAGE_ENTER_NAME"));
        }
        return reader.readLine();
    }

    private static float inputWeight(String type) {
        switch (type) {
            case "predator" -> System.out.println(Resources.rb.getString("MESSAGE_ENTER_WEIGHT_PREDATOR"));
            case "herbivore" -> System.out.println(Resources.rb.getString("MESSAGE_ENTER_WEIGHT_HERBIVORE"));
            case "grass" -> System.out.println(Resources.rb.getString("MESSAGE_ENTER_WEIGHT_GRASS"));
            default -> System.out.println(Resources.rb.getString("MESSAGE_ENTER_WEIGHT"));
        }
        return scanner.nextFloat();
    }
    //---------------------------------------------------------------


    //Убить ---------------------------------------------------------
    public static void killHerbivore(){
        //Список всех живых травоядных
        System.out.println(storage.printAllAliveHerbivores());
        //Выбрать травоядное
        System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHO_TO_KILL"));
        int selection = scanner.nextInt();
        Herbivore herbivore = storage.findHerbivoreById(selection);
        //Убить травоядное
        herbivore.die();
        //Обновить статус в хранилище на "Убит"
        storage.update(herbivore);
    }

    public static void killPredator(){
        //Список всех живых хищников
        System.out.println(storage.printAllAlivePredators());
        //Выбрать хищника
        System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHO_TO_KILL"));
        int selection = scanner.nextInt();
        Predator predator = storage.findPredatorById(selection);
        //Убить хищника
        predator.die();
        //Обновить статус в хранилище на "Убит"
        storage.update(predator);
    }
    //---------------------------------------------------------------

    //Покормить -----------------------------------------------------
    public static void feedHerbivore(){
        //Список всех живых травоядных
        System.out.println(storage.printAllAliveHerbivores());
        //Выбрать травоядное
        System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHO_TO_FEED"));
        int selection = scanner.nextInt();
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

    public static void feedPredator(){
        //Список всех живых хищников
        System.out.println(storage.printAllAlivePredators());
        //Выбрать хищника
        System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHO_TO_FEED"));
        int selection = scanner.nextInt();
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
    //---------------------------------------------------------------

    //Вывести -------------------------------------------------------
    public static void print(int selection){
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

    public static void loadData(Choice choice, int selection){
        switch (selection) {
            case 1 -> {
                for(Animal animal : storage.getAllAnimals())
                    choice.add(animal.getInfo());
            }
            case 2 -> {
                for(Animal animal : storage.getAllAliveAnimals().values())
                    choice.add(animal.getInfo());
            }
            case 3 -> {
                for(Animal animal : storage.getAllHerbivores().values())
                    choice.add(animal.getInfo());
            }
            case 4 -> {
                for(Animal animal : storage.getAllPredators().values())
                    choice.add(animal.getInfo());
            }
            case 5 -> {
                for(Animal animal : storage.getAllAliveHerbivores().values())
                    choice.add(animal.getInfo());
            }
            case 6 -> {
                for(Animal animal : storage.getAllAlivePredators().values())
                    choice.add(animal.getInfo());
            }
            case 7 -> {
                for(Grass grass : storage.getAllGrasses().values())
                    choice.add(grass.getInfo());
            }
            default -> throw new IllegalArgumentException("Неверный пункт меню!");
        }
    }
    //---------------------------------------------------------------



}
