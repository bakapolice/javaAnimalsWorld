package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private boolean isStarted;
    private ServerSocket serverSocket = null;
    private ArrayList<ServerThread> serverThreads = new ArrayList<>();


    public void startServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        isStarted = true;
        while (isStarted){
            Socket socket = serverSocket.accept();
            ServerThread serverThread = new ServerThread(socket);
            serverThreads.add(serverThread);
            Thread thread = new Thread(serverThread);
            thread.start();
        }
    }

    public void stopServer() throws IOException {
        for(ServerThread serverThread : serverThreads)
            serverThread.getSocket().close();
        serverSocket.close();
        isStarted = false;
    }
}
