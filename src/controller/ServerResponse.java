package controller;

import java.io.Serializable;

public class ServerResponse implements Serializable {
    int command;
    String data;

    public ServerResponse(int command, String data){
        this.command = command;
        this.data = data;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
