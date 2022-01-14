package client;

import controller.GeneralController;
import controller.Listener.NetListener;
import org.json.JSONObject;
import resources.Resources;

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

    public void disconnect(){
        try {
            socket.close();
            isConnected = false;
        }
        catch (IOException ex) {
            System.err.println(Resources.rb.getString("MESSAGE_ERROR_SOCKET_CLOSING"));
        }
    }

    @Override
    public void run() {
        try {
            NetListener.sendRequestToServer(NetListener.REQUEST_TYPE_CONNECT);
            while (isConnected) {
                while (!newCommand)
                {
                    if(!isConnected)
                    {
                        System.err.println(Resources.rb.getString("MESSAGE_DISCONNECTING"));
                        return;
                    }
                    Thread.sleep(100);
                }
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                serverRequest = NetListener.getJsonRequest().toString();
                while (newCommand) {
                    objectOutputStream.writeObject(serverRequest);
                    objectInputStream = new ObjectInputStream(socket.getInputStream());
                    serverResponse = objectInputStream.readObject().toString();
                    NetListener.getResponseFromServer(new JSONObject(serverResponse));
                    newCommand = false;
                }
            }
        } catch (SocketException ex){
            GeneralController.ConnectErrorMessage();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
        finally {
            GeneralController.disableComponents();
            disconnect();
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
