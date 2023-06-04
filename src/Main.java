import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
 /*       int numVertices = 5;
        Vertex[] vertices = new Vertex[numVertices];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vertex("Node "+i);
        }

        vertices[0].addConnection(vertices[1]);
        vertices[0].addConnection(vertices[2]);
        vertices[1].addConnection(vertices[3]);
        vertices[2].addConnection(vertices[3]);
        vertices[3].addConnection(vertices[4]);

        System.out.println(vertices[1].findErdosNumber(vertices[4]));
        resetAll(vertices);
        System.out.println(vertices[2].findErdosNumber(vertices[1]));

        for (int i = 0; i < numVertices; i++) {
            System.out.println(vertices[i].name+ " " +vertices[i].numberValue);
        }
        LinkedList<Vertex> mutual = vertices[0].findMutual(vertices[3]);
        for (Vertex vertex : mutual) {
            System.out.println(vertex.name);
        }
    }

    public static void resetAll(Vertex[] vertices) {
        for (Vertex vertex : vertices) {
            vertex.reset();
        } */
        
        System.out.println("Selamat datang di YazidBook");
        System.out.println("Tentukan aksi anda : login(l)/ register(r)");
        Scanner sc = new Scanner(System.in);
        String lr = sc.nextLine();
        if (lr =="l") {
		;} else if (lr =="r") {
		;} else System.out.println("Invalid input");
		
    }
}
