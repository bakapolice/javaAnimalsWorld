package controller;

import Client.Client;
import model.Animal;
import model.Grass;
import org.json.JSONObject;
import storage.DataManager;

import javax.xml.crypto.Data;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NetController {

    public static final int REQUEST_TYPE_CONNECT = 0;
    public static final int REQUEST_TYPE_CREATE = 1;
    public static final int REQUEST_TYPE_KILL = 2;
    public static final int REQUEST_TYPE_FEED = 3;
    public static final int REQUEST_TYPE_PRINT = 4;
    public static final int REQUEST_TYPE_LOAD = 5;

    private static JSONObject jsonRequest;
    private static JSONObject jsonResponse;

    public static Client client;

    private static void createJsonRequest(int command, String type, String name, Float weigh, Integer selectionId, Integer foodId, Boolean isForm) {
        jsonRequest = new JSONObject();
        jsonRequest.put("request_type", command);
        jsonRequest.put("creature_type", type);
        jsonRequest.put("name", name);
        jsonRequest.put("weigh", weigh);
        jsonRequest.put("selection_id", selectionId);
        jsonRequest.put("food_id", foodId);
        jsonRequest.put("is_form", isForm);
    }

    private static void createJsonResponse(int command, String message) {
        jsonResponse = new JSONObject();
        jsonResponse.put("request_type", command);
        jsonResponse.put("message", message);
    }

    private static void createJsonResponse(int command, int selection, String[] data) {
        jsonResponse = new JSONObject();
        for (String str : data) {
            int counter = 0;
            jsonResponse.put(String.valueOf(counter++), data);
        }
        jsonResponse.toString();

    }

    public static void sendRequestToServer(int command){
        createJsonRequest(command, null, null, null, null, null, null);
        client.setNewCommand(true);
    }

    // На стороне клиента
    public static void sendRequestToServer(int command, int selectionId) {  //print
        createJsonRequest(command, null, null, null, selectionId, null, null);
        client.setNewCommand(true);
    }

    public static void sendRequestToServer(int command, String type, String name, Float weigh) { //create
        createJsonRequest(command, type, name, weigh, null, null, null);
        client.setNewCommand(true);
    }

    public static void sendRequestToServer(int command, String type, int selectionId, boolean isForm) { //kill
        createJsonRequest(command, type, null, null, selectionId, null, isForm);
        client.setNewCommand(true);
    }

    public static void sendRequestToServer(int command, String type, int selectionId, int foodID, boolean isForm) { //feed
        createJsonRequest(command, type, null, null, selectionId, foodID, isForm);
        client.setNewCommand(true);
    }

//    public static void getResponseFromServer(JSONObject jsonResponse){
//        switch (jsonRequest.getInt("request_type")){
//            case REQUEST_TYPE_CREATE -> {
//                //Вывести на форму полученную информацию с сервера.
//                //Перезагрузить данные на форме
//            }
//            case REQUEST_TYPE_KILL -> {
//                //Вывести на форму полученную информацию с сервера.
//                //Перезагрузить данные формы
//            }
//            case REQUEST_TYPE_FEED -> {
//                //Вывести на форму полученную информацию с сервера.
//                //Перезагрузить данные
//            }
//            case REQUEST_TYPE_PRINT -> {
//                //Вывести на форму полученную информацию с сервера.
//            }
//        }
//    }


    // На стороне сервера
    public static void getResponseFromServer(JSONObject jsonRequest) {
        switch (jsonRequest.getInt("request_type")) {
            case REQUEST_TYPE_CONNECT -> {
                createJsonResponse(REQUEST_TYPE_CONNECT, "Connected");
            }
            case REQUEST_TYPE_CREATE -> {
                if (jsonRequest.getString("creature_type").equals("Herbivore")) {
                    DataManager.createHerbivore(jsonRequest.getString("name"), (float) jsonRequest.getDouble("weigh"));
                }
                if (jsonRequest.getString("creature_type").equals("Predator")) {
                    DataManager.createHerbivore(jsonRequest.getString("name"), (float) jsonRequest.getDouble("weigh"));
                }
                if (jsonRequest.getString("creature_type").equals("Grass")) {
                    DataManager.createHerbivore(jsonRequest.getString("name"), (float) jsonRequest.getDouble("weigh"));
                }
                String message = "Создано: + " + jsonRequest.getString("creature_type") + jsonRequest.getString("name") + jsonRequest.getString("weigh");
                createJsonResponse(jsonRequest.getInt("request_type"), message);
            }
            case REQUEST_TYPE_KILL -> {
                if (jsonRequest.getString("creature_type").equals("Herbivore")) {
                    DataManager.killHerbivore(jsonRequest.getInt("selection_id"), jsonRequest.getBoolean("is_form"));
                }
                if (jsonRequest.getString("creature_type").equals("Predator")) {
                    DataManager.killPredator(jsonRequest.getInt("selection_id"), jsonRequest.getBoolean("is_form"));
                }
                String message = "Убит: + " + jsonRequest.getString("creature_type");
                createJsonResponse(jsonRequest.getInt("request_type"), message);
            }
            case REQUEST_TYPE_FEED -> {
                if (jsonRequest.getString("creature_type").equals("Herbivore")) {
                    DataManager.feedHerbivore(jsonRequest.getInt("selection_id"), jsonRequest.getInt("food_id"), jsonRequest.getBoolean("is_form"));
                }
                if (jsonRequest.getString("creature_type").equals("Predator")) {
                    DataManager.feedHerbivore(jsonRequest.getInt("selection_id"), jsonRequest.getInt("food_id"), jsonRequest.getBoolean("is_form"));
                }
                String message = "Убит: + " + jsonRequest.getString("creature_type");
                createJsonResponse(jsonRequest.getInt("request_type"), message);
            }
            case REQUEST_TYPE_PRINT -> {
                String message = DataManager.print(jsonRequest.getInt("selection_id"));
                createJsonResponse(jsonRequest.getInt("request_type"), message);
            }
            case REQUEST_TYPE_LOAD -> createJsonResponse(jsonRequest.getInt("request_type"), jsonRequest.getInt("selection_id"), DataManager.loadData(jsonRequest.getInt("selection_id")));
        }
    }


    public static JSONObject getJsonRequest() {
        return jsonRequest;
    }

    public static void setJsonRequest(JSONObject jsonRequest) {
        NetController.jsonRequest = jsonRequest;
    }

    public static JSONObject getJsonResponse() {
        return jsonResponse;
    }

    public static void setJsonResponse(JSONObject jsonResponse) {
        NetController.jsonResponse = jsonResponse;
    }
}
