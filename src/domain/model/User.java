package domain.model;

import java.util.*;

public class User {
    public Integer id;
    public String name;
    public int numberValue;
    public LinkedList<User> connections;
    public boolean isVisited;
    public LinkedList<User> connectionReq;
    public Map<User, LinkedList<String>> inbox;

    public User(String name) {
        this.name = name;
        this.connections = new LinkedList<>();
        this.connectionReq = new LinkedList<>();
        this.inbox = new HashMap<>();
        this.reset();
    }

    public void reset() {
        this.isVisited = false;
        this.numberValue = 999;
    }
}
