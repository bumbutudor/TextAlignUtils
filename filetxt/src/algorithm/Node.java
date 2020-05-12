package algorithm;

import java.util.ArrayList;
import java.util.List;

public class Node {
    String value;
    List<Vertex> vertices = new ArrayList<Vertex>();
    boolean processed = false;

    public Node(String value) {
        this.value = value;
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    public void setProcessed() {
        processed = true;
    }

    public boolean isProcessed() {
        return processed;
    }
}
