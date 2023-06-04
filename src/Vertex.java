import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Comparator;

public class Vertex {
    public String name;
    public int numberValue;
    public LinkedList<Vertex> connections;
    public Boolean isVisited;
    public LinkedList<Vertex> connectionReq;
    public Map<Vertex, String> inbox;

    public Vertex(String name) {
        this.name = name;
        this.connections = new LinkedList<Vertex>();
        this.connectionReq = new LinkedList<Vertex>();
        this.reset();
    }

    public void reset() {
        this.inbox = new HashMap<Vertex, String>();
        this.isVisited = false;
        this.numberValue = 999;
    }

    public void requestConnection(Vertex vertex) {
        this.connections.add(vertex);
        vertex.connectionReq.add(this);
    }

    public List<String> getConnections() {
        List<String> connectionsName = new ArrayList<String>();
        for (Vertex vertex : this.connections) {
            connectionsName.add(vertex.name);
        }
        return connectionsName;
    }

    public List<String> getRequestConnections() {
        List<String> connectionsName = new ArrayList<String>();
        for (Vertex vertex : this.connectionReq) {
            connectionsName.add(vertex.name);
        }
        return connectionsName;
    }

    public void acceptRequestConnection(Vertex v) {
        this.connectionReq.remove(v);
        this.connections.add(v);
    }

    public void sendMessage(Vertex v, String message) {
        v.inbox.put(this, message);
    }

    public String readMessage(Vertex v) {
        return this.inbox.get(v);
    }

    public Map<String, String> getAllInbox() {
        Map<String, String> inbox = new HashMap<String, String>();
        for (Vertex vertex : this.inbox.keySet()) {
            inbox.put(vertex.name, this.inbox.get(vertex));
        }
        return inbox;
    }

    public List<String> getFriendRecommendations() {
        Map<String, Integer> recConnections = new HashMap<String, Integer>();
        int maxDegree = 2;
        LinkedList<Vertex> queue = new LinkedList<Vertex>();
        queue.add(this);
        this.numberValue = 0;
        while (!queue.isEmpty()) {
            Vertex currentFirst = queue.removeFirst();
            if (currentFirst.numberValue > maxDegree)
                break;
            if (currentFirst.isVisited)
                continue;
            currentFirst.isVisited = true;
            LinkedList<Vertex> allConnections = currentFirst.connections;
            if (allConnections == null)
                continue;
            for (Vertex connection : allConnections) {
                if (!connection.isVisited) {
                    queue.add(connection);
                    if (currentFirst.numberValue + 1 < connection.numberValue)
                        connection.numberValue = currentFirst.numberValue + 1;
                }
                if (connection.numberValue <= maxDegree && !this.connections.contains(connection)) {
                    if (recConnections.containsKey(connection.name)) {
                        recConnections.put(connection.name, recConnections.get(connection.name) + 1);
                    } else {
                        recConnections.put(connection.name, 1);
                    }
                }
            }

        }
        Comparator<String> valueComparator = (k1, k2) -> recConnections.get(k1).compareTo(recConnections.get(k2));

        List<String> connectionList = new ArrayList<String>(recConnections.keySet());
        connectionList.remove(this.name);
        Collections.sort(connectionList, valueComparator);

        return connectionList;
    }

    public LinkedList<Vertex> findMutual(Vertex otherVertex) {
        LinkedList<Vertex> mutual = new LinkedList<Vertex>();
        for (Vertex vertex : this.connections) {
            if (vertex.connections.contains(otherVertex)) {
                mutual.add(vertex);
            }
        }
        return mutual;
    }

    public int findErdosNumber(Vertex otherVertex) {
        LinkedList<Vertex> queue = new LinkedList<Vertex>();
        queue.add(this);
        this.numberValue = 0;

        while (!queue.isEmpty()) {
            Vertex currentFirst = queue.removeFirst();

            if (currentFirst.isVisited)
                continue;

            // Mark the otherVertex as visited
            currentFirst.isVisited = true;
            System.out.print(currentFirst.name + " ");

            LinkedList<Vertex> allConnections = currentFirst.connections;

            if (allConnections == null)
                continue;

            for (Vertex connection : allConnections) {
                // We only add unvisited neighbors
                if (!connection.isVisited) {
                    if (currentFirst.numberValue + 1 < connection.numberValue) {
                        connection.numberValue = currentFirst.numberValue + 1;
                    }
                    queue.add(connection);
                }
            }
        }

        return otherVertex.numberValue;
    }
}
