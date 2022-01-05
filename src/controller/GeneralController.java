package controller;

import model.*;
import resources.Resources;
import storage.DataManager;
import storage.Storage;

import java.awt.*;
import java.util.Scanner;

public class GeneralController {
    public static boolean isStarted;

    public final static int ALL_ANIMALS = 1;
    public final static int ALL_ALIVE_ANIMALS = 2;
    public final static int ALL_HERBIVORES = 3;
    public final static int ALL_PREDATORS = 4;
    public final static int ALL_ALIVE_HERBIVORES = 5;
    public final static int ALL_ALIVE_PREDATORS = 6;
    public final static int ALL_FOOD = 7;

    public static boolean startApp(){
        isStarted = Resources.startApp();
        if(isStarted) DataManager.initialise(Resources.initialise);
        return isStarted;
    }

    // Создание -----------------------------------------------------
    public static void createPredator(String name, Float weigh) {
        DataManager.createPredator(name, weigh);
    }

    public static void createHerbivore(String name, Float weigh) {
        DataManager.createHerbivore(name, weigh);
    }

    public static void createGrass(String name, Float weigh) {
        DataManager.createGrass(name, weigh);
    }
    //---------------------------------------------------------------


    //Убить ---------------------------------------------------------
    public static void killHerbivore(){
        DataManager.killHerbivore();
    }

    public static void killPredator(){
        DataManager.killPredator();
    }
    //---------------------------------------------------------------

    //Покормить -----------------------------------------------------
    public static void feedHerbivore(){
        DataManager.feedHerbivore();
    }

    public static void feedPredator(){
        DataManager.feedPredator();
    }
    //---------------------------------------------------------------

    //Вывести -------------------------------------------------------
    public static String print(int selection){
        return DataManager.print(selection);
    }
    //---------------------------------------------------------------

    public static void loadData(Choice choice, int selection){
        DataManager.loadData(choice,selection);
    }

    public static void save(){
        DataManager.save();
    }
}
