package controller;

import java.io.Serializable;

public class ServerRequest implements Serializable {
    private int command, id;
    private String type, name;
    private Float weigh;

    public ServerRequest(int command, String type, String name, Float weigh) {
        this.command = command;
        this.type = type;
        this.name = name;
        this.weigh = weigh;
    }

    public ServerRequest(int command, int id) {
        this.command = command;
        this.id = id;
    }

    public ServerRequest(int command, String type, int id) {
        this.command = command;
        this.type = type;
        this.id = id;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getWeigh() {
        return weigh;
    }

    public void setWeigh(Float weigh) {
        this.weigh = weigh;
    }
}
