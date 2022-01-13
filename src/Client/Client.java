package Client;

import controller.GeneralController;
import controller.NetController;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class Client implements Runnable {
    private Socket socket = null;
    private static boolean isConnected;
    private boolean newCommand;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private String serverRequest;
    private String serverResponse;

    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        isConnected = true;
        Thread clientThread = new Thread(this);
        clientThread.start();
    }

    public void disconnect() throws IOException {
        socket.close();
        isConnected = false;
        //newCommand = true;
    }

    @Override
    public void run() {
        try {
            NetController.sendRequestToServer(NetController.REQUEST_TYPE_CONNECT);
            while (isConnected) {
                while (!newCommand)
                {
                    if(!isConnected)
                    {
                        System.err.println("Отключение");
                        return;
                    }
                    Thread.sleep(100);
                }

                serverRequest = NetController.getJsonRequest().toString();
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                while (newCommand) {

                    objectOutputStream.writeObject(serverRequest);
                    objectInputStream = new ObjectInputStream(socket.getInputStream());
                    serverResponse = objectInputStream.readObject().toString();
                    NetController.getResponseFromServer(new JSONObject(serverResponse));
                    newCommand = false;
                }
            }
        } catch (SocketException ex){
            GeneralController.ConnectErrorMessage();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            GeneralController.disableComponents();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public static boolean isConnected() {
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
