package main;

import client.Client;
import controller.GeneralController;
import controller.Listener.DialogListener;
import resources.Resources;

import java.io.IOException;
import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int selection;
        if (!Resources.setup()) {
            System.out.println(Resources.rb.getString("MESSAGE_SETUP_ERROR"));
            return;
        }

        while (true) {
            System.out.println(Resources.rb.getString("CHOSE_WORKING_MODE"));
            System.out.println(
                    "1." + Resources.rb.getString("DIALOG_MODE") + "\n" +
                            "2." + Resources.rb.getString("CLIENT_FORM_MODE") + '\n' +
                            "0." + Resources.rb.getString("MESSAGE_MAIN_MENU_ITEM_EXIT"));
            try {
                selection = scanner.nextInt();
                switch (selection) {
                    case 0 -> System.exit(0);
                    case 1 -> {
                        GeneralController.startApp(false);
                        showDialog();
                    }
                    case 2 -> {
                        GeneralController.startApp(true);
                        return;
                    }
                    default -> System.err.println(Resources.rb.getString("MESSAGE_WRONG_MENU_POINT"));
                }
                System.out.println(Resources.rb.getString("MESSAGE_HELLO"));
            } catch (InputMismatchException ex) {
                scanner.nextLine();
                System.err.println(Resources.rb.getString("INVALID_DATA"));
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    private static void showDialog() throws IOException {
        int selection;
        while (!Client.isConnected()) {
            try {
                Thread.sleep(500);
                System.out.println(Resources.rb.getString("MESSAGE_ENTER_PORT"));
                selection = scanner.nextInt();
                DialogListener.connectClient(selection);
            } catch (InputMismatchException ex) {
                scanner.nextLine();
                System.err.println(Resources.rb.getString("INVALID_DATA"));
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        }
        while (true) {
            System.out.println(
                    "1. " + Resources.rb.getString("MESSAGE_MAIN_MENU_ITEM_CREATE") + '\n' +
                            "2. " + Resources.rb.getString("MESSAGE_MAIN_MENU_ITEM_KILL") + "\n" +
                            "3. " + Resources.rb.getString("MESSAGE_MAIN_MENU_ITEM_FEED") + "\n" +
                            "4. " + Resources.rb.getString("MESSAGE_MAIN_MENU_ITEM_PRINT") + "\n" +
                            "0. " + Resources.rb.getString("MESSAGE_MAIN_MENU_ITEM_EXIT") + "\n");

            selection = scanner.nextInt();

            switch (selection) {
                case 1 -> {
                    System.out.println(
                            "1. " + Resources.rb.getString("MESSAGE_HERBIVORE") + "\n" +
                                    "2. " + Resources.rb.getString("MESSAGE_PREDATOR") + "\n" +
                                    "3. " + Resources.rb.getString("MESSAGE_GRASS"));
                    selection = scanner.nextInt();
                    switch (selection) {
                        case 1 -> DialogListener.createHerbivore();
                        case 2 -> DialogListener.createPredator();
                        case 3 -> DialogListener.createGrass();
                        default -> System.err.println(Resources.rb.getString("MESSAGE_WRONG_MENU_POINT"));
                    }
                }
                case 2 -> {
                    System.out.println(
                            "1. " + Resources.rb.getString("MESSAGE_HERBIVORE") + "\n" +
                                    "2. " + Resources.rb.getString("MESSAGE_PREDATOR"));
                    selection = scanner.nextInt();
                    switch (selection) {
                        case 1 -> DialogListener.killHerbivore();
                        case 2 -> DialogListener.killPredator();
                        default -> System.err.println(Resources.rb.getString("MESSAGE_WRONG_MENU_POINT"));
                    }
                }
                case 3 -> {
                    System.out.println(
                            "1." + Resources.rb.getString("MESSAGE_HERBIVORE") + "\n" +
                                    "2." + Resources.rb.getString("MESSAGE_PREDATOR"));
                    selection = scanner.nextInt();
                    switch (selection) {
                        case 1 -> DialogListener.feedHerbivore();
                        case 2 -> DialogListener.feedPredator();
                        default -> System.err.println(Resources.rb.getString("MESSAGE_WRONG_MENU_POINT"));
                    }
                }
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
                    DialogListener.print(selection);
                }
                case 0 -> {
                    System.out.println(Resources.rb.getString("MESSAGE_EXIT"));
                    selection = scanner.nextInt();
                    switch (selection) {
                        case 1 -> {
                            DialogListener.save();
                            System.exit(0);
                        }
                        case 2 -> System.exit(0);
                        default -> System.err.println(Resources.rb.getString("MESSAGE_WRONG_MENU_POINT"));
                    }
                }
                default -> System.err.println(Resources.rb.getString("MESSAGE_WRONG_MENU_POINT"));
            }
        }
    }
}


