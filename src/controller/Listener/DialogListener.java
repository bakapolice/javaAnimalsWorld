package controller.Listener;

import controller.GeneralController;
import resources.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DialogListener {
    public static final Scanner scanner = new Scanner(System.in);
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void createPredator() {
        String name;
        float weigh;
        while (true) {
            try {
                System.out.println(Resources.rb.getString("MESSAGE_ENTER_NAME_PREDATOR"));
                name = reader.readLine();
                break;
            } catch (IOException e) {
                System.err.println(Resources.rb.getString("MSGBOX_WRONG_NAME_VALUE"));
                scanner.nextLine();
            }
        }
        while (true) {
            try {
                System.out.println(Resources.rb.getString("MESSAGE_ENTER_WEIGHT_PREDATOR"));
                weigh = scanner.nextFloat();
                break;
            } catch (InputMismatchException ex) {
                System.err.println(Resources.rb.getString("MSGBOX_WRONG_WEIGH_VALUE"));
                scanner.nextLine();
            }
        }
        GeneralController.createPredator(name, weigh);
    }

    public static void createHerbivore() {
        String name;
        float weigh;
        while (true) {
            try {
                System.out.println(Resources.rb.getString("MESSAGE_ENTER_NAME_HERBIVORE"));
                name = reader.readLine();
                break;
            } catch (IOException e) {
                System.err.println(Resources.rb.getString("MSGBOX_WRONG_NAME_VALUE"));
                scanner.nextLine();
            }
        }
        while (true) {
            try {
                System.out.println(Resources.rb.getString("MESSAGE_ENTER_WEIGHT_HERBIVORE"));
                weigh = scanner.nextFloat();
                break;
            } catch (InputMismatchException ex) {
                System.err.println(Resources.rb.getString("MESSAGE_ENTER_WEIGHT_PREDATOR"));
                scanner.nextLine();
            }
        }
        GeneralController.createHerbivore(name, weigh);
    }

    public static void createGrass() {
        String name;
        float weigh;
        while (true) {
            try {
                System.out.println(Resources.rb.getString("MESSAGE_ENTER_NAME_GRASS"));
                name = reader.readLine();
                break;
            } catch (IOException e) {
                System.err.println(Resources.rb.getString("MSGBOX_WRONG_NAME_VALUE"));
                scanner.nextLine();
            }
        }
        while (true) {
            try {
                System.out.println(Resources.rb.getString("MESSAGE_ENTER_WEIGHT_GRASS"));
                weigh = scanner.nextFloat();
                break;
            } catch (InputMismatchException ex) {
                System.err.println(Resources.rb.getString("MESSAGE_ENTER_WEIGHT_PREDATOR"));
                scanner.nextLine();
            }
        }
        GeneralController.createGrass(name, weigh);
    }

    public static void print(int selection) {
        GeneralController.print(selection);
        System.out.println(NetListener.getJsonResponse().getString("message"));
    }

    public static void save() {
        GeneralController.save();
    }

    public static void killHerbivore() {
        GeneralController.print(GeneralController.ALL_ALIVE_HERBIVORES);
        System.out.println(NetListener.getJsonResponse().getString("message"));
        while (true) {
            try {
                System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHO_TO_KILL"));
                int selection = scanner.nextInt();
                GeneralController.killHerbivore(selection, false);
                break;
            } catch (InputMismatchException ex) {
                System.err.println(Resources.rb.getString("INVALID_DATA"));
                scanner.nextLine();
            }
        }
    }

    public static void killPredator() {
        GeneralController.print(GeneralController.ALL_ALIVE_PREDATORS);
        System.out.println(NetListener.getJsonResponse().getString("message"));
        while (true) {
            try {
                System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHO_TO_KILL"));
                int selection = scanner.nextInt();
                GeneralController.killPredator(selection, false);
                break;
            } catch (InputMismatchException ex) {
                System.err.println(Resources.rb.getString("INVALID_DATA"));
                scanner.nextLine();
            }
        }
    }

    public static void feedHerbivore() {
        GeneralController.print(GeneralController.ALL_ALIVE_HERBIVORES);
        System.out.println(NetListener.getJsonResponse().getString("message"));
        int selection = getSelection();
        GeneralController.print(GeneralController.ALL_FOOD);
        System.out.println(NetListener.getJsonResponse().getString("message"));
        int foodID = getFoodID();
        GeneralController.feedHerbivore(selection, foodID, false);
    }

    public static void feedPredator() {
        GeneralController.print(GeneralController.ALL_ALIVE_PREDATORS);
        System.out.println(NetListener.getJsonResponse().getString("message"));
        int selection = getSelection();
        GeneralController.print(GeneralController.ALL_ALIVE_HERBIVORES);
        System.out.println(NetListener.getJsonResponse().getString("message"));
        int foodID = getFoodID();
        GeneralController.feedPredator(selection, foodID, false);
    }

    private static int getFoodID() {
        int foodID;
        while (true) {
            try {
                System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHAT_TO_FEED"));
                foodID = scanner.nextInt();
                break;
            } catch (InputMismatchException ex) {
                System.err.println(Resources.rb.getString("INVALID_DATA"));
                scanner.nextLine();
            }
        }
        return foodID;
    }

    private static int getSelection() {
        int selection;
        while (true) {
            try {
                System.out.println(Resources.rb.getString("MESSAGE_CHOOSE_WHO_TO_FEED"));
                 selection = scanner.nextInt();
                break;
            } catch (InputMismatchException ex) {
                System.err.println(Resources.rb.getString("INVALID_DATA"));
                scanner.nextLine();
            }
        }
        return selection;
    }

    public static void connectClient(int port) throws Exception {
        NetListener.connectClient("localhost", port);
    }
}
