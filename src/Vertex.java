import java.util.LinkedList;

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
        this.reset();
    }

    public void reset() {
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

    public void reset() {
        this.isVisited = false;
        this.numberValue = 999;
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

            LinkedList<Vertex> allConnections = currentFirst.connections;

            if (allConnections == null)
                continue;
            for (Vertex connection : allConnections) {
                // We only add unvisited neighbors
                if (!connection.isVisited) {
                    if (currentFirst.numberValue +1 < connection.numberValue) {
                        connection.numberValue = currentFirst.numberValue + 1;
                    }
                    queue.add(connection);
                }
            }
        }

        return otherVertex.numberValue;
    }
}
