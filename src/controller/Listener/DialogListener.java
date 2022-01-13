package controller.Listener;

import controller.GeneralController;
import controller.NetController;
import resources.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class DialogListener {
    public static final Scanner scanner = new Scanner(System.in);
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void createPredator() throws IOException {
        System.out.println(Resources.rb.getString("MESSAGE_ENTER_NAME_PREDATOR"));
        String name = reader.readLine();
        System.out.println(Resources.rb.getString("MESSAGE_ENTER_WEIGHT_PREDATOR"));
        Float weigh = scanner.nextFloat();
        GeneralController.createPredator(name, weigh);
    }

    public static void createHerbivore() throws IOException {;
        System.out.println(Resources.rb.getString("MESSAGE_ENTER_NAME_HERBIVORE"));
        String name = reader.readLine();
        System.out.println(Resources.rb.getString("MESSAGE_ENTER_WEIGHT_HERBIVORE"));
        Float weigh = scanner.nextFloat();
        GeneralController.createHerbivore(name, weigh);
    }

    public static void createGrass() throws IOException {
        System.out.println(Resources.rb.getString("MESSAGE_ENTER_NAME_GRASS"));
        String name = reader.readLine();
        System.out.println(Resources.rb.getString("MESSAGE_ENTER_NAME_GRASS"));
        Float weigh = scanner.nextFloat();
        GeneralController.createGrass(name, weigh);
    }

    public static void print(int selection){
        GeneralController.print(selection);
        System.out.println(NetController.getJsonResponse().getString("message"));
        //System.out.println(GeneralController.print(selection));
    }

    public static void save(){
        GeneralController.save();
    }

    public static void killHerbivore(){
        //Список всех живых травоядных
        GeneralController.print(GeneralController.ALL_ALIVE_HERBIVORES);
        System.out.println(NetController.getJsonResponse().getString("message"));
        //System.out.println(GeneralController.print(GeneralController.ALL_ALIVE_HERBIVORES));
        //Выбрать травоядное
        System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHO_TO_KILL"));
        int selection = scanner.nextInt();
        GeneralController.killHerbivore(selection, false);
    }

    public static void killPredator(){
        //Список всех живых хищников
        GeneralController.print(GeneralController.ALL_ALIVE_PREDATORS);
        System.out.println(NetController.getJsonResponse().getString("message"));
        //System.out.println(GeneralController.print(GeneralController.ALL_ALIVE_PREDATORS));
        //Выбрать хищника
        System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHO_TO_KILL"));
        int selection = scanner.nextInt();
        GeneralController.killPredator(selection,false);
    }

    public static void feedHerbivore(){
        //Список всех живых травоядных
        GeneralController.print(GeneralController.ALL_ALIVE_HERBIVORES);
        System.out.println(NetController.getJsonResponse().getString("message"));
        //System.out.println(GeneralController.print(GeneralController.ALL_ALIVE_HERBIVORES));
        //Выбрать травоядное
        System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHO_TO_FEED"));
        int selection = scanner.nextInt();

        //Выбрать чем кормить
        GeneralController.print(GeneralController.ALL_FOOD);
        System.out.println(NetController.getJsonResponse().getString("message"));
        //System.out.println(GeneralController.print(GeneralController.ALL_FOOD));
        System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHAT_TO_FEED"));
        int foodID = scanner.nextInt();

        GeneralController.feedHerbivore(selection, foodID, false);
    }

    public static void feedPredator(){
        //Список всех живых хищников
        GeneralController.print(GeneralController.ALL_ALIVE_PREDATORS);
        System.out.println(NetController.getJsonResponse().getString("message"));
        //System.out.println(GeneralController.print(GeneralController.ALL_ALIVE_PREDATORS));
        //Выбрать хищника
        System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHO_TO_FEED"));
        int selection = scanner.nextInt();

        //Выбрать чем кормить
        GeneralController.print(GeneralController.ALL_ALIVE_HERBIVORES);
        System.out.println(NetController.getJsonResponse().getString("message"));
        //System.out.println(GeneralController.print(GeneralController.ALL_ALIVE_HERBIVORES));
        System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHAT_TO_FEED"));
        int foodID = scanner.nextInt();
        GeneralController.feedPredator(selection, foodID, false);
    }

    public static void connectClient(int port) throws Exception {
        NetController.connectClient("localhost", port);
    }
}
