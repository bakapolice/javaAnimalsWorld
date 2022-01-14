package controller.Listener;

import client.Client;
import controller.GeneralController;
import org.json.JSONArray;
import org.json.JSONObject;
import resources.Resources;


public class NetListener {

    public static final int REQUEST_TYPE_CONNECT = 0;
    public static final int REQUEST_TYPE_CREATE = 1;
    public static final int REQUEST_TYPE_KILL = 2;
    public static final int REQUEST_TYPE_FEED = 3;
    public static final int REQUEST_TYPE_PRINT = 4;
    public static final int REQUEST_TYPE_LOAD = 5;
    public static final int REQUEST_TYPE_SAVE = 6;

    private static JSONObject jsonRequest;
    private static JSONObject jsonResponse = new JSONObject();


    public static Client client;

    public static void startApp() {
        try {
            client = new Client();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void connectClient(String host, int port) throws Exception {
        try {
            client.connect(host, port);
        } catch (Exception ex) {
            throw new Exception(Resources.rb.getString("MESSAGE_ERROR_CONNECTING"));
        }
    }

    public static void disconnectClient() {
        try {
            if (client.isConnected())
                client.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean exitClient() {
        try {
            if (client.isConnected())
                client.disconnect();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

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

    public static void sendRequestToServer(int command) { //connect, save
        createJsonRequest(command, null, null, null, null, null, null);
        client.setNewCommand(true);
    }

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

    public static void getResponseFromServer(JSONObject jsonResponse) {
        System.out.println(jsonResponse.toString());
        setJsonResponse(jsonResponse);
        if(GeneralController.clientForm!=null)
        {
            switch (jsonRequest.getInt("request_type")) {
                case REQUEST_TYPE_CONNECT -> {
                    if (client.isConnected()) {
                        GeneralController.clientForm.getTextAreaLogs().append(jsonResponse.getString("message"));
                    }
                }
                case REQUEST_TYPE_CREATE, REQUEST_TYPE_KILL, REQUEST_TYPE_FEED, REQUEST_TYPE_SAVE -> GeneralController.clientForm.getTextAreaLogs().append(jsonResponse.getString("message"));
                case REQUEST_TYPE_PRINT -> GeneralController.clientForm.getTextAreaPrint().setText(jsonResponse.getString("message"));
                case REQUEST_TYPE_LOAD -> {
                    JSONObject data = jsonResponse.getJSONObject("data");
                    JSONArray keys = data.names();
                    switch (jsonResponse.getInt("selection_id")) {
                        case GeneralController.ALL_ALIVE_HERBIVORES -> {
                            for (int i = 0; i < keys.length(); i++) {
                                String key = keys.getString(i);
                                GeneralController.clientForm.getChoiceAllAliveHerbivoresKill().add(data.getString(key));
                                GeneralController.clientForm.getChoiceAllAliveHerbivores().add(data.getString(key));
                                GeneralController.clientForm.getListAllAliveHerbivoresToFeed().add(data.getString(key));
                            }
                        }
                        case GeneralController.ALL_ALIVE_PREDATORS -> {
                            for (int i = 0; i < keys.length(); i++) {
                                String key = keys.getString(i);
                                GeneralController.clientForm.getChoiceAllAlivePredatorsKill().add(data.getString(key));
                                GeneralController.clientForm.getListAllAlivePredators().add(data.getString(key));
                            }
                        }
                        case GeneralController.ALL_FOOD -> {
                            for (int i = 0; i < keys.length(); i++) {
                                String key = keys.getString(i);
                                GeneralController.clientForm.getChoiceAllFood().add(data.getString(key));
                            }
                        }
                    }
                }
            }
        }
    }

    public static JSONObject getJsonRequest() {
        return jsonRequest;
    }

    public static void setJsonRequest(JSONObject jsonRequest) {
        NetListener.jsonRequest = jsonRequest;
    }

    public static JSONObject getJsonResponse() {
        return jsonResponse;
    }

    public static void setJsonResponse(JSONObject jsonResponse) {
        NetListener.jsonResponse = jsonResponse;
    }
}
