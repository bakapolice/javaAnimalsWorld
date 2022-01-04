package controller.Listener;

import controller.GeneralController;
import model.Grass;
import model.Herbivore;
import model.Predator;
import resources.Resources;
import storage.Storage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public static void createHerbivore() throws IOException {
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
    }

    public static void save(){
        GeneralController.save();
    }

    public static void killHerbivore(){
        GeneralController.killHerbivore();
    }

    public static void killPredator(){
        GeneralController.killPredator();
    }

    public static void feedHerbivore(){
        GeneralController.feedHerbivore();
    }

    public static void feedPredator(){
        GeneralController.feedPredator();
    }
}
