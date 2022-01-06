package Client;

import controller.NetController;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {
    private Socket socket = null;
    private boolean isConnected;
    private boolean newCommand;
    private OutputStreamWriter outputStreamWriter;
    private InputStreamReader inputStreamReader;

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
                //while (!newCommand)
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

                while (newCommand) {
                    outputStreamWriter.write(NetController.getJsonRequest().toString());

                    inputStreamReader = new InputStreamReader(socket.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line = null;
                    StringBuilder data = new StringBuilder();
                    while((line  = bufferedReader.readLine())!=null){
                        data.append(line);
                    }
                    NetController.getResponseFromServer(new JSONObject(data));
                    newCommand = false;
                }

            }
        } catch (IOException e) {
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
