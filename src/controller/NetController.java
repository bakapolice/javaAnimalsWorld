package controller;

import storage.DataManager;

import javax.xml.crypto.Data;

public class NetController {
    public static final int REQUEST_TYPE_CREATE = 1;
    public static final int REQUEST_TYPE_KILL = 2;
    public static final int REQUEST_TYPE_FEED = 3;
    public static final int REQUEST_TYPE_PRINT = 4;

    public static ServerResponse serverResponse;
    public static ServerRequest serverRequest;

    public static void sendRequestToServer(int command, int id) {
        serverRequest = new ServerRequest(command, id);
    }
    public static void sendRequestToServer(int command, String type, String name, Float weigh){
        serverRequest = new ServerRequest(command, type, name, weigh);
    }

    public static void sendRequestToServer(int command, String type, int id){
        serverRequest = new ServerRequest(command, type, id);
    }


    public static void getRequestFromClient(ServerRequest serverRequest){
        switch (serverRequest.getCommand()){
            case REQUEST_TYPE_CREATE -> {
                if(serverRequest.getType().equals("Herbivore")){
                    DataManager.createHerbivore(serverRequest.getName(), serverRequest.getWeigh());
                }
                if(serverRequest.getType().equals("Predator")){
                    DataManager.createPredator(serverRequest.getName(), serverRequest.getWeigh());
                }
                if(serverRequest.getType().equals("Grass")){
                    DataManager.createGrass(serverRequest.getName(), serverRequest.getWeigh());
                }
                String data = "Создано: + " + serverRequest.getType() + serverRequest.getName() + serverRequest.getWeigh();
                serverResponse = new ServerResponse(serverRequest.getCommand(), data);
            }
            case REQUEST_TYPE_KILL -> {
                if(serverRequest.getType().equals("Herbivore")){
                    DataManager.killHerbivore();
                }
                if(serverRequest.getType().equals("Predator")){
                    DataManager.killPredator();
                }
                String data = "Убит: + " + serverRequest.getType() + serverRequest.getName() + serverRequest.getWeigh();
                serverResponse = new ServerResponse(serverRequest.getCommand(), data);
            }
        }
    }







    public static void getResponseFromServer() {

    }

    public static ServerResponse getServerResponse() {
        return serverResponse;
    }

    public static void setServerResponse(ServerResponse serverResponse) {
        NetController.serverResponse = serverResponse;
    }

    public static ServerRequest getServerRequest() {
        return serverRequest;
    }

    public static void setServerRequest(ServerRequest serverRequest) {
        NetController.serverRequest = serverRequest;
    }
}
