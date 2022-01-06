package Client;

import controller.NetController;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client implements Runnable {
    private Socket socket = null;
    private boolean isConnected;
    private boolean newCommand;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        isConnected = true;
        Thread clientThread = new Thread(this);
        clientThread.start();
    }

    public void disconnect() throws IOException {
        socket.close();
        isConnected = false;
    }

    @Override
    public void run() {
        try {
            NetController.sendRequestToServer(NetController.REQUEST_TYPE_CONNECT);
            while (isConnected) {
                while (!newCommand) Thread.sleep(100);
                String serverRequest = NetController.getJsonRequest().toString();
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                System.out.println("Новая команда");
                while (newCommand) {
                    JSONObject tmp = new JSONObject(serverRequest);
                    objectOutputStream.writeObject(serverRequest);

                    objectInputStream = new ObjectInputStream(socket.getInputStream());
                    String serverResponse = objectInputStream.readObject().toString();

                    NetController.getResponseFromServer(new JSONObject(serverResponse));
                    newCommand = false;
                }
            }
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean isNewCommand() {
        return newCommand;
    }

    public void setNewCommand(boolean newCommand) {
        this.newCommand = newCommand;
    }
}
