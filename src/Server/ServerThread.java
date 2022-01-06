package Server;

import controller.NetController;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerThread implements Runnable {
    private Socket socket = null;
    private OutputStreamWriter outputStreamWriter;
    private InputStreamReader inputStreamReader;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (socket.isConnected()) {
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = null;
                StringBuilder data = new StringBuilder();
                while((line  = bufferedReader.readLine())!=null){
                    data.append(line);
                }
                NetController.getResponseFromServer(new JSONObject(data));

                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
                outputStreamWriter.write(NetController.getJsonResponse().toString());
            }
        } catch (IOException e) {

        }

    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}
