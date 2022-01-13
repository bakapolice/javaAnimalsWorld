package controller;


import view.ClientForm;

import javax.swing.*;

public class GeneralController {

    public final static int ALL_ANIMALS = 1;
    public final static int ALL_ALIVE_ANIMALS = 2;
    public final static int ALL_HERBIVORES = 3;
    public final static int ALL_PREDATORS = 4;
    public final static int ALL_ALIVE_HERBIVORES = 5;
    public final static int ALL_ALIVE_PREDATORS = 6;
    public final static int ALL_FOOD = 7;

    public static ClientForm clientForm;

    public static void startApp(boolean isForm) {
        if (isForm) {
            clientForm = new ClientForm();
        }
        NetController.startApp();
    }

    // Создание -----------------------------------------------------
    public static void createPredator(String name, Float weigh) {
        NetController.sendRequestToServer(NetController.REQUEST_TYPE_CREATE, "Predator", name, weigh);
    }

    public static void createHerbivore(String name, Float weigh) {
        NetController.sendRequestToServer(NetController.REQUEST_TYPE_CREATE, "Herbivore", name, weigh);
    }

    public static void createGrass(String name, Float weigh) {
        NetController.sendRequestToServer(NetController.REQUEST_TYPE_CREATE, "Grass", name, weigh);
    }
    //---------------------------------------------------------------


    //Убить ---------------------------------------------------------
    public static void killHerbivore(int selection, boolean form) {
        NetController.sendRequestToServer(NetController.REQUEST_TYPE_KILL, "Herbivore", selection, form);
    }

    public static void killPredator(int selection, boolean form) {
        NetController.sendRequestToServer(NetController.REQUEST_TYPE_KILL, "Predator", selection, form);
    }
    //---------------------------------------------------------------

    //Покормить -----------------------------------------------------
    public static void feedHerbivore(int selection, int foodID, boolean form) {
        NetController.sendRequestToServer(NetController.REQUEST_TYPE_FEED, "Herbivore", selection, foodID, form);
    }

    public static void feedPredator(int selection, int foodID, boolean form) {
        NetController.sendRequestToServer(NetController.REQUEST_TYPE_FEED, "Predator", selection, foodID, form);
    }
    //---------------------------------------------------------------

    //Вывести -------------------------------------------------------
    public static void print(int selection) {
        NetController.sendRequestToServer(NetController.REQUEST_TYPE_PRINT, selection);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //---------------------------------------------------------------

    public static void loadData(int selection) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        NetController.sendRequestToServer(NetController.REQUEST_TYPE_LOAD, selection);
    }

    public static void disableComponents(){
        if(clientForm!= null)
        clientForm.disableComponents();
    }

    public static void ConnectErrorMessage(){
        if(clientForm != null)
            JOptionPane.showMessageDialog(GeneralController.clientForm, "Сервер отключен или недоступен!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
    }

    public static void save() {
        NetController.sendRequestToServer(NetController.REQUEST_TYPE_SAVE);
    }
}
